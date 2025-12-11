/**
 * This is the Bird child class
 * @author Edwin Barrack
 * @author Will Perryman
 * @version 12/10/2025
 */
public class Bird extends AirObject {
    private String type;
    private int number;

    public Bird(
        String name,
        int x,
        int y,
        int z,
        int xWidth,
        int yWidth,
        int zWidth,
        String type,
        int number) {
        super(name, x, y, z, xWidth, yWidth, zWidth);
        this.type = type;
        this.number = number;
    }


    /**
     * Get the type of the bird.
     * 
     * @return The type.
     */
    public String getType() {
        return type;
    }


    /**
     * Get the number of birds.
     * 
     * @return The number.
     */
    public int getNumber() {
        return number;
    }
    
    /**
     * Return a string representation of the Bird.
     * 
     * @return A string representation of the Bird.
     */
    @Override
    public String toString() {
        return "Bird " + super.toString() + type + " " + number;
    }
}
