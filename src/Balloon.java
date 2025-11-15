/**
 * 
 */
public class Balloon extends AirObject {
    private String type;
    private int ascent_Rate;

    public Balloon(
        String name,
        int x,
        int y,
        int z,
        int xWidth,
        int yWidth,
        int zWidth,
        String type,
        int ascent_Rate) {
        super(name, x, y, z, xWidth, yWidth, zWidth);
        this.type = type;
        this.ascent_Rate = ascent_Rate;
    }


    /**
     * Get the type of the balloon.
     * 
     * @return The type.
     */
    public String getType() {
        return type;
    }


    /**
     * Get the ascent rate of the balloon.
     * 
     * @return The ascent rate.
     */
    public int getAscent_Rate() {
        return ascent_Rate;
    }
}
