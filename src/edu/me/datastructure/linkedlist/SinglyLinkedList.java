package edu.me.datastructure.linkedlist;

import edu.me.datastructure.model.node.linkedlistnode.SinglyLinkedListNode;

public class SinglyLinkedList extends GeneralLinkedList<SinglyLinkedListNode> {


    public SinglyLinkedList(SinglyLinkedListNode node) {
        super(node);
    }



    @Override
    public void insertAtBeginning(SinglyLinkedListNode newNode) {
        if (this.isEmpty()) reInit(newNode);
        else {
            newNode.setNext(this.getHead());
            this.setHead(newNode);
            this.incrementQuantityByOne();
        }
    }
    @Override
    public void insertAtEnd(SinglyLinkedListNode newNode) {
        if (this.isEmpty()) reInit(newNode);
        else {
            this.getTail().setNext(newNode);
            this.setTail(newNode);
            this.incrementQuantityByOne();
        }
    }
    @Override
    public void insertAfter(SinglyLinkedListNode afterThis, SinglyLinkedListNode newNode) {
            if (this.isEmpty()) insertAtBeginning(afterThis);
            else {
                SinglyLinkedListNode afterNew = afterThis.getNext();
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
    public SinglyLinkedListNode removeAtBeginning() {
        SinglyLinkedListNode nodeToRemove = this.getHead();
        this.setHead(nodeToRemove.getNext());
        this.decrementQuantityByOne();
        return nodeToRemove;
    }
    @Override
    public SinglyLinkedListNode removeAtEnd() {
        if (this.isEmpty()) return null;
        else {
            SinglyLinkedListNode nodeToRemove;
            if (this.getQuantity() == 1) {
                nodeToRemove = this.getHead();
                this.emptyTheList();
            } else {
                SinglyLinkedListNode beforeLastNode = getBeforeLastNode();
                nodeToRemove = beforeLastNode.getNext();
                beforeLastNode.setNext(null);
            }
            this.decrementQuantityByOne();
            return nodeToRemove;
        }
    }
    @Override
    public SinglyLinkedListNode removeItem(SinglyLinkedListNode nodeToRemove) {
        if (this.determineEquality(this.getTail(), nodeToRemove)) return removeAtEnd();
        else if (this.determineEquality(this.getHead(), nodeToRemove)) return removeAtBeginning();
        else {
            SinglyLinkedListNode current = this.getHead();
            SinglyLinkedListNode prior = this.getHead();
            while (current != null) {
                if (this.determineEquality(current, nodeToRemove)) {
                    SinglyLinkedListNode afterToRemove = nodeToRemove.getNext();
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
    private SinglyLinkedListNode getBeforeLastNode() {
        SinglyLinkedListNode lastNode = this.getHead();
        SinglyLinkedListNode previousNode = lastNode;
        while (!this.determineEquality(lastNode, this.getTail())) {
            previousNode = lastNode;
            lastNode = lastNode.getNext();
        }
        return previousNode;
    }

}
