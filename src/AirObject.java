/**
 * This is the parent class for all our airObjects
 * @author Edwin Barrack
 * @author Will Perryman
 * @version 12/10/2025
 */
public class AirObject {
    // Fields based on project description
    private String name;
    private int xOrigin;
    private int yOrigin;
    private int zOrigin;
    private int xWidth;
    private int yWidth;
    private int zWidth;

    /**
     * Constructor for AirObject.
     *
     * @param name The name of the air object.
     * @param xOrg The x-coordinate of the origin.
     * @param yOrg The y-coordinate of the origin.
     * @param zOrg The z-coordinate of the origin.
     * @param xWid The width in the x-dimension.
     * @param yWid The width in the y-dimension.
     * @param zWid The width in the z-dimension.
     */
    public AirObject(
        String name,
        int xOrg,
        int yOrg,
        int zOrg,
        int xWid,
        int yWid,
        int zWid) {

        this.name = name;
        this.xOrigin = xOrg;
        this.yOrigin = yOrg;
        this.zOrigin = zOrg;
        this.xWidth = xWid;
        this.yWidth = yWid;
        this.zWidth = zWid;
    }

    /**
     * Get the name of the air object.
     *
     * @return The name of the air object.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the X origin of the air object.
     *
     * @return The x-coordinate of the origin.
     */
    public int getXOrg() {
        return xOrigin;
    }

    /**
     * Get the Y origin of the air object.
     *
     * @return The y-coordinate of the origin.
     */
    public int getYOrg() {
        return yOrigin;
    }

    /**
     * Get the Z origin of the air object.
     *
     * @return The z-coordinate of the origin.
     */
    public int getZOrg() {
        return zOrigin;
    }

    /**
     * Get the X width of the air object.
     *
     * @return The width in the x-dimension.
     */
    public int getXWidth() {
        return xWidth;
    }

    /**
     * Get the Y width of the air object.
     *
     * @return The width in the y-dimension.
     */
    public int getYWidth() {
        return yWidth;
    }

    /**
     * Get the Z width of the air object.
     *
     * @return The width in the z-dimension.
     */
    public int getZWidth() {
        return zWidth;
    }

    /**
     * Set the X origin of the air object.
     *
     * @param xOrg The new x-coordinate of the origin.
     */
    public void setXOrg(int xOrg) {
        this.xOrigin = xOrg;
    }

    /**
     * Set the Y origin of the air object.
     *
     * @param yOrg The new y-coordinate of the origin.
     */
    public void setYOrg(int yOrg) {
        this.yOrigin = yOrg;
    }

    /**
     * Set the Z origin of the air object.
     *
     * @param zOrg The new z-coordinate of the origin.
     */
    public void setZOrg(int zOrg) {
        this.zOrigin = zOrg;
    }
    
    /**
     * Returns a string representation of the AirObject.
     *
     * @return A string containing the object's attributes.
     */
    public String toString() {
        return name + " " + xOrigin + " " + yOrigin + " " + zOrigin + " "
            + xWidth + " " + yWidth + " " + zWidth + " ";
    }

}