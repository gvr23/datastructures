package edu.me.datastructure.hashtable;


import edu.me.datastructure.linkedlist.SinglyLinkedList;
import edu.me.datastructure.model.node.HashNode;
import edu.me.datastructure.model.node.linkedlistnode.SinglyLinkedListNode;
import edu.me.utils.Helper;

import java.lang.ref.WeakReference;
import java.util.Arrays;

public class HashTable<T> {
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

    public void insert(T item) {
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

    private void insertByDivision(T item) {
        switch (this.cTechnique) {
            case CHAINING:
                this.insertByDivisionAndChaining(item);
                break;
            case LINEAR_PROBING:
                this.insertByDivisionAndLinearProbing(item);
                break;
            case QUADRATIC_PROBING:
                this.insertByDivisionAndQuadraticProbing(item);
                break;
            case DOUBLE_HASHING:
                this.insertByDivisionAndDoubleHashing(item);
                break;
        }
        this.quantity++;
    }
    private void insertByDivisionAndChaining(Object item) {
        SinglyLinkedListNode newNode = new SinglyLinkedListNode(((SinglyLinkedListNode) item).getData());
        int hashingResult = this.firstLevelHashing((Integer) newNode.getData());
        int hashingMethodIndex = HashingMethod.getIndexByDivisionHashing(hashingResult, this.capacity);

        if (!this.isInsertedFirstTry(new SinglyLinkedList(newNode), hashingMethodIndex)) {
            HashNode hashNode = this.hashTable[hashingMethodIndex];
            ((SinglyLinkedList) hashNode.getContent()).insertAtEnd(newNode);
        }
    }
    private boolean isInsertedFirstTry(Object item, int index) {
        boolean inserted = false;
        if (!this.collisionExists(index)) {
            this.hashTable[index] = new HashNode(item);
            inserted = true;
        }
        return inserted;
    }
    private void insertByDivisionAndLinearProbing(T item) {

    }
    private void insertByDivisionAndQuadraticProbing(T item) {

    }
    private void insertByDivisionAndDoubleHashing(T item) {

    }

    private void insertByFolding(T item) {

    }
    private void insertByFoldingAndChaining(T item) {

    }
    private void insertByFoldingAndLinearProbing(T item) {

    }
    private void insertByFoldingAndQuadraticProbing(T item) {

    }
    private void insertByFoldingAndDoubleHashing(T item) {

    }

    private void insertByModulusMultiplication(T item) {

    }
    private void insertByModMultAndChaining() {

    }
    private void insertByModMultAndLinearProbing() {

    }
    private void insertByModMultAndQuadraticProbing() {

    }
    private void insertByModMultAndDoubleHashing() {

    }

    public T remove(T item) {
        return item;
    }

    public T search(T item) {
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
