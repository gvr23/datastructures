package edu.me.datastructure.hashtable;

public enum HashingTechniqueE {
    DIVISION () {
        @Override
        public int getIndex(int hashFunctionResult, int capacity) {
            return hashFunctionResult % capacity;
        }
    },
    FOLDING {
        @Override
        public int getIndex(int hashFunctionResult, int capacity) {
            return 0;
        }
    },
    MODULUS_MULTIPLICATION {
        @Override
        public int getIndex(int hashFunctionResult, int capacity) {
            return 0;
        }
    };

    public abstract int getIndex(int hashFunctionResult, int capacity);
}
