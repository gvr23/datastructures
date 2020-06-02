package edu.me.datastructure.hashtable;

import edu.me.datastructure.hashtable.option.CollisionTechniqueE;
import edu.me.datastructure.hashtable.option.HashingTechniqueE;
import edu.me.datastructure.model.node.HashNode;
import java.lang.ref.WeakReference;
import java.util.Arrays;

public class HashTable {
    private final float LOAD_FACTOR;
    private final HashingTechniqueE hMethod;
    private final CollisionTechniqueE cTechnique;
    private final CollisionFactory collisionFactory;
    private int capacity;
    private int quantity;
    private HashNode[] hashTable;


    public HashTable(int capacity, HashingTechniqueE hMethod, CollisionTechniqueE cTechnique, float loadFactor) {
        this.capacity = capacity;
        this.quantity = 0;
        this.hMethod = hMethod;
        this.cTechnique = cTechnique;
        this.LOAD_FACTOR = loadFactor;
        this.hashTable = new HashNode[this.capacity];
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
    public HashNode[] getHashTable() {
        return hashTable;
    }

    public void insert(HashNode item) {
        int firstHashingResult = this.firstLevelHashing(item.getNumericRepresentation());
        int hashingMethodIndex = this.hMethod.getIndex(firstHashingResult, this.capacity);

        if (!this.isInserted(item, hashingMethodIndex)) this.shipToCollisionFactory(item, hashingMethodIndex);
        if (this.isRehashingNeeded() && !cTechnique.equals(CollisionTechniqueE.CHAINING)) this.resize();
    }
    protected boolean isInserted(HashNode item, int index) {
        boolean inserted = false;
        if (!this.collisionFactory.collisionExists(index, new WeakReference<>(this))) {
            this.hashTable[index] = item;
            this.quantity++;
            inserted = true;
        }
        return inserted;
    }
    private void shipToCollisionFactory(HashNode item, int hashingMethodIndex) {
        if (!this.isInserted(item, hashingMethodIndex)) {
            item.setFirstCollidedLocation(hashingMethodIndex);
            if (this.cTechnique.equals(CollisionTechniqueE.DOUBLE_HASHING)) {
                int secondHashingResult = this.secondLevelHashing(item.getNumericRepresentation());
                int secondHashingIndex = secondHashingResult % this.capacity;
                if (secondHashingIndex == 0) return;
                item.setSecondLevelHashing(secondHashingIndex);
            }
            this.collisionFactory.collisionRedirection(item, new WeakReference<>(this));
        }
    }

    public HashNode remove(HashNode item) {
        return item;
    }

    private int firstLevelHashing(int itemRepresentation) {
        return (2 * itemRepresentation) + 3;
    }

    private int secondLevelHashing(int itemRepresentation) {
        return (3 * itemRepresentation) + 1;
    }

    private boolean isRehashingNeeded() {
        return (((float) this.quantity / this.capacity) > this.LOAD_FACTOR);
    }

    private void resize() {
        int newLength = this.capacity *= 2;
        this.hashTable = Arrays.copyOf(this.hashTable, newLength);
    }


}
