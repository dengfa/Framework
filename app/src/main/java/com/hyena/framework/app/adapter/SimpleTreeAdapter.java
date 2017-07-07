package com.hyena.framework.app.adapter;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangzc on 17/5/2.
 */

public abstract class SimpleTreeAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {

    private TreeNode mTreeRoot;
    private List<TreeNode> mItems = new ArrayList<TreeNode>();

    public void setTree(TreeNode tree) {
        this.mTreeRoot = tree;
        updateData();
    }

    @Override
    public int getItemCount() {
        if (mItems != null) {
            return mItems.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (mItems != null && position < mItems.size()) {
            return mItems.get(position).getLevel();
        }
        return super.getItemViewType(position);
    }

    public TreeNode getItem(int position) {
        if (mItems != null && position < mItems.size()) {
            return mItems.get(position);
        }
        return null;
    }

    public List<TreeNode> getItems() {
        return mItems;
    }

    public void expandAll() {
        mItems.clear();
        if (mTreeRoot != null) {
            treeWatch(new TreeNodeWatcher() {
                @Override
                public boolean onWatch(TreeNode node) {
                    mItems.add(node);
                    node.setExpand(true);
                    return true;
                }
            });
        }
        notifyDataSetChanged();
    }

    public void collapseAll() {
        mItems.clear();
        if (mTreeRoot != null) {
            treeWatch(new TreeNodeWatcher() {
                @Override
                public boolean onWatch(TreeNode node) {
                    mItems.add(node);
                    node.setExpand(false);
                    return false;
                }
            });
        }
        notifyDataSetChanged();
    }

    protected void updateData() {
        mItems.clear();
        if (mTreeRoot != null) {
            treeWatch(new TreeNodeWatcher() {
                @Override
                public boolean onWatch(TreeNode node) {
                    mItems.add(node);
                    return node.isExpand();
                }
            });
        }
        notifyDataSetChanged();
    }

    protected void treeWatch(TreeNodeWatcher watcher) {
        treeWatch(mTreeRoot, watcher);
    }

    private void treeWatch(TreeNode treeItem, TreeNodeWatcher watcher) {
        List<TreeNode> children = treeItem.getChildren();
        if (children != null) {
            for (int i = 0; i < children.size(); i++) {
                TreeNode child = children.get(i);
                if (watcher.onWatch(child)) {
                    treeWatch(child, watcher);
                }
            }
        }
    }

    interface TreeNodeWatcher {
        boolean onWatch(TreeNode node);
    }

    public static class TreeNode {
        private TreeNode       parent;
        private int            level;
        private Object         value;
        private List<TreeNode> children;
        private boolean expand = false;

        public TreeNode(TreeNode parent, int level, Object value) {
            this.parent = parent;
            this.level = level;
            this.value = value;
        }

        public Object getValue() {
            return value;
        }

        public int getLevel() {
            return level;
        }

        public TreeNode getParent() {
            return parent;
        }

        public void setChildren(List<TreeNode> children) {
            this.children = children;
        }

        public List<TreeNode> getChildren() {
            return children;
        }

        public void setExpand(boolean expand) {
            this.expand = expand;
        }

        public boolean isExpand() {
            return expand;
        }

        public boolean isLeaf() {
            return children == null || children.isEmpty();
        }
    }

}
