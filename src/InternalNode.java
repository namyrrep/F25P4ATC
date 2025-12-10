/**
 * Represents an internal node in the BinTree, which has two children.
 */
public final class InternalNode extends BinNode {

    private BinNode left = FlyweightNode.getInstance();
    private BinNode right = FlyweightNode.getInstance();

    @Override
    public BinNode insert(AirObject obj, Region region, int level) {
        int axis = level % 3;
        int midpoint = region.midpoint(axis);

        // Get the object's extent along this axis
        int objStart = BinTree.getAxisStart(obj, axis);
        int objEnd = BinTree.getAxisEnd(obj, axis);

        // Insert into left child if object's bounding box intersects left region
        if (objStart < midpoint) {
            left = left.insert(obj, region.leftChild(axis), level + 1);
        }
        // Insert into right child if object's bounding box intersects right region
        if (objEnd > midpoint) {
            right = right.insert(obj, region.rightChild(axis), level + 1);
        }
        return this;
    }

    @Override
    public AirObject find(AirObject target, Region region, int level) {
        int axis = level % 3;
        int midpoint = region.midpoint(axis);

        // Search based on origin point
        if (BinTree.compareAxis(target, axis) < midpoint) {
            return left.find(target, region.leftChild(axis), level + 1);
        } else {
            return right.find(target, region.rightChild(axis), level + 1);
        }
    }

    @Override
    public BinNode remove(AirObject target, Region region, int level) {
        int axis = level % 3;
        int midpoint = region.midpoint(axis);

        // Get the object's extent along this axis
        int objStart = BinTree.getAxisStart(target, axis);
        int objEnd = BinTree.getAxisEnd(target, axis);

        // Remove from left child if object's bounding box intersects left region
        if (objStart < midpoint) {
            left = left.remove(target, region.leftChild(axis), level + 1);
        }
        // Remove from right child if object's bounding box intersects right region
        if (objEnd > midpoint) {
            right = right.remove(target, region.rightChild(axis), level + 1);
        }

        // After removal, check if this internal node can be simplified.
        if (left instanceof FlyweightNode && right instanceof LeafNode) {
            return right;
        }
        if (left instanceof LeafNode && right instanceof FlyweightNode) {
            return left;
        }
        if (left instanceof FlyweightNode && right instanceof FlyweightNode) {
            return FlyweightNode.getInstance();
        }

        return this;
    }

    @Override
    public int print(StringBuilder buffer, Region region, int level) {
        indent(buffer, level);
        buffer.append("I ").append(region.toString()).append(" ").append(level).append("\n");
        int nodes = 1;
        int axis = level % 3;
        nodes += left.print(buffer, region.leftChild(axis), level + 1);
        nodes += right.print(buffer, region.rightChild(axis), level + 1);
        return nodes;
    }
    
    private void indent(StringBuilder buffer, int level) {
        for (int i = 0; i < level; i++) {
            buffer.append("  ");
        }
    }


    @Override
    public int collisions(StringBuilder sb, Region region, int level) {
        int collisionCount = 0;
        int axis = level % 3;
        collisionCount += left.collisions(sb, region.leftChild(axis), level + 1);
        collisionCount += right.collisions(sb, region.rightChild(axis), level + 1);
        return collisionCount;
    }

    @Override
    public int intersect(StringBuilder sb, Region region, int level,
        int qx, int qy, int qz, int qxw, int qyw, int qzw) {
        sb.append("In Internal node ").append(region.toString()).append(" ")
            .append(level).append("\n");
        int nodesVisited = 1;
        int axis = level % 3;
        Region leftRegion = region.leftChild(axis);
        Region rightRegion = region.rightChild(axis);
        if (regionIntersectsQuery(leftRegion, qx, qy, qz, qxw, qyw, qzw)) {
            nodesVisited += left.intersect(sb, leftRegion, level + 1,
                qx, qy, qz, qxw, qyw, qzw);
        }
        if (regionIntersectsQuery(rightRegion, qx, qy, qz, qxw, qyw, qzw)) {
            nodesVisited += right.intersect(sb, rightRegion, level + 1,
                qx, qy, qz, qxw, qyw, qzw);
        }
        return nodesVisited;
    }
}