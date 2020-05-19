package edu.me.datastructure.model.node.treenode;

public class RedBlackNode<T> extends GeneralBinaryNode<T> {
    public enum NodeColor {
        RED,
        BLACK,
        DOUBLEBLACK
    }
    private NodeColor color;

    public RedBlackNode(T data, int number) {
        super(data, number);
    }

    public boolean isBlack() { return this.color.equals(NodeColor.BLACK); }

    public void reColor() {
        if (this.isBlack()) this.color = NodeColor.RED;
        else this.color = NodeColor.BLACK;
    }
    public void setParent(RedBlackNode<T> parent) {
        if (this.getNumber() < parent.getNumber()) parent.setLeft(this);
        else parent.setRight(this);
    }
    public NodeColor getColor() { return color; }
    public void setColor(NodeColor color) {
        this.color = color;
    }
    public void printData() {
        final String RED = "\033[0;31m";
        final String BLACK = "\033[0;30m";
        final String RESET = "\033[0m";
        String color;
        color = (this.isBlack()) ? BLACK : RED;
        System.out.print(color + this.getData() + RESET);
    }
}