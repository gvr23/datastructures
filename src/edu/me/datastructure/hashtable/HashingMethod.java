package edu.me.datastructure.hashtable;

public abstract class HashingMethod {
    public enum HashingTechnique {
        DIVISION,
        FOLDING,
        MODULUS_MULTIPLICATION
    }

    public static int getIndexByDivisionHashing(int hasFunctionResult, int capacity) { return hasFunctionResult % capacity; }
    public static int getIndexByFoldingHashing(int itemRepresentation, int capacity) { return 0; }
    public static int getIndexByModulusMultiplication() {  return 0; }
}
