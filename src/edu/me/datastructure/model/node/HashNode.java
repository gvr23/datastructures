package edu.me.datastructure.model.node;

public class HashNode {
    private enum HashNodeStates {
        ACTIVE,
        TEMPORARY,
        DELETED
    }
    private Object content;
    private int probes;
    private int secondLevelHashing;
    private int firstLocation;
    private int collidedNumbersQuantity;
    private int numericRepresentation;
    private HashNodeStates state;

    public HashNode(Object content) {
        this.content = content;
        this.collidedNumbersQuantity = 0;
        this.probes = 0;
        this.state = HashNodeStates.ACTIVE;
    }

    public int getProbes() {
        return probes;
    }
    public Object getContent() {
        return content;
    }
    public int getNumericRepresentation() {
        return numericRepresentation;
    }
    public int getFirstLocation() {
        return firstLocation;
    }
    public int getSecondLevelHashing() {
        return secondLevelHashing;
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
    public void setLocation(int firstLocation) {
        this.firstLocation = firstLocation;
    }
    public void setSecondLevelHashing(int secondLevelHashing) {
        this.secondLevelHashing = secondLevelHashing;
    }

    public boolean checkEquality(Object content) {
        return this.content.equals(content);
    }

    public boolean isActive() {
        return this.state.equals(HashNodeStates.ACTIVE);
    }
    public boolean isTemporary() {
        return this.state.equals(HashNodeStates.TEMPORARY);
    }
    public void changeToTemporary() { this.state = HashNodeStates.TEMPORARY; }
    public boolean isDeleted() {
        return this.state.equals(HashNodeStates.DELETED);
    }
    public void changeToDeleted() { this.state = HashNodeStates.DELETED; }

    @Override
    public String toString() {
        return  String.format("HashNode (#%s) {", this.numericRepresentation) +
                String.format("\n   State: %s", this.state) +
                String.format("\n   FirstLocation: %s", this.firstLocation) +
                String.format("\n   CollidedNumbersQuantity: %s", this.collidedNumbersQuantity) +
                String.format("\n   Probes: %s ", this.probes) +
                "\n}";
    }

    public HashNode transferTo() {
        HashNode item = new HashNode(this.content);
        item.setProbes(this.probes);
        item.setLocation(this.firstLocation);
        return item;
    }
}
