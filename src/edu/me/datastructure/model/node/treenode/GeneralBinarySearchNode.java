package edu.me.datastructure.model.node.treenode;

public abstract class GeneralBinarySearchNode<T> extends GeneralBinaryNode<T> {
    public GeneralBinarySearchNode(T data, int number) {
        super(data, number);
    }

    @Override
    public boolean checkEquality(T nodeToCompare) {
        boolean equal = false;
        if (nodeToCompare != null) {
            GeneralBinaryNode<T> nodeToCompareMod = (GeneralBinaryNode<T>) nodeToCompare;
            if (nodeToCompareMod.contentNotNull() && this.contentNotNull()) {
                if (nodeToCompareMod.getLeft() == this.getLeft()) {
                    if (nodeToCompareMod.getRight() == this.getRight()) {
                        if (nodeToCompareMod.getNumber() == this.getNumber()) {
                            if (nodeToCompareMod.getData().equals(this.getData())) equal = true;
                        }
                    }
                }
            } else equal = this.determineWildCardEquality(nodeToCompare);
        }
        return equal;
    }

    public void setParent(T parent) {
        GeneralBinarySearchNode<T> parentMod = (GeneralBinarySearchNode<T>) parent;
        if (this.getNumber() < parentMod.getNumber()) parentMod.setLeft(this);
        else parentMod.setRight(this);
    }
    public boolean isParentOf(T child) {
        GeneralBinarySearchNode<T> childMod = (GeneralBinarySearchNode<T>) child;
        return childMod.isLeftChild((T) this) || childMod.isRightChild((T) this);
    }
    public boolean isLeftChild(T parent) {
        return this.checkEquality((T) ((GeneralBinarySearchNode<T>) parent).getLeft());
    }
    public boolean isRightChild(T parent) { return this.checkEquality((T) ((GeneralBinarySearchNode<T>) parent).getRight()); }
    private boolean determineWildCardEquality(T wildCard) {
        return wildCard.equals(this);
    }
}
