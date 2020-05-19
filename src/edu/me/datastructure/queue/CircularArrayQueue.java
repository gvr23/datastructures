package edu.me.datastructure.queue;

public class CircularArrayQueue<T> extends GeneralQueue<T> {

    public CircularArrayQueue(int capacity) {
        super(capacity);
    }

    @Override
    public void enqueue(T element) {
        if (this.isFull()) System.out.print("Queue is full");
        else {
            if (this.isEmpty()) this.getElements()[this.getTop()] = element;
            else {
                this.setLast((this.getTop() + getQuantity()) % this.getQueueCapacity());
                this.getElements()[this.getLast()] = element;
            }
            this.incrementQuantityByOne();
        }
    }
    @Override
    public void printElements() {
        if (this.isEmpty()) System.out.print("There are no elements to print.");
        else {
            if (this.getQuantity() == 1) System.out.print(peek());
            else {
                CircularArrayQueue<T> auxQueue = new CircularArrayQueue<T>(this.getQuantity());
                while(!this.isEmpty()) {
                    T removed = this.deque();
                    System.out.print(String.format(" <- %s", removed));
                    auxQueue.enqueue(removed);
                }
                this.requeue(auxQueue);
            }
        }
    }
    @Override
    public T deque() {
        if (this.isEmpty()) return null;
        T removed = this.getElement(this.getTop());
        int newTop = this.getTop();
        this.setTop(++newTop % this.getQueueCapacity());
        this.decrementQuantityByOne();
        return removed;
    }
}
