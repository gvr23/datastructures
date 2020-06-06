package edu.me.datastructure.hashtable;

import edu.me.datastructure.hashtable.collision.CollisionTechniqueGeneral;
import edu.me.datastructure.hashtable.collision.DivisionCollision;
import edu.me.datastructure.hashtable.option.HashingTechniqueE;
import edu.me.datastructure.hashtable.option.CollisionTechniqueE;
import edu.me.datastructure.linkedlist.SinglyLinkedList;
import edu.me.datastructure.model.node.HashNode;
import edu.me.datastructure.model.node.linkedlistnode.SinglyLinkedListNode;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class CollisionFactory {
    private static CollisionFactory instance;
    private static CollisionTechniqueGeneral collisionSatelliteDivision;
    private static HashTable hashTableClassReference;
    private final Map<HashingTechniqueE, CollisionTechniqueGeneral> collisionMap = new HashMap<>();

    {
        collisionMap.put(HashingTechniqueE.DIVISION, DivisionCollision.getInstance());
        collisionMap.put(HashingTechniqueE.FOLDING, DivisionCollision.getInstance());
        collisionMap.put(HashingTechniqueE.MODULUS_MULTIPLICATION, DivisionCollision.getInstance());
    }

    private CollisionFactory() {}

    public static CollisionFactory getInstance() {
        if (instance == null) {
            instance = new CollisionFactory();
            collisionSatelliteDivision = instance
                    .getCollisionApplication(hashTableClassReference.gethMethod())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid method or technique"));
        }
        collisionSatelliteDivision.setColTechnique(hashTableClassReference.getcTechnique());

        return instance;
    }
    private Optional<CollisionTechniqueGeneral> getCollisionApplication(HashingTechniqueE hashingTechnique) {
        return Optional.ofNullable(collisionMap.get(hashingTechnique));
    }
    public static void setHashTableClassReference(HashTable hashTableClassReference) {
        CollisionFactory.hashTableClassReference = hashTableClassReference;
    }

    public Object collisionRedirection(HashNode item) {
        Object executedSuccessFully;

        if (item.isActive()) {
            if (CollisionTechniqueE.CHAINING.equals(hashTableClassReference.getcTechnique())) executedSuccessFully = this.chainingCollision(item);
            else executedSuccessFully = this.insertionProcess(item);
        } else executedSuccessFully = this.collisionSearch(item);

        return executedSuccessFully;
    }
    private boolean chainingCollision(HashNode item) {
        boolean inserted = true;
        try {
            if (item.getContent() instanceof SinglyLinkedList) {
                SinglyLinkedList newSinglyList = (SinglyLinkedList) item.getContent();
                SinglyLinkedListNode newSinglyNode = newSinglyList.removeAtBeginning();
                SinglyLinkedList collidedList = (SinglyLinkedList) hashTableClassReference.requestNode(item.getFirstLocation()).getContent();
                collidedList.insertAtEnd(newSinglyNode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in the chaining collision " + e.toString());
            inserted = false;
        }
        return inserted;
    }
    private boolean insertionProcess(HashNode item) {
        int probes = 0;
        int indexToInsertAttempt = item.getFirstLocation();
        boolean inserted = hashTableClassReference.isInserted(item, indexToInsertAttempt);

        try {
            int hashTableLength = hashTableClassReference.getLength();

            while (!inserted && (probes < hashTableLength)) {
                probes++;
                indexToInsertAttempt = collisionSatelliteDivision.getIndex(item, probes, hashTableClassReference.getCapacity());
                inserted = hashTableClassReference.isInserted(item, indexToInsertAttempt);
            }
            item.setProbes(probes);
        } catch (Exception e) {
            System.out.println("Error in collision index " + e.toString());
            inserted = false;
        }
        return inserted;
    }

    public HashNode collisionSearch(HashNode surrogateNode) {
        int probes = 0;
        int indexToSearch = surrogateNode.getFirstLocation();
        HashNode possibleNode = hashTableClassReference.requestNode(indexToSearch);

        try {
            while (possibleNode == null || (possibleNode.getNumericRepresentation() != surrogateNode.getNumericRepresentation()) && (probes < hashTableClassReference.getCapacity())) {
                probes++;
                indexToSearch = collisionSatelliteDivision.getIndex(surrogateNode, probes, hashTableClassReference.getCapacity());
                possibleNode = hashTableClassReference.requestNode(indexToSearch);
            }
            if (probes == hashTableClassReference.getCapacity()) possibleNode = null;
        } catch (Exception e) {
            System.out.println("Error in collision search " + e.toString());
        }
        return possibleNode;
    }

    public boolean collisionExists(int index) {
        HashNode fetchedNode = hashTableClassReference.requestNode(index);
        boolean collided = false;

        if (fetchedNode != null) {
            collided = true;
        }
        return collided;
    }
}
