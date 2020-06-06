package edu.me.datastructure.hashtable;

import edu.me.datastructure.hashtable.option.CollisionTechniqueE;
import edu.me.datastructure.hashtable.option.HashingTechniqueE;
import edu.me.datastructure.model.node.HashNode;

public class HashTable {
    private final float MAX_ALLOWED_CAPACITY;
    private final float MIN_ALLOWED_CAPACITY;
    private final HashingTechniqueE hMethod;
    private final CollisionTechniqueE cTechnique;
    private final CollisionFactory collisionFactory;
    private int capacity;
    private int quantity;
    private HashNode[] hashArray;


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
        int firstLevelIndex = this.getFirstLevelIndex(surrogateNode.getNumericRepresentation());

        if (!this.isInserted(surrogateNode, firstLevelIndex)) this.shipToCollisionFactory(surrogateNode);
        if (this.isRehashingNeeded() && !cTechnique.equals(CollisionTechniqueE.CHAINING)) this.resize();
    }
    private HashNode attachNodeToContent(Object content, int numericRepresentation) {
        HashNode node = new HashNode(content);
        node.setNumericRepresentation(numericRepresentation);
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
            this.quantity++;
            inserted = true;
        }
        return inserted;
    }
    private Object shipToCollisionFactory(HashNode item) {
        if (this.cTechnique.equals(CollisionTechniqueE.DOUBLE_HASHING)) {
            int secondHashingResult = this.secondLevelHashing(item.getNumericRepresentation());
            int secondHashingIndex = secondHashingResult % this.capacity;
            if (secondHashingIndex == 0) return false;
            item.setSecondLevelHashing(secondHashingIndex);
        }
         return this.collisionFactory.collisionRedirection(item);
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

    private void resize() {
        this.capacity *= 2;
        HashNode[] tempHashNodeArray = this.hashArray;
        this.hashArray = new HashNode[this.capacity];
        for (HashNode node : tempHashNodeArray) {
            if (node != null) this.reInsert(node);
        }
    }
    private void reInsert(HashNode originalNode) {
        int firstLevelIndex = this.getFirstLevelIndex(originalNode.getNumericRepresentation());
        if (!isReinserted(firstLevelIndex, originalNode)) this.shipToCollisionFactory(originalNode);
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
            searchedNode.changeToDeleted();
            nodeCopy = searchedNode.transferTo();
            this.removeNode(searchedNode.getFirstLocation());
        } else {
            searchedNode = this.attachNodeToContent(numericRepresentation, numericRepresentation);
            searchedNode.changeToDeleted();
            int firstLevelIndex = this.getFirstLevelIndex(numericRepresentation);
            searchedNode.setLocation(firstLevelIndex);
            searchedNode = (HashNode) this.shipToCollisionFactory(searchedNode);
            if (searchedNode!= null){
                //TODO: error with -7 in double hashing
                searchedNode.setLocation(searchedNode.getFirstLocation());
                this.removeNode(searchedNode.getFirstLocation());
            }
        }

        return nodeCopy;
    }
    public HashNode search(int numericRepresentation) {
        HashNode surrogateNode = this.attachNodeToContent(numericRepresentation, numericRepresentation);
        int firstLevelIndex = this.getFirstLevelIndex(numericRepresentation);
        HashNode possibleNode = this.hashArray[firstLevelIndex];

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
    protected void removeNode(int location) { this.hashArray[location] = null; }
    public boolean isEmpty() {
        boolean isEmpty = true;
        for (int current = 0; current < this.hashArray.length; current++) {
            if (this.hashArray[current] != null) {
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
