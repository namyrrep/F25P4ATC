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

        if (BinTree.compareAxis(obj, axis) < midpoint) {
            left = left.insert(obj, region.leftChild(axis), level + 1);
        } else {
            right = right.insert(obj, region.rightChild(axis), level + 1);
        }
        return this;
    }

    @Override
    public AirObject find(AirObject target, Region region, int level) {
        int axis = level % 3;
        int midpoint = region.midpoint(axis);

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

        if (BinTree.compareAxis(target, axis) < midpoint) {
            left = left.remove(target, region.leftChild(axis), level + 1);
        } else {
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
}