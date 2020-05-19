package edu.me.datastructure.model.node.linkedlistnode;

import edu.me.datastructure.model.node.GeneralNode;

public class SinglyLinkedListNode<T> extends GeneralNode<T> {
    private SinglyLinkedListNode<T> next;

    public SinglyLinkedListNode(T data, int number) {
        super(data, number);
        this.next = null;
    }

    public SinglyLinkedListNode<T> getNext() {
        return next;
    }
    public void setNext(SinglyLinkedListNode<T> next) {
        this.next = next;
    }
}
