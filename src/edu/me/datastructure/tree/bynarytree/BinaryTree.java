package edu.me.datastructure.tree.bynarytree;

import edu.me.datastructure.model.node.treenode.BinaryNode;
import edu.me.datastructure.model.node.treenode.GeneralBinaryNode;
import edu.me.datastructure.queue.CircularArrayQueue;
import edu.me.datastructure.stack.ArrayStack;

public class BinaryTree<T> extends GeneralBinaryTree<BinaryNode<T>> {

    public BinaryTree(BinaryNode<T> root) {
        super(root);
    }

    @Override
    protected void removeLeaf(BinaryNode<T> nodeToRemove) {
        BinaryNode<T> parent;
        parent = this.getParentFromLeaf(nodeToRemove);
        if (parent.getRight().checkEquality(nodeToRemove)) parent.setRight(null);
        else parent.setLeft(null);
    }

    @Override
    public BinaryNode<T> searchForItem(BinaryNode<T> item) {
        BinaryNode<T> node = null;
        CircularArrayQueue<BinaryNode<T>> auxQueue = this.createQueueAndEnqueue(null, this.getRoot());
        GeneralBinaryNode<T> current;
        while (!auxQueue.isEmpty() && this.checkIfNull(node)) {
            current = (GeneralBinaryNode<T>) auxQueue.deque();
            if (this.checkEquality(item, (BinaryNode<T>) current)) node = (BinaryNode<T>) current;
            else if (!current.hasNoChildren()) {
                if (current.hasLeftChild()) auxQueue.enqueue((BinaryNode<T>) current.getLeft());
                if (current.hasRightChild()) auxQueue.enqueue((BinaryNode<T>) current.getRight());
            }
        }
        return node;
    }

    @Override
    protected void removeInternalNode(BinaryNode<T> nodeToRemove) {
        BinaryNode<T> child = this.getInOrderSuccessor(nodeToRemove);
        if (nodeToRemove.hasLeftChild()) child = this.getDownwardsPredecessor(nodeToRemove);

        this.transferChildData(nodeToRemove, child);
    }

    @Override
    protected void removeWithOneChild(BinaryNode<T> nodeToRemove) {
        BinaryNode<T> parent = (BinaryNode<T>) this.getParentLeafFromNode(nodeToRemove);
        if (!this.checkIfNull((BinaryNode<T>) parent.getRight())) {
            if (parent.getRight().getData() != nodeToRemove.getData()) parent.setLeft(null);
            else parent.setRight(null);
        }
        else parent.setLeft(null);
    }

    @Override
    public int calculateNodeHeight(BinaryNode<T> node, BinaryNode<T> wildCard) {
        int nodesDepth = calculateNodeDepth(node, wildCard);
        if (nodesDepth < 0) return -1;
        return Math.abs(nodesDepth - calculateTreeHeight());
    }
    @Override
    public int calculateNodeDepth(BinaryNode<T> node, BinaryNode<T> wildCard) {
        int nodeDepth;
        if (this.getQuantity() <= 1 || this.checkEquality(node, this.getRoot())) nodeDepth = 0;
        else {
            nodeDepth = 0;
            CircularArrayQueue<BinaryNode<T>> auxQueue = createQueueAndEnqueue(wildCard, this.getRoot());
            while (!auxQueue.isEmpty()) {
                BinaryNode<T> current = auxQueue.deque();
                if (this.checkEquality(node, current)) break;
                else if (this.keepLevelTraversing(current, wildCard, auxQueue)) nodeDepth++;
                if (!current.hasNoChildren()) {
                    if (current.hasLeftChild()) auxQueue.enqueue((BinaryNode<T>) current.getLeft());
                    if (current.hasRightChild()) auxQueue.enqueue((BinaryNode<T>) current.getRight());
                }
            }
        }
        return nodeDepth;
    }
    @Override
    public int calculateSubTreeNodeHeight(BinaryNode<T> node, BinaryNode<T> wildCard) {
        if (this.checkEquality(node, this.getRoot())) return this.calculateTreeHeight();
        int nodeHeight = 0;
        if (this.isEmpty() || checkIfNull(node) || checkIfOneNode()) return nodeHeight;
        CircularArrayQueue<BinaryNode<T>> auxQueue = createQueueAndEnqueue(wildCard, node);
        while (!auxQueue.isEmpty()) {
            BinaryNode<T> current = auxQueue.deque();
            if (this.checkEquality(wildCard, current)) {
                if (!auxQueue.isEmpty()) {
                    nodeHeight++;
                    auxQueue.enqueue(wildCard);
                } else break;
            }
            if (current.hasLeftChild()) auxQueue.enqueue((BinaryNode<T>) current.getLeft());
            if (current.hasRightChild()) auxQueue.enqueue((BinaryNode<T>) current.getRight());
        }
        return nodeHeight;
    }

    @Override
    protected boolean determineIfRightSubTree(BinaryNode<T> nodeToRemove, BinaryNode<T> root) {
        return false;
    }

    @Override
    protected void transferChildData(BinaryNode<T> parent, BinaryNode<T> child) {
        if (this.checkEquality((BinaryNode<T>) parent.getLeft(), child)) {
            if (child.hasNoChildren()) parent.setLeft(null);
            parent.setLeft(child.getLeft());
        } else {
            BinaryNode<T> tempParent = this.getParentFromLeaf(child);
            if (this.checkEquality((BinaryNode<T>) tempParent.getLeft(), child)) {
                if (child.hasNoChildren()) tempParent.setLeft(null);
                else tempParent.setLeft(child.getLeft());
            } else {
                if (child.hasNoChildren()) tempParent.setRight(null);
                else tempParent.setLeft(child.getRight());
            }
        }
        parent.setData(child.getData());
    }
}
