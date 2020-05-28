import edu.me.datastructure.hashtable.CollisionMethod;
import edu.me.datastructure.hashtable.HashTable;
import edu.me.datastructure.hashtable.HashingMethod;
import edu.me.datastructure.model.node.linkedlistnode.SinglyLinkedListNode;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class HashTableTest {
    static HashTable<SinglyLinkedListNode<Integer>> openHashTable;
    private static final ArrayList<SinglyLinkedListNode<Integer>> dataList = new ArrayList<>(); {{

    }};

    @BeforeAll
    static void setUp() {
        try {
            System.out.println("Hash table prepare to initialize");

            dataList.add(new SinglyLinkedListNode<>(3));
            dataList.add(new SinglyLinkedListNode<>(2));
            dataList.add(new SinglyLinkedListNode<>(9));
            dataList.add(new SinglyLinkedListNode<>(6));
            dataList.add(new SinglyLinkedListNode<>(11));
            dataList.add(new SinglyLinkedListNode<>(13));
            dataList.add(new SinglyLinkedListNode<>(7));
            dataList.add(new SinglyLinkedListNode<>(12));

            openHashTable = new HashTable<>(10, HashingMethod.HashingTechnique.DIVISION, CollisionMethod.CollisionTechnique.CHAINING, 0.75f);
            System.out.println("Finished successfully the hash initialization");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error initializing the hash table");
        }
    }

    @Test
    void insert() {
        for (SinglyLinkedListNode<Integer> node : dataList) {
            openHashTable.insert(node);
        }
        System.out.println("finished inserting");
    }

    @AfterAll
    static void tearDown() {
        openHashTable = null;
    }
}