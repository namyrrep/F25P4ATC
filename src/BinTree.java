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


        // This method adds the node based on its position
        // Recursive method, starts with the root, which cannot be a fly weight,
        // must have two children.
        public void insert(
            int x,
            int y,
            int z,
            int xwid,
            int ywid,
            int zwid,
            int level) {
            // Must continuously add flyWeights using recursion to fill out the
            // bin tree
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
        if (root == FLYWEIGHT) {
            root = new Node(obj);
            root.left = FLYWEIGHT;
            root.right = FLYWEIGHT;
            return true;
        }
        root.insert(obj.getXOrg(), obj.getYOrg(), obj.getZOrg(), worldSize,
            worldSize, worldSize, 0);
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
        root = root.remove(target, 0, 0, 0, worldSize, worldSize, worldSize, 0);
    }


    public boolean intersects(AirObject obj) {
        // Intersects implementation
        return false;
    }
}
