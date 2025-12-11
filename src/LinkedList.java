import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This is our sorted LinkedList that stores AirObjects.
 * @author Edwin Barrack
 * @author Will Perryman
 * @version 12/10/2025
 */
public class LinkedList implements Iterable<AirObject> {
    
    private Node head;
    private int size;
    
    public LinkedList() {
        this.head = null;
        this.size = 0;
    }
    
    public void add(AirObject obj) {
        Node newNode = new Node(obj);

        // Case 1: List is empty or new node should be the new head
        if (head == null || newNode.getName().compareTo(head.getName()) < 0) {
            newNode.next = head;
            head = newNode;
            size++;
            return;
        }

        // Case 2: Find the correct position to insert
        Node currentNode = head;
        while (currentNode.next != null
            && newNode.getName().compareTo(currentNode.next.getName()) > 0) {
            currentNode = currentNode.next;
        }

        // Insert the new node
        newNode.next = currentNode.next;
        currentNode.next = newNode;
        size++;
    }
    
    public boolean remove(AirObject obj) {
        Node current = head;
        Node previous = null;
        
        while (current != null) {
            if (current.getName().equals(obj.getName())) {
                if (previous == null) {
                    head = current.next;
                } 
                else {
                    previous.next = current.next;
                }
                size--;
                return true;
            }
            previous = current;
            current = current.next;
        }
        return false;
    }
    
    public int getSize() {
        return size;
    }
    
    public boolean isEmpty() {
        return size == 0;
    }
    
    public AirObject[] toArray() {
        AirObject[] array = new AirObject[size];
        Node current = head;
        int i = 0;
        while (current != null) {
            array[i++] = current.air;
            current = current.next;
        }
        return array;
    }

    @Override
    public Iterator<AirObject> iterator() {
        return new LinkedListIterator();
    }
    
    private class LinkedListIterator implements Iterator<AirObject> {
        private Node current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public AirObject next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            AirObject air = current.air;
            current = current.next;
            return air;
        }
    }
    
    public static class Node {
        private AirObject air;
        private Node next;
        
        public Node(AirObject obj) {
            this.air = obj;
            this.next = null;
        }

        public String getName() {
            return air.getName();
        }
    }
    
}