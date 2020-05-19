package edu.me.datastructure.model.node.treenode;

public class ThreadedNode<T> extends GeneralBinaryNode<T> {
    private int predecessor;
    private int successor;

    public ThreadedNode(T data, int number) {
        super(data, number);
    }

    @Override
    public int calculateDegree() {
        int degree = 0;
        if (this.hasAllChildren()) degree = 2;
        else degree = 1;
        return degree;
    }
    @Override
    public boolean hasLeftChild() {
        return this.predecessor == 1;
    }
    @Override
    public boolean hasRightChild() {
        return this.successor == 1;
    }
    @Override
    public boolean hasAllChildren() {
        return this.predecessor == 1 && this.successor == 1;
    }
    @Override
    public boolean hasNoChildren() {
        return this.predecessor == 0 && this.successor == 0;
    }
    @Override
    public boolean checkEquality(T nodeToCompare) {
        boolean equal = false;
        if (nodeToCompare != null) {
            ThreadedNode<T> nodeCompareMod = (ThreadedNode<T>) nodeToCompare;
            if (nodeCompareMod.getLeft() == this.getLeft()) {
                if (nodeCompareMod.getRight() == this.getRight()) {
                    if (nodeCompareMod.getNumber() == this.getNumber()) {
                        if (nodeCompareMod.getData() == this.getData()) equal = true;
                    }
                }
            }
        }
        return equal;
    }

    public void setPredecessor(int predecessor) {
        this.predecessor = predecessor;
    }
    public void setSuccessor(int successor) {
        this.successor = successor;
    }
    public int getPredecessor() {
        return predecessor;
    }
    public int getSuccessor() {
        return successor;
    }
}
