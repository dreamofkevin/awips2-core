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

import java.util.Arrays;
import java.util.List;

import com.raytheon.uf.common.localization.LocalizationContext.LocalizationType;
import com.raytheon.uf.viz.localization.perspective.adapter.LocalizationPerspectiveAdapter;

/**
 * Holds the data for each extension contribution including context information
 * to search, filters, recursive
 * 
 * <pre>
 * 
 * SOFTWARE HISTORY
 * 
 * Date          Ticket#  Engineer  Description
 * ------------- -------- --------- --------------------------------------------
 * Aug 17, 2010           mpduff    Initial creation
 * Oct 13, 2015  4410     bsteffen  Allow localization perspective to mix files
 *                                  for multiple Localization Types.
 * 
 * </pre>
 * 
 * @author mpduff
 */

public class PathData {
    /**
     * Path name.
     */
    private String name = null;

    private List<LocalizationType> types = null;

    /**
     * The path of the file.
     */
    private String path = null;

    /**
     * The filter used when reading the files in.
     */
    private String[] filter = null;

    /**
     * The application this file belongs to.
     */
    private String application = null;

    /**
     * Search recursively for files.
     */
    private boolean recursive = false;

    /**
     * The Localization Adapter for this data object.
     */
    private LocalizationPerspectiveAdapter adapter = null;

    public PathData() {

    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the type
     */
    public List<LocalizationType> getTypes() {
        return types;
    }

    /**
     * @param types
     *            the list of types to set
     */
    public void setTypes(List<LocalizationType> types) {
        this.types = types;
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path
     *            the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name = " + name + "\n");
        sb.append("Path = " + path + "\n");
        sb.append("Types = " + types.toString() + "\n");
        return sb.toString();
    }

    /**
     * @return the filter
     */
    public String[] getFilter() {
        return filter;
    }

    /**
     * @param filter
     *            the filter to set
     */
    public void setFilter(String[] filter) {
        this.filter = filter;
    }

    /**
     * Set the filter. Comma separated list of filters
     * 
     * @param filterList
     */
    public void setFilter(String filterList) {
        if (filterList != null) {
            if (filterList.indexOf(",") > -1) {
                filter = filterList.split(",");
            } else {
                filter = new String[1];
                filter[0] = filterList;
            }
        }
    }

    /**
     * Set the application.
     * 
     * @param application
     *            The application name
     */
    public void setApplication(String application) {
        this.application = application;
    }

    /**
     * Get the application name.
     * 
     * @return The application name
     */
    public String getApplication() {
        return application;
    }

    /**
     * @param recursive
     *            the recursive to set
     */
    public void setRecursive(boolean recursive) {
        this.recursive = recursive;
    }

    /**
     * @return the recursive
     */
    public boolean isRecursive() {
        return recursive;
    }

    /**
     * @param adapter
     *            the adapter to set
     */
    public void setAdapter(LocalizationPerspectiveAdapter adapter) {
        this.adapter = adapter;
    }

    /**
     * @return the adapter
     */
    public LocalizationPerspectiveAdapter getAdapter() {
        return adapter;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((application == null) ? 0 : application.hashCode());
        result = prime * result + Arrays.hashCode(filter);
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((path == null) ? 0 : path.hashCode());
        result = prime * result + (recursive ? 1231 : 1237);
        result = prime * result + ((types == null) ? 0 : types.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PathData other = (PathData) obj;
        if (application == null) {
            if (other.application != null)
                return false;
        } else if (!application.equals(other.application))
            return false;
        if (!Arrays.equals(filter, other.filter))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (path == null) {
            if (other.path != null)
                return false;
        } else if (!path.equals(other.path))
            return false;
        if (recursive != other.recursive)
            return false;
        if (types == null) {
            if (other.types != null)
                return false;
        } else if (!types.equals(other.types))
            return false;
        return true;
    }

}
