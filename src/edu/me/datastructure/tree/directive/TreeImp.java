package edu.me.datastructure.tree.directive;

public interface TreeImp<T> {
    int calculateTreeHeight();
    int calculateTreeDegree();
    int calculateTreeLeafNodes();

    int calculateNodeHeight(T node, T wildCard);
    int calculateSubTreeNodeHeight(T node, T wildCard);
    int calculateNodeDepth(T node, T wildCard);

    T searchForItem(T item);
    T removeItem(T toRemove);
    void insert(T item);
}
