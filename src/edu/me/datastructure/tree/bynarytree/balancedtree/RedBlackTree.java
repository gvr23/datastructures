package edu.me.datastructure.tree.bynarytree.balancedtree;

import edu.me.datastructure.model.node.treenode.GeneralBinaryNode;
import edu.me.datastructure.model.node.treenode.GeneralBinarySearchNode;
import edu.me.datastructure.model.node.treenode.RedBlackNode;
import edu.me.datastructure.queue.CircularArrayQueue;

import java.awt.*;

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
    private RedBlackNode<T> lastNodeRecolored;
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
            item.setParent((T) parent);
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
            if ((parent.hasLeftChild() && parent.getLeft().getNumber() != child.getNumber())  && (parent.hasRightChild() && parent.getRight().getNumber() != child.getNumber())) {
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
        if (parent.isLeftChild((T) grandFather)) brother = (RedBlackNode<T>) grandFather.getRight();
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
        if (this.getQuantity() >= 2) {
            int balanceFactor = this.determineBalanceFactor(evaluatedNode, (RedBlackNode<T>) new RedBlackNode<>(-5, -5));
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
            if (evaluatedNode.isLeftChild((T) grandFather)) grandFather.setLeft(parent); else grandFather.setRight(parent);
        }
    }

    @Override
    public RedBlackNode<T> removeItem(RedBlackNode<T> dataToRemove) {
        RedBlackNode<T> nodeToRemove = this.searchForItem(dataToRemove);
        if (!this.checkIfNull(nodeToRemove) && !this.isEmpty()) {
            this.lastNodeRecolored = nodeToRemove;
            this.executeDeletionCase(nodeToRemove);
            if (!this.isEmpty() && !this.getRoot().isBlack()) this.getRoot().setColor(RedBlackNode.NodeColor.BLACK);
            this.decrementQuantityByOne();
        }
        return nodeToRemove;
    }

    @Override
    protected void executeDeletionCase(RedBlackNode<T> nodeToRemove) {
        if (nodeToRemove.hasNoChildren()) this.removeLeaf(nodeToRemove);
        else this.removeInternalNode(nodeToRemove);
    }

    private void executeRecoloringCases(RedBlackNode<T> nodeRemoved, RedBlackNode<T> formerParent, RedBlackNode<T> formerBrother) {
        if (this.checkIfNull(nodeRemoved)) {
            if (!this.checkIfNull(formerBrother) && formerBrother.isBlack()) this.executeBlackNodeCases(formerParent, formerBrother, nodeRemoved);
            else this.redBrotherCase(formerParent, formerBrother, nodeRemoved);
        } else {
            if (nodeRemoved.isBlack()) nodeRemoved.setColor(RedBlackNode.NodeColor.DOUBLEBLACK);
            if (formerParent.isBlack()) formerParent.setColor(RedBlackNode.NodeColor.DOUBLEBLACK);
            boolean doubleBlackExists = formerParent.isDoubleBlack() || nodeRemoved.isDoubleBlack();
            if (doubleBlackExists) {
                if (!formerParent.checkEquality((T) this.getRoot())) {
                    if (this.checkIfNull(nodeRemoved) || nodeRemoved.isBlack() || nodeRemoved.isDoubleBlack()) {
                        if (this.checkIfNull(formerBrother)) {
                            formerBrother = this.getSibling(formerParent);
                            this.executeBlackNodeCases(formerParent, formerBrother, nodeRemoved);
                        }
                        else {
                            if (formerBrother.isBlack()) this.executeBlackNodeCases(formerParent, formerBrother, nodeRemoved);
                            else this.redBrotherCase(formerParent, formerBrother, nodeRemoved);
                        }
                    }
                } else {
                   if (nodeRemoved.hasNoChildren()) {
                       if (nodeRemoved.isLeftChild((T) formerParent)) formerParent.setLeft(null);
                       else formerParent.setRight(null);
                   }
                   nodeRemoved.setColor(RedBlackNode.NodeColor.BLACK);
                   if (!this.checkIfNull(formerBrother)) {
                       if (formerBrother.isBlack()) formerBrother.setColor(RedBlackNode.NodeColor.RED);
                       else formerBrother.setColor(RedBlackNode.NodeColor.BLACK);
                   }
                }
            }
        }
    }

    @Override
    protected void removeLeaf(RedBlackNode<T> nodeToRemove) {
        RedBlackNode<T> parent;
        RedBlackNode<T> brother;
        if (nodeToRemove.isRed()) super.removeLeaf(nodeToRemove);
        else {
            parent = this.searchCorrectParent(nodeToRemove);
            brother = (RedBlackNode<T>) ((nodeToRemove.isLeftChild((T) parent)) ? parent.getRight() : parent.getLeft());
            if (nodeToRemove.isBlack()) nodeToRemove.setColor(RedBlackNode.NodeColor.DOUBLEBLACK);
            else {
                super.removeLeaf(nodeToRemove);
                nodeToRemove = null;
            }
            this.executeRecoloringCases(nodeToRemove, parent, brother);
        }
    }
    @Override
    protected void transferChildData(RedBlackNode<T> parent, RedBlackNode<T> child) {
        boolean executed;
        RedBlackNode<T> brother;
        if (this.determineIfRightSubTree(child, parent)) {
            parent.setData(child.getData());
            if (parent.hasRightChild() && parent.getRight().checkEquality((T) child)) {
                brother = (RedBlackNode<T>) parent.getLeft();
                executed = this.executeRedNodeCase(child); // todo: change
                if (!executed) {
                    parent.setRight(null);
                    this.executeRecoloringCases(child, parent, brother);
                }
                    /*parent.setRight(child.getRight()); // todo: another change
                    if (child.hasLeftChild()) {
                        RedBlackNode<T> leftChild = (RedBlackNode<T>) child.getLeft();
                        RedBlackNode.NodeColor leftColor = leftChild.getColor();
                        this.decrementQuantityByOne();
                        this.insert(leftChild);
                        leftChild.setColor(leftColor);
                    }*/
            }else this.executeDeletionCase(child);
        }
        else {
            parent.setData(child.getData());
            if (parent.hasLeftChild() && parent.getLeft().checkEquality((T) child)) {
                brother = (RedBlackNode<T>) parent.getRight();
                executed = this.executeRedNodeCase(child); // todo: change
                if (!executed) {
                    parent.setLeft(null);
                    this.executeRecoloringCases(null, parent, brother);
                }
                    /*parent.setRight(child.getRight()); // todo: another change
                    if (child.hasLeftChild()) {
                        RedBlackNode<T> leftChild = (RedBlackNode<T>) child.getLeft();
                        RedBlackNode.NodeColor leftColor = leftChild.getColor();
                        this.decrementQuantityByOne();
                        this.insert(leftChild);
                        leftChild.setColor(leftColor);
                    }*/
            } else this.executeDeletionCase(child);
           /* parent.setData(child.getData());
            if (parent.getLeft().checkEquality((T) child)) {
                executed = this.executeRedNodeCase(child); // todo change
                if (!executed && !child.hasNoChildren()) {
                    parent.setLeft(child.getLeft()); // todo: another change
                    if (child.hasRightChild()) {
                        RedBlackNode<T> rightChild = (RedBlackNode<T>) child.getLeft();
                        RedBlackNode.NodeColor rightColor = rightChild.getColor();
                        this.decrementQuantityByOne();
                        this.insert(rightChild);
                        rightChild.setColor(rightColor);
                    }
                }
            } else this.executeDeletionCase(child);*/
        }
    }

    /*@Override
                protected void executeDeletionCase(RedBlackNode<T> nodeToRemove) {
                    if (!this.executeRedNodeCase(nodeToRemove)) {
                        RedBlackNode<T> parent = this.searchCorrectParent(nodeToRemove);
                        RedBlackNode<T> brother = this.getSibling(nodeToRemove);
                        if (!this.executeBlackNodeCases(parent, brother, nodeToRemove)) this.redBrotherCase(parent, brother, nodeToRemove);
                        else nodeToRemove.setColor(RedBlackNode.NodeColor.BLACK);
                    }
                }*/
    private boolean executeRedNodeCase(RedBlackNode<T> evaluatedNode) {
        boolean executed = false;
        if (evaluatedNode.isRed()) {
            if (!evaluatedNode.hasNoChildren()) {
                this.removeInternalNode(evaluatedNode);
            } else this.removeLeaf(evaluatedNode);
            executed = true;
        }
        return executed;
    }
    private boolean executeBlackNodeCases(RedBlackNode<T> parent, RedBlackNode<T> brother, RedBlackNode<T> doubleBlackChild) {
        boolean execute = false;
        if (!this.checkIfNull(brother)) {
            execute = true;
            RedBlackNode<T> rightChild = (RedBlackNode<T>) brother.getRight();
            RedBlackNode<T> leftChild = (RedBlackNode<T>) brother.getLeft();

            if (this.checkIfFamilyIsBlack(brother)) this.blackFamilyCase(parent, brother);
            else {
                if (this.determineIfRightSubTree(brother, parent)) {
                    if (rightChild.isBlack()) {
                        if (leftChild.isRed()) this.blackBrotherNearRedNephewCase(brother, leftChild);
                        else {
                            RedBlackNode<T> doubleBlackNode = (RedBlackNode<T>) parent.getLeft();
                            this.blackBrotherRedFarNephewCase(brother, doubleBlackNode);
                        }
                    }
                } else {
                   if (!this.checkIfNull(leftChild)) {
                       if (leftChild.isBlack()) {
                           if (rightChild.isRed()) this.blackBrotherNearRedNephewCase(brother, rightChild);
                           else {
                               RedBlackNode<T> doubleBlackNode = (RedBlackNode<T>) parent.getRight();
                               this.blackBrotherRedFarNephewCase(brother, doubleBlackNode);
                           }
                       }
                   } else this.blackBrotherNearRedNephewCase(brother, rightChild);
                }

            }
        } else this.redBrotherCase(parent, brother, doubleBlackChild);
        return execute;
    }
    private void blackFamilyCase(RedBlackNode<T> parent, RedBlackNode<T> brother) {
        if (parent.isBlack()) parent.setColor(RedBlackNode.NodeColor.DOUBLEBLACK);
        else parent.setColor(RedBlackNode.NodeColor.BLACK);
        brother.setColor(RedBlackNode.NodeColor.RED);
        if (parent.getColor().equals(RedBlackNode.NodeColor.DOUBLEBLACK)) {
            RedBlackNode<T> grandFather = this.searchCorrectParent(parent);
            RedBlackNode<T> uncle = (RedBlackNode<T>) ((parent.isLeftChild((T) grandFather)) ? grandFather.getRight() : grandFather.getLeft());
            this.lastNodeRecolored = parent;
            this.executeRecoloringCases(parent, grandFather, uncle);
        }
    }
    private void blackBrotherNearRedNephewCase(RedBlackNode<T> brother, RedBlackNode<T> child) {
        RedBlackNode<T> doubleBlackUncle;
        this.swapColor(brother, child);
        doubleBlackUncle = this.rotateAwayAndReturnUncle(brother, child);
        this.blackBrotherRedFarNephewCase(child, doubleBlackUncle);
    }
    private RedBlackNode<T> rotateAwayAndReturnUncle(RedBlackNode<T> parent, RedBlackNode<T> child) {
        RedBlackNode<T> grandFarther = this.searchCorrectParent(parent);
        RedBlackNode<T> doubleBlackUncle;
        if (child.isLeftChild((T) parent)) {
            doubleBlackUncle = (RedBlackNode<T>) grandFarther.getLeft();
            this.rightRotation(parent);
        }
        else {
            doubleBlackUncle = (RedBlackNode<T>) grandFarther.getRight();
            this.leftRotation(parent);
        }
        return doubleBlackUncle;
    }
    private void blackBrotherRedFarNephewCase(RedBlackNode<T> brother, RedBlackNode<T> doubleBlackChild) {
        RedBlackNode<T> nephew;
        RedBlackNode<T> parent = this.searchCorrectParent(brother);
        this.swapColor(parent, brother);
        nephew = this.rotateAndReturnRedNephew(parent, doubleBlackChild, brother);
        if (doubleBlackChild.isLeftChild((T) parent)) parent.setLeft(null); else parent.setRight(null);
//        doubleBlackChild.setColor(RedBlackNode.NodeColor.BLACK);
        nephew.setColor(RedBlackNode.NodeColor.BLACK);
    }
    private RedBlackNode<T> rotateAndReturnRedNephew(RedBlackNode<T> parent, RedBlackNode<T> child, RedBlackNode<T> brother) {
        RedBlackNode<T> nephew;
        if (child.isLeftChild((T) parent)) {
            nephew = (RedBlackNode<T>) brother.getRight();
            this.leftRotation(parent);
        } else {
            nephew = (RedBlackNode<T>) brother.getLeft();
            this.rightRotation(parent);
        }
        return nephew;
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

/*    @Override
    protected void executeDeletionCase(RedBlackNode<T> nodeToRemove) {
        if (this.checkIfRedLeaf(nodeToRemove)) this.removeLeaf(nodeToRemove);
        else {
            RedBlackNode<T> remove = nodeToRemove;
            nodeToRemove.setColor(RedBlackNode.NodeColor.DOUBLEBLACK);
            RedBlackNode<T> brother = this.getSibling(nodeToRemove);
            if (!brother.isBlack()) this.redBrotherCase(brother);
            else if (this.checkIfFamilyIsBlack(brother)) {
                nodeToRemove.setColor(RedBlackNode.NodeColor.BLACK);
                this.blackFamilyCase(brother);
            } else {
                RedBlackNode<T> parent = this.searchCorrectParent(brother);
                if (brother.isRightChild((T) parent)) {
                    RedBlackNode<T> rightChild = (RedBlackNode<T>) brother.getRight();
                    if (!brother.hasRightChild() || rightChild.isBlack()) {
                        this.swapColor(brother, rightChild);
                        this.rightRotation(brother);
                        //TODO: APPLY LAST CASE
                    } else {
                        this.swapColor(parent, brother);
                        rightChild.setColor(RedBlackNode.NodeColor.BLACK);
                        this.leftRotation(parent);
                    }
                } else {
                    RedBlackNode<T> leftChild = (RedBlackNode<T>) brother.getLeft();
                    if (!brother.hasLeftChild() || leftChild.isBlack()) {
                        this.swapColor(brother, leftChild);
                        this.leftRotation(brother);
                        //TODO: APPLY LAST CASE
                    } else {
                        this.swapColor(parent, brother);
                        leftChild.setColor(RedBlackNode.NodeColor.BLACK);
                        this.rightRotation(parent);
                    }
                }
            }
            this.removeLeaf(remove);
        }
    }*/
    private void redBrotherCase(RedBlackNode<T> parent, RedBlackNode<T> brother, RedBlackNode<T> doubleBlackChild) {
        this.swapColor(parent, brother);
        this.rotateToDoubleBlack(parent, brother);
        if (!this.checkIfNull(doubleBlackChild) && parent.isParentOf((T) doubleBlackChild))this.executeDeletionCase(doubleBlackChild);
    }
    private void swapColor(RedBlackNode<T> parent, RedBlackNode<T> child) {
        if (!this.checkIfNull(parent) && !this.checkIfNull(child)) {
            RedBlackNode.NodeColor parentColor = parent.getColor();
            if (!parent.checkEquality((T) this.getRoot())) parent.setColor(child.getColor());
            child.setColor(parentColor);
        }
    }
    private void rotateToDoubleBlack(RedBlackNode<T> parent, RedBlackNode<T> brother) {
        if (brother.isLeftChild((T) parent)) this.rightRotation(parent);
        else this.leftRotation(parent);
    }

    @Override
    protected void leftRotation(RedBlackNode<T> evaluatedNode) {
        GeneralBinaryNode<T> parent = evaluatedNode.getRight();
        if (evaluatedNode.checkEquality((T) this.getRoot())) this.setRoot((RedBlackNode<T>) parent);
        else {
            GeneralBinaryNode<T> grandFather = this.searchCorrectParent(evaluatedNode);
            if (evaluatedNode.isLeftChild((T) grandFather)) grandFather.setLeft(parent);
            else grandFather.setRight(parent);
        }
        evaluatedNode.setRight(parent.getLeft());
        parent.setLeft(evaluatedNode);
    }

    public boolean checkIfFamilyIsBlack(RedBlackNode<T> sibling) {
        boolean hasBlackFamily = true;
        if (sibling.isBlack()) {
            if (!sibling.hasNoChildren()) {
                RedBlackNode<T> leftChild = (RedBlackNode<T>) sibling.getLeft();
                RedBlackNode<T> rightChild = (RedBlackNode<T>) sibling.getRight();
                if (sibling.hasRightChild() && !rightChild.isBlack()) hasBlackFamily = false;
                if (sibling.hasLeftChild() && !leftChild.isBlack()) hasBlackFamily = false;
            }
        } else hasBlackFamily = false;
        return hasBlackFamily;
    }
}