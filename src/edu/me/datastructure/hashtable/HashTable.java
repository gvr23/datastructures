package edu.me.datastructure.hashtable;


import edu.me.datastructure.linkedlist.SinglyLinkedList;
import edu.me.datastructure.model.node.HashNode;
import edu.me.datastructure.model.node.linkedlistnode.SinglyLinkedListNode;
import edu.me.utils.Helper;

import java.lang.ref.WeakReference;
import java.util.Arrays;

public class HashTable {
    private final float LOAD_FACTOR;
    private final HashingMethod.HashingTechnique hMethod;
    private final CollisionMethod.CollisionTechnique cTechnique;
    private int capacity;
    private int quantity;
    private HashNode[] hashTable;


    public HashTable(int capacity, HashingMethod.HashingTechnique hMethod, CollisionMethod.CollisionTechnique cTechnique, float loadFactor) {
        this.capacity = capacity;
        this.quantity = 0;
        this.hMethod = hMethod;
        this.cTechnique = cTechnique;
        this.LOAD_FACTOR = loadFactor;
        this.hashTable = new HashNode[this.capacity];
    }

    public int getCapacity() {
        return capacity;
    }
    public int getQuantity() {
        return quantity;
    }
    public HashNode[] getHashTable() { return hashTable; }

    public void insert(HashNode item) {
        switch (this.hMethod) {
            case DIVISION:
                this.insertByDivision(item);
                break;
            case FOLDING:
                this.insertByFolding(item);
                break;
            case MODULUS_MULTIPLICATION:
                this.insertByModulusMultiplication(item);
                break;
        }
        if (this.isRehashingNeeded() && !cTechnique.equals(CollisionMethod.CollisionTechnique.CHAINING)) this.resize();
    }

    private void insertByDivision(HashNode item) {
        int hashingResult = this.firstLevelHashing(item.getNumericRepresentation());
        int hashingMethodIndex = HashingMethod.getIndexByDivisionHashing(hashingResult, this.capacity);

        if (!this.isInserted(item, hashingMethodIndex)) {
            switch (this.cTechnique) {
                case CHAINING:
                    this.chainingCollision(item, hashingMethodIndex);
                    break;
                case LINEAR_PROBING:
                    this.insertByDivisionAndLinearProbing(item, hashingMethodIndex);
                    break;
                case QUADRATIC_PROBING:
                    this.insertByDivisionAndQuadraticProbing(item, hashingMethodIndex);
                    break;
                case DOUBLE_HASHING:
                    this.insertByDivisionAndDoubleHashing(item);
                    break;
            }
        }

        this.quantity++;
    }
    private boolean isInserted(HashNode item, int index) {
        boolean inserted = false;
        if (!this.collisionExists(index)) {
            this.hashTable[index] = item;
            inserted = true;
        }
        return inserted;
    }
    private void chainingCollision(HashNode item, int index) {
        SinglyLinkedListNode newNode = ((SinglyLinkedList) item.getContent()).removeAtBeginning();
        if (this.hashTable[index].getContent() instanceof SinglyLinkedList) {
            SinglyLinkedList hashList = (SinglyLinkedList) this.hashTable[index].getContent();
            hashList.insertAtEnd(newNode);
        }
    }
    private void insertByDivisionAndLinearProbing(HashNode item, int collidedIndex) {
        int probes = 0;
        int indexToInsertAttempt = collidedIndex;
        while (probes < this.hashTable.length && !this.isInserted(item, indexToInsertAttempt)) {
            probes++;
            indexToInsertAttempt = CollisionMethod.linearProbingDivisionIndex(collidedIndex, probes, this.capacity);
        }
        item.setProbes(probes);
    }
    private void insertByDivisionAndQuadraticProbing(HashNode item, int collidedIndex) {
        int probes = 0;
        int indexToInsertAttempt = collidedIndex;
        while (probes < this.hashTable.length && !this.isInserted(item, indexToInsertAttempt)) {
            probes++;
            indexToInsertAttempt = CollisionMethod.quadraticProbingDivisionIndex(collidedIndex, probes, this.capacity);
        }
        item.setProbes(probes);
    }
    private void insertByDivisionAndDoubleHashing(HashNode item) {

    }

    private void insertByFolding(HashNode item) {

    }
    private void insertByFoldingAndChaining(HashNode item) {

    }
    private void insertByFoldingAndLinearProbing(HashNode item) {

    }
    private void insertByFoldingAndQuadraticProbing(HashNode item) {

    }
    private void insertByFoldingAndDoubleHashing(HashNode item) {

    }

    private void insertByModulusMultiplication(HashNode item) {

    }
    private void insertByModMultAndChaining() {

    }
    private void insertByModMultAndLinearProbing() {

    }
    private void insertByModMultAndQuadraticProbing() {

    }
    private void insertByModMultAndDoubleHashing() {

    }

    public HashNode remove(HashNode item) {
        return item;
    }

    public HashNode search(HashNode item) {
        return item;
    }

    private int firstLevelHashing(int itemRepresentation) {
        return (2 * itemRepresentation) + 3;
    }
    private int secondLevelHashing(int itemRepresentation) {
        return (3 * itemRepresentation) + 1;
    }
    private boolean isRehashingNeeded() { return (((float) this.quantity/this.capacity) > this.LOAD_FACTOR); }
    private void resize() {
        int newLength = this.capacity *= 2;
        this.hashTable = Arrays.copyOf(this.hashTable, newLength);
    }
    private boolean collisionExists(int index) {
        return this.hashTable[index] != null;
    }

    private boolean isEmpty() { return this.quantity == 0; }
}
