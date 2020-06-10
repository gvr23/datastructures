package edu.me.datastructure;

import edu.me.datastructure.linkedlist.SinglyLinkedList;
import edu.me.datastructure.model.node.TrieNode;
import edu.me.datastructure.model.node.linkedlistnode.SinglyLinkedListNode;
import edu.me.datastructure.stack.ArrayStack;

import java.util.*;

public class Trie {
    private TrieNode root;
    private int wordQuantity;

    public Trie(TrieNode root) {
        this.root = root;
        this.wordQuantity = 0;
    }

    public void insert(String wordEntry) {
        char[] entryCharArray = this.getCharArray(wordEntry);
        TrieNode currentNode = this.root;
        for (char child : entryCharArray) {
            TrieNode childNode = new TrieNode(child);
            if (!currentNode.isMy(child)) {
                currentNode.adopt(childNode);
            } else childNode = currentNode.getChild(child);
            currentNode = childNode;
        }
        currentNode.markAsEnding();
        this.wordQuantity++;
    }
    public TrieNode remove() {
        return null;
    }

    public List<String> autoComplete(String wordEntry) {
        TrieNode currentNode;
        StringBuilder wordBuilder;
        List<String> wordList = null;
        Map<Character, TrieNode> visitedChildren;
        Stack<Path> pathStack;
        Path currentPitStop;

        if (this.isFirstLetterStored(wordEntry)) {
            int pops;
            pathStack = new Stack<>();
            wordList = new ArrayList<>();
            wordBuilder = new StringBuilder();
            currentNode = this.getLastValidChild(wordEntry);
            pathStack.push(new Path(currentNode));
            wordBuilder.append(wordEntry, 0, wordEntry.length() - 1);

            while (!pathStack.isEmpty()) {
                pops = 0;
                currentPitStop = pathStack.pop();
                currentNode = currentPitStop.getParent();
                wordBuilder.append(currentNode.getContent());

                if (currentNode.hasAtLeastOneChild()) {
                    pathStack.push(currentPitStop);
                    visitedChildren = currentPitStop.getVisitedChildren();
                    currentNode = currentNode.getUnknownChild(visitedChildren.values());
                    if (currentNode != null) {
                        visitedChildren.put(currentNode.getContent(), currentNode);
                        pathStack.push(new Path(currentNode));
                    }
                } else {
                    TrieNode nextChild;
                    wordList.add(wordBuilder.toString());
                    while (!pathStack.isEmpty()) {
                        currentPitStop = pathStack.pop();
                        currentNode = currentPitStop.getParent();
                        pops++;
                        if (currentNode.hasAtLeastOneChild()) {
                            visitedChildren = currentPitStop.getVisitedChildren();
                            nextChild = currentNode.getUnknownChild(visitedChildren.values());
                            if (nextChild != null) {
                                visitedChildren.put(nextChild.getContent(), nextChild);
                                pathStack.push(currentPitStop);
                                pathStack.push(new Path(nextChild));
                                break;
                            }
                        }
                    }
                    wordBuilder.delete(wordBuilder.length() - pops, wordBuilder.length());
                }
            }
        }


        return wordList;
    }
    public TrieNode recordAndContinue(TrieNode parent, StringBuilder wordBuilder, Stack<TrieNode> savedPath) {
        TrieNode[] children;
        TrieNode newStop = null;
        if (parent.hasAtLeastOneChild()) {
            children = parent.getChildren();
            savedPath.push(parent);
        }
        wordBuilder.append(parent.getContent());
        return newStop;
    }
    public TrieNode getLastValidChild(String wordEntry) {
        char[] entryArray = wordEntry.toCharArray();
        TrieNode currentNode = this.root;
        TrieNode currentChild;

        for (char child : entryArray) {
            currentChild = currentNode.getChild(child);
            if (currentChild == null) {
                currentNode = null;
                break;
            }
            currentNode = currentChild;
        }
        return currentNode;
    }
    public void addChild(TrieNode parent, StringBuilder wordBuilder, Character child) {
        TrieNode[] children = parent.getChildren();
        wordBuilder.append(parent.getChild(child));
    }
//    public TrieNode getNextChild(TrieNode parent) {
//        HashMap<Character, TrieNode> children = parent.getChildren();
//        for (Character childName : children.keySet()) {
//            parent.getChild(childName);
//        }
//    }

    public void traverse() {

    }
    private boolean isFirstLetterStored(String entryWord) {
        char child = entryWord.charAt(0);
        return this.root.isMy(child);
    }
    private char[] getCharArray(String entryWord) {
        return entryWord.toCharArray();
    }

    static class Path extends Stack<Map<Character , LinkedList<TrieNode>>> {
        private final Map<Character, TrieNode> visitedChildren;
        private final TrieNode parent;

        public Path(TrieNode parent) {
            this.visitedChildren = new HashMap<>();
            this.parent = parent;
        }

        public TrieNode getParent() {
            return parent;
        }
        public Map<Character, TrieNode> getVisitedChildren() {
            return visitedChildren;
        }
    }
}
