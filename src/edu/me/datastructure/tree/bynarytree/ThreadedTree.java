package edu.me.datastructure.tree.bynarytree;

import edu.me.datastructure.model.node.treenode.ThreadedNode;
import edu.me.datastructure.queue.CircularArrayQueue;
import edu.me.datastructure.stack.ArrayStack;
import edu.me.datastructure.tree.bynarytree.GeneralBinaryTree;

public class ThreadedTree<T> extends GeneralBinaryTree<ThreadedNode<T>> {

    public ThreadedTree(ThreadedNode<T> firstNode, ThreadedNode<T> wildCard) {
        super(wildCard);
        initRoot(firstNode);
        setFirstNode(firstNode);
    }

    private void initRoot(ThreadedNode<T> firstNode) {
        this.getRoot().setPredecessor(1);
        this.getRoot().setLeft(firstNode);
        this.getRoot().setSuccessor(1);
        this.getRoot().setRight(this.getRoot());
    }

    @Override
    public void setRoot(ThreadedNode<T> newRoot) {
        System.out.println("That operation is not permitted in this tree");
    }

    @Override
    public void levelOrderInsert(ThreadedNode<T> newNode) {
        if (this.isEmpty()) setFirstNode(newNode);
        else {
            CircularArrayQueue<ThreadedNode<T>> auxQueue = createQueueAndEnqueue(this.getRoot(), (ThreadedNode<T>) this.getRoot().getLeft());
            while (!auxQueue.isEmpty()) {
                ThreadedNode<T> current = auxQueue.deque();
                if (keepLevelTraversing(current, this.getRoot(), auxQueue)) if (!current.checkEquality((T) this.getRoot())) if (determineInsert(current, auxQueue, newNode)) break;
            }
        }
    }
    private void setFirstNode(ThreadedNode<T> firstNode) {
        firstNode.setPredecessor(0);
        firstNode.setLeft(this.getRoot());
        firstNode.setSuccessor(0);
        firstNode.setRight(this.getRoot());
        if (this.isEmpty()) this.getRoot().setLeft(firstNode);
    }
    private boolean determineInsert(ThreadedNode<T> current, CircularArrayQueue<ThreadedNode<T>> auxQueue, ThreadedNode<T> newNode) {
        if (current.hasNoChildren()) {
            insertLeftChildOf(current, newNode);
            return true;
        }
        if (current.hasLeftChild()) auxQueue.enqueue((ThreadedNode<T>) current.getLeft());
        else {
            insertLeftChildOf(current, newNode);
            return true;
        }
        if (current.hasRightChild()) auxQueue.enqueue((ThreadedNode<T>) current.getRight());
        else {
            insertRightChildOf(current, newNode);
            return true;
        }
        return false;
    }
    private void insertLeftChildOf(ThreadedNode<T> parent, ThreadedNode<T> newChild) {
        transferLeftPointersFrom(parent, newChild);
        if (newChild.hasLeftChild()) {
            ThreadedNode<T> rightMostNode = searchRightMostNode((ThreadedNode<T>) newChild.getLeft());
            rightMostNode.setRight(newChild);
        }
    }

    private void transferLeftPointersFrom(ThreadedNode<T> parent, ThreadedNode<T> newChild) {
        newChild.setPredecessor(parent.getPredecessor());
        newChild.setLeft(parent.getLeft());
        newChild.setSuccessor(0);
        newChild.setRight(parent);
        parent.setLeft(newChild);
        parent.setPredecessor(1);
    }
    private void insertRightChildOf(ThreadedNode<T> parent, ThreadedNode<T> newChild) {
        transferRightPointersFrom(parent, newChild);
        if (newChild.hasRightChild()) {
            ThreadedNode<T> leftMostNode = searchLeftMostNode((ThreadedNode<T>) newChild.getRight());
            leftMostNode.setLeft(newChild);
        }
    }

    private void transferRightPointersFrom(ThreadedNode<T> parent, ThreadedNode<T> newChild) {
        newChild.setSuccessor(parent.getSuccessor());
        newChild.setRight(parent.getRight());
        newChild.setPredecessor(0);
        newChild.setLeft(parent);
        parent.setRight(newChild);
        parent.setSuccessor(1);
    }

    @Override
    public ThreadedNode<T> removeItem(ThreadedNode<T> nodeToRemove) {
        ThreadedNode<T> nodeCopy = null;
        if (this.isEmpty() || this.checkIfNull(nodeToRemove) || nodeToRemove.checkEquality((T) this.getRoot())) return nodeCopy;
        else {
            nodeCopy = new ThreadedNode<>(nodeToRemove.getData(), nodeToRemove.getNumber());
            if (this.checkIfOneNode()) {
                if (nodeToRemove.checkEquality((T) this.getRoot().getLeft())) {
                    nodeCopy = (ThreadedNode<T>) this.getRoot().getLeft();
                    this.getRoot().setLeft(this.getRoot());
                }
            }
            else this.executeDeletionCase(nodeToRemove);
        }
        this.decrementQuantityByOne();
        return nodeCopy;
    }
    @Override
    protected void removeLeaf(ThreadedNode<T> nodeToRemove) {
        ThreadedNode<T> parent = this.getParent(nodeToRemove);
        this.transferChildData(parent, nodeToRemove);
    }
    private ThreadedNode<T> getParent(ThreadedNode<T> child) {
        ThreadedNode<T> possibleParent = (ThreadedNode<T>) this.searchRightMostNode(child).getRight();
        if (!child.checkEquality((T) possibleParent.getLeft())) {
            possibleParent = (ThreadedNode<T>) this.searchLeftMostNode(child).getLeft();
        }
        return possibleParent;
    }
    @Override
    protected void transferChildData(ThreadedNode<T> parent, ThreadedNode<T> child) {
        if (!this.checkIfNull(parent)) {
            if (child.checkEquality((T) parent.getLeft())) {
                parent.setPredecessor(child.getPredecessor());
                parent.setLeft(child.getLeft());
                this.transferRightGrandChildren(parent,child);
           } else {
                parent.setSuccessor(child.getSuccessor());
                parent.setRight(child.getRight());
                this.transferLeftGrandChildren(parent,child);
            }
        }
    }
    private void transferRightGrandChildren(ThreadedNode<T> newParent, ThreadedNode<T> child) {
        ThreadedNode<T> grandChild;

        if (!child.hasNoChildren()) {
            if (child.hasRightChild()) {
                grandChild = (ThreadedNode<T>) child.getRight();
                grandChild.setLeft(newParent);
                if (!newParent.hasRightChild()) {
                    grandChild.setRight(newParent.getRight());
                    grandChild.setSuccessor(newParent.getSuccessor());
                }
            }
            if (child.hasLeftChild()) {
                grandChild = (ThreadedNode<T>) child.getLeft();
                grandChild.setRight(newParent);
            }
            newParent.setSuccessor(child.getSuccessor());
            newParent.setRight(child.getRight());
        }
    }
    private void transferLeftGrandChildren(ThreadedNode<T> newParent, ThreadedNode<T> child) {
        ThreadedNode<T> grandChild;

        if (!child.hasNoChildren()) {
            if (child.hasLeftChild()) {
                grandChild = (ThreadedNode<T>) child.getLeft();
                grandChild.setRight(newParent);
                if (!newParent.hasLeftChild()) {
                    grandChild.setLeft(newParent.getLeft());
                    grandChild.setPredecessor(newParent.getPredecessor());
                }
            }
            if (child.hasRightChild()) {
                grandChild = (ThreadedNode<T>) child.getRight();
                grandChild.setLeft(newParent);
            }
            newParent.setPredecessor(child.getPredecessor());
            newParent.setLeft(child.getLeft());
        }
    }

    @Override
    protected void removeWithOneChild(ThreadedNode<T> nodeToRemove) {
        ThreadedNode<T> child;
        if (nodeToRemove.hasRightChild() && nodeToRemove.getRight().hasNoChildren()) child = (ThreadedNode<T>) nodeToRemove.getRight();
        else if (nodeToRemove.hasLeftChild()) child = (ThreadedNode<T>) nodeToRemove.getLeft();
        else child = (ThreadedNode<T>) nodeToRemove.getRight();
        nodeToRemove.setData(child.getData());
        this.transferChildData(nodeToRemove, child);
    }
    @Override
    protected void removeInternalNode(ThreadedNode<T> nodeToRemove) {
        ThreadedNode<T> child = this.getDownwardsPredecessor(nodeToRemove);
        nodeToRemove.setData(child.getData());
        if (child.hasNoChildren()) this.removeLeaf(child);
        else this.removeWithOneChild(child);
    }
    private void transferUntilLastLevel(ThreadedNode<T> nodeToRemove, ThreadedNode<T> inOrderSuccessor) {
        if (!inOrderSuccessor.checkEquality((T) nodeToRemove.getRight())) {
            this.transferInOrderSuccessorData(nodeToRemove, inOrderSuccessor);
        } else {
            this.transferUntilNoChildren(inOrderSuccessor, nodeToRemove);
            if (!inOrderSuccessor.checkEquality((T) nodeToRemove.getLeft()) && !inOrderSuccessor.checkEquality((T) nodeToRemove.getRight())) {
                this.transferInOrderSuccessorData(nodeToRemove, inOrderSuccessor);
            } else {
                this.transferChildData(nodeToRemove, inOrderSuccessor);
            }
        }
    }
    private void transferInOrderSuccessorData(ThreadedNode<T> nodeToRemove, ThreadedNode<T> inOrderSuccessor) {
        ThreadedNode<T> rightChild = (ThreadedNode<T>) nodeToRemove.getRight();
        nodeToRemove.setData(inOrderSuccessor.getData());
        rightChild.setPredecessor(inOrderSuccessor.getPredecessor());
        rightChild.setLeft(inOrderSuccessor.getLeft());
        inOrderSuccessor.setRight(null);
        inOrderSuccessor.setLeft(null);
    }
    private void transferUntilNoChildren(ThreadedNode<T> inOrderSuccessor, ThreadedNode<T> previous) {
        while (!inOrderSuccessor.hasNoChildren()) {
            previous.setData(inOrderSuccessor.getData());
            previous = inOrderSuccessor;
            if (inOrderSuccessor.hasRightChild()) inOrderSuccessor = this.getInOrderSuccessor(inOrderSuccessor);
            else inOrderSuccessor = this.getInOrderSuccessor(previous);
        }
    }

    @Override
    protected boolean determineIfRightSubTree(ThreadedNode<T> nodeToRemove, ThreadedNode<T> root) {
        return false;
    }

    @Override
    public ThreadedNode<T> searchForItem(ThreadedNode<T> node) {
        ThreadedNode<T> current = (ThreadedNode<T>) this.getRoot().getLeft();
        if (!node.checkEquality((T) current)) {
            ArrayStack<ThreadedNode<T>> auxStack = this.createStackAndPush(current);
            while (!auxStack.isEmpty()) {
                current = auxStack.pop();
                if (current.getData() == node.getData() && current.getNumber() == node.getNumber()) return current;
                else if (!current.hasNoChildren()) {
                    if (current.hasRightChild()) auxStack.push((ThreadedNode<T>) current.getRight());
                    if (current.hasLeftChild()) auxStack.push((ThreadedNode<T>) current.getLeft());
                }
            }
        }
        return current;
    }

    @Override
    public void levelOrderTraversal() {
        if (this.isEmpty()) System.out.println("Tree is Empty");
        else if (this.checkIfOneNode()) System.out.println(String.format(" %s <-- ", this.getRoot().getLeft().getData()));
        else {
            CircularArrayQueue<ThreadedNode<T>> auxQueue = createQueueAndEnqueue(this.getRoot(), (ThreadedNode<T>) this.getRoot().getLeft());
            while (!auxQueue.isEmpty()) {
                ThreadedNode<T> current = auxQueue.deque();
                if (this.keepLevelTraversing(current, this.getRoot(), auxQueue)) {
                    if (!current.checkEquality((T) this.getRoot())) {
                        System.out.print(String.format(" %s <-- ", current.getData().toString()));
                        if (!current.hasNoChildren()) {
                            if (current.hasLeftChild()) auxQueue.enqueue((ThreadedNode<T>) current.getLeft());
                            if (current.hasRightChild()) auxQueue.enqueue((ThreadedNode<T>) current.getRight());
                        }
                    }
                }
            }
        }
    }

    @Override
    public void inOrderTraversal() {
        if (this.isEmpty()) System.out.println("Tree is empty");
        else if (this.checkIfOneNode()) System.out.print(String.format(" <-- %s", this.getRoot().getLeft().getData()));
        else {
            ThreadedNode<T> current = this.getInOrderSuccessor(this.getRoot());
            while (!this.checkIfNull(current) && !current.checkEquality((T) this.getRoot())) {
                System.out.print(String.format(" %s <-- ", current.getData()));
                current = this.getInOrderSuccessor(current);
            }
        }
    }
    @Override
    protected ThreadedNode<T> getInOrderSuccessor(ThreadedNode<T> startingNode) {
        ThreadedNode<T> successor;
        if (!startingNode.hasRightChild()) successor = (ThreadedNode<T>) startingNode.getRight();
        else {
            successor = (ThreadedNode<T>) startingNode.getRight();
            while (successor.hasLeftChild()) successor = (ThreadedNode<T>) successor.getLeft();
        }
        return successor;
    }

    @Override
    public void preOrderTraversal() {
        if (this.isEmpty()) System.out.println("Tree is empty");
        else if (this.checkIfOneNode()) System.out.print(String.format(" <-- %s", this.getRoot().getLeft().getData()));
        else {
            ThreadedNode<T> current = (ThreadedNode<T>) this.getRoot().getLeft();
            while (!this.checkIfNull(current) && !current.checkEquality((T) this.getRoot())) {
                System.out.print(String.format(" %s <-- ", current.getData()));
                current = getPreOrderSuccessor(current);
            }
        }
    }

    @Override
    public void postOrderTraversal() {
        if (this.isEmpty()) System.out.println("Tree is empty");
        else if (this.checkIfOneNode()) System.out.print(String.format(" <-- %s", this.getRoot().getLeft().getData()));
        else {
            ThreadedNode<T> current = this.getInOrderSuccessor(this.getRoot());
            while (current.notVisited()) {
                if (current.hasRightChild() && current.getRight().notVisited()) current = this.getInOrderSuccessor(current);
                else if (current.checkEquality((T) this.getRoot().getLeft())) {
                    System.out.print(String.format(" %s <-- ", current.getData()));
                    current.addTimesVisited();
                } else {
                    current.addTimesVisited();
                    System.out.print(String.format(" %s <-- ", current.getData()));
                    current = this.getParent(current);
                }
            }
            this.resetAllNodesVisited();
        }
    }

    @Override
    public int calculateNodeHeight(ThreadedNode<T> node, ThreadedNode<T> wildCard) {
        int nodesDepth = this.calculateNodeDepth(node, wildCard);
        if (nodesDepth < 0) return -1;
        return Math.abs(nodesDepth - calculateTreeHeight());
    }
    @Override
    public int calculateNodeDepth(ThreadedNode<T> node, ThreadedNode<T> wildCard) {
        int nodeDepth = -1;
        ThreadedNode<T> current = (ThreadedNode<T>) this.getRoot().getLeft();
        if (this.getQuantity() <= 1 || node.checkEquality((T) current)) nodeDepth++;
        else if (!this.checkIfNull(node)) {
            nodeDepth++;
            wildCard = this.getRoot();
            CircularArrayQueue<ThreadedNode<T>> auxQueue = createQueueAndEnqueue(wildCard, current);
            while (!auxQueue.isEmpty()) {
                current = auxQueue.deque();
                if (node.checkEquality((T) current)) return nodeDepth;
                else if (keepLevelTraversing(current, wildCard, auxQueue) && wildCard.checkEquality((T) current)) nodeDepth++;
                if (!current.hasNoChildren() && !wildCard.checkEquality((T) current)) {
                    if (current.hasLeftChild()) auxQueue.enqueue((ThreadedNode<T>) current.getLeft());
                    if (current.hasRightChild()) auxQueue.enqueue((ThreadedNode<T>) current.getRight());
                }
            }
            return -1;
        }
        return nodeDepth;
    }

    @Override
    public int calculateSubTreeNodeHeight(ThreadedNode<T> node, ThreadedNode<T> wildCard) {
        if (node.checkEquality((T) this.getRoot())) return this.calculateTreeHeight();
        int nodeHeight = 0;
        if (this.isEmpty() || checkIfNull(node) || checkIfOneNode()) return nodeHeight;
        CircularArrayQueue<ThreadedNode<T>> auxQueue = this.createQueueAndEnqueue(wildCard, node);
        while (!auxQueue.isEmpty()) {
            ThreadedNode<T> current = auxQueue.deque();
            if (this.keepLevelTraversing(current, wildCard, auxQueue) && wildCard.checkEquality((T) current)) {
                nodeHeight++;
                continue;
            }
            if (!current.checkEquality((T) this.getRoot()) && !current.hasNoChildren()) {
                if (current.hasLeftChild()) auxQueue.enqueue((ThreadedNode<T>) current.getLeft());
                if (current.hasRightChild()) auxQueue.enqueue((ThreadedNode<T>) current.getRight());
            }
        }
        return nodeHeight;
    }

    private void resetAllNodesVisited() {
        if (!this.isEmpty() && !this.checkIfOneNode()) {
            ThreadedNode<T> current = this.getInOrderSuccessor(this.getRoot());
            while (!this.checkIfNull(current) && !current.checkEquality((T) this.getRoot())) {
                current.resetTimesVisited();
                current = this.getInOrderSuccessor(current);
            }
        }
    }

/*    @Override
    public boolean checkEquality(ThreadedNode<T> firstNode, ThreadedNode<T> secondNode) {
        boolean equal = false;
        if (!this.checkIfNull(firstNode) && !this.checkIfNull(secondNode)) {
            if (firstNode.getLeft() == secondNode.getLeft()) {
                if (firstNode.getRight() == secondNode.getRight()) {
                    if (firstNode.getNumber() == secondNode.getNumber()) {
                        if (firstNode.getData() == secondNode.getData()) equal = true;
                    }
                }
            }
        }
        return equal;
    }*/



























}
