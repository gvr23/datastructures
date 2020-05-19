package edu.me.datastructure.tree.bynarytree.balancedtree;

import edu.me.datastructure.model.node.treenode.BinaryNode;
import edu.me.datastructure.model.node.treenode.GeneralBinarySearchNode;
import edu.me.datastructure.model.node.treenode.GeneralBinaryNode;
import edu.me.datastructure.stack.ArrayStack;
import edu.me.datastructure.tree.bynarytree.GeneralBinarySearchTree;

public abstract class GeneralBalancedTree<T> extends GeneralBinarySearchTree<T> {

    public GeneralBalancedTree(T root) {
        super(root);
    }

    @Override
    public void insert(T item) {
        super.insert(item);
        this.balanceTree(item);
    }
    protected void balanceTree(T evaluatedNode) {
        if (this.getQuantity() > 2) {
            ArrayStack<T> auxStack = this.createStackAndPush(this.getRoot());
            T current = auxStack.pop();
            int balanceFactor;
            GeneralBinarySearchNode currentMod = (GeneralBinarySearchNode) current;
            while (!this.checkIfNull((T) currentMod) && !currentMod.hasNoChildren() && !this.checkEquality((T) currentMod, evaluatedNode)) {
                auxStack.push((T) currentMod);
                if (this.determineIfRightSubTree(evaluatedNode, (T) currentMod)) currentMod = (GeneralBinarySearchNode) currentMod.getRight();
                else currentMod = (GeneralBinarySearchNode) currentMod.getLeft();
            }
            while (!auxStack.isEmpty()) {
                currentMod = (GeneralBinarySearchNode) auxStack.pop();
                balanceFactor = this.determineBalanceFactor((T) currentMod, (T) new GeneralBinaryNode<T>(null, -5));
                if (balanceFactor < -1 || balanceFactor > 1) {
                    this.executeBalancingCase(balanceFactor, (T) currentMod);
                    break;
                }
            }
        }
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

    @Override
    public T removeItem(T dataToRemove) {
        T nodeRemoved = super.removeItem(dataToRemove);
        if (!this.checkIfNull(nodeRemoved)) this.balanceTree(dataToRemove);
        return nodeRemoved;
    }

    protected void executeBalancingCase(int balanceFactor, T evaluatedNode) {
        if (balanceFactor >= 2) this.determineLeftHeavyRotation(evaluatedNode);
        else determineRightHeavyRotation(evaluatedNode);
    }
    private void determineLeftHeavyRotation(T evaluatedNode) {
        GeneralBinaryNode<T> evaluatedNodeMod = (GeneralBinaryNode<T>) evaluatedNode;
        if (evaluatedNodeMod.hasLeftChild() && (!evaluatedNodeMod.getLeft().hasRightChild() ||
                (evaluatedNodeMod.getLeft().hasAllChildren() && evaluatedNodeMod.hasLeftGrandChildren()))) this.rightRotation(evaluatedNode);
        else this.leftRightRotation(evaluatedNode);
    }
    private void determineRightHeavyRotation(T evaluatedNode) {
        GeneralBinaryNode<T> evaluatedNodeMod = (GeneralBinaryNode<T>) evaluatedNode;
        if (evaluatedNodeMod.hasRightChild() && (!evaluatedNodeMod.getRight().hasLeftChild() ||
                (evaluatedNodeMod.getRight().hasAllChildren() && evaluatedNodeMod.hasRightGrandChildren()))) this.leftRotation(evaluatedNode);
        else this.rightLeftRotation(evaluatedNode);
    }
    protected void leftRotation(T evaluatedNode) {
        GeneralBinaryNode<T> evaluatedNodeMod = (GeneralBinaryNode<T>) evaluatedNode;
        GeneralBinaryNode<T> parent = evaluatedNodeMod.getRight();
        if (this.checkEquality((T) evaluatedNodeMod, this.getRoot())) this.setRoot((T) parent);
        else {
            GeneralBinaryNode<T> grandFather = (GeneralBinaryNode<T>) this.searchCorrectParent(evaluatedNode);
            grandFather.setRight(parent);
        }
        evaluatedNodeMod.setRight(parent.getLeft());
        parent.setLeft(evaluatedNodeMod);
    }
    protected void rightRotation(T evaluatedNode) {
        GeneralBinaryNode<T> evaluatedNodeMod = (GeneralBinaryNode<T>) evaluatedNode;
        GeneralBinaryNode<T> parent = evaluatedNodeMod.getLeft();
        if (this.checkEquality((T) evaluatedNodeMod, this.getRoot())) this.setRoot((T) parent);
        else {
            GeneralBinaryNode<T> grandFather = (GeneralBinaryNode<T>) this.getInOrderSuccessor(evaluatedNode);
            grandFather.setLeft(parent);
        }
        evaluatedNodeMod.setLeft(parent.getRight());
        parent.setRight(evaluatedNodeMod);
    }
    protected void leftRightRotation(T evaluatedNode) {
        GeneralBinaryNode<T> evaluatedNodeMod = (GeneralBinaryNode<T>) evaluatedNode;
        GeneralBinaryNode<T> leftChild = evaluatedNodeMod.getLeft();
        GeneralBinaryNode<T> parent = leftChild.getRight();
        GeneralBinaryNode<T> grandFather = (GeneralBinaryNode<T>) this.searchCorrectParent(evaluatedNode);

        leftChild.setRight(parent.getLeft());
        evaluatedNodeMod.setLeft(parent.getRight());
        parent.setLeft(leftChild);
        parent.setRight(evaluatedNodeMod);

        if (this.checkEquality((T) grandFather.getRight(), evaluatedNode)) grandFather.setRight(parent);
        else grandFather.setLeft(parent);
    }
    protected void rightLeftRotation(T evaluatedNode) {
        GeneralBinaryNode<T> evaluatedNodeMod = (GeneralBinaryNode<T>) evaluatedNode;
        GeneralBinaryNode<T> rightChild = evaluatedNodeMod.getRight();
        GeneralBinaryNode<T> parent = rightChild.getLeft();
        GeneralBinaryNode<T> grandFather = (GeneralBinaryNode<T>) this.searchCorrectParent(evaluatedNode);

        rightChild.setLeft(parent.getRight());
        evaluatedNodeMod.setRight(parent.getLeft());
        parent.setLeft(evaluatedNodeMod);
        parent.setRight(rightChild);

        if (this.checkEquality((T) grandFather.getLeft(), evaluatedNode)) grandFather.setLeft(parent);
        else grandFather.setRight(parent);
    }
}
