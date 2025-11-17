/**
 * @author William Perryman & Edwin Barrack
 * @version 2025-11
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

    /*
     *  Get the name
     *  @return name
     */
    public String getName() {
        return name;
    }

    /*
     * Get the X origin
     * @return xOrg
     */
    public int getXOrg() {
        return xOrigin;
    }

    /*
     * Get the Y origin
     * @return yOrg
     */
    public int getYOrg() {
        return yOrigin;
    }

    /*
     * Get the Z origin
     * @return zOrg
     */
    public int getZOrg() {
        return zOrigin;
    }

    /*
     * Get the X width
     */
    public int getXWidth() {
        return xWidth;
    }

    /*
     * Get the Y width
     * @return yWidth
     */
    public int getYWidth() {
        return yWidth;
    }

    /*
     * Get the Z width
     * @return zWidth
     */
    public int getZWidth() {
        return zWidth;
    }

    /*
     * Set the X origin
     * @param xOrg
     */
    public void setXOrg(int xOrg) {
        this.xOrigin = xOrg;
    }

    /*
     * Set the Y origin
     * @param yOrg
     */
    public void setYOrg(int yOrg) {
        this.yOrigin = yOrg;
    }

    /*
     * Set the Z origin
     * @param zOrg
     */
    public void setZOrg(int zOrg) {
        this.zOrigin = zOrg;
    }
    
    public String toString() {
        return name + " " + xOrigin + " " + yOrigin + " " + zOrigin + " "
            + xWidth + " " + yWidth + " " + zWidth + " ";
    }

}
