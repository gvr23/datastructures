package edu.me.datastructure.hashtable;

import edu.me.datastructure.hashtable.collision.CollisionTechniqueGeneral;
import edu.me.datastructure.hashtable.collision.DivisionCollision;
import edu.me.datastructure.hashtable.option.HashingTechniqueE;
import edu.me.datastructure.hashtable.option.CollisionTechniqueE;
import edu.me.datastructure.linkedlist.SinglyLinkedList;
import edu.me.datastructure.model.node.HashNode;
import edu.me.datastructure.model.node.linkedlistnode.SinglyLinkedListNode;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public final class CollisionFactory {
    private static CollisionFactory instance;
    private final Map<HashingTechniqueE, CollisionTechniqueGeneral> collisionMap = new HashMap<>();

    {
        collisionMap.put(HashingTechniqueE.DIVISION, DivisionCollision.getInstance());
        collisionMap.put(HashingTechniqueE.FOLDING, DivisionCollision.getInstance());
        collisionMap.put(HashingTechniqueE.MODULUS_MULTIPLICATION, DivisionCollision.getInstance());
    }

    private CollisionFactory() {}

    public static CollisionFactory getInstance() {
        if (instance == null) instance = new CollisionFactory();
        return instance;
    }

    public boolean collisionExists(int index, WeakReference<HashTable> hashTableWeakReference) {
        HashTable hashTableReference = Objects.requireNonNull(hashTableWeakReference.get());
        HashNode fetchedNode = hashTableReference.getHashTable()[index];
        boolean collided = false;

        if (fetchedNode != null) {
            collided = true;
            fetchedNode.addACollidedNumber();
        }
        return collided;
    }

    public void collisionRedirection(HashNode item, WeakReference<HashTable> hashTableWeakReference) {
        HashTable hashTableReference = Objects.requireNonNull(hashTableWeakReference.get());
        if (CollisionTechniqueE.CHAINING.equals(hashTableReference.getcTechnique())) this.chainingCollision(item, hashTableReference);
        else this.insertionProcess(item, hashTableReference);
    }
    private void insertionProcess(HashNode item, HashTable hashTableReference) {
        CollisionTechniqueGeneral application;
        int probes = 0;
        int indexToInsertAttempt = item.getFirstCollidedLocation();
        boolean inserted = hashTableReference.isInserted(item, indexToInsertAttempt);

        try {
            int hashTableLength = hashTableReference.getHashTable().length;
            application = instance
                    .getCollisionApplication(hashTableReference.gethMethod())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid method or technique"));
            application.setColTechnique(hashTableReference.getcTechnique());

            while (!inserted && (probes < hashTableLength)) {
                probes++;
                indexToInsertAttempt = application.getIndex(item, probes, hashTableReference.getCapacity());
                inserted = hashTableReference.isInserted(item, indexToInsertAttempt);
            }
            item.setProbes(probes);
        } catch (Exception e) {
            System.out.println("Error in collision index " + e.toString());
        }
    }
    private Optional<CollisionTechniqueGeneral> getCollisionApplication(HashingTechniqueE hashingTechnique) {
        return Optional.ofNullable(collisionMap.get(hashingTechnique));
    }

    private void chainingCollision(HashNode item, HashTable hashTableReference) {
        SinglyLinkedListNode newNode = ((SinglyLinkedList) item.getContent()).removeAtBeginning();
        int index = item.getFirstCollidedLocation();

        if (hashTableReference.getHashTable()[index].getContent() instanceof SinglyLinkedList) {
            SinglyLinkedList hashList = (SinglyLinkedList) hashTableReference.getHashTable()[index].getContent();
            hashList.insertAtEnd(newNode);
        }
    }
}
