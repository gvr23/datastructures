package edu.me.datastructure.model;

import edu.me.datastructure.model.node.TrieNode;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;

public class Path extends Stack<Map<Character , LinkedList<TrieNode>>> {
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
