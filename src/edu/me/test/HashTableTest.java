import edu.me.datastructure.hashtable.CollisionMethod;
import edu.me.datastructure.hashtable.HashTable;
import edu.me.datastructure.hashtable.HashingMethod;
import edu.me.datastructure.linkedlist.SinglyLinkedList;
import edu.me.datastructure.model.node.HashNode;
import edu.me.datastructure.model.node.linkedlistnode.SinglyLinkedListNode;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

class HashTableTest {
    private final float LOAD_FACTOR = 0.75f;
    private final ArrayList<SinglyLinkedListNode> dataList = new ArrayList<>();
    private HashTable openHashTable;

    @BeforeEach
    void setUp() {
        try {
            System.out.println("Hash table prepare to initialize");

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
        this.openHashTable = new HashTable(10, HashingMethod.HashingTechnique.DIVISION, CollisionMethod.CollisionTechnique.CHAINING, LOAD_FACTOR);
        for (SinglyLinkedListNode node : dataList) {
            surrogateList = new SinglyLinkedList(node);
            surrogateNode = new HashNode(surrogateList);
            surrogateNode.setNumericRepresentation((Integer) node.getData());
            openHashTable.insert(surrogateNode);
        }
        System.out.println("finished inserting with chaining");
    }

    @Test
    void insertLinearProbing() {
        this.openHashTable = new HashTable(10, HashingMethod.HashingTechnique.DIVISION, CollisionMethod.CollisionTechnique.LINEAR_PROBING, LOAD_FACTOR);
        System.out.println("finished inserting with linear probing");
        this.insertTestHelper();
        System.out.println("Finished printing with linear probing");
    }

    @Test
    void insertingQuadraticProbing() {
        HashNode surrogateNode;
        this.openHashTable = new HashTable(10, HashingMethod.HashingTechnique.DIVISION, CollisionMethod.CollisionTechnique.QUADRATIC_PROBING, LOAD_FACTOR);
        for (SinglyLinkedListNode node : dataList) {
            surrogateNode = new HashNode(node);
            surrogateNode.setNumericRepresentation((Integer) node.getData());
            openHashTable.insert(surrogateNode);
        }
        System.out.println("Finished inserting with quadratic probing");
        for (HashNode node : this.openHashTable.getHashTable()) {
            int itemToPrint = (node == null) ? 0 : (int) ((SinglyLinkedListNode) node.getContent()).getData();
            System.out.print(String.format(" <-- %s", itemToPrint));
        }
        System.out.println();
        System.out.println("Finished printing with quadratic probing");
    }

    @Test
    void insertingWithDoubleHashing() {
        this.openHashTable = new HashTable(10, HashingMethod.HashingTechnique.DIVISION, CollisionMethod.CollisionTechnique.DOUBLE_HASHING, LOAD_FACTOR);
        System.out.println("Finished inserting with double hashing");
        this.insertTestHelper();
        System.out.println("Finished printing with  double hashing");
    }
    private void insertTestHelper() {
        HashNode surrogateNode;
        for (SinglyLinkedListNode node : dataList) {
            surrogateNode = new HashNode(node);
            surrogateNode.setNumericRepresentation((Integer) node.getData());
            openHashTable.insert(surrogateNode);
        }
        for (HashNode node : this.openHashTable.getHashTable()) {
            int itemToPrint = (node == null) ? 0 : (int) ((SinglyLinkedListNode) node.getContent()).getData();
            System.out.print(String.format(" <-- %s", itemToPrint));
        }
        System.out.println();
    }

    @AfterEach
    void tearDown() {
        this.openHashTable = null;
    }
}