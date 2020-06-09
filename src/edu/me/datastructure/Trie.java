package edu.me.datastructure;

import edu.me.datastructure.model.node.TrieNode;

import java.util.Arrays;

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
            if (!currentNode.isItMy(child)) currentNode.adopt(childNode);
            currentNode = childNode;
        }
        currentNode.markAsEnding();
        this.wordQuantity++;
    }
    public TrieNode remove() {
        return null;
    }
    public String search(String wordEntry) {
        char[] charsToSearch = this.getCharArray(wordEntry);
        StringBuilder stringBuilder = new StringBuilder();
        TrieNode currentNode = this.root;
        for (char child : charsToSearch) {
            TrieNode childNode = new TrieNode(child);
            if (!currentNode.isItMy(child)) {
                stringBuilder.replace(0, stringBuilder.length(),"");
                break;
            }
            stringBuilder.append(childNode.getContent());
            currentNode = currentNode.getChild(child);
        }
        return stringBuilder.toString();
    }
//    public String autoComplete(String entryWord) {
//
//    }

    public void traverse() {

    }
    private char[] getCharArray(String entryWord) {
        return entryWord.toCharArray();
    }
}
