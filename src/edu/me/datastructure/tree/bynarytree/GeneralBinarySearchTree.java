package edu.me.datastructure.tree.bynarytree;

import edu.me.datastructure.model.node.treenode.GeneralBinaryNode;
import edu.me.datastructure.queue.CircularArrayQueue;

public  abstract class GeneralBinarySearchTree<T> extends GeneralBinaryTree<T> {
    public GeneralBinarySearchTree(T root) {
        super(root);
    }

    protected T searchCorrectParent(T item) {
        GeneralBinaryNode<Integer> current = (GeneralBinaryNode<Integer>) this.getRoot();
        GeneralBinaryNode<Integer> previous = current;
        GeneralBinaryNode<Integer> itemMod = (GeneralBinaryNode<Integer>) item;
        while (!this.checkIfNull((T) current) && !this.checkEquality((T) current, item)) {
            previous = current;
            if (itemMod.getData() < current.getData()) current = current.getLeft();
            else current = current.getRight();
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
        GeneralBinaryNode<Integer> parent = (GeneralBinaryNode<Integer>) this.getParentFromLeaf(nodeToRemove);
        if (this.checkEquality((T) parent.getRight(), nodeToRemove)) parent.setRight(null);
        else parent.setLeft(null);
    }
    @Override
    protected void removeInternalNode(T nodeToRemove) {
        T child = this.getInOrderSuccessor(nodeToRemove);
        if (((GeneralBinaryNode<Integer>)nodeToRemove).hasLeftChild()) child = this.getDownwardsPredecessor(nodeToRemove);

        this.transferChildData(nodeToRemove, child);
    }
    @Override
    protected void transferChildData(T parent, T child) {
        GeneralBinaryNode<T> parentMod = (GeneralBinaryNode<T>) parent;
        GeneralBinaryNode<T> childMod = (GeneralBinaryNode<T>) child;
        if (this.determineIfRightSubTree(child, parent)) {
            if (this.checkEquality((T) parentMod.getRight(), child)) {
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
            if (this.checkEquality((T) parentMod.getLeft(), child)) {
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
    protected boolean determineIfRightSubTree(T nodeToRemove, T root) {
        return  ((GeneralBinaryNode<Integer>) nodeToRemove).getData() > ((GeneralBinaryNode<Integer>) root).getData();
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
        if (this.getQuantity() <= 1 || this.checkEquality(this.getRoot(), node)) nodeDepth = 0;
        else {
            nodeDepth = 0;
            CircularArrayQueue<T> auxQueue = createQueueAndEnqueue(wildCard, this.getRoot());
            while (!auxQueue.isEmpty()) {
                GeneralBinaryNode<T> current = (GeneralBinaryNode<T>) auxQueue.deque();
                if (this.checkEquality((T) current, node)) break;
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
            GeneralBinaryNode<T> current = (GeneralBinaryNode<T>) auxQueue.deque();
            if (this.checkEquality((T) current, wildCard)) {
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

    @Override
    public boolean checkEquality(T firstNode, T secondNode) {
        boolean equal = false;
        if (!this.checkIfNull(firstNode) && !this.checkIfNull(secondNode)) {
            GeneralBinaryNode<T> firstMod = (GeneralBinaryNode<T>) firstNode;
            GeneralBinaryNode<T> secondMod = (GeneralBinaryNode<T>) secondNode;
            if (firstMod.getLeft() == secondMod.getLeft()) {
                if (firstMod.getRight() == secondMod.getRight()) {
                    if (secondMod.getData() == ((GeneralBinaryNode<T>) secondNode).getData()) {
                        if (firstMod.getNumber() == secondMod.getNumber()) equal = true;
                    }
                }
            }
        }
        return equal;
    }
}
