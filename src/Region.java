/**
 * Represents a region in the 3D space.
 * 
 * @author Edwin Barrack
 * @author Will Perryman
 * @version 12/10/2025
 */
public class Region {
    /**
     * The regions x value
     */
    public final int x;
    /**
     * The regions y value
     */
    public final int y;
    /**
     * The regions z value
     */
    public final int z;
    /**
     * The regions xWid value
     */
    public final int xWidth;
    /**
     * The regions yWid value
     */
    public final int yWidth;
    /**
     * The regions zWid value
     */
    public final int zWidth;

    public Region(int x, int y, int z, int xWidth, int yWidth, int zWidth) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.xWidth = xWidth;
        this.yWidth = yWidth;
        this.zWidth = zWidth;
    }


    /**
     * Creates the region for the left child.
     * 
     * @param axis
     *            The axis of division.
     * @return The region for the left child.
     */
    public Region leftChild(int axis) {
        switch (axis) {
            case 0: // X
                return new Region(x, y, z, xWidth / 2, yWidth, zWidth);
            case 1: // Y
                return new Region(x, y, z, xWidth, yWidth / 2, zWidth);
            default: // Z
                return new Region(x, y, z, xWidth, yWidth, zWidth / 2);
        }
    }


    /**
     * Creates the region for the right child.
     * 
     * @param axis
     *            The axis of division.
     * @return The region for the right child.
     */
    public Region rightChild(int axis) {
        switch (axis) {
            case 0: // X
                int halfX = xWidth / 2;
                return new Region(x + halfX, y, z, xWidth - halfX, yWidth,
                    zWidth);
            case 1: // Y
                int halfY = yWidth / 2;
                return new Region(x, y + halfY, z, xWidth, yWidth - halfY,
                    zWidth);
            default: // Z
                int halfZ = zWidth / 2;
                return new Region(x, y, z + halfZ, xWidth, yWidth, zWidth
                    - halfZ);
        }
    }


    /**
     * Gets the midpoint of the region along a given axis.
     * 
     * @param axis
     *            The axis.
     * @return The midpoint.
     */
    public int midpoint(int axis) {
        switch (axis) {
            case 0: // X
                return x + (xWidth / 2);
            case 1: // Y
                return y + (yWidth / 2);
            default: // Z
                return z + (zWidth / 2);
        }
    }


    /**
     * Checks if the region contains the given point.
     * 
     * @param px
     *            The x coordinate of the point.
     * @param py
     *            The y coordinate of the point.
     * @param pz
     *            The z coordinate of the point.
     * @return True if the region contains the point.
     */
    public boolean contains(int px, int py, int pz) {
        return px >= x && px < x + xWidth && py >= y && py < y + yWidth
            && pz >= z && pz < z + zWidth;
    }


    @Override
    public String toString() {
        return String.format("(%d, %d, %d, %d, %d, %d)", x, y, z, xWidth,
            yWidth, zWidth);
    }
}
