package edu.me.datastructure.queue;
/*
    Analogy: Queue at the bank
    First in First out approach

    Applications
    a). First come first served
    b). Asynchronous data transfers
    c). Call center
    d). O/S job scheduling
    e). Breadth First Search

    Implementations
    1. Array
    2. Linked List O(n) to delete from stack
    3. Heaps

    Types
    1. Circular
    2. Priority
    3. Double ended (insert from both sides)
*/
public interface Queue<T> {
    boolean isEmpty();
    boolean isFull();
    T peek();
    void enqueue(T element);
    T dequeue();
    int getQuantity();
    void printElements();
}
