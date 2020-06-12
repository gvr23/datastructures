package edu.me.datastructure.model.node;


import edu.me.datastructure.Trie;
import edu.me.datastructure.model.Path;
import java.util.*;

public class TrieNode {
    static final int ALPHABET_SIZE = 26;
    private boolean wordEnding;
    private char content;
    private Map<Character, TrieNode> visitedChildren;
    private final TrieNode[] children;
    private final Stack<Path> pathStack;
    private final StringBuilder wordBuilder;

    public TrieNode(char content) {
        this.content = content;
        this.wordEnding = false;
        this.children = new TrieNode[ALPHABET_SIZE];
        this.visitedChildren = null;
        this.pathStack = Trie.WordModifier.getPathStack();
        this.wordBuilder = Trie.WordModifier.getWordBuilder();
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
    public void obtainChildren() {
        int steps;
        Path currentPitStop;
        TrieNode currentNode;

        while (!pathStack.isEmpty()) {
            steps = 0;
            currentPitStop = pathStack.pop();
            currentNode = currentPitStop.getParent();
            Trie.WordModifier.appendChild(currentNode.getContent());

            if (currentNode.hasAtLeastOneChild()) this.proceedToNextChild(currentNode, currentPitStop);
            else this.processNextSet(steps);
        }
    }

    private void proceedToNextChild(TrieNode currentNode, Path currentPitStop) {
        pathStack.push(currentPitStop);
        this.visitedChildren = currentPitStop.getVisitedChildren();
        currentNode = currentNode.getUnknownChild(this.visitedChildren.values());
        if (currentNode != null) {
            this.visitedChildren.put(currentNode.getContent(), currentNode);
            pathStack.push(new Path(currentNode));
        }
    }
    private void processNextSet(int steps) {
        Path currentPitStop;
        TrieNode nextChild;
        TrieNode currentNode;

        Trie.WordModifier.emptyBuilder();
        while (!pathStack.isEmpty()) {
            currentPitStop = pathStack.pop();
            currentNode = currentPitStop.getParent();
            steps++;
            if (currentNode.hasAtLeastOneChild()) {
                this.visitedChildren = currentPitStop.getVisitedChildren();
                nextChild = currentNode.getUnknownChild(this.visitedChildren.values());
                if (nextChild != null) {
                    this.visitedChildren.put(nextChild.getContent(), nextChild);
                    pathStack.push(currentPitStop);
                    pathStack.push(new Path(nextChild));
                    break;
                }
            }
        }
        Trie.WordModifier.deleteVisited(wordBuilder.length() - steps, wordBuilder.length());
    }
}
