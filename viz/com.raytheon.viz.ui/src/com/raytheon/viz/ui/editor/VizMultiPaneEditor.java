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
package com.raytheon.viz.ui.editor;

import com.raytheon.uf.viz.core.IDisplayPane;
import com.raytheon.uf.viz.core.drawables.IRenderableDisplay;
import com.raytheon.viz.ui.panes.PaneManager;

/**
 * Editor class that allows multiple panes to be displayed
 * 
 * <pre>
 * 
 * SOFTWARE HISTORY
 * 
 * Date         Ticket#    Engineer    Description
 * ------------ ---------- ----------- --------------------------
 * Jan 18, 2011            mschenke    Initial creation
 * Mar 02, 2015  4204      njensen     Only add "n Panel" to title if not already there
 * Oct 21, 2015  5023      njensen     Smarter updateTitle() logic
 * 
 * </pre>
 * 
 * @author mschenke
 * @version 1.0
 */

public class VizMultiPaneEditor extends AbstractEditor implements
        IMultiPaneEditor {

    /**
     * Set the title of the tab
     */
    @Override
    public void setTabTitle(String title) {
        editorInput.setName(title);
        updateTitle();
    }

    /**
     * Update the title of the tab, given the current state
     */
    protected void updateTitle() {
        // set the name on the tab
        String name = getDefaultName();
        String partName = getPartName();
        if (partName.equals(name) || partName.matches("\\d+ Panel " + name)) {
            if (getNumberofPanes() > 1) {
                name = getNumberofPanes() + " Panel " + name;
            }
            setPartName(name);
        }
    }

    @Override
    public int getNumberofPanes() {
        return editorInput.getPaneManager().getNumberofPanes();
    }

    @Override
    public void setSelectedPane(String action, IDisplayPane pane) {
        editorInput.getPaneManager().setSelectedPane(action, pane);
    }

    @Override
    public IDisplayPane getSelectedPane(String action) {
        return editorInput.getPaneManager().getSelectedPane(action);
    }

    @Override
    public IDisplayPane[] getSelectedPanes(String action) {
        return editorInput.getPaneManager().getSelectedPanes(action);
    }

    @Override
    public boolean isSelectedPane(String action, IDisplayPane pane) {
        return editorInput.getPaneManager().isSelectedPane(action, pane);
    }

    @Override
    public void addSelectedPaneChangedListener(
            ISelectedPanesChangedListener listener) {
        editorInput.getPaneManager().addSelectedPaneChangedListener(listener);
    }

    @Override
    public void removeSelectedPaneChangedListener(
            ISelectedPanesChangedListener listener) {
        editorInput.getPaneManager()
                .removeSelectedPaneChangedListener(listener);
    }

    @Override
    public IDisplayPane addPane(IRenderableDisplay renderableDisplay) {
        IDisplayPane pane = super.addPane(renderableDisplay);
        updateTitle();
        return pane;
    }

    @Override
    public void removePane(IDisplayPane pane) {
        editorInput.getPaneManager().removePane(pane);
        updateTitle();
    }

    @Override
    public void hidePane(IDisplayPane pane) {
        editorInput.getPaneManager().hidePane(pane);
    }

    @Override
    public void showPane(IDisplayPane pane) {
        editorInput.getPaneManager().showPane(pane);
    }

    @Override
    public int displayedPaneCount() {
        return editorInput.getPaneManager().displayedPaneCount();
    }

    @Override
    public void clear() {
        editorInput.getPaneManager().clear();
        updateTitle();
    }

    protected String getDefaultName() {
        return editorInput.getName();
    }

    @Override
    protected PaneManager getNewPaneManager() {
        return new PaneManager();
    }

}
