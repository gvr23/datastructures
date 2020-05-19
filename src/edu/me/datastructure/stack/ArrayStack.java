package edu.me.datastructure.stack;
/*
    Principles
    a). Operates on a First in Last out principle
    Drawbacks
    a). Max Size cannot be changed
 */
public class ArrayStack<T> implements Stack<T> {
    private T[] elements;
    private int quantity;

    public ArrayStack(int capacity) {
        this.elements = (T[]) new Object[capacity];
        this.quantity = -1;
    }

    @Override
    public boolean isEmpty() {
        return getQuantity() == 0;
    }
    @Override
    public boolean isFull() {
        return getQuantity() >= this.elements.length;
    }
    @Override
    public void push(T element) {
        if(!isFull()) {
            this.elements[++this.quantity] = element;
        } else {
            System.out.println("Stack is full");
        }
    }
    @Override
    public T pop() {
        if(!isEmpty()) {
            T popped = this.elements[this.quantity];
            this.quantity--;
            return popped;
        }
        return null;
    }
    @Override
    public T peek() {
        return this.elements[this.quantity];
    }
    @Override
    public int getQuantity() {
        return this.quantity + 1;
    }
    @Override
    public void printElements() {
        if (!isEmpty()){
            if (this.quantity == 0) {
                System.out.print(String.format(" --> %s", peek()));
            } else {
                ArrayStack<T> auxStack = new ArrayStack<T>(getQuantity());
                while (!this.isEmpty()) {
                    auxStack.push(this.pop());
                }
                while (!auxStack.isEmpty()) {
                    T popped = auxStack.pop();
                    System.out.print(String.format(" --> %s", popped));
                    this.push(popped);
                }
            }
        } else {
            System.out.println("Stack is Empty");
        }
    }
}
