/**
 * 
 */
public class Balloon extends AirObject {
    private String type;
    private int ascentRate;

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
        this.ascentRate = ascent_Rate;
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
    public int getAscentRate() {
        return ascentRate;
    }
    
    /**
     * Return a string representation of the Balloon.
     * 
     * @return A string representation of the Balloon.
     */
    @Override
    public String toString() {
        return "Balloon " + super.toString() + type + " " + ascentRate;
    }
}
