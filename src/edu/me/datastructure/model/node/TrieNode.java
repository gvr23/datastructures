package edu.me.datastructure.model.node;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class TrieNode {
    static final int ALPHABET_SIZE = 26;
    private boolean wordEnding;
    private char content;
    private final TrieNode[] children;

    public TrieNode(char content) {
        this.content = content;
        this.wordEnding = false;
        this.children = new TrieNode[ALPHABET_SIZE];
        this.init();
    }

    private void init() {
        for (int current = 0; current < ALPHABET_SIZE; current++) {
            this.children[current] = null;
        }
    }
    public boolean isWordEnding() {
        return wordEnding;
    }
    public void markAsEnding() {
        this.wordEnding = true;
    }
    public void unMarkAsEnding() { this.wordEnding = false; }

    public char getContent() {
        return content;
    }
    public void setContent(char content) {
        this.content = content;
    }

    public boolean isMy(char child) { return this.children[this.getChildLocation(child)] != null; }
    public TrieNode getChild(char child) { return this.children[this.getChildLocation(child)]; }
    private int getChildLocation(char child) { return (child + ALPHABET_SIZE) % ALPHABET_SIZE; }
    public boolean hasAtLeastOneChild() {
        for (TrieNode child : this.children) {
            if (child != null) return true;
        }
        return false;
    }
    public TrieNode getUnknownChild(Collection<TrieNode> visitedChildren) {
        TrieNode nextChild = null;

         if (visitedChildren.size() > 0) {
             for (TrieNode child : this.children) {
                 if (child != null) {
                     if (!visitedChildren.contains(child)) {
                         nextChild = child;
                         break;
                     }
                 }
             }
         } else nextChild = this.getFirstChild();

        return nextChild;
    }
    public TrieNode getFirstChild() {
        TrieNode child = null;

        for (TrieNode trieNode : this.children) {
            if (trieNode != null) {
                child = trieNode;
                break;
            }
        }
        return child;
    }
    public TrieNode[] getChildren() { return this.children; }
    public void adopt(TrieNode childNode) { this.children[this.getChildLocation(childNode.getContent())] = childNode; }
    public boolean isEquivalentTo(char content) { return this.content == content; }
    public int getNumberOfChildren() { return this.children.length; }
}
