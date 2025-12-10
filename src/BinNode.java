import java.util.List;

/**
 * Abstract base class for nodes in the BinTree, following the composite pattern.
 */
public abstract class BinNode {

    /**
     * Inserts an AirObject into the tree.
     * @param obj The AirObject to insert.
     * @param region The region of this node.
     * @param level The depth of this node.
     * @return The new node for this position.
     */
    public abstract BinNode insert(AirObject obj, Region region, int level);

    /**
     * Finds an AirObject in the tree.
     * @param target The AirObject to find.
     * @param region The region of this node.
     * @param level The depth of this node.
     * @return The found AirObject, or null.
     */
    public abstract AirObject find(AirObject target, Region region, int level);

    /**
     * Removes an AirObject from the tree.
     * @param target The AirObject to remove.
     * @param region The region of this node.
     * @param level The depth of this node.
     * @return The new node for this position.
     */
    public abstract BinNode remove(AirObject target, Region region, int level);
    
    /**
     * Prints the tree structure.
     * @param buffer The string buffer to append to.
     * @param region The region of this node.
     * @param level The depth of this node.
     * @return The number of nodes printed.
     */
    public abstract int print(StringBuilder buffer, Region region, int level);

    /**
     * Finds all collisions in the subtree of this node.
     * 
     * @param sb
     *            The StringBuilder to append collision reports to.
     * @param region
     *            The region of this node.
     * @param level
     *            The depth of this node.
     * @return The number of collisions found.
     */
    public abstract int collisions(StringBuilder sb, Region region, int level);

    /**
     * Finds all objects that intersect the given query box.
     * 
     * @param sb
     *            The StringBuilder to append results to.
     * @param region
     *            The region of this node.
     * @param level
     *            The depth of this node.
     * @param qx
     *            Query box x origin.
     * @param qy
     *            Query box y origin.
     * @param qz
     *            Query box z origin.
     * @param qxw
     *            Query box x width.
     * @param qyw
     *            Query box y width.
     * @param qzw
     *            Query box z width.
     * @return The number of nodes visited.
     */
    public abstract int intersect(StringBuilder sb, Region region, int level,
        int qx, int qy, int qz, int qxw, int qyw, int qzw);


    /**
     * Checks if two AirObjects intersect.
     * 
     * @param obj1
     *            The first AirObject.
     * @param obj2
     *            The second AirObject.
     * @return True if they intersect, false otherwise.
     */
    public static boolean intersects(AirObject obj1, AirObject obj2) {
        return obj1.getXOrg() < obj2.getXOrg() + obj2.getXWidth() && obj1
            .getXOrg() + obj1.getXWidth() > obj2.getXOrg() && obj1
                .getYOrg() < obj2.getYOrg() + obj2.getYWidth() && obj1
                    .getYOrg() + obj1.getYWidth() > obj2.getYOrg() && obj1
                        .getZOrg() < obj2.getZOrg() + obj2.getZWidth() && obj1
                            .getZOrg() + obj1.getZWidth() > obj2.getZOrg();
    }

    /**
     * Checks if a region intersects with a query box.
     * 
     * @param region
     *            The region to check.
     * @param qx
     *            Query box x origin.
     * @param qy
     *            Query box y origin.
     * @param qz
     *            Query box z origin.
     * @param qxw
     *            Query box x width.
     * @param qyw
     *            Query box y width.
     * @param qzw
     *            Query box z width.
     * @return True if the region intersects the query box.
     */
    public static boolean regionIntersectsQuery(Region region,
        int qx, int qy, int qz, int qxw, int qyw, int qzw) {
        return region.x < qx + qxw && region.x + region.xWidth > qx
            && region.y < qy + qyw && region.y + region.yWidth > qy
            && region.z < qz + qzw && region.z + region.zWidth > qz;
    }

    /**
     * Checks if an AirObject intersects with a query box.
     * 
     * @param obj
     *            The AirObject to check.
     * @param qx
     *            Query box x origin.
     * @param qy
     *            Query box y origin.
     * @param qz
     *            Query box z origin.
     * @param qxw
     *            Query box x width.
     * @param qyw
     *            Query box y width.
     * @param qzw
     *            Query box z width.
     * @return True if the object intersects the query box.
     */
    public static boolean objectIntersectsQuery(AirObject obj,
        int qx, int qy, int qz, int qxw, int qyw, int qzw) {
        return obj.getXOrg() < qx + qxw && obj.getXOrg() + obj.getXWidth() > qx
            && obj.getYOrg() < qy + qyw && obj.getYOrg() + obj.getYWidth() > qy
            && obj.getZOrg() < qz + qzw && obj.getZOrg() + obj.getZWidth() > qz;
    }
}