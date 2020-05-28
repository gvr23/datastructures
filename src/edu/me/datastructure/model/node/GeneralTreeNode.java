package edu.me.datastructure.model.node;

import edu.me.datastructure.model.node.nodedirective.TreeNodeImp;

public abstract class GeneralTreeNode<T> extends GeneralNode<T> implements TreeNodeImp<T> {
    private int timesVisited;
    private int number;

    public GeneralTreeNode(T data, int number) {
        super(data);
        this.number = number;
    }

    public int getNumber() { return number; }
    public void setNumber(int number) {
        this.number = number;
    }

    public int getTimesVisited() { return this.timesVisited; }
    public void addTimesVisited() { this.timesVisited++; }
    public boolean notVisited() { return this.timesVisited == 0; }
    public void resetTimesVisited() { this.timesVisited = 0; }
}
