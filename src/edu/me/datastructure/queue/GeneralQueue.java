package edu.me.datastructure.queue;

public abstract class GeneralQueue<T> {
    private T[] elements;
    private int quantity;
    private int top;
    private int last;

    public GeneralQueue(int capacity) {
        this.elements = (T[])new Object[capacity];
        this.quantity = 0;
        this.top = 0;
        this.last = 0;
    }

    public abstract void enqueue(T deque);
    public abstract T deque();
    public abstract void printElements();

    public void setTop(int newTop) { this.top = newTop; }
    public void setLast(int newLast) { this.last = newLast; }
    public void decrementQuantityByOne() { this.quantity--; }
    public void incrementQuantityByOne() { this.quantity++; }
    public GeneralQueue<T> requeue(GeneralQueue<T> auxQueue) {
        while (!auxQueue.isEmpty()) {
            this.enqueue(auxQueue.deque());
        }
        return this;
    }
    public int getQuantity() {
        return this.quantity;
    }
    public int getTop() { return this.top; }
    public int getLast() { return this.last; }
    public int getQueueCapacity() { return this.elements.length; }
    public boolean isEmpty() { return this.quantity == 0; }
    public boolean isFull() {
        return getQuantity() == this.elements.length;
    }
    public T[] getElements() { return this.elements; }
    public T getElement(int position) {
        return this.elements[position];
    }
    public T peek() {
        return this.elements[this.top];
    }
}
