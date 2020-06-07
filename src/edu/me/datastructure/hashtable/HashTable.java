package edu.me.datastructure.hashtable;

import edu.me.datastructure.hashtable.option.CollisionTechniqueE;
import edu.me.datastructure.hashtable.option.HashingTechniqueE;
import edu.me.datastructure.model.node.HashNode;

import java.util.function.Function;
import java.util.function.Supplier;

public class HashTable {
    private final float MAX_ALLOWED_CAPACITY;
    private final float MIN_ALLOWED_CAPACITY;
    private final HashingTechniqueE hMethod;
    private final CollisionTechniqueE cTechnique;
    private final CollisionFactory collisionFactory;
    private int capacity;
    private int quantity;
    private HashNode[] hashArray;
    private boolean rehashing;


    public HashTable(int capacity, HashingTechniqueE hMethod, CollisionTechniqueE cTechnique, float maxCapacity, float minCapacity) {
        this.capacity = capacity;
        this.quantity = 0;
        this.hMethod = hMethod;
        this.cTechnique = cTechnique;
        this.MAX_ALLOWED_CAPACITY = maxCapacity;
        this.MIN_ALLOWED_CAPACITY = minCapacity;
        this.hashArray = new HashNode[this.capacity];
        CollisionFactory.setHashTableClassReference(this);
        this.collisionFactory = CollisionFactory.getInstance();
        this.rehashing = false;
    }

    public int getCapacity() {
        return capacity;
    }
    public int getQuantity() {
        return quantity;
    }
    public HashingTechniqueE gethMethod() {
        return hMethod;
    }
    public CollisionTechniqueE getcTechnique() {
        return cTechnique;
    }
    public HashNode[] getHashArray() {
        return hashArray;
    }

    public void insert(Object item, int itemRepresentation) {
        HashNode surrogateNode = this.attachNodeToContent(item, itemRepresentation);

        if (!this.isInserted(surrogateNode, surrogateNode.getFirstLevelIndex())) this.shipToCollisionFactory(surrogateNode);
        if (this.isRehashingNeeded() && !cTechnique.equals(CollisionTechniqueE.CHAINING)) this.resize(() -> this.capacity * 2);
    }
    private HashNode attachNodeToContent(Object content, int numericRepresentation) {
        HashNode node = new HashNode(content);
        int firstLevelIndex = this.getFirstLevelIndex(numericRepresentation);
        node.setNumericRepresentation(numericRepresentation);
        node.setFirstLevelIndex(firstLevelIndex);
        return node;
    }
    private int firstLevelHashing(int itemRepresentation) {
        return (2 * itemRepresentation) + 3;
    }
    private int getFirstLevelIndex(int numericRepresentation) {
        int firstHashingResult = this.firstLevelHashing(numericRepresentation);
        return this.hMethod.getIndex(firstHashingResult, this.capacity);
    }
    protected boolean isInserted(HashNode item, int index) {
        boolean inserted = false;

        if (!this.collisionFactory.collisionExists(index)) {
            item.setLocation(index);
            this.hashArray[index] = item;
            if (!this.rehashing) this.quantity++;
            inserted = true;
        }
        return inserted;
    }
    private Object shipToCollisionFactory(HashNode item) {
        if (this.cTechnique.equals(CollisionTechniqueE.DOUBLE_HASHING)) this.attachSecondLevelIndex(item);
        return this.collisionFactory.collisionRedirection(item);
    }
    private void attachSecondLevelIndex(HashNode item) {
        int secondHashingResult = this.secondLevelHashing(item.getNumericRepresentation());
        int secondHashingIndex = secondHashingResult % this.capacity;
        if (secondHashingIndex != 0) item.setSecondLevelIndex(secondHashingIndex);
    }
    private int secondLevelHashing(int itemRepresentation) {
        return (3 * itemRepresentation) + 1;
    }

    private boolean isRehashingNeeded() {
        return (((float) this.quantity / this.capacity) > this.MAX_ALLOWED_CAPACITY);
    }
    private boolean isUnderHashingNeeded() {
        return (((float) this.quantity / this.capacity) < this.MIN_ALLOWED_CAPACITY);
    }

    private void resize(Supplier<Integer> getNewCapacity) {
        this.capacity = getNewCapacity.get();
        this.rehashing = true;
        HashNode[] tempHashNodeArray = this.hashArray;
        this.hashArray = new HashNode[this.capacity];
        for (HashNode node : tempHashNodeArray) {
            if (node != null) this.reInsert(node);
        }
        this.rehashing = false;
    }
    private void reInsert(HashNode originalNode) {
        originalNode = this.attachNodeToContent(originalNode.getContent(),originalNode.getNumericRepresentation());
        if (!isReinserted(originalNode.getFirstLevelIndex(), originalNode)) this.shipToCollisionFactory(originalNode);
    }
    private boolean isReinserted(int location, HashNode node) {
        boolean inserted = false;
        if (this.hashArray[location] == null) {
            node.setLocation(location);
            this.hashArray[location] = node;
            inserted = true;
        }
        return inserted;
    }

    public HashNode remove(int numericRepresentation) {
        HashNode searchedNode = this.search(numericRepresentation);
        HashNode nodeCopy = null;

        if (searchedNode != null) {
            nodeCopy = new HashNode(-1);
            searchedNode.changeToDeleted();
            searchedNode.transferTo(nodeCopy);
            this.removeNode(searchedNode.getLocation());
        } else this.deleteByCollision(this.attachNodeToContent(numericRepresentation, numericRepresentation));
        if (this.isUnderHashingNeeded() && !cTechnique.equals(CollisionTechniqueE.CHAINING)) this.resize(() -> this.capacity / 2);
        return nodeCopy;
    }
    private void deleteByCollision(HashNode nodeToDelete) {
        nodeToDelete.changeToDeleted();
        nodeToDelete.setLocation(nodeToDelete.getFirstLevelIndex());
        nodeToDelete = (HashNode) this.shipToCollisionFactory(nodeToDelete);
        if (nodeToDelete != null){
            nodeToDelete.setLocation(nodeToDelete.getLocation());
            this.removeNode(nodeToDelete.getLocation());
        }
    }
    public HashNode search(int numericRepresentation) {
        HashNode surrogateNode = this.attachNodeToContent(numericRepresentation, numericRepresentation);
        HashNode possibleNode = this.hashArray[surrogateNode.getFirstLevelIndex()];

        if (possibleNode != null) {
            if (possibleNode.getNumericRepresentation() != numericRepresentation) {
                surrogateNode.changeToTemporary();
                possibleNode = (HashNode) this.shipToCollisionFactory(surrogateNode);
            }
        }

        return possibleNode;
    }


    protected HashNode requestNode(int location) {
        return this.hashArray[location];
    }
    protected void removeNode(int location) {
        this.hashArray[location] = null;
        this.quantity--;
    }
    public boolean isEmpty() {
        boolean isEmpty = true;
        for (HashNode hashNode : this.hashArray) {
            if (hashNode != null) {
                isEmpty = false;
                break;
            }
        }
        return isEmpty;
    }
    protected int getLength() {
        return this.hashArray.length;
    }
}
