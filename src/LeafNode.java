/**
 * Represents a leaf node in the BinTree, which stores AirObjects.
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
        for (AirObject existing : objects) {
            if (existing.getName().equals(obj.getName())) {
                return this;
            }
        }

        objects.add(obj);

        if (objects.getSize() <= CAPACITY || region.isUnit()) {
            return this;
        }

        if (cannotSplit(region, level)) {
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
     * Checks if splitting would help separate objects.
     * Returns true if splitting would NOT help:
     * - All objects span the midpoint (go to both children), or
     * - All objects go exclusively to the same side 
     * (all left-only or all right-only)
     */
    private boolean cannotSplit(Region region, int level) {
        int axis = level % 3;
        int midpoint = region.midpoint(axis);
        
        boolean hasLeftOnly = false;
        boolean hasRightOnly = false;
        boolean hasBoth = false;
        
        for (AirObject obj : objects) {
            int objStart = BinTree.getAxisStart(obj, axis);
            int objEnd = BinTree.getAxisEnd(obj, axis);
            
            boolean goesLeft = objStart < midpoint;
            boolean goesRight = objEnd > midpoint;
            
            if (goesLeft && goesRight) {
                hasBoth = true;
            } 
            else if (goesLeft) {
                hasLeftOnly = true;
            } 
            else if (goesRight) {
                hasRightOnly = true;
            }
        }
        
        // Splitting helps if we have objects going to 
        // different exclusive sides
        // (one goes left-only, another goes right-only)
        if (hasLeftOnly && hasRightOnly) {
            return false; // Can split - objects will be separated
        }
        
        // Splitting also helps if we have some objects 
        // spanning and some exclusive
        // The exclusive ones won't be duplicated
        if (hasBoth && (hasLeftOnly || hasRightOnly)) {
            return false; 
            // Can split - exclusive objects won't be in both children
        }
        
        // All objects go to the same place(s), splitting won't help
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
            // Only report object if its origin is within this region
            if (originInRegion(obj, region) && objectIntersectsQuery(obj, qx,
                qy, qz, qxw, qyw, qzw)) {
                sb.append(obj.toString()).append("\n");
            }
        }
        return 1;
    }


    /**
     * Checks if an object's origin is within the given region.
     */
    private boolean originInRegion(AirObject obj, Region region) {
        return obj.getXOrg() >= region.x && obj.getXOrg() < region.x
            + region.xWidth && obj.getYOrg() >= region.y && obj
                .getYOrg() < region.y + region.yWidth && obj
                    .getZOrg() >= region.z && obj.getZOrg() < region.z
                        + region.zWidth;
    }
}