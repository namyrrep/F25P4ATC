
/**
 * 
 */
public class Drone extends AirObject {
    private String brand;
    private int numEngines;

    public Drone(
        String name,
        int x,
        int y,
        int z,
        int xWidth,
        int yWidth,
        int zWidth,
        String brand,
        int numEngines) {
        super(name, x, y, z, xWidth, yWidth, zWidth);
        this.brand = brand;
        this.numEngines = numEngines;
    }


    /**
     * Get the brand of the drone.
     * 
     * @return The brand.
     */
    public String getBrand() {
        return brand;
    }


    /**
     * Get the number of engines of the drone.
     * 
     * @return The number of engines.
     */
    public int getNumEngines() {
        return numEngines;
    }


    /**
     * Return a string representation of the Drone.
     * 
     * @return A string representation of the Drone.
     */
    @Override
    public String toString() {
        return "Drone " + super.toString() + brand + " " + numEngines;
    }

}
