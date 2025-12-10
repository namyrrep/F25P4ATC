/**
 * Represents the flyweight node, a singleton empty node.
 */
public final class FlyweightNode extends BinNode {

    private static final FlyweightNode INSTANCE = new FlyweightNode();

    private FlyweightNode() {
        // Private constructor for singleton
    }

    public static FlyweightNode getInstance() {
        return INSTANCE;
    }

    @Override
    public BinNode insert(AirObject obj, Region region, int level) {
        LeafNode leaf = new LeafNode();
        return leaf.insert(obj, region, level);
    }

    @Override
    public AirObject find(AirObject target, Region region, int level) {
        return null;
    }

    @Override
    public BinNode remove(AirObject target, Region region, int level) {
        return this;
    }

    @Override
    public int print(StringBuilder buffer, Region region, int level) {
        // Do not print anything for a flyweight node
        return 0;
    }
    
    private void indent(StringBuilder buffer, int level) {
        for (int i = 0; i < level; i++) {
            buffer.append("  ");
        }
    }
}