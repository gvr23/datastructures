package edu.me.datastructure.hashtable.collision;

import edu.me.datastructure.hashtable.option.CollisionTechniqueE;
import edu.me.datastructure.model.node.HashNode;

public abstract class CollisionTechniqueGeneral {
    private CollisionTechniqueE colTechnique;

    public void setColTechnique(CollisionTechniqueE colTechnique) {
        this.colTechnique = colTechnique;
    }

    public int getIndex(HashNode item, int currentPosition, int capacity) {
        switch (this.colTechnique) {
            case LINEAR_PROBING:
                return this.getIndexByLinearProbing(item.getFirstLevelIndex(), currentPosition, capacity);
            case QUADRATIC_PROBING:
                return this.getIndexByQuadraticProbing(item.getFirstLevelIndex(), currentPosition, capacity);
            case DOUBLE_HASHING:
                return this.getIndexByDoubleHashing(item.getFirstLevelIndex(), item.getSecondLevelIndex(), currentPosition, capacity);
            default:
                return -1;
        }
    }

    protected abstract int getIndexByLinearProbing(int collidedLocation, int currentPosition, int capacity);
    protected abstract int getIndexByQuadraticProbing(int collidedLocation, int currentPosition, int capacity);
    protected abstract int getIndexByDoubleHashing(int collidedLocation, int secondLevelHashingResult, int currentPosition, int capacity);
}
