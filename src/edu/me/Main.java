package edu.me;

import edu.me.datastructure.model.node.treenode.RedBlackNode;
import edu.me.datastructure.tree.bynarytree.balancedtree.RedBlackTree;

public class Main {
    public static void main(String[] args) {
        RedBlackNode<Integer> c = new RedBlackNode<>(10,  10);
        RedBlackNode<Integer> b = new RedBlackNode<>(18, 18);
        RedBlackNode<Integer> a = new RedBlackNode<>(7, 7);
        RedBlackNode<Integer> f = new RedBlackNode<>(15, 15);
        RedBlackNode<Integer> j = new RedBlackNode<>(16, 16);
        RedBlackNode<Integer> d = new RedBlackNode<>(30, 30);
        RedBlackNode<Integer> g = new RedBlackNode<>(25, 25);
        RedBlackNode<Integer> h = new RedBlackNode<>(40, 40);
        RedBlackNode<Integer> k = new RedBlackNode<>(60, 60);
        RedBlackNode<Integer> l = new RedBlackNode<>(2, 2);
        RedBlackNode<Integer> m = new RedBlackNode<>(1, 1);
        RedBlackNode<Integer> n = new RedBlackNode<>(70, 70);

        RedBlackTree<Integer> btree = new RedBlackTree<>(c);
        btree.insert(b);
        btree.insert(a);
        btree.insert(f);
        btree.insert(j);
        btree.insert(d);
        btree.insert(g);
        btree.insert(h);
        btree.insert(k);
        btree.insert(l);
        btree.insert(m);
        btree.insert(n);
        btree.levelOrderTraversal();
    }
}
