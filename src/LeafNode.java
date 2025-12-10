

/**
 * Represents a leaf node in the BinTree, which stores AirObjects.
 */
public final class LeafNode extends BinNode {

    private static final int CAPACITY = 3;
    private final LinkedList objects = new LinkedList();

    @Override
    public BinNode insert(AirObject obj, Region region, int level) {
        // Check for duplicate origin before adding
        for (AirObject existing : objects) {
            if (BinTree.sameOrigin(existing, obj)) {
                return this; // Duplicate origin, do not insert
            }
        }

        if (objects.getSize() < CAPACITY || region.isUnit()) {
            objects.add(obj);
            return this;
        }

        // Convert to internal node
        InternalNode internal = new InternalNode();
        // Re-insert existing objects into the new internal node
        for (AirObject existing : objects) {
            internal.insert(existing, region, level);
        }
        // Insert the new object
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
            indent(buffer, level + 1);
            buffer.append("(").append(obj.toString()).append(")\n");
        }
        return 1;
    }
    
    private void indent(StringBuilder buffer, int level) {
        for (int i = 0; i < level; i++) {
            buffer.append("  ");
        }
    }
}