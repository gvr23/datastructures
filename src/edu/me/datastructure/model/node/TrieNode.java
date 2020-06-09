package edu.me.datastructure.model.node;

import java.util.HashMap;

public class TrieNode {
    private boolean endingNode;
    private char content;
    private HashMap<Character, TrieNode> children;

    public TrieNode(char content) {
        this.content = content;
        this.endingNode = false;
        this.children = new HashMap<>();
    }

    public boolean isEndingNode() {
        return endingNode;
    }
    public void markAsEnding() {
        this.endingNode = true;
    }
    public void unMarkAsEnding() { this.endingNode = false; }

    public char getContent() {
        return content;
    }
    public void setContent(char content) {
        this.content = content;
    }

    public boolean isItMy(char child) { return this.children.get(child) != null; }
    public TrieNode getChild(char child) { return this.children.get(child); }
    public void adopt(TrieNode childNode) { this.children.put(childNode.getContent(), childNode); }
    public boolean isEquivalentTo(char content) { return this.content == content; }
}
