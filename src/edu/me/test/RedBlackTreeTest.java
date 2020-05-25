import edu.me.datastructure.model.node.treenode.RedBlackNode;
import edu.me.datastructure.tree.bynarytree.balancedtree.RedBlackTree;
import org.junit.jupiter.api.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

class RedBlackTreeTest {
    private static RedBlackTree<Integer> rbTree;
    private static final List<RedBlackNode<Integer>> nodesList = new ArrayList<>() {{
        //        add(new RedBlackNode<>(10, 10));
//        add(new RedBlackNode<>(18, 18));
//        add(new RedBlackNode<>(7, 7));
//        add(new RedBlackNode<>(15, 15));
//        add(new RedBlackNode<>(16, 16));
//        add(new RedBlackNode<>(30, 30));
//        add(new RedBlackNode<>(25, 25));
//        add(new RedBlackNode<>(40, 40));
//        add(new RedBlackNode<>(60, 60));
//        add(new RedBlackNode<>(2, 2));
//        add(new RedBlackNode<>(1, 1));
//        add(new RedBlackNode<>(70, 70));

        add(new RedBlackNode<>(50, 50));
        add(new RedBlackNode<>(30, 30));
        add(new RedBlackNode<>(65, 65));
        add(new RedBlackNode<>(15, 15));
        add(new RedBlackNode<>(35, 35));
        add(new RedBlackNode<>(55, 55));
        add(new RedBlackNode<>(70, 70));
        add(new RedBlackNode<>(68, 68));
        add(new RedBlackNode<>(80, 80));
        add(new RedBlackNode<>(90, 90));
    }};
    private static List<RedBlackNode<Integer>> nodesArrayList;
    private static List<RedBlackNode<Integer>> nodesToRemoveList;

    @BeforeAll
    static void setUp() {
        try {
            System.out.println("Prepare to initialize");
            nodesArrayList = new ArrayList<>();
            nodesToRemoveList = new ArrayList<>();
            rbTree = new RedBlackTree<>(nodesList.get(1));

            nodesToRemoveList.add(nodesList.get(5));
            nodesToRemoveList.add(nodesList.get(1));
            nodesToRemoveList.add(nodesList.get(9));
            nodesToRemoveList.add(nodesList.get(8));
            nodesToRemoveList.add(nodesList.get(0));
            nodesToRemoveList.add(nodesList.get(4));
            nodesToRemoveList.add(nodesList.get(3));
            nodesToRemoveList.add(nodesList.get(2));
            nodesToRemoveList.add(nodesList.get(7));
            nodesToRemoveList.add(nodesList.get(6));

            nodesArrayList.add(nodesList.get(2));
            nodesArrayList.add(nodesList.get(3));
            nodesArrayList.add(nodesList.get(4));
            nodesArrayList.add(nodesList.get(0));
            nodesArrayList.add(nodesList.get(5));
            nodesArrayList.add(nodesList.get(6));
            nodesArrayList.add(nodesList.get(7));
            nodesArrayList.add(nodesList.get(8));
            nodesArrayList.add(nodesList.get(9));

            System.out.println("Completed Successfully");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error initializing");
        }

    }

    @Test
    void insert() {
        int prevQuantity = rbTree.getQuantity();
        RedBlackNode<Integer> notInsertedNode = null;
        try {
            for (RedBlackNode<Integer> node : this.nodesArrayList) {
                notInsertedNode = node;
                rbTree.insert(node);
                if (rbTree.getQuantity() != (prevQuantity + 1)) break;
                prevQuantity = rbTree.getQuantity();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(String.format("There was a problem tring to insert %s", notInsertedNode.getData()));
        }
    }

    @Test
    void removeItem() {
        int prevQuantity = rbTree.getQuantity();
        RedBlackNode<Integer> nodeRemoved = null;

        try {
            rbTree.levelOrderTraversal();
            System.out.println();
            while (!nodesToRemoveList.isEmpty()) {
                nodeRemoved = rbTree.removeItem(nodesToRemoveList.remove(0));
                if (rbTree.getQuantity() != (prevQuantity - 1)) break;
                prevQuantity = rbTree.getQuantity();
                System.out.println(String.format("Test, node removed is %s", nodeRemoved.getData()));
                rbTree.levelOrderTraversal();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(String.format("Error removing the %d node", nodeRemoved.getData()));
        }
    }

    @AfterAll
    static void tearDown() { rbTree = null; }
}