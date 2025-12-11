//-------------------------------------------------------------------------
/**
 * Interface class for the AirControl project
 * You probably don't need to edit this file
 *
 * @author CS3114/5040 Staff
 * @version Fall 2025
 *
 */
public interface ATC {

    // ----------------------------------------------------------
    /**
     * Reinitializes the database, clearing all stored AirObjects.
     */
    public void clear();


    // ----------------------------------------------------------
    /**
     * Attempts to insert an AirObject into the database.
     * @param a The AirObject to be added.
     * @return True if the AirObject is successfully inserted; false otherwise (e.g., if the object is invalid or a duplicate).
     */
    public boolean add(AirObject a);


    // ----------------------------------------------------------
    /**
     * Deletes an AirObject with the specified name from the database.
     * If found, the method prints the AirObject's details.
     * @param name The name of the AirObject to delete.
     * @return A string representation of the deleted AirObject, or null if no object with that name exists.
     */
    public String delete(String name);


    // ----------------------------------------------------------
    /**
     * Generates a listing of all AirObjects in the SkipList, sorted alphabetically by name.
     * @return A formatted string of the SkipList's contents.
     */
    public String printskiplist();


    // ----------------------------------------------------------
    /**
     * Generates a pre-order traversal listing of the nodes in the BinTree.
     * @return A formatted string representing the BinTree's structure and contents.
     */
    public String printbintree();


    // ----------------------------------------------------------
    /**
     * Retrieves and prints the details of an AirObject with a given name.
     * @param name The name of the AirObject to find and print.
     * @return A string representation of the AirObject if found; otherwise, null.
     */
    public String print(String name);


    // ----------------------------------------------------------
    /**
     * Returns a listing of all AirObjects whose names fall within a specified alphabetical range.
     * @param min The minimum name in the range (inclusive).
     * @param max The maximum name in the range (inclusive).
     * @return A formatted string of the AirObjects found within the specified range.
     */
    public String rangeprint(String min, String max);


    // ----------------------------------------------------------
    /**
     * Detects and lists all pairs of AirObjects whose bounding boxes collide.
     * Collisions are reported based on the node containing the origin of the intersection.
     * @return A formatted string listing all detected collisions.
     */
    public String collisions();


    // ----------------------------------------------------------
    /**
     * Finds and lists all AirObjects whose bounding boxes intersect with a given query box.
     * @param x The x-coordinate of the query box's origin.
     * @param y The y-coordinate of the query box's origin.
     * @param z The z-coordinate of the query box's origin.
     * @param xwid The width of the query box along the x-axis.
     * @param ywid The width of the query box along the y-axis.
     * @param zwid The width of the query box along the z-axis.
     * @return A formatted string of AirObjects that intersect the query box.
     */
    public String intersect(int x, int y, int z, int xwid, int ywid, int zwid);
}