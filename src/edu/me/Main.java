package edu.me;

import edu.me.datastructure.model.node.treenode.RedBlackNode;
import edu.me.datastructure.tree.bynarytree.balancedtree.RedBlackTree;

public class Main {
    public static void main(String[] args) {
        /*RedBlackNode<Integer> c = new RedBlackNode<>(10,  10);
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
        RedBlackNode<Integer> n = new RedBlackNode<>(70, 70);*/
        RedBlackNode<Integer> c = new RedBlackNode<>(50,  50);
        RedBlackNode<Integer> b = new RedBlackNode<>(20, 20);
        RedBlackNode<Integer> a = new RedBlackNode<>(65, 65);
        RedBlackNode<Integer> f = new RedBlackNode<>(15, 15);
        RedBlackNode<Integer> j = new RedBlackNode<>(35, 35);
        RedBlackNode<Integer> d = new RedBlackNode<>(55, 55);
        RedBlackNode<Integer> g = new RedBlackNode<>(70, 70);
        RedBlackNode<Integer> h = new RedBlackNode<>(68, 68);
        RedBlackNode<Integer> k = new RedBlackNode<>(80, 80);
        RedBlackNode<Integer> l = new RedBlackNode<>(90, 90);
//        55, 30, 90, 80, 50, 35, 15

        RedBlackTree<Integer> btree = new RedBlackTree<>(b);
        btree.insert(a);
        btree.insert(f);
        btree.insert(j);
        btree.insert(c);
        btree.insert(d);
        btree.insert(g);
        btree.insert(h);
        btree.insert(k);
        btree.insert(l);
        btree.levelOrderTraversal();

    }
}
