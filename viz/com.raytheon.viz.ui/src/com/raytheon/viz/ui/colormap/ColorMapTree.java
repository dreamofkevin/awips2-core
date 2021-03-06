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
package com.raytheon.viz.ui.colormap;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.raytheon.uf.common.colormap.ColorMapLoader;
import com.raytheon.uf.common.localization.FileUpdatedMessage;
import com.raytheon.uf.common.localization.ILocalizationFile;
import com.raytheon.uf.common.localization.ILocalizationFileObserver;
import com.raytheon.uf.common.localization.IPathManager;
import com.raytheon.uf.common.localization.LocalizationContext;
import com.raytheon.uf.common.localization.LocalizationContext.LocalizationLevel;
import com.raytheon.uf.common.localization.LocalizationContext.LocalizationType;
import com.raytheon.uf.common.localization.LocalizationFile;

/**
 * ColorMapTree represents the directory structure of colormaps directory. The
 * levels of a Tree can represent a {@link LocalizationLevel}, a
 * {@link LocalizationContext} or a localization directory.
 * 
 * <pre>
 * 
 * SOFTWARE HISTORY
 * 
 * Date          Ticket#  Engineer    Description
 * ------------- -------- ----------- --------------------------
 * Sep 18, 2013  2421     bsteffen    Initial creation
 * Sep 11, 2014  3516     rferrel     file updates now inform the factory.
 *                                      getName() no longer returns a null.
 *                                      FileChangeListener now only gets colormaps changes.
 * Aug 24, 2015  4393     njensen     Updates for observer changes
 * Nov 18, 2015  4834     njensen     API updates due to removal of LocalizationNotificationObserver
 * Jan 13, 2016  5242     kbisanz     Replaced calls to deprecated LocalizationFile methods
 * 
 * </pre>
 * 
 * @author bsteffen
 * @version 1.0
 */
public class ColorMapTree {

    private final String path;

    private final IPathManager pathManager;

    private final LocalizationLevel level;

    private final LocalizationContext context;

    private final Object filesLock = new Object();

    private ILocalizationFile[] files;

    private final Object subTreesLock = new Object();

    private List<ColorMapTree> subTrees;

    /**
     * Create a tree for the given path and context. The tree will represent the
     * colormap files that exist at the path within the context.
     */
    public ColorMapTree(IPathManager pathManager, LocalizationContext context,
            String path) {
        this.path = path;
        this.pathManager = pathManager;
        this.level = null;
        this.context = context;
    }

    /**
     * Create a tree for the given path and level. The tree will have the same
     * name as the level and will have a subtree for each context that exists at
     * that level. Each context tree will represent the colormap files that
     * exist at the path.
     */
    public ColorMapTree(IPathManager pathManager, LocalizationLevel level,
            String path) {
        this.path = path;
        this.pathManager = pathManager;
        this.level = level;
        this.context = null;

        LocalizationFile dir = pathManager.getLocalizationFile(
                pathManager.getContext(LocalizationType.COMMON_STATIC, level),
                path);
        dir.addFileUpdatedObserver(new FileChangeListener(this));
    }

    /**
     * For a tree based only on a {@link LocalizationLevel} this returns the
     * name of the level, for a tree at the root directory of a context this
     * will be the name of the {@link LocalizationContext} and for a tree
     * representing a subdirectory this will be the directory name.
     */
    public String getName() {
        if (context == null) {
            return level.name();
        } else {
            int start = path.lastIndexOf(IPathManager.SEPARATOR);
            if (start <= 0) {
                String name = context.getContextName();
                if (name == null) {
                    name = context.getLocalizationLevel().name();
                }
                return name;
            }
            return path.substring(start + 1);
        }
    }

    /**
     * For a tree based on a {@link LocalizationLevel} this returns a tree for
     * each context at the level. Otherwise it returns a tree for each
     * subdirectory of this tree.
     */
    public List<ColorMapTree> getSubTrees() {
        synchronized (subTreesLock) {
            if (subTrees == null) {
                subTrees = new ArrayList<>();
                if (context == null) {
                    for (String context : pathManager.getContextList(level)) {
                        LocalizationContext ctx = pathManager.getContext(
                                LocalizationType.COMMON_STATIC, level);
                        ctx.setContextName(context);
                        subTrees.add(new ColorMapTree(pathManager, ctx, path));
                    }
                } else {
                    for (ILocalizationFile file : requestFiles()) {
                        if (file.isDirectory() && !path.equals(file.getPath())) {
                            subTrees.add(new ColorMapTree(pathManager, context,
                                    file.getPath()));
                        }
                    }
                }
            }
            return new ArrayList<ColorMapTree>(subTrees);
        }
    }

    /**
     * 
     * @return all color map files within this level of the tree.
     */
    public List<ILocalizationFile> getColorMapFiles() {
        if (context == null) {
            return Collections.emptyList();
        } else {
            List<ILocalizationFile> result = new ArrayList<>();
            for (ILocalizationFile file : requestFiles()) {
                if (!file.isDirectory()) {
                    result.add(file);
                }
            }
            return result;
        }
    }

    /**
     * 
     * @return true if this tree does not contain any color map files or any
     *         subtrees which contain color map files(recursively).
     */
    public boolean isEmpty() {
        if (getColorMapFiles().isEmpty()) {
            for (ColorMapTree tree : getSubTrees()) {
                if (!tree.isEmpty()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Recursively optimize the internal structure so future {@link #isEmpty()}
     * calls are fast. isEmpty() is a slow operations on trees with many empty
     * subtrees, so this can be called in the background to enable faster calls
     * to isEmpty when it is needed. In cases where isEmpty does not need extra
     * data or is already optimized this call should complete very quickly.
     * Intended for use on non-UI thread.
     */
    public void optimizeIsEmpty() {
        System.out.println(Thread.currentThread().getName() + " Optimizing "
                + getName());
        isEmpty();

        /*
         * isEmpty() may not check all sub trees. Force check of all sub trees.
         */
        for (ColorMapTree subTree : getSubTrees()) {
            subTree.optimizeIsEmpty();
        }
    }

    /**
     * This method will receive a message for every localization file in all
     * contexts. It must filter by path and by level and/or context.
     */
    protected void handleUpdate(FileUpdatedMessage message) {
        if (message.getFileName().startsWith(path)) {
            LocalizationContext context = message.getContext();
            if (context.getLocalizationLevel().equals(level)) {
                synchronized (subTreesLock) {
                    if (subTrees != null) {
                        for (ColorMapTree subTree : subTrees) {
                            if (subTree.getName().equals(
                                    context.getContextName())) {
                                subTree.handleUpdate(message);
                                return;
                            }
                        }
                        subTrees.add(new ColorMapTree(pathManager, context,
                                path));
                    }
                }
            } else if (context.equals(this.context)) {
                synchronized (filesLock) {
                    files = null;
                }
                synchronized (subTreesLock) {
                    subTrees = null;
                }
            }
        }
    }

    private ILocalizationFile[] requestFiles() {
        synchronized (filesLock) {
            if (files == null) {
                files = pathManager
                        .listFiles(context, path,
                                new String[] { ColorMapLoader.EXTENSION },
                                false, false);
            }
            return files;
        }
    }

    /**
     * {@link WeakReference} based listener which automatically removes itself
     * when a notification arrives and the {@link ColorMapTree} has been garbage
     * collected.
     */
    private class FileChangeListener implements ILocalizationFileObserver {

        private final Reference<ColorMapTree> treeRef;

        private FileChangeListener(ColorMapTree tree) {
            treeRef = new WeakReference<>(tree);
        }

        @Override
        public void fileUpdated(FileUpdatedMessage message) {
            ColorMapTree tree = treeRef.get();
            if (tree != null) {
                tree.handleUpdate(message);
                ColorMapTreeFactory factory = ColorMapTreeFactory.getInstance();
                factory.optimizeTree(tree);
                factory.refresh();
            }
        }
    }
}
