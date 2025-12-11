/**
 * Represents the flyweight node, a singleton empty node.
 * @author Edwin Barrack
 * @author Will Perryman
 * @version 12/10/2025
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
    public BinNode remove(AirObject target, Region region, int level) {
        return this;
    }


    @Override
    public int print(StringBuilder buffer, Region region, int level) {
        indent(buffer, level);
        buffer.append("E ").append(region.toString()).append(" ").append(level)
            .append("\n");
        return 1;
    }


    private void indent(StringBuilder buffer, int level) {
        for (int i = 0; i < level; i++) {
            buffer.append("  ");
        }
    }


    @Override
    public int collisions(StringBuilder sb, Region region, int level) {
        return 0;
    }


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
        // Flyweight node counts as 1 visited node but produces no output
        return 1;
    }
}
