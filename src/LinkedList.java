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
        Node newNode = new Node(obj.getName());
        newNode.next = head;
        head = newNode;
        size++;
    }
    
    public boolean remove(AirObject obj) {
        Node current = head;
        Node previous = null;
        
        while (current != null) {
            if (current.name.equals(obj.getName())) {
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
                if (current.name.compareTo(nextNode.name) > 0) {
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
        public String name;
        public Node next;
        
        public Node(String name) {
            this.name = name;
            this.next = null;
        }
    }
    
}