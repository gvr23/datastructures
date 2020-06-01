package edu.me.datastructure.hashtable;

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
    private final Map<HashingTechniqueE, CollisionTechniqueImp> collisionMap = new HashMap<>();

    {
        collisionMap.put(HashingTechniqueE.DIVISION, DivisionCollision.getInstance());
    }

    private CollisionFactory() {}

    public static CollisionFactory getInstance() {
        if (instance == null) instance = new CollisionFactory();
        return instance;
    }

    public void collisionInsertion(HashNode item, WeakReference<HashTable> hashTableWeakReference) {
        HashTable hashTableReference = Objects.requireNonNull(hashTableWeakReference.get());
        int probes = 0;
        int indexToInsertAttempt = item.getFirstCollidedLocation();
        boolean inserted = hashTableReference.isInserted(item, indexToInsertAttempt);

        CollisionTechniqueImp application = instance
                .getCollisionApplication(hashTableReference.gethMethod())
                .orElseThrow(() -> new IllegalArgumentException("Invalid method or technique"));
        application.setColTechnique(hashTableReference.getcTechnique());

        if (CollisionTechniqueE.CHAINING.equals(hashTableReference.getcTechnique())) this.chainingCollision(item, hashTableReference);
        else {
            try {
                while (!inserted && (probes < hashTableReference.getHashTable().length)) {
                    probes++;
                    indexToInsertAttempt = application.getIndex(item, probes, hashTableReference.getCapacity());
                    inserted = hashTableReference.isInserted(item, indexToInsertAttempt);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error in collision index");
            }
        }
    }

    private void chainingCollision(HashNode item, HashTable hashTableReference) {
        SinglyLinkedListNode newNode = ((SinglyLinkedList) item.getContent()).removeAtBeginning();
        int index = item.getFirstCollidedLocation();

        if (hashTableReference.getHashTable()[index].getContent() instanceof SinglyLinkedList) {
            SinglyLinkedList hashList = (SinglyLinkedList) hashTableReference.getHashTable()[index].getContent();
            hashList.insertAtEnd(newNode);
        }
    }

    private Optional<CollisionTechniqueImp> getCollisionApplication(HashingTechniqueE hashingTechnique) {
        return Optional.ofNullable(collisionMap.get(hashingTechnique));
    }
}
