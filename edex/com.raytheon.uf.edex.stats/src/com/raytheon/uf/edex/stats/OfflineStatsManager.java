/**
 * This software was developed and / or modified by Raytheon Company,
 * pursuant to Contract DG133W-05-CQ-1067 with the US Government.
 * 
 * U.S. EXPORT CONTROLLED TECHNICAL DATA
 * This software product contains export-restricted data whose
 * export/transfer/disclosure is restricted by U.S. law. Dissemination
 * to non-U.S. persons whether in the United States or abroad requires
 * an export license or other authorization.
 * 
 * Contractor Name:        Raytheon Company
 * Contractor Address:     6825 Pine Street, Suite 340
 *                         Mail Stop B8
 *                         Omaha, NE 68106
 *                         402.291.0100
 * 
 * See the AWIPS II Master Rights File ("Master Rights File.pdf") for
 * further licensing information.
 **/
package com.raytheon.uf.edex.stats;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.TimeZone;

import com.google.common.collect.Multimap;
import com.raytheon.edex.util.Util;
import com.raytheon.uf.common.localization.exception.LocalizationException;
import com.raytheon.uf.common.stats.AggregateRecord;
import com.raytheon.uf.common.stats.StatisticsEvent;
import com.raytheon.uf.common.stats.StatsGrouping;
import com.raytheon.uf.common.stats.StatsGroupingColumn;
import com.raytheon.uf.common.stats.xml.StatisticsAggregate;
import com.raytheon.uf.common.stats.xml.StatisticsEventConfig;
import com.raytheon.uf.common.stats.xml.StatisticsGroup;
import com.raytheon.uf.common.status.IUFStatusHandler;
import com.raytheon.uf.common.status.UFStatus;
import com.raytheon.uf.common.status.UFStatus.Priority;
import com.raytheon.uf.common.time.TimeRange;
import com.raytheon.uf.common.time.util.TimeUtil;
import com.raytheon.uf.common.util.FileUtil;
import com.raytheon.uf.edex.stats.data.StatsDataAccumulator;

/**
 * Offlines data to csv format for long term comparison.
 * 
 * <pre>
 * 
 * SOFTWARE HISTORY
 * Date         Ticket#    Engineer    Description
 * ------------ ---------- ----------- --------------------------
 * Aug 21, 2012            jsanchez    Initial creation.
 * Nov 09, 2012            dhladky     Changed to CSV output
 * Jan 24, 2013 1357       mpduff      Fix comma output and paths.
 * May 22, 2013 1917       rjpeter     Renamed from Archiver, added generation of raw statistics,
 *                                     added method to purge statistics, moved saving of statistics
 *                                     to configured instead of site level.
 * Jun 02, 2014 2715       rferrel     Stats' directory location no longer uses localization.
 * Feb 18, 2015 4125       rjpeter     Create a StatsDataAccumulator.
 * </pre>
 * 
 * @author jsanchez
 * 
 */
public class OfflineStatsManager {

    /** Class for tracking a given epoch hour. */
    private class StatisticsKey {
        private final long epochHours;

        public StatisticsKey(Date time) {
            this.epochHours = time.getTime() / TimeUtil.MILLIS_PER_HOUR;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = (prime * result)
                    + (int) (epochHours ^ (epochHours >>> 32));
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            StatisticsKey other = (StatisticsKey) obj;
            if (!getOuterType().equals(other.getOuterType())) {
                return false;
            }
            if (epochHours != other.epochHours) {
                return false;
            }
            return true;
        }

        private OfflineStatsManager getOuterType() {
            return OfflineStatsManager.this;
        }
    }

    /** Property key defined in stats.properties. */
    private final static String STATIC_DIR_KEY = "stats.directory";

    /** Default value when directory when property not defined. */
    private final static String STATIC_DIR_DEFAULT = "/awips2/edex/data/stats";

    /** The comma separator. */
    private static final String COMMA = ",";

    /** Status handler logger. */
    private static final IUFStatusHandler statusHandler = UFStatus
            .getHandler(OfflineStatsManager.class);

    /** Date formatter for field of data. */
    private final SimpleDateFormat fieldSdf;

    /** Date formatter for creating date directories. */
    private final SimpleDateFormat directorySdf;

    /** Date formatter to use to place timestamp in csv file names. */
    private final SimpleDateFormat fileSdf;

    /** Formatter for generating the average data field. */
    private final DecimalFormat avgFormatter = new DecimalFormat("0.######");

    /** Stats' root directory. */
    private final File statsDir;

    /**
     * The Constructor.
     */
    public OfflineStatsManager() {
        TimeZone gmt = TimeZone.getTimeZone("GMT");
        fieldSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        fieldSdf.setTimeZone(gmt);
        directorySdf = new SimpleDateFormat("yyyyMMdd");
        directorySdf.setTimeZone(gmt);
        fileSdf = new SimpleDateFormat("yyyyMMddHH");
        fileSdf.setTimeZone(gmt);
        String statsPath = System.getProperty(STATIC_DIR_KEY);
        if ((statsPath == null)
                && statusHandler.isPriorityEnabled(Priority.ERROR)) {
            statusHandler
                    .handle(Priority.ERROR,
                            String.format(
                                    "%s not defined in stats.properties. Using default value: %s",
                                    STATIC_DIR_KEY, STATIC_DIR_DEFAULT));
            statsPath = STATIC_DIR_DEFAULT;
        }
        statsDir = new File(statsPath);
    }

    /**
     * Gets a directory file in the format stats/[rawStats|aggregates]/StatType
     * 
     * @param conf
     * @param isAggregate
     * @return baseDir
     */
    private File getBaseDir(StatisticsEventConfig conf, boolean isAggregate) {
        StringBuilder sb = new StringBuilder(80);
        if (isAggregate) {
            sb.append("aggregates");
        } else {
            sb.append("rawStats");
        }
        sb.append(File.separatorChar).append(conf.getTypeClass().getName());
        return new File(statsDir, sb.toString());
    }

    /**
     * Create a file in the format
     * stats/[rawStats|aggregates]/StatType/yyyyMMdd/StatType_yyyyMMddHH.csv
     * 
     * @param conf
     * @param isAggregate
     * @param epochHours
     * @return statFile
     */
    private File getStatFile(StatisticsEventConfig conf, boolean isAggregate,
            long epochHours) {
        File baseFile = getBaseDir(conf, isAggregate);
        StringBuilder sb = new StringBuilder(80);
        Date time = new Date(epochHours * TimeUtil.MILLIS_PER_HOUR);
        sb.append(File.separatorChar).append(directorySdf.format(time))
                .append(File.separatorChar)
                .append(conf.getTypeClass().getSimpleName()).append("_")
                .append(fileSdf.format(time)).append(".csv");
        return new File(baseFile, sb.toString());
    }

    /**
     * Writes a raw statistic in CSV format to the passed BufferedWriter.
     * 
     * @param bw
     * @param conf
     * @param grouping
     * @param event
     * @throws IOException
     */
    private void writeCSVOutput(BufferedWriter bw, StatisticsEventConfig conf,
            StatsGroupingColumn grouping, StatisticsEvent event)
            throws IOException {

        Calendar time = event.getDate();

        if (time != null) {
            bw.write(fieldSdf.format(time.getTime()));
        }

        for (StatsGrouping group : grouping.getGroup()) {
            bw.write(COMMA);
            bw.write(group.getValue());
        }

        for (Method m : conf.getAggregateMethods()) {
            try {
                bw.write(COMMA);
                Number number = (Number) m.invoke(event, new Object[0]);
                bw.write(number.toString());
            } catch (Exception e) {
                statusHandler.error(
                        "Unable to aggregate '" + m.getName() + "'", e);
            }
        }

        bw.newLine();
    }

    /**
     * Writes the aggregate statistic to the passed BufferedWriter.
     * 
     * @param bw
     * @param conf
     * @param agg
     * @param accumulator
     * @throws IOException
     */
    private void writeCSVOutput(BufferedWriter bw, StatisticsEventConfig conf,
            AggregateRecord agg, StatsDataAccumulator accumulator)
            throws IOException {

        Calendar startDate = agg.getStartDate();
        Calendar endDate = agg.getEndDate();
        double sum = agg.getSum();
        double count = agg.getCount();

        if (startDate != null) {
            bw.write(fieldSdf.format(startDate.getTime()));
        }
        bw.write(COMMA);

        if (endDate != null) {
            bw.write(fieldSdf.format(endDate.getTime()));
        }

        StatsGroupingColumn grouping = accumulator
                .unmarshalGroupingColumnFromRecord(agg);
        for (StatsGrouping group : grouping.getGroup()) {
            bw.write(COMMA);
            bw.write(group.getValue());
        }

        bw.write(COMMA);
        bw.write(agg.getField());
        bw.write(COMMA);

        if (count > 0) {
            bw.write(avgFormatter.format(sum / count));
        } else {
            bw.write("0");
        }

        bw.write(COMMA);
        bw.write(String.valueOf(agg.getMin()));
        bw.write(COMMA);
        bw.write(String.valueOf(agg.getMax()));
        bw.write(COMMA);
        bw.write(String.valueOf(sum));
        bw.write(COMMA);
        bw.write(String.valueOf(count));
        bw.newLine();
    }

    /**
     * Opens a buffered writer for the given StatisticsKey and
     * StatisticsEventConfig. If its a new CSV file a header is also added to
     * the file.
     * 
     * @param key
     * @param conf
     * @return bw
     * @throws IOException
     */
    private BufferedWriter getStatEventBufferedWriter(StatisticsKey key,
            StatisticsEventConfig conf) throws IOException {
        BufferedWriter bw = null;
        File outFile = getStatFile(conf, false, key.epochHours);
        boolean addHeader = outFile.length() == 0;

        if (addHeader) {
            // pre-create directories if necessary
            outFile.getParentFile().mkdirs();
        }

        bw = new BufferedWriter(new FileWriter(outFile, true));

        if (addHeader) {
            bw.write("Time");
            for (StatisticsGroup group : conf.getGroupList()) {
                bw.write(COMMA);
                bw.write(group.getDisplayName());
            }
            for (StatisticsAggregate aggr : conf.getAggregateList()) {
                bw.write(COMMA);
                bw.write(aggr.getDisplayName());
            }
            bw.newLine();
        }

        return bw;
    }

    /**
     * Opens a buffered writer for the given StatisticsKey and
     * StatisticsEventConfig. If its a new CSV file a header is also added to
     * the file.
     * 
     * @param key
     * @param conf
     * @return bw
     * @throws IOException
     */
    private BufferedWriter getAggregateBufferedWriter(StatisticsKey key,
            StatisticsEventConfig conf) throws IOException {
        BufferedWriter bw = null;
        File outFile = getStatFile(conf, true, key.epochHours);
        boolean addHeader = outFile.length() == 0;

        if (addHeader) {
            // pre-create directories if necessary
            outFile.getParentFile().mkdirs();
        }

        bw = new BufferedWriter(new FileWriter(outFile, true));

        if (addHeader) {
            bw.write("Start,End,");
            for (StatisticsGroup group : conf.getGroupList()) {
                bw.write(group.getDisplayName());
                bw.write(COMMA);
            }
            bw.write("Field,Avg,Min,Max,Sum,Count");
            bw.newLine();
        }

        return bw;
    }

    /**
     * Writes the raw statistics to disk in CSV format.
     * 
     * @param conf
     * @param timeMap
     */
    public void writeStatsToDisk(
            StatisticsEventConfig conf,
            Map<TimeRange, Multimap<StatsGroupingColumn, StatisticsEvent>> timeMap) {
        if (!timeMap.isEmpty()) {
            String outfilePath = null;
            BufferedWriter bw = null;

            try {
                for (Multimap<StatsGroupingColumn, StatisticsEvent> groupedEvents : timeMap
                        .values()) {
                    for (StatsGroupingColumn group : groupedEvents.keySet()) {
                        Iterator<StatisticsEvent> iter = groupedEvents.get(
                                group).iterator();
                        StatisticsKey prevKey = null;

                        while (iter.hasNext()) {
                            StatisticsEvent event = iter.next();
                            StatisticsKey curKey = new StatisticsKey(event
                                    .getDate().getTime());

                            if (!curKey.equals(prevKey)) {
                                Util.close(bw);
                                bw = getStatEventBufferedWriter(curKey, conf);
                            }

                            writeCSVOutput(bw, conf, group, event);
                        }
                    }
                }
            } catch (IOException e) {
                statusHandler.handle(Priority.ERROR, "Failed to write File: "
                        + outfilePath, e);
            } finally {
                Util.close(bw);
            }
        }
    }

    /**
     * Writes the aggregate records to disk in CSV format.
     * 
     * @param conf
     *            The StatisticsEventConfig the aggregates belong to
     * @param aggregateRecords
     *            The aggregate records
     */
    public void writeAggregatesToDisk(StatisticsEventConfig conf,
            Collection<AggregateRecord> aggregateRecords) {
        if (!aggregateRecords.isEmpty()) {

            String outfilePath = null;
            BufferedWriter bw = null;

            try {

                Iterator<AggregateRecord> iter = aggregateRecords.iterator();
                StatisticsKey prevKey = null;
                StatsDataAccumulator accumulator = new StatsDataAccumulator();

                while (iter.hasNext()) {
                    AggregateRecord agg = iter.next();
                    StatisticsKey curKey = new StatisticsKey(agg.getStartDate()
                            .getTime());

                    if (!curKey.equals(prevKey)) {
                        Util.close(bw);
                        bw = getAggregateBufferedWriter(curKey, conf);
                    }

                    writeCSVOutput(bw, conf, agg, accumulator);
                }
            } catch (IOException e) {
                statusHandler.handle(Priority.ERROR, "Failed to write File: "
                        + outfilePath, e);
            } finally {
                Util.close(bw);
            }
        }
    }

    /**
     * Returns the most recent offlined date for the given
     * StatisticsEventConfig.
     * 
     * @param conf
     * @return date
     * @throws LocalizationException
     * @throws IOException
     */
    public Date getMostRecentOfflinedAggregate(StatisticsEventConfig conf)
            throws LocalizationException, IOException {
        Date rval = null;

        File eventDir = getBaseDir(conf, true);

        if (eventDir.exists() && eventDir.isDirectory()) {
            File latestDir = null;
            File[] eventDirFiles = eventDir.listFiles();
            if (eventDirFiles != null) {
                for (File handle : eventDirFiles) {
                    if (handle.isDirectory()) {
                        try {
                            Date handleDate = directorySdf.parse(handle
                                    .getName());

                            if ((rval == null) || rval.before(handleDate)) {
                                rval = handleDate;
                                latestDir = handle;
                            }
                        } catch (ParseException e) {
                            statusHandler.handle(Priority.WARN, "Directory ["
                                    + handle.getAbsolutePath()
                                    + "] is not in expected date format ["
                                    + directorySdf.toPattern() + "]");
                        }
                    }
                }
            } else {
                statusHandler.error("Unable to list files from directory "
                        + eventDir.getAbsolutePath());
            }

            // found latest directory date
            if (latestDir != null) {
                for (File csv : latestDir.listFiles()) {
                    String name = csv.getName();
                    if (csv.isFile() && name.endsWith(".csv")) {
                        // StatType_yyyyMMddHH.csv
                        int index = name.indexOf('_');
                        if (index >= 0) {
                            try {
                                Date handleDate = fileSdf.parse(name.substring(
                                        index + 1, index + 11));
                                if ((rval == null) || rval.before(handleDate)) {
                                    rval = handleDate;
                                }
                            } catch (ParseException e) {
                                statusHandler.handle(Priority.WARN, "File ["
                                        + csv.getAbsolutePath()
                                        + "] is not in expected date format ["
                                        + fileSdf.toPattern() + "]");
                            }
                        }
                    }
                }
            }
        }

        return rval;
    }

    /**
     * Handle retention day rules, -1 keep nothing, 0 keep everything, any
     * positive number keep that many full days.
     * 
     * @param retentionDays
     * @return
     */
    private long getMinTime(int retentionDays) {
        long currentDay = System.currentTimeMillis() / TimeUtil.MILLIS_PER_DAY;

        if (retentionDays == 0) {
            return 0;
        } else if (retentionDays < 0) {
            return currentDay * TimeUtil.MILLIS_PER_DAY;
        } else {
            // add 1 day to not include current day
            return (currentDay - (retentionDays + 1)) * TimeUtil.MILLIS_PER_DAY;
        }
    }

    /**
     * Purges offline statistics directories for the given
     * StatisticsEventConfig.
     * 
     * @param conf
     */
    public void purgeOffline(StatisticsEventConfig conf) {
        // purge aggregates
        long minTime = getMinTime(conf.getAggregateOfflineRetentionDays());

        if (minTime > 0) {
            purgeDir(getBaseDir(conf, true), minTime);
        }

        // purge raw
        minTime = getMinTime(conf.getRawOfflineRetentionDays());

        if (minTime > 0) {
            purgeDir(getBaseDir(conf, false), minTime);
        }
    }

    /**
     * Purges a given stat event dir keeping any directories newer than minTime.
     * 
     * @param dir
     * @param minTime
     */
    private void purgeDir(File eventDir, long minTime) {

        if (eventDir.exists() && eventDir.isDirectory()) {
            try {
                File[] eventDirFiles = eventDir.listFiles();
                if (eventDirFiles != null) {
                    for (File handle : eventDirFiles) {
                        if (handle.isDirectory()) {
                            try {
                                Date handleDate = directorySdf.parse(handle
                                        .getName());

                                if (handleDate.getTime() <= minTime) {
                                    FileUtil.deleteDir(handle);
                                }
                            } catch (ParseException e) {
                                statusHandler.warn("Directory ["
                                        + handle.getAbsolutePath()
                                        + "] is not in expected date format ["
                                        + directorySdf.toPattern() + "]");
                            }
                        }
                    }
                } else {
                    statusHandler.error("Unable to list files from directory "
                            + eventDir.getAbsolutePath());
                }
            } catch (Exception e) {
                statusHandler.error(
                        "Error occurred purging " + eventDir.getAbsolutePath(),
                        e);
            }
        }
    }
}
