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

    public void setParent(RedBlackNode<T> parent) {
        if (this.getNumber() < parent.getNumber()) parent.setLeft(this);
        else parent.setRight(this);
    }
    private boolean determineWildCardEquality(T wildCard) {
        return wildCard.equals(this);
    }
}
