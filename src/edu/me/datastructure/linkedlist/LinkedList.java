package edu.me.datastructure.linkedlist;

/*
    Analogy: Train with wagons

    Applications

    Benefits
    a). Dynamic data structure
    b). memory allocated at runtime

    Disadvantages
    a). No direct access to linked list elements
    b). no contiguous blocks of memory

    Useful
    a). When we don't know the number of elements ahead of time.

    Types
    1. Singly Linked List
    2. Doubly Linked List
    3. Circular Linked List (insert from both sides)
    4. Unrolled Linked List
    5. Skip List
*/
public interface LinkedList<T> {
    boolean isEmpty();
    int getQuantity();
    T getHead();
    void addFromBeginning(T newAddition);
    void addFromEnd(T newAddition);
    void addAfter(T afterThis, T newAddition);
    T removeAtBeginning();
    T removeAtEnd();
    T removeItem(T toRemove);
    void printList();
}
