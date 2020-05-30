package edu.me.datastructure.model.node;

public class HashNode {
    private Object content;
    private int probes;
    private int firstCollidedLocation;
    private int collidedNumbersQuantity;
    private int numericRepresentation;

    public HashNode(Object content) {
        this.content = content;
        this.collidedNumbersQuantity = 0;
        this.probes = 0;
    }

    public Object getContent() {
        return content;
    }
    public int getProbes() {
        return probes;
    }
    public int getNumericRepresentation() {
        return numericRepresentation;
    }
    public int getFirstCollidedLocation() {
        return firstCollidedLocation;
    }
    public int getCollidedNumbersQuantity() {
        return collidedNumbersQuantity;
    }

    public void setContent(Object content) {
        this.content = content;
    }
    public void setProbes(int probes) {
        this.probes = probes;
    }
    public void setNumericRepresentation(int numericRepresentation) {
        this.numericRepresentation = numericRepresentation;
    }
    public void setFirstCollidedLocation(int firstCollidedLocation) {
        this.firstCollidedLocation = firstCollidedLocation;
    }

    public void addACollidedNumber() {
        this.collidedNumbersQuantity++;
    }
    public void subtractCollidedNumber() {
        this.collidedNumbersQuantity--;
    }
}
