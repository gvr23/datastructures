package edu.me.datastructure.tree.bynarytree;

import edu.me.datastructure.model.node.treenode.GeneralBinaryNode;
import edu.me.datastructure.model.node.treenode.GeneralBinarySearchNode;
import edu.me.datastructure.queue.CircularArrayQueue;

public  abstract class GeneralBinarySearchTree<T> extends GeneralBinaryTree<T> {
    public GeneralBinarySearchTree(T root) {
        super(root);
    }

    protected T searchCorrectParent(T item) {
        GeneralBinarySearchNode<T> current = (GeneralBinarySearchNode<T>) this.getRoot();
        GeneralBinarySearchNode<T> previous = current;
        GeneralBinarySearchNode<T> itemMod = (GeneralBinarySearchNode<T>) item;
        while (!this.checkIfNull((T) current) && !itemMod.checkEquality((T) current)) {
            previous = current;
            if (itemMod.getNumber() < current.getNumber()) current = (GeneralBinarySearchNode<T>) current.getLeft();
            else current = (GeneralBinarySearchNode<T>) current.getRight();
        }
        return (T) previous;
    }

    @Override
    public T searchForItem(T item) {
        GeneralBinaryNode<T> current = null;
        GeneralBinaryNode<T> itemMod = (GeneralBinaryNode<T>) item;
        if (!this.checkIfNull(item)) {
            current = (GeneralBinaryNode<T>) this.getRoot();
            while (!this.checkIfNull((T) current)) {
                if (current.getData().equals(itemMod.getData())) break;
                else if (this.determineIfRightSubTree(item, (T) current)) current = current.getRight();
                else current = current.getLeft();
            }
        }
        return (T) current;
    }

    @Override
    protected void removeLeaf(T nodeToRemove) {
        GeneralBinarySearchNode<T> parent = (GeneralBinarySearchNode<T>) this.getParentFromLeaf(nodeToRemove);
        if (parent.hasRightChild() && parent.getRight().checkEquality(nodeToRemove)) parent.setRight(null);
        else parent.setLeft(null);
    }
    @Override
    protected void removeInternalNode(T nodeToRemove) {
        T child = this.getInOrderSuccessor(nodeToRemove);
        if (((GeneralBinarySearchNode<Integer>)nodeToRemove).hasLeftChild()) child = this.getDownwardsPredecessor(nodeToRemove);

        this.transferChildData(nodeToRemove, child);
    }
    @Override
    protected void transferChildData(T parent, T child) {
        GeneralBinaryNode<T> parentMod = (GeneralBinarySearchNode<T>) parent;
        GeneralBinaryNode<T> childMod = (GeneralBinarySearchNode<T>) child;
        if (this.determineIfRightSubTree(child, parent)) {
            if (parentMod.getRight().checkEquality(child)) {
                parentMod.setData(childMod.getData());
                if (childMod.hasNoChildren()) parentMod.setRight(null);
                else {
                    parentMod.setRight(childMod.getRight());
                    if (childMod.hasLeftChild()) {
                        this.decrementQuantityByOne();
                        this.insert((T) childMod.getLeft());
                    }
                }
            } else this.removeLeaf(child);
        }
        else {
            parentMod.setData(childMod.getData());
            if (parentMod.getLeft().checkEquality(child)) {
                if (childMod.hasNoChildren()) parentMod.setLeft(null);
                else {
                    parentMod.setLeft(childMod.getLeft());
                    if (childMod.hasRightChild()) {
                        this.decrementQuantityByOne();
                        this.insert((T) childMod.getRight());
                    }
                }
            } else this.removeLeaf(child);
        }
    }
    @Override
    protected void removeWithOneChild(T nodeToRemove) {
        GeneralBinaryNode<T> nodeToRemoveMod = (GeneralBinaryNode<T>) nodeToRemove;
        if (nodeToRemoveMod.hasRightChild()) this.transferChildData(nodeToRemove, (T) nodeToRemoveMod.getRight());
        else this.transferChildData(nodeToRemove, (T) nodeToRemoveMod.getLeft());
    }

    @Override
    protected boolean determineIfRightSubTree(T child, T parent) {
        return  ((GeneralBinaryNode<T>) child).getNumber() > ((GeneralBinaryNode<T>) parent).getNumber();
    }

    @Override
    public int calculateNodeHeight(T node, T wildCard) {
        int nodesDepth = calculateNodeDepth(node, wildCard);
        if (nodesDepth < 0) return -1;
        return Math.abs(nodesDepth - calculateTreeHeight());
    }

    @Override
    public int calculateNodeDepth(T node, T wildCard) {
        int nodeDepth;
        GeneralBinarySearchNode<T> nodeMod = (GeneralBinarySearchNode<T>) node;
        if (this.getQuantity() <= 1 || nodeMod.checkEquality(this.getRoot())) nodeDepth = 0;
        else {
            nodeDepth = 0;
            CircularArrayQueue<T> auxQueue = createQueueAndEnqueue(wildCard, this.getRoot());
            while (!auxQueue.isEmpty()) {
                GeneralBinaryNode<T> current = (GeneralBinaryNode<T>) auxQueue.deque();
                if (current.checkEquality(node)) break;
                else if (this.keepLevelTraversing((T) current, wildCard, auxQueue)) nodeDepth++;
                if (!current.hasNoChildren()) {
                    if (current.hasLeftChild()) auxQueue.enqueue((T) current.getLeft());
                    if (current.hasRightChild()) auxQueue.enqueue((T) current.getRight());
                }
            }
        }
        return nodeDepth;
    }

    @Override
    public int calculateSubTreeNodeHeight(T node, T wildCard) {
        int nodeHeight = 0;
        if (this.isEmpty() || this.checkIfNull(node) || checkIfOneNode()) return nodeHeight;
        CircularArrayQueue<T> auxQueue = createQueueAndEnqueue(wildCard, node);
        while (!auxQueue.isEmpty()) {
            GeneralBinarySearchNode<T> current = (GeneralBinarySearchNode<T>) auxQueue.deque();
            if (current.checkEquality(wildCard)) {
                if (!auxQueue.isEmpty()) {
                    nodeHeight++;
                    auxQueue.enqueue(wildCard);
                } else break;
            }
            if (current.hasLeftChild()) auxQueue.enqueue((T) current.getLeft());
            if (current.hasRightChild()) auxQueue.enqueue((T) current.getRight());
        }
        return nodeHeight;
    }
}
