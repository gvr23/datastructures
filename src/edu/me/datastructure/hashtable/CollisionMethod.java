package edu.me.datastructure.hashtable;

public abstract class CollisionTechnique {
    public enum ClosedHashing {
            LINEAR_PROBING,
            QUADRATIC_PROBING,
            DOUBLE_HASHING
    };
    public enum OpenHashing {
        CHAINING
    }
}
