package edu.me.datastructure.tree;

import edu.me.datastructure.tree.directive.TreeImp;

/*
    Definition: hierarchical structure
    Properties:
        i). Root: Node with no parents.
        ii). Edge: Link from parent to child.
        iii). Siblings: Children of the same parent.
        iv). Ancestor: There exists a path that starts at the root and passes by q and p.
        v). Tree Depth: Length of the path from the node to root.
        vi). Tree Height: Length of path from root to deepest node.
*/
public abstract class GeneralTree<T> implements TreeImp<T>{
    private T root;
    private int quantity;

    public GeneralTree(T root) {
        this.root = root;
        this.quantity = 1;
    }

    public boolean isEmpty() {
        if (this.quantity == 0) {
            System.out.println("The tree is empty");
            return true;
        }
        return false;
    }
    public boolean checkIfOneNode() {
        return this.quantity == 1;
    }
    public boolean checkIfNull(T evaluatedNode) {
        if (evaluatedNode == null) {
            return true;
        }
        return false;
    }
    public T getRoot() { return this.root; }
    public void setRoot(T newRoot) {
        if (this.isEmpty()) {
            this.root = newRoot;
            this.quantity = 1;
        } else {
            this.root = newRoot;
        }
    }
    public int getQuantity() { return this.quantity; }
    public void incrementQuantityByOne() { this.quantity++; }
    public void decrementQuantityByOne() { this.quantity--; }

    public abstract void updateRoot(T newRoot);
    public abstract boolean checkEquality(T firstNode, T secondNode);
}
