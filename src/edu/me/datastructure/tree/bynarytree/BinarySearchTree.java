package edu.me.datastructure.tree.bynarytree;

import edu.me.datastructure.model.node.treenode.GeneralBinaryNode;
import edu.me.datastructure.model.node.treenode.GeneralBinarySearchNode;

public class BinarySearchTree<T> extends GeneralBinarySearchTree<T> {

    public BinarySearchTree(T root) {
        super(root);
    }

    @Override
    public void insert(T item) {
        if (!this.checkIfNull(item)) {
            if (this.isEmpty()) this.setRoot(item);
            else {
                GeneralBinarySearchNode<T> parent = (GeneralBinarySearchNode<T>) this.searchCorrectParent(item);
//                if (item.getData() < parent.getData()) parent.setLeft(item);
                if (this.checkIfNull((T) parent.getLeft())) parent.setLeft((GeneralBinaryNode<T>) item);
                else parent.setRight((GeneralBinaryNode<T>) item);
            }
            this.incrementQuantityByOne();
        } else System.out.println("Item provided is invalid");
    }


}
