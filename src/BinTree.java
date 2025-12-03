/**
 * 
 */

/**
 * 
 */
public class BinTree {
        
    // Empty leaf node for flyweight pattern
	private static final Node FLYWEIGHT = new FlyweightNode();
	
	private Node root; 
    
    private static class FlyweightNode extends Node {
    	
        FlyweightNode() {
        	super(null);
        }
    }

    private class Node {
        AirObject value;
        Node left;
        Node right;
        
        private class FlyweightNode extends Node {
        	
            FlyweightNode() {
            	super(new AirObject("FlyWeigt", -1, -1, -1, -1, -1, -1));
            }
        }

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
        
        // This method adds the node based on its position
        // Recursive method, starts with the root, which cannot be a fly weight, must have two children. 
        public void insert(int x, int y, int z, int xwid, int ywid, int zwid, int level) {
        	// Must continuously add flyWeights using recursion to fill out the bin tree
            // Logic for splitting by the x dimension
        	// Make sure left/right is not null or a position node. 
        	if (level % 3 == 0) {
                // Left Child 
            	if (value.getXOrg() < x) {
            		right = FLYWEIGHT;
            		left.insert(x, y, z, xwid, ywid, zwid, level + 1);
            	}
                // Right Child
            	else if (value.getXOrg() > x) {
            		left = FLYWEIGHT;
            		right.insert(x, y, z, xwid, ywid, zwid, level + 1);
            	}
            	else {
            		
            	}
        	}
            // Logic for splitting by the y dimension
        	else if (level % 3 == 1) {
	            // Left Child
	
	
	            // Right Child

        	}
            // Logic for splitting by the z dimension
        	else {
            // Left Child   


            // Right Child

        	}
        }
        
        public AirObject find(AirObject name) {
            // Find implementation
            return null;
        }
        
        public void remove(String name) {
            // Remove implementation
        }
    }
    
    
    public BinTree() {
    	root = new Node(null);
        // Constructor implementation
        // World is assumed to be largest size of 0 - 1023 in each dimension
    }
    
    public boolean insert(AirObject obj) {
        root.insert(obj.getXOrg(), obj.getYOrg(), obj.getZOrg(), obj.getXWidth(), obj.getYWidth(), obj.getZWidth(), 0);
        return false;
    }
    
    public AirObject find(AirObject name) {
        root.find(name);
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
