package edu.me.datastructure.hashtable.collision;

public final class ModulusMultiplicationCollision extends CollisionTechniqueGeneral {
    private static ModulusMultiplicationCollision instance;

    private ModulusMultiplicationCollision() {}

    public static ModulusMultiplicationCollision getInstance() {
        if (instance == null) instance = new ModulusMultiplicationCollision();
        return instance;
    }

    @Override
    protected int getIndexByLinearProbing(int collidedLocation, int currentPosition, int capacity) {
        return (collidedLocation + currentPosition) % capacity;
    }

    @Override
    protected int getIndexByQuadraticProbing(int collidedLocation, int currentPosition, int capacity) {
        return (int) ((collidedLocation + Math.pow(currentPosition, 2)) % capacity);
    }

    @Override
    protected int getIndexByDoubleHashing(int collidedLocation, int secondLevelHashingResult, int currentPosition, int capacity) {
        return (collidedLocation + secondLevelHashingResult * currentPosition) % capacity;
    }
}
