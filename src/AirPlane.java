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
     * Return a string representation of the Airplane.
     * 
     * @return A string representation of the Airplane.
     */
    @Override
    public String toString() {
        return "Airplane " + super.toString() + airline + " " + flightNumber
            + " " + numEngines;
    }
}