package edu.me.datastructure.hashtable.collision;

public final class FoldingCollision extends CollisionTechniqueGeneral {
    private static FoldingCollision instance;

    private FoldingCollision() {}

    public static FoldingCollision getInstance() {
        if (instance == null) instance = new FoldingCollision();
        return instance;
    }

    @Override
    protected int getIndexByLinearProbing(int collidedLocation, int currentPosition, int capacity) {
        return 0;
    }

    @Override
    protected int getIndexByQuadraticProbing(int collidedLocation, int currentPosition, int capacity) {
        return 0;
    }

    @Override
    protected int getIndexByDoubleHashing(int collidedLocation, int secondLevelHashingResult, int currentPosition, int capacity) {
        return 0;
    }
}
