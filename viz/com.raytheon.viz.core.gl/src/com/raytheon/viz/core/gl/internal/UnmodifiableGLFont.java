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
package com.raytheon.viz.core.gl.internal;

import org.eclipse.swt.graphics.Point;

import com.raytheon.uf.common.status.IUFStatusHandler;
import com.raytheon.uf.common.status.UFStatus;
import com.raytheon.uf.common.status.UFStatus.Priority;
import com.raytheon.uf.viz.core.drawables.IFont;
import com.raytheon.viz.core.gl.IGLFont;
import com.sun.opengl.util.j2d.TextRenderer;

/**
 * GLFont that does not support modifying it, used for shared font (default
 * font)
 * 
 * <pre>
 * 
 * SOFTWARE HISTORY
 * 
 * Date         Ticket#    Engineer    Description
 * ------------ ---------- ----------- --------------------------
 * Oct 18, 2010            mschenke    Initial creation
 * Nov 04, 2015   5070     randerso    Added DPI font scaling
 * 
 * </pre>
 * 
 * @author mschenke
 * @version 1.0
 */

public class UnmodifiableGLFont implements IGLFont {
    private static final transient IUFStatusHandler statusHandler = UFStatus
            .getHandler(UnmodifiableGLFont.class);

    private IGLFont unmodifiableFont;

    /**
     * @param target
     * @param font
     * @param fontSize
     * @param styles
     */
    public UnmodifiableGLFont(IGLFont font) {
        this.unmodifiableFont = font;
    }

    @Override
    public void dispose() {
        statusHandler.handle(Priority.PROBLEM,
                "Attempting to dispose an unmodifiable font,"
                        + " must be disposed of internally only");
    }

    @Override
    public void setMagnification(float magnification) {
        statusHandler.handle(Priority.PROBLEM,
                "Attempting to change magnification of an unmodifiable font,"
                        + " must be disposed of internally only");
    }

    @Override
    public void setMagnification(float magnification, boolean scaleFont) {
        statusHandler.handle(Priority.PROBLEM,
                "Attempting to change magnification of an unmodifiable font,"
                        + " must be disposed of internally only");

    }

    @Override
    public float getMagnification() {
        return unmodifiableFont.getMagnification();
    }

    @Override
    public void setSmoothing(boolean smoothing) {
        statusHandler.handle(Priority.PROBLEM,
                "Attempting to change smoothing of an unmodifiable font,"
                        + " must be disposed of internally only");
    }

    @Override
    public void setScaleFont(boolean scaleFont) {
        statusHandler.handle(Priority.PROBLEM,
                "Attempting to change font scaling of an unmodifiable font,"
                        + " must be disposed of internally only");
    }

    @Override
    public void disposeInternal() {
        unmodifiableFont.disposeInternal();
    }

    @Override
    public int hashCode() {
        return unmodifiableFont.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return unmodifiableFont.equals(obj);
    }

    @Override
    public Point getDPI() {
        return unmodifiableFont.getDPI();
    }

    @Override
    public String getFontName() {
        return unmodifiableFont.getFontName();
    }

    @Override
    public float getFontSize() {
        return unmodifiableFont.getFontSize();
    }

    @Override
    public Style[] getStyle() {
        return unmodifiableFont.getStyle();
    }

    @Override
    public TextRenderer getTextRenderer() {
        return unmodifiableFont.getTextRenderer();
    }

    @Override
    public IFont deriveWithSize(float size) {
        return unmodifiableFont.deriveWithSize(size);
    }

    @Override
    public boolean getSmoothing() {
        return unmodifiableFont.getSmoothing();
    }

    @Override
    public boolean isScaleFont() {
        return unmodifiableFont.isScaleFont();
    }

    @Override
    public String toString() {
        return unmodifiableFont.toString();
    }

}
