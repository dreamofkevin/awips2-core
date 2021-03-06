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
package com.raytheon.edex.site;

import com.raytheon.uf.edex.core.EDEXUtil;

/**
 * Utility for site and station information
 * 
 * <pre>
 * 
 * SOFTWARE HISTORY
 * Date         Ticket#    Engineer    Description
 * ------------ ---------- ----------- --------------------------
 * Mar 18, 2009            njensen     Initial creation
 * Jul 10, 2014 2914       garmendariz Remove EnvProperties
 * 
 * </pre>
 * 
 * @author njensen
 * @version 1.0
 */

public class SiteUtil {
    /**
     * Gets the site name configured in environment.xml
     * 
     * @return The current site name
     */
    public static String getSite() {
        return EDEXUtil.getEdexSite();
    }
}
