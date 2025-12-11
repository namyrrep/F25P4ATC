/**
 * This is the Rocket child class
 * @author Edwin Barrack
 * @author Will Perryman
 * @version 12/10/2025
 */
public class Rocket extends AirObject {
    private int ascentRate;
    private double trajectory;

    public Rocket(
        String name,
        int x,
        int y,
        int z,
        int xWidth,
        int yWidth,
        int zWidth,
        int ascentRate,
        double trajectory) {
        super(name, x, y, z, xWidth, yWidth, zWidth);
        this.ascentRate = ascentRate;
        this.trajectory = trajectory;
    }


    /**
     * Get the ascent rate of the rocket.
     * 
     * @return The ascent rate.
     */
    public int getAscentRate() {
        return ascentRate;
    }


    /**
     * Get the trajectory of the rocket.
     * 
     * @return The trajectory.
     */
    public double getTrajectory() {
        return trajectory;
    }
    
    /**
     * Return a string representation of the Rocket.
     * 
     * @return A string representation of the Rocket.
     */
    @Override
    public String toString() {
        return "Rocket " + super.toString() + ascentRate + " " + trajectory;
    }
}
