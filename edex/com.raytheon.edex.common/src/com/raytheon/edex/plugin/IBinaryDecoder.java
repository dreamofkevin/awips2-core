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
package com.raytheon.edex.plugin;

import com.raytheon.edex.exception.DecoderException;
import com.raytheon.uf.common.dataplugin.PluginDataObject;

/**
 * Describes a decoder that takes a byte array as input
 * 
 * @deprecated Does not provide useful functionality.  Camel enables
 * a variety of potential method signatures.
 * 
 * <pre>
 * SOFTWARE HISTORY
 * Date         Ticket#    Engineer    Description
 * ------------ ---------- ----------- --------------------------
 * Nov 11, 2008            chammack    Initial creation
 * Jun 25, 2015 4495       njensen     Deprecated
 * </pre>
 * 
 * @author chammack
 * @version 1.0
 */

@Deprecated
public interface IBinaryDecoder {

    /**
     * Implements a decoder that takes a byte array as input and returns zero or
     * more plugin data objects.
     * 
     * Null is not a valid return type
     * 
     * @param data
     * @return the plugindataobjects
     */
    public abstract PluginDataObject[] decode(byte[] data)
            throws DecoderException;
}
