import edu.me.datastructure.hashtable.option.CollisionTechniqueE;
import edu.me.datastructure.hashtable.HashTable;
import edu.me.datastructure.hashtable.option.HashingTechniqueE;
import edu.me.datastructure.linkedlist.SinglyLinkedList;
import edu.me.datastructure.model.node.HashNode;
import edu.me.datastructure.model.node.linkedlistnode.SinglyLinkedListNode;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

class HashTableTest {
    private final float M_ALLOWED_CAPACITY = 0.75f;
    private final float MIN_ALLOWED_CAPACITY = 0.35f;
    private ArrayList<SinglyLinkedListNode> dataList;
    private HashTable openHashTable;

    @BeforeEach
    void setUp() {
        try {
            System.out.println("Hash table prepare to initialize");
            this.dataList = new ArrayList<>();
            this.dataList.add(new SinglyLinkedListNode(3));
            this.dataList.add(new SinglyLinkedListNode(2));
            this.dataList.add(new SinglyLinkedListNode(9));
            this.dataList.add(new SinglyLinkedListNode(6));
            this.dataList.add(new SinglyLinkedListNode(11));
            this.dataList.add(new SinglyLinkedListNode(13));
            this.dataList.add(new SinglyLinkedListNode(7));
            this.dataList.add(new SinglyLinkedListNode(12));

            System.out.println("Finished successfully the hash initialization");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error initializing the hash table");
        }
    }

    @Test
    void insertChaining() {
        HashNode surrogateNode;
        SinglyLinkedList surrogateList;
        this.openHashTable = new HashTable(10, HashingTechniqueE.DIVISION, CollisionTechniqueE.CHAINING, M_ALLOWED_CAPACITY, MIN_ALLOWED_CAPACITY);
        for (SinglyLinkedListNode node : dataList) {
            surrogateList = new SinglyLinkedList(node);
            surrogateNode = new HashNode(surrogateList);
            surrogateNode.setNumericRepresentation((Integer) node.getData());
            openHashTable.insert(surrogateNode, (Integer) node.getData());
        }
        System.out.println("finished inserting with chaining");
    }

    @Test
    void insertLinearProbing() {
        this.openHashTable = new HashTable(10, HashingTechniqueE.DIVISION, CollisionTechniqueE.LINEAR_PROBING, M_ALLOWED_CAPACITY, MIN_ALLOWED_CAPACITY);
        System.out.println("finished inserting with linear probing");
        this.insertTestHelper();
        this.printTestHelper();
        System.out.println("Finished printing with linear probing");
    }

    @Test
    void insertingQuadraticProbing() {
        this.openHashTable = new HashTable(10, HashingTechniqueE.DIVISION, CollisionTechniqueE.QUADRATIC_PROBING, M_ALLOWED_CAPACITY, MIN_ALLOWED_CAPACITY);
        System.out.println("Finished inserting with quadratic probing");
        this.insertTestHelper();
        this.printTestHelper();
        System.out.println("Finished printing with quadratic probing");
    }

    @Test
    void insertingWithDoubleHashing() {
        this.openHashTable = new HashTable(10, HashingTechniqueE.DIVISION, CollisionTechniqueE.DOUBLE_HASHING, M_ALLOWED_CAPACITY, MIN_ALLOWED_CAPACITY);
        System.out.println("Finished inserting with double hashing");
        this.insertTestHelper();
        this.printTestHelper();
        System.out.println("Finished printing with  double hashing");
    }

    private void insertTestHelper() {
        for (SinglyLinkedListNode node : dataList) {
            openHashTable.insert(node, (Integer) node.getData());
        }
    }

    private void printTestHelper() {
        HashNode[] hashArray = this.openHashTable.getHashArray();
        for (HashNode node : hashArray) {
            int itemToPrint = (node == null) ? 0 : node.getNumericRepresentation();
            System.out.print(String.format(" <-- %s", itemToPrint));
        }
        System.out.println();
    }

    @Test
    void searching() {
        this.openHashTable = new HashTable(10, HashingTechniqueE.DIVISION, CollisionTechniqueE.LINEAR_PROBING, M_ALLOWED_CAPACITY, MIN_ALLOWED_CAPACITY);
        this.insertTestHelper();
        System.out.println("Searching for 11");
        HashNode node = this.openHashTable.search(11);
        System.out.println(node.toString());
    }

    @Test
    void deleting() {
        this.openHashTable = new HashTable(10, HashingTechniqueE.DIVISION, CollisionTechniqueE.LINEAR_PROBING, M_ALLOWED_CAPACITY, MIN_ALLOWED_CAPACITY);
        System.out.println("started deletingTest");
        System.out.println("     inserting with linear probing");
        this.insertTestHelper();
        System.out.println("     ---------------------------------------------");
        System.out.println("     Began removing process");

        for (SinglyLinkedListNode node : this.dataList) {
            if (!this.openHashTable.isEmpty()) {
                int data = (int) node.getData();
                this.openHashTable.remove(data);
                this.printTestHelper();
            }
        }
        System.out.println("     ----------------------");

        System.out.println("Finished deletingTest");
    }

    @AfterEach
    void tearDown() {
        this.openHashTable = null;
    }
}