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
package com.raytheon.uf.viz.core.drawables;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.Pattern;
import org.eclipse.swt.graphics.RGB;

/**
 * Utility class for retrieving common fill patters for swt/core graphics
 * 
 * <pre>
 * 
 * SOFTWARE HISTORY
 * 
 * Date         Ticket#    Engineer    Description
 * ------------ ---------- ----------- --------------------------
 * Apr 15, 2011            mschenke     Initial creation
 * 
 * </pre>
 * 
 * @author mschenke
 * @version 1.0
 */

public class FillPatterns {
    private static Map<String, byte[]> patternMap = new HashMap<String, byte[]>();
    static {
        patternMap.put("WIDE", new byte[] { //
                0x40, 0x08, 0x40, 0x08, //
                        0x00, 0x01, 0x00, 0x01, //
                        0x12, 0x40, 0x12, 0x40, //
                        0x41, 0x04, 0x41, 0x04, //
                        0x00, (byte) 0x90, 0x00, (byte) 0x90, //
                        0x08, 0x01, 0x08, 0x01, //
                        0x40, 0x00, 0x40, 0x00, //
                        0x02, 0x44, 0x02, 0x44, //
                        0x10, 0x01, 0x10, 0x01, //
                        0x01, 0x10, 0x01, 0x10, //
                        0x04, 0x00, 0x04, 0x00, //
                        0x40, (byte) 0x82, 0x40, (byte) 0x82, //
                        0x11, 0x10, 0x11, 0x10, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x04, 0x44, 0x04, 0x44, //
                        0x40, 0x00, 0x40, 0x00, //

                        0x40, 0x08, 0x40, 0x08, //
                        0x00, 0x01, 0x00, 0x01, //
                        0x12, 0x40, 0x12, 0x40, //
                        0x41, 0x04, 0x41, 0x04, //
                        0x00, (byte) 0x90, 0x00, (byte) 0x90, //
                        0x08, 0x01, 0x08, 0x01, //
                        0x40, 0x00, 0x40, 0x00, //
                        0x02, 0x44, 0x02, 0x44, //
                        0x10, 0x01, 0x10, 0x01, //
                        0x01, 0x10, 0x01, 0x10, //
                        0x04, 0x00, 0x04, 0x00, //
                        0x40, (byte) 0x82, 0x40, (byte) 0x82, //
                        0x11, 0x10, 0x11, 0x10, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x04, 0x44, 0x04, 0x44, //
                        0x40, 0x00, 0x40, 0x00, //
                });

        patternMap.put("SCATTERED", new byte[] { //
                0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        (byte) 0x80, 0x00, (byte) 0x80, 0x00, //
                        (byte) 0xc0, 0x01, (byte) 0xc0, 0x01, //
                        (byte) 0x80, 0x00, (byte) 0x80, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x10, 0x08, 0x10, 0x08, //
                        0x38, 0x1c, 0x38, 0x1c, //
                        0x10, 0x08, 0x10, 0x08, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //

                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        (byte) 0x80, 0x00, (byte) 0x80, 0x00, //
                        (byte) 0xc0, 0x01, (byte) 0xc0, 0x01, //
                        (byte) 0x80, 0x00, (byte) 0x80, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x10, 0x08, 0x10, 0x08, //
                        0x38, 0x1c, 0x38, 0x1c, //
                        0x10, 0x08, 0x10, 0x08, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                });

        patternMap.put("WIDE_SCATTERED", new byte[] { //
                0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x08, 0x00, 0x08, 0x00, //
                        0x1c, 0x00, 0x1c, 0x00, //
                        0x08, 0x00, 0x08, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x10, 0x00, 0x10, //
                        0x00, 0x38, 0x00, 0x38, //
                        0x00, 0x10, 0x00, 0x10, //
                        0x00, 0x00, 0x00, 0x00, //

                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x08, 0x00, 0x08, 0x00, //
                        0x1c, 0x00, 0x1c, 0x00, //
                        0x08, 0x00, 0x08, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x10, 0x00, 0x10, //
                        0x00, 0x38, 0x00, 0x38, //
                        0x00, 0x10, 0x00, 0x10, //
                        0x00, 0x00, 0x00, 0x00, //
                });

        patternMap.put("ISOLATED", new byte[] { //
                0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        (byte) 0x80, 0x01, (byte) 0x80, 0x01, //
                        (byte) 0xc0, 0x03, (byte) 0xc0, 0x03, //
                        (byte) 0xc0, 0x03, (byte) 0xc0, 0x03, //
                        (byte) 0x80, 0x01, (byte) 0x80, 0x01, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //

                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        (byte) 0x80, 0x01, (byte) 0x80, 0x01, //
                        (byte) 0xc0, 0x03, (byte) 0xc0, 0x03, //
                        (byte) 0xc0, 0x03, (byte) 0xc0, 0x03, //
                        (byte) 0x80, 0x01, (byte) 0x80, 0x01, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                });

        patternMap.put("TRANS_25PC_135DEG", new byte[] { //
                0x30, 0x30, 0x30, 0x30, //
                        0x18, 0x18, 0x18, 0x18, //
                        0x0c, 0x0c, 0x0c, 0x0c, //
                        0x06, 0x06, 0x06, 0x06, //
                        0x03, 0x03, 0x03, 0x03, //
                        (byte) 0x81, (byte) 0x81, (byte) 0x81, (byte) 0x81, //
                        (byte) 0xc0, (byte) 0xc0, (byte) 0xc0, (byte) 0xc0, //
                        0x60, 0x60, 0x60, 0x60, //
                        0x30, 0x30, 0x30, 0x30, //
                        0x18, 0x18, 0x18, 0x18, //
                        0x0c, 0x0c, 0x0c, 0x0c, //
                        0x06, 0x06, 0x06, 0x06, //
                        0x03, 0x03, 0x03, 0x03, //
                        (byte) 0x81, (byte) 0x81, (byte) 0x81, (byte) 0x81, //
                        (byte) 0xc0, (byte) 0xc0, (byte) 0xc0, (byte) 0xc0, //
                        0x60, 0x60, 0x60, 0x60, //
                        0x30, 0x30, 0x30, 0x30, //
                        0x18, 0x18, 0x18, 0x18, //
                        0x0c, 0x0c, 0x0c, 0x0c, //
                        0x06, 0x06, 0x06, 0x06, //
                        0x03, 0x03, 0x03, 0x03, //
                        (byte) 0x81, (byte) 0x81, (byte) 0x81, (byte) 0x81, //
                        (byte) 0xc0, (byte) 0xc0, (byte) 0xc0, (byte) 0xc0, //
                        0x60, 0x60, 0x60, 0x60, //
                        0x30, 0x30, 0x30, 0x30, //
                        0x18, 0x18, 0x18, 0x18, //
                        0x0c, 0x0c, 0x0c, 0x0c, //
                        0x06, 0x06, 0x06, 0x06, //
                        0x03, 0x03, 0x03, 0x03, //
                        (byte) 0x81, (byte) 0x81, (byte) 0x81, (byte) 0x81, //
                        (byte) 0xc0, (byte) 0xc0, (byte) 0xc0, (byte) 0xc0, //
                        0x60, 0x60, 0x60, 0x60, //
                });

        patternMap.put("OCNL", new byte[] { //
                0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x04, 0x00, 0x04, //
                        0x00, 0x0e, 0x00, 0x0e, //
                        0x00, 0x04, 0x00, 0x04, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x04, 0x00, 0x04, //
                        0x00, 0x0e, 0x00, 0x0e, //
                        0x00, 0x04, 0x00, 0x04, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                });

        patternMap.put("LKLY", new byte[] { //
                0x00, 0x10, 0x00, 0x10, //
                        0x00, 0x38, 0x00, 0x38, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x44, 0x00, 0x44, //
                        0x00, (byte) 0xc6, 0x00, (byte) 0xc6, //
                        0x00, 0x44, 0x00, 0x44, //
                        0x08, 0x00, 0x08, 0x00, //
                        0x1c, 0x38, 0x1c, 0x38, //
                        0x00, 0x10, 0x00, 0x10, //
                        0x22, 0x00, 0x22, 0x00, //
                        0x63, 0x00, 0x63, 0x00, //
                        0x22, 0x00, 0x22, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x1c, 0x00, 0x1c, 0x00, //
                        0x08, 0x00, 0x08, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //

                        0x00, 0x10, 0x00, 0x10, //
                        0x00, 0x38, 0x00, 0x38, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x44, 0x00, 0x44, //
                        0x00, (byte) 0xc6, 0x00, (byte) 0xc6, //
                        0x00, 0x44, 0x00, 0x44, //
                        0x08, 0x00, 0x08, 0x00, //
                        0x1c, 0x38, 0x1c, 0x38, //
                        0x00, 0x10, 0x00, 0x10, //
                        0x22, 0x00, 0x22, 0x00, //
                        0x63, 0x00, 0x63, 0x00, //
                        0x22, 0x00, 0x22, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x1c, 0x00, 0x1c, 0x00, //
                        0x08, 0x00, 0x08, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                });

        patternMap.put("TRANS_25PC_45DEG", new byte[] { //
                0x30, 0x30, 0x30, 0x30, //
                        0x60, 0x60, 0x60, 0x60, //
                        (byte) 0xc0, (byte) 0xc0, (byte) 0xc0, (byte) 0xc0, //
                        (byte) 0x81, (byte) 0x81, (byte) 0x81, (byte) 0x81, //
                        0x03, 0x03, 0x03, 0x03, //
                        0x06, 0x06, 0x06, 0x06, //
                        0x0c, 0x0c, 0x0c, 0x0c, //
                        0x18, 0x18, 0x18, 0x18, //
                        0x30, 0x30, 0x30, 0x30, //
                        0x60, 0x60, 0x60, 0x60, //
                        (byte) 0xc0, (byte) 0xc0, (byte) 0xc0, (byte) 0xc0, //
                        (byte) 0x81, (byte) 0x81, (byte) 0x81, (byte) 0x81, //
                        0x03, 0x03, 0x03, 0x03, //
                        0x06, 0x06, 0x06, 0x06, //
                        0x0c, 0x0c, 0x0c, 0x0c, //
                        0x18, 0x18, 0x18, 0x18, //
                        0x30, 0x30, 0x30, 0x30, //
                        0x60, 0x60, 0x60, 0x60, //
                        (byte) 0xc0, (byte) 0xc0, (byte) 0xc0, (byte) 0xc0, //
                        (byte) 0x81, (byte) 0x81, (byte) 0x81, (byte) 0x81, //
                        0x03, 0x03, 0x03, 0x03, //
                        0x06, 0x06, 0x06, 0x06, //
                        0x0c, 0x0c, 0x0c, 0x0c, //
                        0x18, 0x18, 0x18, 0x18, //
                        0x30, 0x30, 0x30, 0x30, //
                        0x60, 0x60, 0x60, 0x60, //
                        (byte) 0xc0, (byte) 0xc0, (byte) 0xc0, (byte) 0xc0, //
                        (byte) 0x81, (byte) 0x81, (byte) 0x81, (byte) 0x81, //
                        0x03, 0x03, 0x03, 0x03, //
                        0x06, 0x06, 0x06, 0x06, //
                        0x0c, 0x0c, 0x0c, 0x0c, //
                        0x18, 0x18, 0x18, 0x18, //
                });

        patternMap.put("SELECTED_AREA", new byte[] { //
                0x04, 0x04, 0x04, 0x04, //
                        0x02, 0x02, 0x02, 0x02, //
                        0x01, 0x01, 0x01, 0x01, //
                        (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, //
                        0x40, 0x40, 0x40, 0x40, //
                        0x20, 0x20, 0x20, 0x20, //
                        0x10, 0x10, 0x10, 0x10, //
                        0x08, 0x08, 0x08, 0x08, //
                        0x04, 0x04, 0x04, 0x04, //
                        0x02, 0x02, 0x02, 0x02, //
                        0x01, 0x01, 0x01, 0x01, //
                        (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, //
                        0x40, 0x40, 0x40, 0x40, //
                        0x20, 0x20, 0x20, 0x20, //
                        0x10, 0x10, 0x10, 0x10, //
                        0x08, 0x08, 0x08, 0x08, //
                        0x04, 0x04, 0x04, 0x04, //
                        0x02, 0x02, 0x02, 0x02, //
                        0x01, 0x01, 0x01, 0x01, //
                        (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, //
                        0x40, 0x40, 0x40, 0x40, //
                        0x20, 0x20, 0x20, 0x20, //
                        0x10, 0x10, 0x10, 0x10, //
                        0x08, 0x08, 0x08, 0x08, //
                        0x04, 0x04, 0x04, 0x04, //
                        0x02, 0x02, 0x02, 0x02, //
                        0x01, 0x01, 0x01, 0x01, //
                        (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, //
                        0x40, 0x40, 0x40, 0x40, //
                        0x20, 0x20, 0x20, 0x20, //
                        0x10, 0x10, 0x10, 0x10, //
                        0x08, 0x08, 0x08, 0x08, //
                });

        patternMap.put("DUALCURVE", new byte[] { //
                (byte) 0xc0, 0x01, (byte) 0xc0, 0x01, //
                        0x70, (byte) 0x83, 0x70, (byte) 0x83, //
                        0x11, (byte) 0x86, 0x11, (byte) 0x86, //
                        0x1b, (byte) 0xcc, 0x1b, (byte) 0xcc, //
                        0x0e, 0x78, 0x0e, 0x78, //
                        0x06, 0x30, 0x06, 0x30, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        (byte) 0xc0, 0x01, (byte) 0xc0, 0x01, //
                        0x70, (byte) 0x83, 0x70, (byte) 0x83, //
                        0x11, (byte) 0x86, 0x11, (byte) 0x86, //
                        0x1b, (byte) 0xcc, 0x1b, (byte) 0xcc, //
                        0x0e, 0x78, 0x0e, 0x78, //
                        0x06, 0x30, 0x06, 0x30, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        (byte) 0xc0, 0x01, (byte) 0xc0, 0x01, //
                        0x70, (byte) 0x83, 0x70, (byte) 0x83, //
                        0x11, (byte) 0x86, 0x11, (byte) 0x86, //
                        0x1b, (byte) 0xcc, 0x1b, (byte) 0xcc, //
                        0x0e, 0x78, 0x0e, 0x78, //
                        0x06, 0x30, 0x06, 0x30, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        (byte) 0xc0, 0x01, (byte) 0xc0, 0x01, //
                        0x70, (byte) 0x83, 0x70, (byte) 0x83, //
                        0x11, (byte) 0x86, 0x11, (byte) 0x86, //
                        0x1b, (byte) 0xcc, 0x1b, (byte) 0xcc, //
                        0x0e, 0x78, 0x0e, 0x78, //
                        0x06, 0x30, 0x06, 0x30, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                });

        patternMap.put("CURVE", new byte[] { //
                0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        (byte) 0xc0, 0x01, (byte) 0xc0, 0x01, //
                        0x70, (byte) 0x83, 0x70, (byte) 0x83, //
                        0x11, (byte) 0x86, 0x11, (byte) 0x86, //
                        0x1b, (byte) 0xcc, 0x1b, (byte) 0xcc, //
                        0x0e, 0x78, 0x0e, 0x78, //
                        0x06, 0x30, 0x06, 0x30, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        (byte) 0xc0, 0x01, (byte) 0xc0, 0x01, //
                        0x70, (byte) 0x83, 0x70, (byte) 0x83, //
                        0x11, (byte) 0x86, 0x11, (byte) 0x86, //
                        0x1b, (byte) 0xcc, 0x1b, (byte) 0xcc, //
                        0x0e, 0x78, 0x0e, 0x78, //
                        0x06, 0x30, 0x06, 0x30, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                });

        patternMap.put("VERTICAL", new byte[] { //
                0x11, 0x11, 0x11, 0x11, //
                        0x11, 0x11, 0x11, 0x11, //
                        0x11, 0x11, 0x11, 0x11, //
                        0x11, 0x11, 0x11, 0x11, //
                        0x11, 0x11, 0x11, 0x11, //
                        0x11, 0x11, 0x11, 0x11, //
                        0x11, 0x11, 0x11, 0x11, //
                        0x11, 0x11, 0x11, 0x11, //
                        0x11, 0x11, 0x11, 0x11, //
                        0x11, 0x11, 0x11, 0x11, //
                        0x11, 0x11, 0x11, 0x11, //
                        0x11, 0x11, 0x11, 0x11, //
                        0x11, 0x11, 0x11, 0x11, //
                        0x11, 0x11, 0x11, 0x11, //
                        0x11, 0x11, 0x11, 0x11, //
                        0x11, 0x11, 0x11, 0x11, //
                        0x11, 0x11, 0x11, 0x11, //
                        0x11, 0x11, 0x11, 0x11, //
                        0x11, 0x11, 0x11, 0x11, //
                        0x11, 0x11, 0x11, 0x11, //
                        0x11, 0x11, 0x11, 0x11, //
                        0x11, 0x11, 0x11, 0x11, //
                        0x11, 0x11, 0x11, 0x11, //
                        0x11, 0x11, 0x11, 0x11, //
                        0x11, 0x11, 0x11, 0x11, //
                        0x11, 0x11, 0x11, 0x11, //
                        0x11, 0x11, 0x11, 0x11, //
                        0x11, 0x11, 0x11, 0x11, //
                        0x11, 0x11, 0x11, 0x11, //
                        0x11, 0x11, 0x11, 0x11, //
                        0x11, 0x11, 0x11, 0x11, //
                        0x11, 0x11, 0x11, 0x11, //
                });

        patternMap.put("CROSS", new byte[] { //
                0x11, 0x11, 0x11, 0x11, //
                        0x11, 0x11, 0x11, 0x11, //
                        0x11, 0x11, 0x11, 0x11, //
                        (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, //
                        0x11, 0x11, 0x11, 0x11, //
                        0x11, 0x11, 0x11, 0x11, //
                        0x11, 0x11, 0x11, 0x11, //
                        (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, //
                        0x11, 0x11, 0x11, 0x11, //
                        0x11, 0x11, 0x11, 0x11, //
                        0x11, 0x11, 0x11, 0x11, //
                        (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, //
                        0x11, 0x11, 0x11, 0x11, //
                        0x11, 0x11, 0x11, 0x11, //
                        0x11, 0x11, 0x11, 0x11, //
                        (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, //
                        0x11, 0x11, 0x11, 0x11, //
                        0x11, 0x11, 0x11, 0x11, //
                        0x11, 0x11, 0x11, 0x11, //
                        (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, //
                        0x11, 0x11, 0x11, 0x11, //
                        0x11, 0x11, 0x11, 0x11, //
                        0x11, 0x11, 0x11, 0x11, //
                        (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, //
                        0x11, 0x11, 0x11, 0x11, //
                        0x11, 0x11, 0x11, 0x11, //
                        0x11, 0x11, 0x11, 0x11, //
                        (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, //
                        0x11, 0x11, 0x11, 0x11, //
                        0x11, 0x11, 0x11, 0x11, //
                        0x11, 0x11, 0x11, 0x11, //
                        (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, //
                });

        patternMap.put("HORIZONTAL", new byte[] { //
                (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                });

        patternMap.put("BIGCROSS", new byte[] { //
                (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, //
                        0x01, 0x01, 0x01, 0x01, //
                        0x01, 0x01, 0x01, 0x01, //
                        0x01, 0x01, 0x01, 0x01, //
                        0x01, 0x01, 0x01, 0x01, //
                        0x01, 0x01, 0x01, 0x01, //
                        0x01, 0x01, 0x01, 0x01, //
                        0x01, 0x01, 0x01, 0x01, //
                        (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, //
                        0x01, 0x01, 0x01, 0x01, //
                        0x01, 0x01, 0x01, 0x01, //
                        0x01, 0x01, 0x01, 0x01, //
                        0x01, 0x01, 0x01, 0x01, //
                        0x01, 0x01, 0x01, 0x01, //
                        0x01, 0x01, 0x01, 0x01, //
                        0x01, 0x01, 0x01, 0x01, //
                        (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, //
                        0x01, 0x01, 0x01, 0x01, //
                        0x01, 0x01, 0x01, 0x01, //
                        0x01, 0x01, 0x01, 0x01, //
                        0x01, 0x01, 0x01, 0x01, //
                        0x01, 0x01, 0x01, 0x01, //
                        0x01, 0x01, 0x01, 0x01, //
                        0x01, 0x01, 0x01, 0x01, //
                        (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, //
                        0x01, 0x01, 0x01, 0x01, //
                        0x01, 0x01, 0x01, 0x01, //
                        0x01, 0x01, 0x01, 0x01, //
                        0x01, 0x01, 0x01, 0x01, //
                        0x01, 0x01, 0x01, 0x01, //
                        0x01, 0x01, 0x01, 0x01, //
                        0x01, 0x01, 0x01, 0x01, //
                });

        patternMap.put("WHOLE", new byte[] { //
                (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, //
                        (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, //
                        (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, //
                        (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, //
                        (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, //
                        (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, //
                        (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, //
                        (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, //
                        (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, //
                        (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, //
                        (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, //
                        (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, //
                        (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, //
                        (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, //
                        (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, //
                        (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, //
                        (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, //
                        (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, //
                        (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, //
                        (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, //
                        (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, //
                        (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, //
                        (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, //
                        (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, //
                        (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, //
                        (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, //
                        (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, //
                        (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, //
                        (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, //
                        (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, //
                        (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, //
                        (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, //
                });

        patternMap.put("VERTICAL_DOTTED", new byte[] { //
                0x11, 0x11, 0x11, 0x11, //
                        0x11, 0x11, 0x11, 0x11, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x11, 0x11, 0x11, 0x11, //
                        0x11, 0x11, 0x11, 0x11, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x11, 0x11, 0x11, 0x11, //
                        0x11, 0x11, 0x11, 0x11, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x11, 0x11, 0x11, 0x11, //
                        0x11, 0x11, 0x11, 0x11, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x11, 0x11, 0x11, 0x11, //
                        0x11, 0x11, 0x11, 0x11, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x11, 0x11, 0x11, 0x11, //
                        0x11, 0x11, 0x11, 0x11, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x11, 0x11, 0x11, 0x11, //
                        0x11, 0x11, 0x11, 0x11, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x11, 0x11, 0x11, 0x11, //
                        0x11, 0x11, 0x11, 0x11, //
                        0x00, 0x00, 0x00, 0x00, //
                        0x00, 0x00, 0x00, 0x00, //
                });

    }

    private static byte[] getPattern(String name) {
        byte[] pattern = patternMap.get(name);
        if (pattern == null) {
            throw new IllegalArgumentException("\"" + name
                    + "\" is not a valid GFEFillPattern.");
        }
        return pattern;
    }

    public static byte[] getGLPattern(String name) {
        return getPattern(name);
    }

    public static Pattern getSWTPattern(RGB foreground, String name) {
        byte[] glPattern = getPattern(name);
        byte[] swtPattern = new byte[glPattern.length];

        int i = 0;
        for (int y = 0; y < 32; y++) {
            for (int x = 0; x < 4; x++) {
                swtPattern[(31 - y) * 4 + x] = glPattern[i++];
            }
        }

        return new Pattern(null, new Image(null, new ImageData(32, 32, 1,
                new PaletteData(new RGB[] { new RGB(0, 0, 0), foreground }), 1,
                swtPattern), new ImageData(32, 32, 1, new PaletteData(
                new RGB[] { new RGB(0, 0, 0), new RGB(255, 255, 255) }), 1,
                swtPattern)));
    }
}
