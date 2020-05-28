package edu.me.datastructure.hastable;

public class HashTable<T> {
    public abstract static class CollisionTechnique {
        enum ClosedHashing {
            LINEAR_PROBING,
            QUADRATIC_PROBING,
            DOUBLE_HASHING
        };
        enum OpenHashing {
            CHAINING
        }
    }
    public enum HashingMethod {
        DIVISION,
        FOLDING,
        MODULUS_MULTIPLICATION
    }
    private final float LOAD_FACTOR = 0.75f;
    private final HashingMethod hMethod;
    private final CollisionTechnique cTechnique;
    private int capacity;
    private int quantity;
    private T[] hashTable;


    public HashTable(int capacity, T[] hashTable, HashingMethod hMethod, CollisionTechnique cTechnique) {
        this.capacity = capacity;
        this.quantity = 0;
        this.hashTable = hashTable;
        this.hMethod = hMethod;
        this.cTechnique = cTechnique;
    }

    public int getCapacity() {
        return capacity;
    }
    public int getQuantity() {
        return quantity;
    }
    public T[] getHashTable() {
        return hashTable;
    }

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
    }

    private void insertByDivision(T item) {

    }
    private int getIndexByDivisionHashing(int formulaResult) { return formulaResult % this.capacity; }
    private void insertByDivisionAndChaining() {

    }
    private void insertByDivisionAndLinearProbing() {

    }
    private void insertByDivisionAndQuadraticProbing() {

    }
    private void insertByDivisionAndDoubleHashing() {

    }

    private void insertByFolding(T item) {

    }

    private void insertByModulusMultiplication(T item) {

    }

    public T remove(T item) {
        return item;
    }

    public T search(T item) {
        return item;
    }

    private boolean isRehashingNeeded() { return (((float) this.quantity/this.capacity) > this.LOAD_FACTOR); }

    private boolean isEmpty() { return this.quantity == 0; }
}
