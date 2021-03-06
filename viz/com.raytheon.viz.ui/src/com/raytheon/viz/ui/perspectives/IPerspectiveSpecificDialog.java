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
package com.raytheon.viz.ui.perspectives;

/**
 * Interface meant for dialogs so that they get notified when to
 * 
 * <pre>
 * 
 * SOFTWARE HISTORY
 * Date         Ticket#    Engineer    Description
 * ------------ ---------- ----------- --------------------------
 * Apr 27, 2010            mschenke     Initial creation
 * Apr 20, 2015 5541       dgilling     Add additional hide/restore methods 
 *                                      so we can detect perspective switching.
 * 
 * </pre>
 * 
 * @author mschenke
 * @version 1.0
 */

public interface IPerspectiveSpecificDialog {

    /**
     * Hide the dialog
     */
    public void hide();

    /**
     * Hide the dialog
     * 
     * @param isPerspectiveSwitch
     *            restore was triggered by a perspective switch event or not
     */
    public void hide(boolean isPerspectiveSwitch);

    /**
     * Restore dialog to location
     */
    public void restore();

    /**
     * Restore dialog to location
     * 
     * @param isPerspectiveSwitch
     *            restore was triggered by a perspective switch event or not
     */
    public void restore(boolean isPerspectiveSwitch);

    /**
     * Close the dialog, called when perspective is closed
     */
    public boolean close();
}
