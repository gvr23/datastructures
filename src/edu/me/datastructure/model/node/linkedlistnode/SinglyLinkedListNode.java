package edu.me.datastructure.model.node.linkedlistnode;

import edu.me.datastructure.model.node.GeneralNode;

public class SinglyLinkedListNode extends GeneralNode<Object> {
    private SinglyLinkedListNode next;

    public SinglyLinkedListNode(Object data) {
        super(data);
        this.next = null;
    }

    public SinglyLinkedListNode getNext() {
        return next;
    }
    public void setNext(SinglyLinkedListNode next) {
        this.next = next;
    }
}
