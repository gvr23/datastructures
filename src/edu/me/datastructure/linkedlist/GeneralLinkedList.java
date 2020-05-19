package edu.me.datastructure.linkedlist;

public abstract class GeneralLinkedList<T> {
    private T head;
    private T tail;
    private int quantity;

    public GeneralLinkedList(T node) {
        this.head = node;
        this.tail = node;
        this.quantity = 1;
    }

    public abstract void insertAtBeginning(T newNode);
    public abstract void insertAtEnd(T newNode);
    public abstract void insertAfter(T afterThis, T newNode);
    public abstract void printList();
    public abstract T removeAtBeginning();
    public abstract T removeAtEnd();
    public abstract T removeItem(T nodeToRemove);

    public void reInit(T newNode) {
        this.head = newNode;
        this.tail = newNode;
        this.quantity++;
    }
    public void emptyTheList() {
        this.head = null;
        this.tail = null;
    }
    public void setHead(T newHead) { this.head = newHead; }
    public void setTail(T newTail) { this.tail = newTail; }
    public void incrementQuantityByOne() { this.quantity++; }
    public void decrementQuantityByOne() { this.quantity--; }
    public boolean isEmpty() { return this.quantity == 0; }
    public boolean determineIfNull(T evaluatedNode) { return evaluatedNode == null; }
    public boolean determineEquality(T firstNode, T secondNode) { return firstNode == secondNode; }
    public int getQuantity() { return this.quantity; }
    public T getHead() { return this.head; }
    public T getTail() {return this.tail; }
}
