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
package com.raytheon.uf.viz.localization.perspective.view;

import org.eclipse.core.resources.IFile;

import com.raytheon.uf.common.localization.LocalizationFile;

/**
 * File Tree Localization File object.
 * 
 * <pre>
 * 
 * SOFTWARE HISTORY
 * 
 * Date          Ticket#  Engineer  Description
 * ------------- -------- --------- --------------------------------------------
 * Nov 03, 2010           mschenke  Initial creation
 * Oct 13, 2015  4410     bsteffen  Allow localization perspective to mix files
 *                                  for multiple Localization Types.
 * Jan 11, 2016  5242     kbisanz   Replaced calls to deprecated
 *                                  LocalizationFile methods
 * 
 * </pre>
 * 
 * @author mschenke
 */

public class LocalizationFileEntryData extends FileTreeEntryData {

    private final LocalizationFile file;

    /**
     * Flag indicating if this particular file is also available under a
     * different localization type. If this is true then extra information needs
     * to be displayed to the user indicating which type this file is for. This
     * should not happen under normal circumstances but when it does happen it
     * is important to make it obvious to the user what is going on.
     */
    private final boolean multipleTypes;

    public LocalizationFileEntryData(PathData pathData, LocalizationFile file,
            boolean multipleTypes) {
        super(pathData, file.getPath());
        this.file = file;
        this.multipleTypes = multipleTypes;
    }

    public boolean isMultipleTypes() {
        return multipleTypes;
    }

    public LocalizationFile getFile() {
        return file;
    }

    @Override
    public IFile getResource() {
        return (IFile) super.getResource();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result
                + ((file == null) ? 0 : file.getContext().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        LocalizationFileEntryData other = (LocalizationFileEntryData) obj;
        if (file == null) {
            if (other.file != null)
                return false;
        } else if (!file.getContext().equals(other.file.getContext()))
            return false;
        return true;
    }

}
