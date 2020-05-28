package edu.me.datastructure.model.node;

public abstract class GeneralNode<T> {
    private T data;

    public GeneralNode(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }


    public boolean contentNotNull() {
        return this.getData() != null;
    }
}
