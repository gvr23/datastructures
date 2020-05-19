package edu.me.datastructure.tree.bynarytree.balancedtree;

import edu.me.datastructure.model.node.treenode.RedBlackNode;
import edu.me.datastructure.queue.CircularArrayQueue;

/*Details: Roughly height Balanced
* Properties:
*   1. Root always black.
*   2. Every node is either Red or Black.
*   3. Every leaf node has black null children.
*   4. There are no adjacent red nodes.
*   5. Same number of black nodes for any path starting at a certain node.
*   6. AVL tree are subset of red black trees but not the other way.
* */

public class RedBlackTree<T> extends GeneralBalancedTree<RedBlackNode<T>> {
    private RedBlackNode<T> newestNode;
    public RedBlackTree(RedBlackNode<T> root) {
        super(root);
        this.decideNodeColor(root);
        this.newestNode = this.getRoot();
    }

    @Override
    public void insert(RedBlackNode<T> item) {
        if (!this.checkIfNull(item)) {
            this.decideNodeColor(item);
            RedBlackNode<T> parent = this.searchCorrectParent(item);
            item.setParent(parent);
            this.executeUntilNoConflict(parent, item);
            this.incrementQuantityByOne();
        } else System.out.println("Item provided is invalid");
    }
    private void decideNodeColor(RedBlackNode<T> node) {
        RedBlackNode.NodeColor color;
        if (this.checkIfOneNode() && node.checkEquality((T) this.getRoot())) color = RedBlackNode.NodeColor.BLACK;
        else color = RedBlackNode.NodeColor.RED;

        node.setColor(color);
    }
    private void executeUntilNoConflict(RedBlackNode<T> parent, RedBlackNode<T> child) {
        boolean isThereRedRedConflict = parent.getColor().equals(RedBlackNode.NodeColor.RED) && child.getColor().equals(RedBlackNode.NodeColor.RED);
        this.newestNode = child;
        RedBlackNode<T> uncle;
        while (isThereRedRedConflict && !parent.checkEquality((T) this.getRoot())) {
            uncle = this.getSibling(parent);
            parent = this.determineUncleSituation(uncle, parent);
            if (parent.getLeft().getNumber() != child.getNumber()  && parent.getRight().getNumber() != child.getNumber()) {
                RedBlackNode<T> rightChild = (RedBlackNode<T>) parent.getRight();
                RedBlackNode<T> leftChild = (RedBlackNode<T>) parent.getLeft();
                if (!rightChild.isBlack()) child = rightChild;
                else child = leftChild;
            }
            isThereRedRedConflict = parent.getColor().equals(RedBlackNode.NodeColor.RED) && child.getColor().equals(RedBlackNode.NodeColor.RED);
        }
    }
    private RedBlackNode<T>  getSibling(RedBlackNode<T> parent) {
        RedBlackNode<T> grandFather = this.searchCorrectParent(parent);
        RedBlackNode<T> brother;
        if (parent.checkEquality((T) grandFather.getLeft())) brother = (RedBlackNode<T>) grandFather.getRight();
        else brother = (RedBlackNode<T>) grandFather.getLeft();

        return brother;
    }
    private RedBlackNode<T> determineUncleSituation(RedBlackNode<T> uncle, RedBlackNode<T> parent) {
        RedBlackNode<T> grandFather = this.searchCorrectParent(parent);
        if (this.checkIfNull(uncle) || uncle.isBlack()) {
            this.blackUncleOrNull(parent, grandFather);
        }
        else {
            this.redUncle(uncle, parent);
            if (!grandFather.checkEquality((T) this.getRoot())) grandFather.reColor();
        }
        return this.searchCorrectParent(grandFather);
    }
    private void blackUncleOrNull(RedBlackNode<T> parent, RedBlackNode<T> grandFather) {
        this.balanceTree(grandFather);
        this.chooseReColorCase(parent, grandFather);
    }
    private void redUncle(RedBlackNode<T> uncle, RedBlackNode<T> parent) {
        parent.reColor();
        uncle.reColor();
    }
    private void chooseReColorCase(RedBlackNode<T> parent, RedBlackNode<T> grandFather) {
        RedBlackNode<T> child = this.newestNode;
        boolean reColored = this.simpleRotationReColor(parent, grandFather);
        if (!reColored) this.compositeRotationReColor(child, grandFather);
    }
    private boolean compositeRotationReColor(RedBlackNode<T> child, RedBlackNode<T> grandFather) {
        boolean reColored = false;
        if (!child.hasNoChildren()) {
            if ( (child.hasRightChild() && child.getRight().getData().equals(grandFather.getData()) ||
                    (child.hasLeftChild() && child.getLeft().getData().equals(grandFather.getData())))
            ) {
                child.reColor();
                grandFather.reColor();
                reColored = true;
            }
        }
        return reColored;
    }
    private boolean simpleRotationReColor(RedBlackNode<T> parent, RedBlackNode<T> grandFather) {
        boolean reColored = false;
        if (!parent.hasNoChildren()) {
            if ((parent.hasLeftChild() && parent.getLeft().getData().equals(grandFather.getData()) ||
                    (parent.hasRightChild() && parent.getRight().getData().equals(grandFather.getData())))
            ) {
                parent.reColor();
                grandFather.reColor();
                reColored = true;
            }
        }
        return reColored;
    }
    @Override
    protected void balanceTree(RedBlackNode<T> evaluatedNode) {
        if (this.getQuantity() > 2) {
            int balanceFactor = this.determineBalanceFactor(evaluatedNode, new RedBlackNode<>(null, -5));
            this.executeBalancingCase(balanceFactor, evaluatedNode);
        }
    }
    @Override
    protected void rightRotation(RedBlackNode<T> evaluatedNode) {
        if (evaluatedNode.checkEquality((T) this.getRoot())) this.setRoot(evaluatedNode);
        else {
            RedBlackNode<T> grandFather = this.searchCorrectParent(evaluatedNode);
            RedBlackNode<T> parent = (RedBlackNode<T>) evaluatedNode.getLeft();
            evaluatedNode.setLeft(parent.getRight());
            parent.setRight(evaluatedNode);
            grandFather.setLeft(parent);
        }
    }

    @Override
    public RedBlackNode<T> removeItem(RedBlackNode<T> dataToRemove) {
        RedBlackNode<T> nodeToRemove = this.searchForItem(dataToRemove);
        if (!this.checkIfNull(nodeToRemove) && !this.isEmpty()) {
            this.executeDeletionCase(nodeToRemove);
            this.decrementQuantityByOne();
        }
        return nodeToRemove;
    }
    @Override
    protected void removeWithOneChild(RedBlackNode<T> nodeToRemove) {
        super.removeWithOneChild(nodeToRemove);
    }
    @Override
    protected void removeInternalNode(RedBlackNode<T> nodeToRemove) {
        super.removeInternalNode(nodeToRemove);
    }

    @Override
    protected void dequeAndInsertChildren(CircularArrayQueue<RedBlackNode<T>> auxQueue) {
        RedBlackNode<T> current = auxQueue.deque();
        current.printData();
        System.out.print(" <-- ");
        if (!current.hasNoChildren()) {
            if (current.hasLeftChild()) auxQueue.enqueue((RedBlackNode<T>) current.getLeft());
            if (current.hasRightChild()) auxQueue.enqueue((RedBlackNode<T>) current.getRight());
        }
    }
}