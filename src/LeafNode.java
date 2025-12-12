/**
 * Represents a leaf node in the BinTree, which stores AirObjects.
 * 
 * @author Edwin Barrack
 * @author Will Perryman
 * @version 12/10/2025
 */
public final class LeafNode extends BinNode {

    private static final int CAPACITY = 3;
    private final LinkedList objects = new LinkedList();

    /**
     * Inserts an AirObject into this leaf node. If the leaf is full and can be
     * split, it is converted into an InternalNode.
     *
     * @param obj
     *            The AirObject to insert.
     * @param region
     *            The region this node represents.
     * @param level
     *            The depth of this node in the tree.
     * @return The new root of this subtree, which may be this leaf or a new
     *         InternalNode.
     */
    @Override
    public BinNode insert(AirObject obj, Region region, int level) {

        objects.add(obj);

        // Don't split if 3 or fewer objects, or if region can't be split
        // further
        if (objects.getSize() <= CAPACITY) {
            return this;
        }

        // Don't split if all objects share a common intersection point
        if (allIntersect()) {
            return this;
        }

        InternalNode internal = new InternalNode();
        for (AirObject existing : objects) {
            internal.insert(existing, region, level);
        }
        return internal;
    }


    /**
     * Removes an AirObject from this leaf. If the leaf becomes empty, it is
     * replaced by the FlyweightNode.
     *
     * @param target
     *            The AirObject to remove.
     * @param region
     *            The region this node represents (unused in leaf remove).
     * @param level
     *            The depth of this node in the tree (unused in leaf remove).
     * @return This node if it still contains objects, otherwise the
     *         FlyweightNode.
     */
    @Override
    public BinNode remove(AirObject target, Region region, int level) {
        objects.remove(target);
        return objects.isEmpty() ? FlyweightNode.getInstance() : this;
    }


    /**
     * Gets the number of objects in this leaf.
     * 
     * @return The number of objects.
     */
    public int getSize() {
        return objects.getSize();
    }


    /**
     * Gets an iterable over the objects in this leaf.
     * 
     * @return The objects.
     */
    public Iterable<AirObject> getObjects() {
        return objects;
    }


    /**
     * Adds an object directly to this leaf (for merging).
     * 
     * @param obj
     *            The object to add.
     */
    public void addObject(AirObject obj) {
        objects.add(obj);
    }


    /**
     * Appends a string representation of this leaf node and its contents to a
     * StringBuilder.
     *
     * @param buffer
     *            The StringBuilder to append to.
     * @param region
     *            The region this node represents.
     * @param level
     *            The depth of this node for indentation.
     * @return The number of nodes in this subtree (always 1 for a leaf).
     */
    @Override
    public int print(StringBuilder buffer, Region region, int level) {
        indent(buffer, level);
        buffer.append("Leaf with ").append(objects.getSize()).append(
            " objects ").append(region.toString()).append(" ").append(level)
            .append("\n");
        for (AirObject obj : objects) {
            indent(buffer, level);
            buffer.append("(").append(obj.toString()).append(")\n");
        }
        return 1;
    }


    private void indent(StringBuilder buffer, int level) {
        for (int i = 0; i < level; i++) {
            buffer.append("  ");
        }
    }


    /**
     * Checks if all objects in this leaf share a common intersection point.
     * Two 3D boxes intersect if they overlap in all three dimensions.
     * All objects share a common point if every pair of objects intersects.
     * 
     * @return true if all objects pairwise intersect, false otherwise.
     */
    private boolean allIntersect() {
        // Convert to array for indexed access
        AirObject[] arr = new AirObject[objects.getSize()];
        int i = 0;
        for (AirObject obj : objects) {
            arr[i++] = obj;
        }

        // Check all pairs
        for (i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (!BinNode.intersects(arr[i], arr[j])) {
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * Checks for collisions between all pairs of AirObjects within this leaf.
     *
     * @param sb
     *            The StringBuilder to append collision reports to.
     * @param region
     *            The region this node represents.
     * @param level
     *            The depth of this node in the tree.
     * @return The number of collisions found in this leaf.
     */
    @Override
    public int collisions(StringBuilder sb, Region region, int level) {
        sb.append("In leaf node ").append(region.toString()).append(" ").append(
            level).append("\n");
        int collisionCount = 0;
        AirObject[] objectArray = objects.toArray();
        for (int i = 0; i < objectArray.length; i++) {
            for (int j = i + 1; j < objectArray.length; j++) {
                if (intersects(objectArray[i], objectArray[j])) {
                    // Calculate the origin of the intersection box
                    int intersectX = Math.max(objectArray[i].getXOrg(),
                        objectArray[j].getXOrg());
                    int intersectY = Math.max(objectArray[i].getYOrg(),
                        objectArray[j].getYOrg());
                    int intersectZ = Math.max(objectArray[i].getZOrg(),
                        objectArray[j].getZOrg());

                    // Only report if the intersection origin is in this region
                    if (intersectX >= region.x && intersectX < region.x
                        + region.xWidth && intersectY >= region.y
                        && intersectY < region.y + region.yWidth
                        && intersectZ >= region.z && intersectZ < region.z
                            + region.zWidth) {
                        sb.append("(").append(objectArray[i].toString()).append(
                            ") and (").append(objectArray[j].toString()).append(
                                ")\n");
                        collisionCount++;
                    }
                }
            }
        }
        return collisionCount;
    }


    /**
     * Finds and reports all AirObjects in this leaf that intersect with a given
     * query box.
     *
     * @param sb
     *            The StringBuilder to append intersecting objects to.
     * @param region
     *            The region this node represents.
     * @param level
     *            The depth of this node in the tree.
     * @param qx
     *            The x-coordinate of the query box origin.
     * @param qy
     *            The y-coordinate of the query box origin.
     * @param qz
     *            The z-coordinate of the query box origin.
     * @param qxw
     *            The width of the query box.
     * @param qyw
     *            The height of the query box.
     * @param qzw
     *            The depth of the query box.
     * @return The number of nodes visited (always 1 for a leaf).
     */
    @Override
    public int intersect(
        StringBuilder sb,
        Region region,
        int level,
        int qx,
        int qy,
        int qz,
        int qxw,
        int qyw,
        int qzw) {
        sb.append("In leaf node ").append(region.toString()).append(" ").append(
            level).append("\n");
        for (AirObject obj : objects) {
            if (objectIntersectsQuery(obj, qx, qy, qz, qxw, qyw, qzw)) {
                // Calculate the origin of the intersection box between object
                // and query
                int intersectX = Math.max(obj.getXOrg(), qx);
                int intersectY = Math.max(obj.getYOrg(), qy);
                int intersectZ = Math.max(obj.getZOrg(), qz);

                // Only report if the intersection origin is in this region
                if (intersectX >= region.x && intersectX < region.x
                    + region.xWidth && intersectY >= region.y
                    && intersectY < region.y + region.yWidth
                    && intersectZ >= region.z && intersectZ < region.z
                        + region.zWidth) {
                    sb.append(obj.toString()).append("\n");
                }
            }
        }
        return 1;
    }
}
