/**
 * The main BinTree class, which manages the root of the tree.
 */
public class BinTree {

    private final Region world;
    private BinNode root;

    /**
     * Constructs a new BinTree.
     * @param worldSize The size of the world.
     */
    public BinTree(int worldSize) {
        this.world = new Region(0, 0, 0, worldSize, worldSize, worldSize);
        this.root = FlyweightNode.getInstance();
    }

    /**
     * Inserts an AirObject into the tree.
     * @param obj The AirObject to insert.
     * @return True if insertion was successful.
     */
    public boolean insert(AirObject obj) {
        if (obj == null) {
            return false;
        }
        root = root.insert(obj, world, 0);
        return true;
    }

    /**
     * Finds an AirObject in the tree.
     * @param target The AirObject to find.
     * @return The found AirObject, or null.
     */
    public AirObject find(AirObject target) {
        if (target == null) {
            return null;
        }
        return root.find(target, world, 0);
    }

    /**
     * Removes an AirObject from the tree.
     * @param target The AirObject to remove.
     */
    public void remove(AirObject target) {
        if (target != null) {
            root = root.remove(target, world, 0);
        }
    }

    /**
     * Prints the tree structure.
     * @return A string representation of the tree.
     */
    public String print() {
        StringBuilder sb = new StringBuilder();
        int count = root.print(sb, world, 0);
        // If the root was a flyweight, the count will be 0. 
        // In that case, we should print the empty tree representation.
        if (count == 0) {
            sb.append("E (0, 0, 0, 1024, 1024, 1024) 0\n");
            count = 1;
        }
        sb.append(count).append(" Bintree nodes printed\n");
        return sb.toString();
    }


    /**
     * Finds all collisions in the tree.
     * 
     * @return A string reporting all collisions.
     */
    public String collisions() {
        StringBuilder sb = new StringBuilder();
        sb.append("The following collisions exist in the database:\n");
        root.collisions(sb, world, 0);
        return sb.toString();
    }

    /**
     * Finds all objects that intersect the given query box.
     * 
     * @param x
     *            Query box x origin.
     * @param y
     *            Query box y origin.
     * @param z
     *            Query box z origin.
     * @param xwid
     *            Query box x width.
     * @param ywid
     *            Query box y width.
     * @param zwid
     *            Query box z width.
     * @return A string reporting the objects that intersect and nodes visited.
     */
    public String intersect(int x, int y, int z, int xwid, int ywid, int zwid) {
        StringBuilder sb = new StringBuilder();
        sb.append("The following objects intersect (").append(x).append(" ")
            .append(y).append(" ").append(z).append(" ").append(xwid)
            .append(" ").append(ywid).append(" ").append(zwid).append("):\n");
        int nodesVisited = root.intersect(sb, world, 0, x, y, z, xwid, ywid, zwid);
        sb.append(nodesVisited).append(" nodes were visited in the bintree\n");
        return sb.toString();
    }


    /**
     * Checks if two AirObjects have the same origin.
     * @param first The first object.
     * @param second The second object.
     * @return True if they have the same origin.
     */
    public static boolean sameOrigin(AirObject first, AirObject second) {
        return first.getXOrg() == second.getXOrg()
            && first.getYOrg() == second.getYOrg()
            && first.getZOrg() == second.getZOrg();
    }

    /**
     * Compares an AirObject's coordinate along a given axis.
     * @param obj The object.
     * @param axis The axis.
     * @return The coordinate value.
     */
    public static int compareAxis(AirObject obj, int axis) {
        switch (axis) {
            case 0:
                return obj.getXOrg();
            case 1:
                return obj.getYOrg();
            default:
                return obj.getZOrg();
        }
    }

    /**
     * Gets the start coordinate of an AirObject's bounding box along a given axis.
     * @param obj The object.
     * @param axis The axis.
     * @return The start coordinate (origin).
     */
    public static int getAxisStart(AirObject obj, int axis) {
        switch (axis) {
            case 0:
                return obj.getXOrg();
            case 1:
                return obj.getYOrg();
            default:
                return obj.getZOrg();
        }
    }

    /**
     * Gets the end coordinate of an AirObject's bounding box along a given axis.
     * @param obj The object.
     * @param axis The axis.
     * @return The end coordinate (origin + width).
     */
    public static int getAxisEnd(AirObject obj, int axis) {
        switch (axis) {
            case 0:
                return obj.getXOrg() + obj.getXWidth();
            case 1:
                return obj.getYOrg() + obj.getYWidth();
            default:
                return obj.getZOrg() + obj.getZWidth();
        }
    }
}