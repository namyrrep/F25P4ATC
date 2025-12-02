/**
 * 
 */

/**
 * 
 */
public class BinTree {
        
    // Empty leaf node for flyweight pattern
    private final Node EMPTY_LEAF = new Node(null);

    private class Node {
        AirObject value;
        Node left;
        Node right;
        
        

        public Node(AirObject value) {
            this.value = value;
            this.left = null;
            this.right = null;
        }
        
        public Node(AirObject value, Node left, Node right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }
        
        public AirObject getValue() {
            return value;
        }
        
        
    }
    
    
    public BinTree() {
        // Constructor implementation
        // World is assumed to be largest size of 0 - 1023 in each dimension
    }
    
    public boolean insert(AirObject obj) {
        // Insert implementation
        return false;
    }
    
    public AirObject find(String name) {
        // Find implementation
        return null;
    }
    
    public String remove(String name) {
        // Remove implementation
        return null;
    }
    
    public boolean intersects(AirObject obj) {
        // Intersects implementation
        return false;
    }
}
