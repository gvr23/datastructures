package edu.me.datastructure.linkedlist;

import edu.me.datastructure.model.node.linkedlistnode.DoublyLinkedListNode;

public class DoublyLinkedList<T> implements LinkedList<DoublyLinkedListNode<T>> {
    private DoublyLinkedListNode<T> head;
    private DoublyLinkedListNode<T> tail;
    private int quantity;

    public DoublyLinkedList(DoublyLinkedListNode<T> node) {
        this.head = node;
        this.tail = node;
        this.quantity = 1;
    }

    @Override
    public boolean isEmpty() {
        return this.quantity == 0;
    }
    @Override
    public int getQuantity() {
        return this.quantity;
    }
    @Override
    public DoublyLinkedListNode<T> getHead() {
        return this.head;
    }
    @Override
    public void addFromBeginning(DoublyLinkedListNode<T> newAddition) {
        newAddition.setNext(this.head);
        this.head.setPrevious(newAddition);
        this.head = newAddition;
        this.quantity++;
    }
    @Override
    public void addFromEnd(DoublyLinkedListNode<T> newAddition) {
        this.tail.setNext(newAddition);
        newAddition.setPrevious(this.tail);
        this.tail = newAddition;
        this.quantity++;
    }
    @Override
    public void addAfter(DoublyLinkedListNode<T> afterThis, DoublyLinkedListNode<T> newAddition) {
        if (afterThis == this.tail) {
            addFromEnd(newAddition);
        }
        else {
            DoublyLinkedListNode<T> afterNew = afterThis.getNext();
            newAddition.setNext(afterNew);
            newAddition.setPrevious(afterThis);
            afterNew.setPrevious(newAddition);
            afterThis.setNext(newAddition);
            this.quantity++;
        }
    }
    @Override
    public DoublyLinkedListNode<T> removeAtBeginning() {
        DoublyLinkedListNode<T> toRemove = this.head;
        this.head = this.head.getNext();
        this.head.setPrevious(null);
        this.quantity--;
        return toRemove;
    }
    @Override
    public DoublyLinkedListNode<T> removeAtEnd() {
        DoublyLinkedListNode<T> toRemove = this.tail;
        this.tail = toRemove.getPrevious();
        toRemove.setPrevious(null);
        this.tail.setNext(null);
        this.quantity--;
        return toRemove;
    }
    @Override
    public DoublyLinkedListNode<T> removeItem(DoublyLinkedListNode<T> toRemove) {
        if (this.tail == toRemove) return removeAtEnd();
        else if(this.head == toRemove) return removeAtBeginning();
        else {
            DoublyLinkedListNode<T> preceding = toRemove.getPrevious();
            DoublyLinkedListNode<T> succeeding = toRemove.getNext();
            preceding.setNext(succeeding);
            succeeding.setPrevious(preceding);
            this.quantity--;
            return toRemove;
        }
    }
    @Override
    public void printList() {
        if (this.isEmpty()) System.out.println("The list is empty");
        else if (this.quantity == 1) System.out.print(String.format(" %s <-- ", getHead().getData()));
        else {
            DoublyLinkedListNode<T> current = this.head;
            while (current != null) {
                System.out.print(String.format(" %s <-- ", current.getData()));
                current = current.getNext();
            }
        }
    }
}
