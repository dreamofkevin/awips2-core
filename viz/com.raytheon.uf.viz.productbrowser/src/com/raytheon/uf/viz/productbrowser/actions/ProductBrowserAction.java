package com.raytheon.uf.viz.productbrowser.actions;

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

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.raytheon.uf.common.status.IUFStatusHandler;
import com.raytheon.uf.common.status.UFStatus;
import com.raytheon.uf.common.status.UFStatus.Priority;
import com.raytheon.uf.viz.core.status.StatusConstants;
import com.raytheon.uf.viz.productbrowser.Activator;
import com.raytheon.uf.viz.productbrowser.ProductBrowserView;

public class ProductBrowserAction extends AbstractHandler {
    private static final transient IUFStatusHandler statusHandler = UFStatus.getHandler(ProductBrowserAction.class);

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        // this opens the product browser view
        try {
            PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                    .getActivePage().showView(ProductBrowserView.ID);
        } catch (PartInitException e) {
            statusHandler.handle(Priority.PROBLEM,
                    "Unable to open product browser", e);
        }
        return event;
    }

}
