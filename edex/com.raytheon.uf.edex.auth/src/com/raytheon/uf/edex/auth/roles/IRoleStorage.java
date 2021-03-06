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
package com.raytheon.uf.edex.auth.roles;

import com.raytheon.uf.common.auth.exception.AuthorizationException;
import com.raytheon.uf.edex.auth.authorization.IAuthorizer;

/**
 * Storage class for roles. Should have a concept of a default role which all
 * users get by default and the ability to lookup a role given an id. NOTE, ALL
 * ROLES IDS SHOULD BE TREATED AS CASE-INSENSITIVE
 * 
 * <pre>
 * 
 * SOFTWARE HISTORY
 * Date         Ticket#    Engineer    Description
 * ------------ ---------- ----------- --------------------------
 * May 18, 2010            mschenke     Initial creation
 * May 28, 2014 3211       njensen      Extracted isAuthorized() to IAuthorizer
 * 
 * </pre>
 * 
 * @author mschenke
 * @version 1.0
 */

public interface IRoleStorage extends IAuthorizer {

    /**
     * Get all the defined permissions for this application.
     * 
     * @param application
     *            The application
     * 
     * @return String[] of permissions
     * @throws AuthorizationException
     */
    public String[] getAllDefinedPermissions(String application)
            throws AuthorizationException;

}
