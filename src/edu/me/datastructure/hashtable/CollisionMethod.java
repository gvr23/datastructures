package edu.me.datastructure.hashtable;

public abstract class CollisionMethod {
    public enum CollisionTechnique {
        CHAINING,
        LINEAR_PROBING,
        QUADRATIC_PROBING,
        DOUBLE_HASHING
    }



    public static int linearProbingDivisionIndex(int collidedLocation, int currentPosition, int capacity) {
        return (collidedLocation + currentPosition) % capacity;
    }

    public static int quadraticProbingDivisionIndex(int collidedLocation, int currentPosition, int capacity) {
        return (int) ((collidedLocation + Math.pow(currentPosition, 2)) % capacity);
    }

    public static int doubleHashingDivisionIndex(int collidedLocation, int secondLevelHashingResult, int currentPosition, int capacity) {
        return (collidedLocation + secondLevelHashingResult * currentPosition) % capacity;
    }
}
