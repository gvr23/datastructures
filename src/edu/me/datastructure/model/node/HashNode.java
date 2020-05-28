package edu.me.datastructure.model.node;

public class HashNode {
    private Object content;
    private int probes;
    private int numericRepresentation;

    public HashNode(Object content) {
        this.content = content;
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

    public void setContent(Object content) {
        this.content = content;
    }
    public void setProbes(int probes) {
        this.probes = probes;
    }
    public void setNumericRepresentation(int numericRepresentation) {
        this.numericRepresentation = numericRepresentation;
    }
}
