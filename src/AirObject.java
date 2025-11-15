/**
 * @author William Perryman & Edwin Barrack
 * @version 2025-11
 */
public class AirObject {
    // Fields based on project description
    private String name;
    private int xOrg;
    private int yOrg;
    private int zOrg;
    private int xWidth;
    private int yWidth;
    private int zWidth;

    public AirObject(
        String name,
        int xOrg,
        int yOrg,
        int zOrg,
        int xWidth,
        int yWidth,
        int zWidth) {

        this.name = name;
        this.xOrg = xOrg;
        this.yOrg = yOrg;
        this.zOrg = zOrg;
        this.xWidth = xWidth;
        this.yWidth = yWidth;
        this.zWidth = zWidth;
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
        return xOrg;
    }

    /*
     * Get the Y origin
     * @return yOrg
     */
    public int getYOrg() {
        return yOrg;
    }

    /*
     * Get the Z origin
     * @return zOrg
     */
    public int getZOrg() {
        return zOrg;
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
        this.xOrg = xOrg;
    }

    /*
     * Set the Y origin
     * @param yOrg
     */
    public void setYOrg(int yOrg) {
        this.yOrg = yOrg;
    }

    /*
     * Set the Z origin
     * @param zOrg
     */
    public void setZOrg(int zOrg) {
        this.zOrg = zOrg;
    }
    
    public String toString() {
        return name + " " + xOrg + " " + yOrg + " " + zOrg + " "
            + xWidth + " " + yWidth + " " + zWidth + " ";
    }

}
