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
package com.raytheon.uf.viz.core.status;

import java.io.File;
import java.io.IOException;
import java.util.MissingResourceException;

import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import com.raytheon.uf.common.localization.IPathManager;
import com.raytheon.uf.common.localization.LocalizationContext;
import com.raytheon.uf.common.localization.LocalizationContext.LocalizationLevel;
import com.raytheon.uf.common.localization.LocalizationContext.LocalizationType;
import com.raytheon.uf.common.localization.LocalizationFile;
import com.raytheon.uf.common.localization.PathManagerFactory;
import com.raytheon.uf.common.status.AbstractHandlerFactory;
import com.raytheon.uf.common.status.FilterPatternContainer;
import com.raytheon.uf.common.status.IUFStatusHandler;
import com.raytheon.uf.common.status.StatusHandler;
import com.raytheon.uf.common.status.UFStatus.Priority;
import com.raytheon.uf.common.status.slf4j.Slf4JBridge;
import com.raytheon.uf.common.status.slf4j.UFMarkers;

/**
 * Viz Status Handler Factory
 * 
 * <pre>
 * 
 * SOFTWARE HISTORY
 * Date         Ticket#    Engineer    Description
 * ------------ ---------- ----------- --------------------------
 * Aug 25, 2010            rjpeter     Initial creation
 * Oct 23, 2013 2303       bgonzale    Merged VizStatusHandler and SysErrStatusHandler into StatusHandler.
 *                                     Implemented log method from base class.
 * May 22, 2015 4473       njensen     Send to SLF4J instead of Eclipse                                    
 * 
 * </pre>
 * 
 * @author rjpeter
 * @version 1.0
 */

public class VizStatusHandlerFactory extends AbstractHandlerFactory {

    private static final String CATEGORY = "WORKSTATION";

    private transient static final Logger logger = LoggerFactory
            .getLogger("CaveLogger");

    static {
        /*
         * Disables the packaging data feature of logback (ie how the
         * stacktraces list the jar the class is in). Due to the viz dependency
         * tree, in some scenarios the determination of the packaging data can
         * spend an inordinate amount of time in the OSGi classloader trying to
         * find classes. If the viz dependency tree is cleaned up (ie
         * modularized, unnecessary imports removed, register buddies reduced)
         * then this may be able to be re-enabled without a performance hit.
         * 
         * Unfortunately there is no way to do this other than casting to a
         * logback Logger, see http://jira.qos.ch/browse/LOGBACK-730 and
         * http://jira.qos.ch/browse/LOGBACK-899
         * 
         * TODO This is fixed in logback 1.1.4. Once that is upgraded, we should
         * remove this and disable packaging data through the xml file. Please
         * place the first paragraph about why we want to disiable packaging
         * data in the xml as a comment at that time.
         */
        try {
            ((ch.qos.logback.classic.Logger) logger).getLoggerContext()
                    .setPackagingDataEnabled(false);
        } catch (Throwable t) {
            /*
             * given that this static block is for initializing the logger
             * correctly, if that went wrong let's not even try to "log" it,
             * just use stderr
             */
            System.err.println("Error disabling logback packaging data");
            t.printStackTrace();
        }

        /*
         * TODO logback 1.1.3 adds a configuration tag to the XML for shutdown
         * hooks, we should use that instead
         */
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                ((ch.qos.logback.classic.Logger) logger)
                        .detachAndStopAllAppenders();
            }
        });
    }

    private static final StatusHandler instance = new StatusHandler(
            StatusHandler.class.getPackage().getName(), CATEGORY, CATEGORY);

    public VizStatusHandlerFactory() {
        super(CATEGORY);
        PathManagerFactory.addObserver(this);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.raytheon.uf.common.status.IUFStatusHandlerFactory#getInstance()
     */
    @Override
    public IUFStatusHandler getInstance() {
        return instance;
    }

    @Override
    public IUFStatusHandler createInstance(String pluginId, String category,
            String source) {
        return new StatusHandler(pluginId, category, source);
    }

    @Override
    public IUFStatusHandler createInstance(AbstractHandlerFactory factory,
            String pluginId, String category) {
        return new StatusHandler(pluginId, category, getSource(null, pluginId));
    }

    @Override
    public IUFStatusHandler createMonitorInstance(String pluginId,
            String monitorSource) {
        return new VizMonitorHandler(pluginId, monitorSource);
    }

    @Override
    protected FilterPatternContainer createSourceContainer() {
        IPathManager pm = PathManagerFactory.getPathManager();
        LocalizationContext ctx = pm.getContext(LocalizationType.COMMON_STATIC,
                LocalizationLevel.BASE);

        LocalizationFile locFile = pm.getLocalizationFile(ctx,
                "configuredHandlers.xml");
        if (locFile == null) {
            throw new MissingResourceException(
                    "Unable to retrieve the localization file",
                    VizStatusHandlerFactory.class.getName(),
                    LocalizationType.COMMON_STATIC.name() + File.separator
                            + LocalizationLevel.BASE.name() + File.separator
                            + "configuredHandlers.xml");
        }
        try {
            File file = locFile.getFile();
            return new FilterPatternContainer(file);
        } catch (JAXBException e) {
            instance.handle(Priority.CRITICAL, e.getLocalizedMessage(), e);
        } catch (IOException e) {
            instance.handle(Priority.CRITICAL, e.getLocalizedMessage(), e);
        }
        return FilterPatternContainer.createDefault();
    }

    @Override
    protected void log(Priority priority, String pluginId, String category,
            String source, String message, Throwable throwable) {
        // detached ensures we will have a new instance
        Marker m = MarkerFactory.getDetachedMarker("viz");
        if (pluginId != null) {
            m.add(UFMarkers.getPluginMarker(pluginId));
        }

        if (category != null) {
            m.add(UFMarkers.getCategoryMarker(category));
        }
        if (source != null) {
            m.add(UFMarkers.getSourceMarker(source));
        }
        m.add(UFMarkers.getUFPriorityMarker(priority));

        Slf4JBridge.logToSLF4J(logger, priority, m, message, throwable);
    }

}
