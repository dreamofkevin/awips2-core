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
package com.raytheon.uf.common.logback;

import ch.qos.logback.core.status.OnConsoleStatusListener;
import ch.qos.logback.core.status.Status;

/**
 * Status listener for Logback's internal status messages. Shows warnings and
 * errors from logback itself to the console.
 * 
 * <pre>
 * 
 * SOFTWARE HISTORY
 * 
 * Date         Ticket#    Engineer    Description
 * ------------ ---------- ----------- --------------------------
 * Jun 27, 2013 2142       njensen     Initial creation
 * Jun 09, 2015 4473       njensen     Moved from status to logback plugin
 * 
 * </pre>
 * 
 * @author njensen
 * @version 1.0
 */

public class UFLogbackInternalStatusListener extends OnConsoleStatusListener {

    @Override
    public void addStatusEvent(Status status) {
        if (status.getLevel() >= Status.WARN) {
            super.addStatusEvent(status);
        }
    }

}
