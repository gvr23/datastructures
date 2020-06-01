package edu.me.datastructure.hashtable;

import edu.me.datastructure.model.node.HashNode;

public abstract class CollisionTechniqueImp {
    protected CollisionTechniqueE colTechnique;

    protected abstract int getIndex(HashNode item, int currentPosition, int capacity);

    public synchronized void setColTechnique(CollisionTechniqueE colTechnique) {
        this.colTechnique = colTechnique;
    }
}
