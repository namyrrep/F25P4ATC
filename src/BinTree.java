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

    private int worldSize;

    private static class FlyweightNode extends Node {

        FlyweightNode() {
            super(new AirObject("FlyWeight", -1, -1, -1, -1, -1, -1));
        }
    }


    private static class Node {
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


        /**
         * Insert method - returns the node that should take this node's place.
         * Uses composite design pattern.
         */
        public Node insert(
            AirObject newObj,
            int x,
            int y,
            int z,
            int xwid,
            int ywid,
            int zwid,
            int level) {
            // Case 1: This is a FLYWEIGHT - replace with leaf node
            if (this == FLYWEIGHT) {
                return new Node(newObj, FLYWEIGHT, FLYWEIGHT);
            }

            // Case 2: This is a LEAF node (has a value, not flyweight)
            if (this.value != null && !this.value.getName().equals("FlyWeight")) {
                // Check for duplicate location - don't insert
                if (this.value.getXOrg() == newObj.getXOrg() 
                    && this.value.getYOrg() == newObj.getYOrg()
                    && this.value.getZOrg() == newObj.getZOrg()) {
                    return this; // Don't insert duplicate location
                }

                // Need to split - create internal node
                AirObject oldObj = this.value;
                this.value = null; // Convert to internal node
                this.left = FLYWEIGHT;
                this.right = FLYWEIGHT;

                // Re-insert the old object
                this.insertHelper(oldObj, x, y, z, xwid, ywid, zwid, level);
                // Insert the new object
                this.insertHelper(newObj, x, y, z, xwid, ywid, zwid, level);
                
                return this;
            }

            // Case 3: This is an INTERNAL node (value == null)
            this.insertHelper(newObj, x, y, z, xwid, ywid, zwid, level);
            return this;
        }


        /**
         * Helper method for inserting into internal nodes
         */
        private void insertHelper(
            AirObject newObj,
            int x,
            int y,
            int z,
            int xwid,
            int ywid,
            int zwid,
            int level) {
            
            if (level % 3 == 0) { // X dimension split
                int mid = x + (xwid / 2);
                if (newObj.getXOrg() < mid) {
                    this.left = this.left.insert(newObj, x, y, z, xwid / 2, ywid, zwid, level + 1);
                }
                else {
                    this.right = this.right.insert(newObj, mid, y, z, xwid / 2, ywid, zwid, level + 1);
                }
            }
            else if (level % 3 == 1) { // Y dimension split
                int mid = y + (ywid / 2);
                if (newObj.getYOrg() < mid) {
                    this.left = this.left.insert(newObj, x, y, z, xwid, ywid / 2, zwid, level + 1);
                }
                else {
                    this.right = this.right.insert(newObj, x, mid, z, xwid, ywid / 2, zwid, level + 1);
                }
            }
            else { // Z dimension split (level % 3 == 2)
                int mid = z + (zwid / 2);
                if (newObj.getZOrg() < mid) {
                    this.left = this.left.insert(newObj, x, y, z, xwid, ywid, zwid / 2, level + 1);
                }
                else {
                    this.right = this.right.insert(newObj, x, y, mid, xwid, ywid, zwid / 2, level + 1);
                }
            }
        }


        /**
         * Recursive Find Method
         * We do NOT compare against this.value because internal nodes are null.
         * We compare against the CALCULATED midpoint of the world at this
         * level.
         */
        public AirObject find(
            AirObject target,
            int x,
            int y,
            int z,
            int xwid,
            int ywid,
            int zwid,
            int level) {
            // 1. Check if this is a LEAF node (Internal nodes have null values)
            if (this.value != null && !this.value.getName().equals(
                "FlyWeight")) {
                if (this.value.getName().equals(target.getName())) {
                    return this.value;
                }
                else {
                    return null;
                }
            }

            // 2. Internal Node Traversal
            if (level % 3 == 0) { // X Split
                int mid = x + (xwid / 2);
                if (target.getXOrg() < mid) {
                    return left.find(target, x, y, z, xwid / 2, ywid, zwid,
                        level + 1);
                }
                else {
                    return right.find(target, mid, y, z, xwid / 2, ywid, zwid,
                        level + 1);
                }
            }
            else if (level % 3 == 1) { // Y Split
                int mid = y + (ywid / 2);
                if (target.getYOrg() < mid) {
                    return left.find(target, x, y, z, xwid, ywid / 2, zwid,
                        level + 1);
                }
                else {
                    return right.find(target, x, mid, z, xwid, ywid / 2, zwid,
                        level + 1);
                }
            }
            else { // Z Split
                int mid = z + (zwid / 2);
                if (target.getZOrg() < mid) {
                    return left.find(target, x, y, z, xwid, ywid, zwid / 2,
                        level + 1);
                }
                else {
                    return right.find(target, x, y, mid, xwid, ywid, zwid / 2,
                        level + 1);
                }
            }
        }


        /**
         * Recursive Remove Method
         * Uses the EXACT SAME navigation logic as find.
         * Returns the Node that should take this Node's place (usually 'this',
         * or FLYWEIGHT if deleted).
         */
        public Node remove(
            AirObject target,
            int x,
            int y,
            int z,
            int xwid,
            int ywid,
            int zwid,
            int level) {
            // 0. FLYWEIGHT NODE: Can't remove from empty node
            if (this == FLYWEIGHT) {
                return FLYWEIGHT;
            }
            
            // 1. LEAF NODE: Check if this is the item to delete
            if (this.value != null && !this.value.getName().equals(
                "FlyWeight")) {
                if (this.value.getName().equals(target.getName())) {
                    // Found it! Return FLYWEIGHT to effectively "delete" this
                    // node from the parent.
                    return FLYWEIGHT;
                }
                else {
                    // Not the item, return 'this' unchanged
                    return this;
                }
            }

            // 2. INTERNAL NODE: Recurse down
            if (level % 3 == 0) { // X Split
                int mid = x + (xwid / 2);
                if (target.getXOrg() < mid) {
                    this.left = left.remove(target, x, y, z, xwid / 2, ywid,
                        zwid, level + 1);
                }
                else {
                    this.right = right.remove(target, mid, y, z, xwid / 2, ywid,
                        zwid, level + 1);
                }
            }
            else if (level % 3 == 1) { // Y Split
                int mid = y + (ywid / 2);
                if (target.getYOrg() < mid) {
                    this.left = left.remove(target, x, y, z, xwid, ywid / 2,
                        zwid, level + 1);
                }
                else {
                    this.right = right.remove(target, x, mid, z, xwid, ywid / 2,
                        zwid, level + 1);
                }
            }
            else { // Z Split
                int mid = z + (zwid / 2);
                if (target.getZOrg() < mid) {
                    this.left = left.remove(target, x, y, z, xwid, ywid, zwid
                        / 2, level + 1);
                }
                else {
                    this.right = right.remove(target, x, y, mid, xwid, ywid,
                        zwid / 2, level + 1);
                }
            }

            // 3. MERGE STEP: Check if we can collapse this Internal Node
            // If both children are FLYWEIGHT, this Internal Node is useless.
            if (this.left == FLYWEIGHT && this.right == FLYWEIGHT) {
                return FLYWEIGHT;
            }

            return this;
        }
    }

    public BinTree(int worldSize) {
        this.root = FLYWEIGHT;
        this.worldSize = worldSize; // Default per project specs
    }


    public boolean insert(AirObject obj) {
        root = root.insert(obj, 0, 0, 0, worldSize, worldSize, worldSize, 0);
        return true;
    }


    public AirObject find(AirObject target) {
        return root.find(target, 0, 0, 0, worldSize, worldSize, worldSize, 0);
    }


    /**
     * Remove needs the actual AirObject (with coordinates) to be efficient.
     * If you only have the name, you should look it up in the SkipList first
     * to get the full AirObject, then pass it here.
     */
    public void remove(AirObject target) {
        if (target != null) {
            root = root.remove(target, 0, 0, 0, worldSize, worldSize, worldSize, 0);
        }
    }


    public boolean intersects(AirObject obj) {
        // Intersects implementation
        return false;
    }
}
