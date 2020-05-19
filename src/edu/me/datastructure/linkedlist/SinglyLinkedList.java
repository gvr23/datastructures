package edu.me.datastructure.linkedlist;

import edu.me.datastructure.model.node.linkedlistnode.SinglyLinkedListNode;

public class SinglyLinkedList<T> extends GeneralLinkedList<SinglyLinkedListNode<T>> {


    public SinglyLinkedList(SinglyLinkedListNode<T> node) {
        super(node);
    }

    @Override
    public void insertAtBeginning(SinglyLinkedListNode<T> newNode) {
        if (this.isEmpty()) reInit(newNode);
        else {
            newNode.setNext(this.getHead());
            this.setHead(newNode);
            this.incrementQuantityByOne();
        }
    }
    @Override
    public void insertAtEnd(SinglyLinkedListNode<T> newNode) {
        if (this.isEmpty()) reInit(newNode);
        else {
            this.getTail().setNext(newNode);
            this.setTail(newNode);
            this.incrementQuantityByOne();
        }
    }
    @Override
    public void insertAfter(SinglyLinkedListNode<T> afterThis, SinglyLinkedListNode<T> newNode) {
            if (this.isEmpty()) insertAtBeginning(afterThis);
            else {
                SinglyLinkedListNode<T> afterNew = afterThis.getNext();
                newNode.setNext(afterNew);
                afterThis.setNext(newNode);
                if (this.getQuantity() == 1) this.setTail(newNode);
                this.incrementQuantityByOne();
            }
    }
    @Override
    public void printList() {

    }
    @Override
    public SinglyLinkedListNode<T> removeAtBeginning() {
        SinglyLinkedListNode<T> nodeToRemove = this.getHead();
        this.setHead(nodeToRemove.getNext());
        this.decrementQuantityByOne();
        return nodeToRemove;
    }
    @Override
    public SinglyLinkedListNode<T> removeAtEnd() {
        if (this.isEmpty()) return null;
        else {
            SinglyLinkedListNode<T> nodeToRemove;
            if (this.getQuantity() == 1) {
                nodeToRemove = this.getHead();
                this.emptyTheList();
            } else {
                SinglyLinkedListNode<T> beforeLastNode = getBeforeLastNode();
                nodeToRemove = beforeLastNode.getNext();
                beforeLastNode.setNext(null);
            }
            this.decrementQuantityByOne();
            return nodeToRemove;
        }
    }
    @Override
    public SinglyLinkedListNode<T> removeItem(SinglyLinkedListNode<T> nodeToRemove) {
        if (this.determineEquality(this.getTail(), nodeToRemove)) return removeAtEnd();
        else if (this.determineEquality(this.getHead(), nodeToRemove)) return removeAtBeginning();
        else {
            SinglyLinkedListNode<T> current = this.getHead();
            SinglyLinkedListNode<T> prior = this.getHead();
            while (current != null) {
                if (this.determineEquality(current, nodeToRemove)) {
                    SinglyLinkedListNode<T> afterToRemove = nodeToRemove.getNext();
                    prior.setNext(afterToRemove);
                    nodeToRemove.setNext(null);
                    this.decrementQuantityByOne();
                    return nodeToRemove;
                }
                prior = current;
                current = current.getNext();
            }
            return null;
        }
    }
    private SinglyLinkedListNode<T> getBeforeLastNode() {
        SinglyLinkedListNode<T> lastNode = this.getHead();
        SinglyLinkedListNode<T> previousNode = lastNode;
        while (!this.determineEquality(lastNode, this.getTail())) {
            previousNode = lastNode;
            lastNode = lastNode.getNext();
        }
        return previousNode;
    }

}
