package edu.me.datastructure.model.node.treenode;

import edu.me.datastructure.model.node.GeneralTreeNode;

public abstract class GeneralBinaryNode<T> extends GeneralTreeNode<T> {
    private GeneralBinaryNode<T> left;
    private GeneralBinaryNode<T> right;

    public GeneralBinaryNode(T data, int number) {
        super(data, number);
    }

    @Override
    public int calculateDegree() {
        int interimChildrenNumber = 0;
        if (hasNoChildren()) return interimChildrenNumber;
        if (hasAllChildren()) interimChildrenNumber = 2;
        else interimChildrenNumber = 1;

        return interimChildrenNumber;
    }
    @Override
    public boolean hasAllChildren() {
        return this.left != null && this.right != null;
    }
    @Override
    public boolean hasNoChildren() {
        return this.left == null && this.right == null;
    }
    @Override
    public boolean hasRightGrandChildren() {
        return this.right.getRight() != null || this.right.left != null;
    }
    @Override
    public boolean hasLeftGrandChildren() {
        return this.left.getLeft() != null || this.left.getRight() != null;
    }

    @Override
    public boolean checkEquality(T nodeToCompare) {
        boolean equal = false;
        if (nodeToCompare != null) {
            GeneralBinaryNode<T> nodeToCompareMod = (GeneralBinaryNode<T>) nodeToCompare;
            if (nodeToCompareMod.getNumber() == this.getNumber()) {
                if (nodeToCompareMod.getData().equals(this.getData())) equal = true;
            }
        }
        return equal;
    }

    public void setLeft(GeneralBinaryNode<T> left) {
        this.left = left;
    }
    public void setRight(GeneralBinaryNode<T> right) {
        this.right = right;
    }
    public GeneralBinaryNode<T> getLeft() {
        return left;
    }
    public GeneralBinaryNode<T> getRight() {
        return right;
    }
    public boolean hasLeftChild() { return this.left != null; }
    public boolean hasRightChild() { return this.right != null; }
}

