package edu.me.datastructure.stack;

/*
    Analogy: Stack of plates.

    Applications
    a). Function calls
    b). Url History
    c). Reversing
    d). Balancing Symbols
    e). Infix to PostFix
    f). Matching XML, HTML tags

    Implementations
    1. Array O(1)
    2. Linked List O(n) to delete from stack
*/
public interface Stack<T> {
    boolean isEmpty();
    boolean isFull();
    void push(T element);
    T pop();
    T peek();
    int getQuantity();
    void printElements();
}
