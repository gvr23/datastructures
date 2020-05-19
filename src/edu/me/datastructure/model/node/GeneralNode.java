package edu.me.datastructure.model.node;

public abstract class GeneralNode<T> {
    private T data;
    private int number;

    public GeneralNode(T data, int number) {
        this.data = data;
        this.number = number;
    }

    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }

    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }
}
