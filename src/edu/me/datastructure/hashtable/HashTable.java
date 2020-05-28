package edu.me.datastructure.hashtable;


import edu.me.datastructure.linkedlist.SinglyLinkedList;
import edu.me.datastructure.model.node.HashNode;
import edu.me.datastructure.model.node.linkedlistnode.SinglyLinkedListNode;

import java.util.ArrayList;
import java.util.List;

public class HashTable<T> {
    private final float LOAD_FACTOR;
    private final HashingMethod.HashingTechnique hMethod;
    private final CollisionMethod.CollisionTechnique cTechnique;
    private int capacity;
    private int quantity;
    private final HashNode[] hashTable;


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
//        if (this.isRehashingNeeded()) this.capacity *= 2;
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
    }
    private void insertByDivisionAndChaining(T item) {
        HashNode hashNode;
        SinglyLinkedListNode<Integer> newNode = new SinglyLinkedListNode<>(((SinglyLinkedListNode<Integer>) item).getData());
        int firstLevelHashingResult = this.firstLevelHashing(newNode.getData());
        int firstLevelIndex = HashingMethod.getIndexByDivisionHashing(firstLevelHashingResult, this.capacity);

        if (this.collisionExists(firstLevelIndex)) {
            hashNode = this.hashTable[firstLevelIndex];
            ((SinglyLinkedList<T>) hashNode.getContent()).insertAtEnd((SinglyLinkedListNode<T>) newNode);
        } else {
            hashNode = new HashNode(new SinglyLinkedList<>(newNode));
            this.hashTable[firstLevelIndex] = hashNode;
        }
        this.quantity++;
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
    private boolean collisionExists(int index) {
        return this.hashTable[index] != null;
    }

    private boolean isEmpty() { return this.quantity == 0; }
}
