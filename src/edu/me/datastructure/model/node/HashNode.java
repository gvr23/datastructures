package edu.me.datastructure.model.node;

public class HashNode {
    private enum HashNodeStates {
        ACTIVE,
        TEMPORARY,
        DELETED
    }
    private Object content;
    private int probes;
    private int firstLevelIndex;
    private int secondLevelIndex;
    private int location;
    private int numericRepresentation;
    private HashNodeStates state;

    public HashNode(Object content) {
        this.content = content;
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
    public int getLocation() {
        return location;
    }
    public int getFirstLevelIndex() {
        return firstLevelIndex;
    }

    public int getSecondLevelIndex() {
        return secondLevelIndex;
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
    public void setLocation(int location) {
        this.location = location;
    }
    public void setFirstLevelIndex(int firstLevelIndex) {
        this.firstLevelIndex = firstLevelIndex;
    }
    public void setSecondLevelIndex(int secondLevelIndex) {
        this.secondLevelIndex = secondLevelIndex;
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
                String.format("\n   Location: %s", this.location) +
                String.format("\n   Probes: %s ", this.probes) +
                "\n}";
    }

    public void transferTo(HashNode item) {
        item.setContent(this.content);
        item.setProbes(this.probes);
        item.setLocation(this.location);
    }
}
