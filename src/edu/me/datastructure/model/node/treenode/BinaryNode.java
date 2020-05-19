package edu.me.datastructure.model.node.treenode;

public class BinaryNode<T> extends GeneralBinaryNode<BinaryNode<T>> {
    public BinaryNode(BinaryNode<T> data, int number) {
        super(data, number);
    }

    @Override
    public boolean isLeftChild(BinaryNode<T> parent) {
        return false;
    }
}
