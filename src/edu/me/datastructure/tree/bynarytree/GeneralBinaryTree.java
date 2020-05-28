package edu.me.datastructure.tree.bynarytree;

import edu.me.datastructure.linkedlist.SinglyLinkedList;
import edu.me.datastructure.model.node.linkedlistnode.SinglyLinkedListNode;
import edu.me.datastructure.model.node.treenode.BinaryNode;
import edu.me.datastructure.model.node.treenode.GeneralBinaryNode;
import edu.me.datastructure.model.node.treenode.ThreadedNode;
import edu.me.datastructure.queue.CircularArrayQueue;
import edu.me.datastructure.stack.ArrayStack;
import edu.me.datastructure.tree.GeneralTree;
import edu.me.utils.Helper;

public abstract class GeneralBinaryTree<T> extends GeneralTree<T> {

    public GeneralBinaryTree(T root) {
        super(root);
    }

    @Override
    public void insert(T item) {
        if (this.isEmpty()) this.setRoot(item);
        else levelOrderInsert(item);
        this.incrementQuantityByOne();
    }
    protected void levelOrderInsert(T newNode) {
        CircularArrayQueue<GeneralBinaryNode<T>> auxQueue = (CircularArrayQueue<GeneralBinaryNode<T>>) createQueueAndEnqueue(null, this.getRoot());
        while (!auxQueue.isEmpty()) {
            GeneralBinaryNode<T> current = auxQueue.deque();
            if (current.hasLeftChild()) auxQueue.enqueue(current.getLeft());
            else {
                current.setLeft((GeneralBinaryNode<T>) newNode);
                break;
            }
            if (current.hasRightChild()) auxQueue.enqueue(current.getRight());
            else {
                current.setRight((GeneralBinaryNode<T>) newNode);
                break;
            }
        }
    }
    protected CircularArrayQueue<T> createQueueAndEnqueue(T wildCard, T firstNode) {
        CircularArrayQueue<T> auxQueue;
        if (!checkIfNull(firstNode) && !checkIfNull(wildCard)) {
            auxQueue = new CircularArrayQueue<>(this.getQuantity() + 1);
            auxQueue.enqueue(firstNode);
            auxQueue.enqueue(wildCard);
            return auxQueue;
        }
        T chosen = null;
        if (!checkIfNull(firstNode) && checkIfNull(wildCard)) chosen = firstNode;
        else if (!checkIfNull(wildCard) && checkIfNull(firstNode)) chosen = wildCard;
        auxQueue = new CircularArrayQueue<>(this.getQuantity());
        auxQueue.enqueue(chosen);
        return auxQueue;
    }

    public T removeItem(T dataToRemove) {
        T nodeToRemove = this.searchForItem(dataToRemove);
        if (!this.checkIfNull(nodeToRemove) && !this.isEmpty()) {
            this.executeDeletionCase(nodeToRemove);
            this.decrementQuantityByOne();
        }
        return nodeToRemove;
    }
    protected void executeDeletionCase(T nodeToRemove) {
        if (((GeneralBinaryNode<T>)nodeToRemove).hasNoChildren()) this.removeLeaf(nodeToRemove);
        else if (((GeneralBinaryNode<T>)nodeToRemove).hasAllChildren()) this.removeInternalNode(nodeToRemove);
        else this.removeWithOneChild(nodeToRemove);
    }
    protected abstract void removeLeaf(T nodeToRemove);
    protected abstract void removeInternalNode(T nodeToRemove);
    protected abstract void removeWithOneChild(T nodeToRemove);

    @Override
    public void updateRoot(T newRoot) {
        if (!this.isEmpty()) ((GeneralBinaryNode<T>) this.getRoot()).setData(((GeneralBinaryNode<T>) newRoot).getData());
    }

    public void preOrderTraversal() {
        if (this.isEmpty()) System.out.println("Tree is Empty");
        else if (this.checkIfOneNode())System.out.print(String.format(" %s <-- ", ((GeneralBinaryNode<T>)this.getRoot()).getData()));
        else {
            ArrayStack<T> auxStack = this.createStackAndPush(this.getRoot());
            while (!auxStack.isEmpty()) {
                this.prePopAndInsertChildren(auxStack);
            }
        }
    }
    protected ArrayStack<T> createStackAndPush(T startingNode) {
        ArrayStack<T> auxStack = new ArrayStack<>(this.getQuantity());
        auxStack.push(startingNode);
        return auxStack;
    }
    private void prePopAndInsertChildren(ArrayStack<T> auxStack) {
        GeneralBinaryNode<T> current = (GeneralBinaryNode<T>) auxStack.pop();
        System.out.print(String.format(" %s <-- ", current.getData()));
        if (!current.hasNoChildren()) {
            if (current.hasRightChild()) auxStack.push((T) current.getRight());
            if (current.hasLeftChild()) auxStack.push((T) current.getLeft());
        }
    }

    public void inOrderTraversal() {
        if (this.isEmpty()) System.out.println("Tree is Empty");
        else if (this.checkIfOneNode()) System.out.println((this.getRoot()).toString());
        else {
            ArrayStack<T> auxStack = this.createStackAndPush(this.getRoot());
            while (!auxStack.isEmpty()) {
               this.inPopAndInsertChildren(auxStack);
            }
        }
    }
    private void inPopAndInsertChildren(ArrayStack<T> auxStack) {
        GeneralBinaryNode<T> current = (GeneralBinaryNode<T>) auxStack.pop();
        current.addTimesVisited();
        if (!current.hasNoChildren() && current.getTimesVisited() < 2) {
            if (current.hasRightChild()) auxStack.push((T) current.getRight());
            auxStack.push((T) current);
            if (current.hasLeftChild()) auxStack.push((T) current.getLeft());
        } else {
            current.resetTimesVisited();
            System.out.print(String.format(" %s <-- ", current.getData().toString()));
        }
    }

    public void postOrderTraversal() {
        if (this.isEmpty()) System.out.println("Tree is Empty");
        else if (this.checkIfOneNode())System.out.print(String.format(" %s <-- ", ((GeneralBinaryNode<T>)this.getRoot()).getData()));
        else {
            ArrayStack<T> auxStack = this.createStackAndPush(this.getRoot());
            while (!auxStack.isEmpty()) {
                this.postPopAndInsertChildren(auxStack);
            }
        }
    }
    private void postPopAndInsertChildren(ArrayStack<T> auxStack) {
        GeneralBinaryNode<T> current = (GeneralBinaryNode<T>) auxStack.pop();
        current.addTimesVisited();
        if (!current.hasNoChildren() && current.getTimesVisited() < 2) {
            auxStack.push((T) current);
            if (current.hasRightChild()) auxStack.push((T) current.getRight());
            if (current.hasLeftChild()) auxStack.push((T) current.getLeft());
        } else {
            current.resetTimesVisited();
            System.out.print(String.format(" %s <-- ", current.getData().toString()));
        }
    }

    public void levelOrderTraversal() {
        if (this.isEmpty()) System.out.println("Tree is Empty");
        else if (this.checkIfOneNode())System.out.print(String.format(" %s <-- ", ((GeneralBinaryNode<T>)this.getRoot()).getData()));
        else {
            CircularArrayQueue<T> auxQueue = this.createQueueAndEnqueue(null, this.getRoot());
            while (!auxQueue.isEmpty()) {
                this.dequeAndInsertChildren(auxQueue);
            }
        }
    }
    protected void dequeAndInsertChildren(CircularArrayQueue<T> auxQueue) {
        GeneralBinaryNode<T> current = (GeneralBinaryNode<T>) auxQueue.deque();
        System.out.print(String.format(" %s <-- ", current.getData().toString()));
        if (!current.hasNoChildren()) {
            if (current.hasLeftChild()) auxQueue.enqueue((T) current.getLeft());
            if (current.hasRightChild()) auxQueue.enqueue((T) current.getRight());
        }
    }

    public int calculateTreeHeight() {
        return Helper.log2nlz(this.getQuantity());
    }

    public int calculateTreeDegree() {
        int treeDegree = 0;
        if (this.getQuantity() <= 1) return treeDegree;

        ArrayStack<GeneralBinaryNode<T>> auxStack = new ArrayStack<>(this.getQuantity());
        int nodeDegree;
        int MAXIMUM_CHILDREN_ALLOWED = 2;
        while (!auxStack.isEmpty() && treeDegree < MAXIMUM_CHILDREN_ALLOWED) {
            GeneralBinaryNode<T> current = auxStack.pop();
            nodeDegree = current.calculateDegree();
            if (nodeDegree == 0) continue;

            if (treeDegree < nodeDegree) treeDegree = nodeDegree;
            if (current.hasLeftChild()) auxStack.push(current.getLeft());
            if (current.hasRightChild()) auxStack.push(current.getRight());
        }
        return treeDegree;
    }

    public int calculateTreeLeafNodes() {
        int numberOfLeafNodes = 0;
        if (this.isEmpty()) return numberOfLeafNodes;
        else if (this.getQuantity() == 1) numberOfLeafNodes = 1;
        else {
            ArrayStack<GeneralBinaryNode<T>> auxStack = new ArrayStack<>(this.getQuantity());
            auxStack.push((GeneralBinaryNode<T>) this.getRoot());
            while (!auxStack.isEmpty()) {
                GeneralBinaryNode<T> current = auxStack.pop();
                if (current.hasNoChildren()) {
                    numberOfLeafNodes++;
                    continue;
                }
                if (current.hasLeftChild()) auxStack.push(current.getLeft());
                if (current.hasRightChild()) auxStack.push(current.getRight());
            }
        }
        return numberOfLeafNodes;
    }

    protected T getParentFromLeaf(T leafNode) {
        CircularArrayQueue<GeneralBinaryNode<T>> auxQueue = (CircularArrayQueue<GeneralBinaryNode<T>>) this.createQueueAndEnqueue(null, this.getRoot());
        GeneralBinaryNode<T> current = null;
        while (!auxQueue.isEmpty()) {
            current = auxQueue.deque();
            if (this.checkEquality(leafNode, (T) current.getLeft()) || this.checkEquality(leafNode, (T) current.getRight())) break;
            if (!current.hasNoChildren()) {
                if (current.hasLeftChild()) auxQueue.enqueue(current.getLeft());
                if (current.hasRightChild()) auxQueue.enqueue(current.getRight());
            }
        }
        return (T) current;
    }

    protected GeneralBinaryNode<T> getParentLeafFromNode(T internalNode) {
        boolean foundLeaf = false;
        GeneralBinaryNode<T> internalMod = (GeneralBinaryNode<T>) internalNode;
        SinglyLinkedList<GeneralBinaryNode<T>> auxSinglyLinkedList = new SinglyLinkedList<GeneralBinaryNode<T>>((SinglyLinkedListNode<GeneralBinaryNode<T>>) new SinglyLinkedListNode<>(internalMod.getData()));
        GeneralBinaryNode<T> nodeToRemove = auxSinglyLinkedList.getHead().getData();
        GeneralBinaryNode<T> leafNode = null;

        while (!auxSinglyLinkedList.isEmpty()) {
            GeneralBinaryNode<T> current = auxSinglyLinkedList.removeAtEnd().getData();

            if (this.checkEquality((T) current, internalNode)) auxSinglyLinkedList.insertAtBeginning((SinglyLinkedListNode<GeneralBinaryNode<T>>) new SinglyLinkedListNode<>(current.getData()));
            if (!this.checkIfNull((T) current.getRight())) {
                if (current.getRight().hasNoChildren()) {
                    leafNode = current.getRight();
                    foundLeaf = true;
                }
                auxSinglyLinkedList.insertAfter(auxSinglyLinkedList.getHead(), new SinglyLinkedListNode<>(current.getRight()));
            }
            if (!this.checkIfNull((T) current.getLeft())) {
                if (current.getLeft().hasNoChildren()) {
                    leafNode = current.getLeft();
                    foundLeaf = true;
                }
                auxSinglyLinkedList.insertAfter(auxSinglyLinkedList.getHead(), new SinglyLinkedListNode<>(current.getLeft()));
            }

            if (foundLeaf) {
                nodeToRemove.setData(leafNode.getData());
                return current;
            }


        }
        return null;
    }

    protected boolean keepLevelTraversing(T evaluatedNode, T wildCard, CircularArrayQueue<T> auxQueue) {
        boolean repeat = true;
        if (checkEquality(evaluatedNode, wildCard)) {
            if (!auxQueue.isEmpty()) auxQueue.enqueue(this.getRoot());
            else repeat = false;
        }
        return repeat;
    }

    protected T getInOrderSuccessor(T startingNode) {
        GeneralBinaryNode<T> successor = null;
        GeneralBinaryNode<T> start = (GeneralBinaryNode<T>) startingNode;
        if (!this.checkIfNull(startingNode) && !this.isEmpty() && !this.checkIfOneNode()) {
            successor = start.getRight();
            if (start.hasRightChild()) successor = (GeneralBinaryNode<T>) this.searchLeftMostNode((T) successor);
        }
        return (T) successor;
    }
    protected T searchLeftMostNode(T right) {
        GeneralBinaryNode<T> leftMostNode = (GeneralBinaryNode<T>) right;
        while (leftMostNode.hasLeftChild()) leftMostNode = leftMostNode.getLeft();
        return (T) leftMostNode;
    }

    protected T getPreOrderSuccessor(T startingNode) {
        GeneralBinaryNode<T> current = (GeneralBinaryNode<T>) startingNode;
        if (current.hasLeftChild()) current = current.getLeft();
        else {
            while (!current.hasRightChild()) current = (GeneralBinaryNode<T>) this.getInOrderSuccessor((T) current);
            current = current.getRight();
        }
        return (T) current;
    }

    protected T searchRightMostNode(T left) {
        GeneralBinaryNode<T> rightMostNode = (GeneralBinaryNode<T>) left;
        while (rightMostNode.hasRightChild()) rightMostNode = rightMostNode.getRight();
        return (T) rightMostNode;
    }

    protected int determineBalanceFactor(T evaluatedNode, T wildCard) {
        GeneralBinaryNode<T> node = (GeneralBinaryNode<T>) evaluatedNode;
        if (!node.hasNoChildren() && this.getQuantity() > 1) {
            int leftSideHeight = (node.hasLeftChild()) ? 1 : 0;
            int rightSideHeight = (node.hasRightChild()) ? 1 : 0;
            leftSideHeight += this.calculateSubTreeNodeHeight((T) node.getLeft(), wildCard);
            rightSideHeight += this.calculateSubTreeNodeHeight((T) node.getRight(), wildCard);
            return leftSideHeight - rightSideHeight;
        }
        return 0;
    }

    protected T getDownwardsPredecessor(T nodeToRemove) {
        GeneralBinaryNode<T> predecessor = (GeneralBinaryNode<T>) nodeToRemove;
        if (predecessor.hasLeftChild()) predecessor = predecessor.getLeft();
        predecessor = (GeneralBinaryNode<T>) this.searchRightMostNode((T) predecessor);
        return (T) predecessor;
    }

    protected abstract boolean determineIfRightSubTree(T nodeToRemove, T root);
    protected abstract void transferChildData(T parent, T child);

    @Override
    public boolean checkEquality(T firstNode, T secondNode) {
        boolean equal = false;
        if (!this.checkIfNull(firstNode) && !this.checkIfNull(secondNode)) {
            GeneralBinaryNode<T> firstMod = (GeneralBinaryNode<T>) firstNode;
            GeneralBinaryNode<T> secondMod = (GeneralBinaryNode<T>) secondNode;
            if (firstMod.getNumber() == secondMod.getNumber() && firstMod.getData().equals(((GeneralBinaryNode<T>) secondNode).getData())) {
                equal = true;
            }
        }
        return equal;
    }
}
