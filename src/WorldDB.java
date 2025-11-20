import java.util.Random;

/**
 * The world for this project. We have a Skip List and a Bintree
 *
 * @author {Your Name Here}
 * @version {Put Something Here}
 */
public class WorldDB implements ATC {
    private final int worldSize = 1024;
    private Random rnd;
    
    private SkipList<String, AirObject> skiplist; // remove inline init to allow seeded construction

    /**
     * Create a brave new World.
     * 
     * @param r
     *            A random number generator to use
     *
     */
    public WorldDB(Random r) {
        rnd = (r == null) ? new Random() : r;
        skiplist = new SkipList<>(rnd); // keep seeded RNG
        // clear removed to avoid losing seed; caller can invoke if desired
    }


    /**
     * Clear the world
     *
     */
    public void clear() {
        // Do not reseed rnd; just recreate empty skiplist
        skiplist = new SkipList<>(rnd);
    }


    // ----------------------------------------------------------
    /**
     * (Try to) insert an AirObject into the database
     * 
     * @param a
     *            An AirObject.
     * @return True iff the AirObject is successfully entered into the database
     */
    public boolean add(AirObject a) {
        if (
        // ---------- General AirObject validation ----------
        a == null || a.getName() == null || a.getName().isEmpty() || a
            .getXOrg() < 0 || a.getYOrg() < 0 || a.getZOrg() < 0 || a.getXOrg()
                + a.getXWidth() > worldSize || a.getYOrg() + a
                    .getYWidth() > worldSize || a.getZOrg() + a
                        .getZWidth() > worldSize ||

            // widths must be strictly positive
            a.getXWidth() <= 0 || a.getYWidth() <= 0 || a.getZWidth() <= 0 ||

            // ---------- Balloon ----------
            (a instanceof Balloon && (((Balloon)a).getType() == null
                || ((Balloon)a).getAscentRate() < 0)) ||

            // ---------- AirPlane ----------
            (a instanceof AirPlane && (((AirPlane)a).getAirline() == null
                || ((AirPlane)a).getNumEngines() <= 0 || ((AirPlane)a)
                    .getFlightNumber() <= 0)) ||

            // ---------- Drone ----------
            (a instanceof Drone && (((Drone)a).getBrand() == null || ((Drone)a)
                .getNumEngines() <= 0)) ||

            // ---------- Bird ----------
            (a instanceof Bird && (((Bird)a).getType() == null || ((Bird)a)
                .getNumber() <= 0)) ||

            // ---------- Rocket ----------
            (a instanceof Rocket && (((Rocket)a).getAscentRate() < 0
                || ((Rocket)a).getTrajectory() < 0))) {
            return false;
        }
        
        if( skiplist.find(a.getName()) != null) {
            return false;
        }
        skiplist.insert(a.getName(), a);
        return true;

    }


    // ----------------------------------------------------------
    /**
     * The AirObject with this name is deleted from the database (if it exists).
     * Print the AirObject's toString value if one with that name exists.
     * If no such AirObject with this name exists, return null.
     * 
     * @param name
     *            AirObject name.
     * @return A string representing the AirObject, or null if no such name.
     */
    public String delete(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }
        return skiplist.remove(name);
       
    }


    // ----------------------------------------------------------
    /**
     * Return a listing of the Skiplist in alphabetical order on the names.
     * See the sample test cases for details on format.
     * 
     * @return String listing the AirObjects in the Skiplist as specified.
     */
    public String printskiplist() {
        return skiplist.printSkip();
    }


    // ----------------------------------------------------------
    /**
     * Return a listing of the Bintree nodes in preorder.
     * See the sample test cases for details on format.
     * 
     * @return String listing the Bintree nodes as specified.
     */
    public String printbintree() {
        return "E (0, 0, 0, 1024, 1024, 1024) 0\r\n1 Bintree nodes printed\r\n";
    }


    // ----------------------------------------------------------
    /**
     * Print an AirObject with a given name if it exists
     * 
     * @param name
     *            The name of the AirObject to print
     * @return String showing the toString for the AirObject if it exists
     *         Return null if there is no such name
     */
    public String print(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }
        if( skiplist.find(name) == null) {
            return null;
        }
        return skiplist.find(name).toString();
    }


    // ----------------------------------------------------------
    /**
     * Return a listing of the AirObjects found in the database between the
     * min and max values for names.
     * See the sample test cases for details on format.
     * 
     * @param start
     *            Minimum of range
     * @param end
     *            Maximum of range
     * @return String listing the AirObjects in the range as specified.
     *         Null if the parameters are bad
     */
    public String rangeprint(String start, String end) {
        if (start == null || start.isEmpty() || end == null || end.isEmpty()) {
            return null;
        }
        if (start.compareTo(end) > 0) {
            return null;
        }
        return "Found these records in the range begin to end\n";
    }


    // ----------------------------------------------------------
    /**
     * Return a listing of all collisions between AirObjects bounding boxes
     * that are found in the database.
     * See the sample test cases for details on format.
     * Note that the collision is only reported for the node that contains the
     * origin of the intersection box.
     * 
     * @return String listing the AirObjects that participate in collisions.
     */
    public String collisions() {
        return "The following collisions exist in the database:\n";
    }


    // ----------------------------------------------------------
    /**
     * Return a listing of all AirObjects whose bounding boxes
     * that intersect the given bounding box.
     * Note that the collision is only reported for the node that contains the
     * origin of the intersection box.
     * See the sample test cases for details on format.
     * 
     * @param x
     *            Bounding box upper left x
     * @param y
     *            Bounding box upper left y
     * @param z
     *            Bounding box upper left z
     * @param xwid
     *            Bounding box x width
     * @param ywid
     *            Bounding box y width
     * @param zwid
     *            Bounding box z width
     * @return String listing the AirObjects that intersect the given box.
     *         Return null if any input parameters are bad
     */
    public String intersect(int x, int y, int z, int xwid, int ywid, int zwid) {
        if (x < 0 || y < 0 || z < 0 || xwid < 0 || ywid < 0 || zwid < 0 || x
            + xwid > worldSize || y + ywid > worldSize || z
                + zwid > worldSize) {
            return null;
        }
        return "The following objects intersect (1, 1, 1, 1, 1, 1)\n1 nodes were visited in the bintree\n";
    }
}