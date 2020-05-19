package edu.me.datastructure.model.node.linkedlistnode;

import edu.me.datastructure.model.node.GeneralNode;

public class DoublyLinkedListNode<T> extends GeneralNode<T> {
    private DoublyLinkedListNode<T> previous;
    private DoublyLinkedListNode<T> next;

    public DoublyLinkedListNode(T data, int number) {
        super(data, number);
        this.previous = null;
        this.next = null;
    }

    public DoublyLinkedListNode<T> getPrevious() {
        return previous;
    }
    public void setPrevious(DoublyLinkedListNode<T> previous) {
        this.previous = previous;
    }
    public DoublyLinkedListNode<T> getNext() {
        return next;
    }
    public void setNext(DoublyLinkedListNode<T> next) {
        this.next = next;
    }
}
