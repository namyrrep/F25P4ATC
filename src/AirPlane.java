/**
 * 
 */

/**
 * 
 */
public class AirPlane extends AirObject {
    private String airline;
    private int numEngines;
    private int flightNumber;

    /**
     * Constructor for AirPlane.
     *
     * @param name
     *            The name of the airplane.
     * @param x
     *            The x-coordinate of the origin.
     * @param y
     *            The y-coordinate of the origin.
     * @param z
     *            The z-coordinate of the origin.
     * @param xWidth
     *            The width in the x-dimension.
     * @param yWidth
     *            The width in the y-dimension.
     * @param zWidth
     *            The width in the z-dimension.
     * @param airline
     *            The airline of the airplane.
     * @param flightNumber
     *            The flight number.
     * @param numEngines
     *            The number of engines.
     */
    public AirPlane(
        String name,
        int x,
        int y,
        int z,
        int xWidth,
        int yWidth,
        int zWidth,
        String airline,
        int flightNumber,
        int numEngines) {
        super(name, x, y, z, xWidth, yWidth, zWidth);
        this.airline = airline;
        this.numEngines = numEngines;
        this.flightNumber = flightNumber;
    }


    /**
     * Get the airline of the airplane.
     * 
     * @return The airline.
     */
    public String getAirline() {
        return airline;
    }


    /**
     * Get the number of engines of the airplane.
     * 
     * @return The number of engines.
     */
    public int getNumEngines() {
        return numEngines;
    }


    /**
     * Get the flight number of the airplane.
     * 
     * @return The flight number.
     */
    public int getFlightNumber() {
        return flightNumber;
    }


    /**
     * Returns a string representation of the Airplane.
     * 
     * @return A string representation of the Airplane, including its type and
     *         attributes.
     */
    @Override
    public String toString() {
        return "Airplane " + super.toString() + airline + " " + flightNumber
            + " " + numEngines;
    }
}
