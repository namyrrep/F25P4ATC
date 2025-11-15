/*
 * Rocket.java
 * 
 *
 */
public class Rocket extends AirObject {
    private int ascentRate;
    private float trajectory;

    public Rocket(
        String name,
        int x,
        int y,
        int z,
        int xWidth,
        int yWidth,
        int zWidth,
        int ascentRate,
        float trajectory) {
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
    public float getTrajectory() {
        return trajectory;
    }
}
