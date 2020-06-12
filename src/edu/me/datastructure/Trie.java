package edu.me.datastructure;

import edu.me.datastructure.model.Path;
import edu.me.datastructure.model.node.TrieNode;

import java.util.*;

public class Trie {
    private final TrieNode root;
    private int wordQuantity;
    private static final List<String> wordList = new ArrayList<>();
    public static class WordModifier {
        private final static StringBuilder wordBuilder = new StringBuilder();
        private final static Stack<Path> pathStack = new Stack<>();

        public static void appendChild(Character child) {
            wordBuilder.append(child);
        }

        public static void deleteVisited(int toHere, int fromHere) {
            wordBuilder.delete(toHere, fromHere);
        }

        public static void emptyBuilder() {
            wordList.add(wordBuilder.toString());
        }

        public static StringBuilder getWordBuilder() {
            return wordBuilder;
        }
        public static Stack<Path> getPathStack() {
            return pathStack;
        }
    }

    public Trie(TrieNode root) {
        this.root = root;
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
    private char[] getCharArray(String entryWord) {
        return entryWord.toCharArray();
    }

    public TrieNode remove() {
        return null;
    }

    public List<String> autoComplete(String wordEntry) {
        if (this.isFirstLetterStored(wordEntry)) {
            WordModifier.getPathStack().push(new Path(this.getLastNodeOn(wordEntry)));
            WordModifier.wordBuilder.append(wordEntry, 0, wordEntry.length() - 1);
            this.root.obtainChildren();
        }

        return wordList;
    }
    private boolean isFirstLetterStored(String entryWord) {
        char child = entryWord.charAt(0);
        return this.root.isMy(child);
    }
    public TrieNode getLastNodeOn(String wordEntry) {
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

    public void traverse() {

    }
}
