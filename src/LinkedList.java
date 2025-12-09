/**

**/

public class LinkedList {
    
    public Node head;
    public int size;
    
    public LinkedList() {
        this.head = null;
        this.size = 0;
    }
    
    public void add(AirObject obj) {
        Node newNode = new Node(obj);
        // If the new nodes name comes before the current head nodes name
        if (newNode.getName().compareTo(head.getName()) < 0) {
        	newNode.next = head;
        	head = newNode;
        	size++;
        	return;
        }
        // If it does not
        Node currentNode = head;
        Node nextNode = head.next;
        while (nextNode != null && newNode.getName().compareTo(nextNode.getName()) < 0) {
        	currentNode = nextNode;
            nextNode = nextNode.next;
        }
        currentNode.next = newNode;
        newNode.next = nextNode;
        size++;
    }
    
    public boolean remove(AirObject obj) {
        Node current = head;
        Node previous = null;
        
        while (current != null) {
            if (current.getName().equals(obj.getName())) {
                if (previous == null) {
                    head = current.next;
                } else {
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
    
    private static class Node {
        public AirObject air;
        public Node next;
        
        public Node(AirObject obj) {
            this.air = obj;
            this.next = null;
        }

        public String getName() {
            return air.getName();
        }
    }
    
}