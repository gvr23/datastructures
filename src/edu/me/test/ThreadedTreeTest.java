import edu.me.datastructure.model.node.treenode.ThreadedNode;
import edu.me.datastructure.tree.bynarytree.ThreadedTree;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

class ThreadedTreeTest {
    private static ThreadedTree<Integer> bTree;
    private final List<ThreadedNode<Integer>> nodesArrayList = new ArrayList<>() {{
        add(new ThreadedNode<>(5, 5));
        add(new ThreadedNode<>(1, 1));
        add(new ThreadedNode<>(2, 2));
        add(new ThreadedNode<>(7, 7));
        add(new ThreadedNode<>(300, 300));
        add(new ThreadedNode<>(16, 16));
        add(new ThreadedNode<>(31, 31));
        add(new ThreadedNode<>(100, 100));
    }};

    @BeforeAll
    static void beforeAll() {
        try {
            System.out.println("Prepare to initialize");
            bTree = new ThreadedTree<>(new ThreadedNode<>(11, 11), new ThreadedNode<>(-1, -1));
            System.out.println("Successfully ended Initialization");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error Initializing");
        }
    }

    @Test
    void insert() {
        try {
            ThreadedNode<Integer> insertedNode;
            for (ThreadedNode<Integer> node : nodesArrayList) {
                bTree.insert(node);
                insertedNode = bTree.searchForItem(node);
                System.out.println(String.format("Test, node inserted is, %s", insertedNode.getData()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("There was a problem inserting");
        }

    }

    @Test
    void removeItem() {
        ThreadedNode<Integer> nodeRemoved;
        try {
            bTree.postOrderTraversal();
            System.out.println();
            while (!bTree.isEmpty()) {
                nodeRemoved = bTree.removeItem((ThreadedNode<Integer>) bTree.getRoot().getLeft());
                System.out.println(String.format("Test, node removed is, %s", nodeRemoved.getData()));
                bTree.postOrderTraversal();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(String.format("Error removing the %d node", bTree.getQuantity()));
        }
    }

    @AfterAll
    static void tearDown() {
        bTree = null;
    }
}