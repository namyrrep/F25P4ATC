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
        newNode.next = head;
        head = newNode;
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
    
    public boolean sortAlphabetically() {
        if (head == null || head.next == null) {
            return true; // List is empty or has one element
        }
        
        Node current = head;
        while (current != null) {
            Node nextNode = current.next;
            while (nextNode != null) {
                if (current.getName().compareTo(nextNode.getName()) > 0) {
                    return false; // Found an out-of-order pair
                }
                nextNode = nextNode.next;
            }
            current = current.next;
        }
        return true; // List is sorted
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