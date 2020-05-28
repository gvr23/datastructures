package edu.me.datastructure.model.node;

public class HashNode {
    private final Object content;
    private int probes;

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

    public void setProbes(int probes) {
        this.probes = probes;
    }
}
