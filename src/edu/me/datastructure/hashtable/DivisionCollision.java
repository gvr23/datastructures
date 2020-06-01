package edu.me.datastructure.hashtable;

import edu.me.datastructure.model.node.HashNode;

public final class DivisionCollision extends CollisionTechniqueImp {
    private static DivisionCollision instance;


    private DivisionCollision() {}

    public static DivisionCollision getInstance() {
        if (instance == null) instance = new DivisionCollision();
        return instance;
    }
    @Override
    public int getIndex(HashNode item, int currentPosition, int capacity) {
        switch (this.colTechnique) {
            case LINEAR_PROBING:
                return this.getIndexByLinearProbing(item.getFirstCollidedLocation(), currentPosition, capacity);
            case QUADRATIC_PROBING:
                return this.getIndexByQuadraticProbing(item.getFirstCollidedLocation(), currentPosition, capacity);
            case DOUBLE_HASHING:
                return this.getIndexByDoubleHashing(item.getFirstCollidedLocation(), item.getSecondLevelHashing(), currentPosition, capacity);
            default:
                return -1;
        }
    }

    private int getIndexByLinearProbing(int collidedLocation, int currentPosition, int capacity) {
        return (collidedLocation + currentPosition) % capacity;
    }

    private int getIndexByQuadraticProbing(int collidedLocation, int currentPosition, int capacity) {
        return (int) ((collidedLocation + Math.pow(currentPosition, 2)) % capacity);
    }

    private int getIndexByDoubleHashing(int collidedLocation, int secondLevelHashingResult, int currentPosition, int capacity) {
        return (collidedLocation + secondLevelHashingResult * currentPosition) % capacity;
    }
}
