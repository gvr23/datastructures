package edu.me.datastructure.model.node.nodedirective;

public interface TreeNodeImp<T> {
    boolean hasNoChildren();
    boolean hasAllChildren();
    boolean hasLeftGrandChildren();
    boolean hasRightGrandChildren();
    int calculateDegree();
    boolean checkEquality(T nodeToCompare);
}
