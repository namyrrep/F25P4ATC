/**
 * Represents a leaf node in the BinTree, which stores AirObjects.
 */
public final class LeafNode extends BinNode {

    private static final int CAPACITY = 3;
    private final LinkedList objects = new LinkedList();

    @Override
    public BinNode insert(AirObject obj, Region region, int level) {
        // Check for duplicate (same name) before adding - same object can be in multiple leaves
        for (AirObject existing : objects) {
            if (existing.getName().equals(obj.getName())) {
                return this; // Already in this leaf, do not insert again
            }
        }

        // If we have room or can't split further, just add to this leaf
        if (objects.getSize() < CAPACITY || region.isUnit()) {
            objects.add(obj);
            return this;
        }

        // Check if splitting would actually separate any objects
        int axis = level % 3;
        int midpoint = region.midpoint(axis);
        
        boolean anyLeft = false;
        boolean anyRight = false;
        
        // Check existing objects
        for (AirObject existing : objects) {
            int start = BinTree.getAxisStart(existing, axis);
            int end = BinTree.getAxisEnd(existing, axis);
            if (start < midpoint) anyLeft = true;
            if (end > midpoint) anyRight = true;
        }
        
        // Check new object
        int newStart = BinTree.getAxisStart(obj, axis);
        int newEnd = BinTree.getAxisEnd(obj, axis);
        if (newStart < midpoint) anyLeft = true;
        if (newEnd > midpoint) anyRight = true;
        
        // If all objects go to the same side, don't split - just add
        if (!anyLeft || !anyRight) {
            objects.add(obj);
            return this;
        }

        // Convert to internal node - objects will be separated
        InternalNode internal = new InternalNode();
        // Re-insert existing objects into the new internal node at the current level
        for (AirObject existing : objects) {
            internal.insert(existing, region, level);
        }
        // Insert the new object that caused the split
        internal.insert(obj, region, level);
        return internal;
    }

    @Override
    public AirObject find(AirObject target, Region region, int level) {
        for (AirObject obj : objects) {
            if (obj.getName().equals(target.getName())) {
                return obj;
            }
        }
        return null;
    }

    @Override
    public BinNode remove(AirObject target, Region region, int level) {
        objects.remove(target);
        return objects.isEmpty() ? FlyweightNode.getInstance() : this;
    }

    @Override
    public int print(StringBuilder buffer, Region region, int level) {
        indent(buffer, level);
        buffer.append("Leaf with ").append(objects.getSize()).append(" objects ").append(region.toString()).append(" ").append(level).append("\n");
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


    @Override
    public int collisions(StringBuilder sb, Region region, int level) {
        sb.append("In leaf node ").append(region.toString()).append(" ")
            .append(level).append("\n");
        int collisionCount = 0;
        AirObject[] objectArray = objects.toArray();
        for (int i = 0; i < objectArray.length; i++) {
            for (int j = i + 1; j < objectArray.length; j++) {
                if (intersects(objectArray[i], objectArray[j])) {
                    sb.append("(").append(objectArray[i].toString())
                        .append(") and (").append(objectArray[j].toString())
                        .append(")\n");
                    collisionCount++;
                }
            }
        }
        return collisionCount;
    }

    @Override
    public int intersect(StringBuilder sb, Region region, int level,
        int qx, int qy, int qz, int qxw, int qyw, int qzw) {
        sb.append("In leaf node ").append(region.toString()).append(" ")
            .append(level).append("\n");
        for (AirObject obj : objects) {
            // Only report object if its origin is within this region
            if (originInRegion(obj, region) 
                && objectIntersectsQuery(obj, qx, qy, qz, qxw, qyw, qzw)) {
                sb.append(obj.toString()).append("\n");
            }
        }
        return 1;
    }

    /**
     * Checks if an object's origin is within the given region.
     */
    private boolean originInRegion(AirObject obj, Region region) {
        return obj.getXOrg() >= region.x 
            && obj.getXOrg() < region.x + region.xWidth
            && obj.getYOrg() >= region.y 
            && obj.getYOrg() < region.y + region.yWidth
            && obj.getZOrg() >= region.z 
            && obj.getZOrg() < region.z + region.zWidth;
    }
}