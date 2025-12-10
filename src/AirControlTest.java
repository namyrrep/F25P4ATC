import java.util.Random;
import student.TestCase;

/**
 * @author {Your Name Here}
 * @version {Put Something Here}
 */
public class AirControlTest extends TestCase {

    /**
     * Sets up the tests that follow. In general, used for initialization
     */
    public void setUp() {
        // Nothing here
    }


    /**
     * Get code coverage of the class declaration.
     *
     * @throws Exception
     */
    public void testRInit() throws Exception {
        AirControl recstore = new AirControl();
        assertNotNull(recstore);
    }


    // ----------------------------------------------------------
    /**
     * Test syntax: Sample Input/Output
     *
     * @throws Exception
     */
    public void testSampleInput() throws Exception {
        Random rnd = new Random();
        rnd.setSeed(0xCAFEBEEF);
        WorldDB w = new WorldDB(rnd);

        assertTrue(w.add(new Balloon("B1",
            10, 11, 11, 21, 12, 31, "hot_air", 15)));
        assertTrue(w.add(new AirPlane("Air1",
            0, 10, 1, 20, 2, 30, "USAir", 717, 4)));
        assertTrue(w.add(new Drone("Air2",
            100, 1010, 101, 924, 2, 900, "Droners", 3)));
        assertTrue(w.add(new Bird("pterodactyl",
            0, 100, 20, 10, 50, 50, "Dinosaur", 1)));
        assertFalse(w.add(new Bird("pterodactyl",
            0, 100, 20, 10, 50, 50, "Dinosaur", 1)));
        assertTrue(w.add(new Rocket("Enterprise",
            0, 100, 20, 10, 50, 50, 5000, 99.29)));

        assertFuzzyEquals(
            "Rocket Enterprise 0 100 20 10 50 50 5000 99.29",
            w.delete("Enterprise"));

        assertFuzzyEquals("Airplane Air1 0 10 1 20 2 30 USAir 717 4",
            w.print("Air1"));
        assertNull(w.print("air1"));
        
        System.out.println(w.printbintree());
        assertFuzzyEquals(
            "I (0, 0, 0, 1024, 1024, 1024) 0\r\n"
                + "  I (0, 0, 0, 512, 1024, 1024) 1\r\n"
                + "    Leaf with 3 objects (0, 0, 0, 512, 512, 1024) 2\r\n"
                + "    (Airplane Air1 0 10 1 20 2 30 USAir 717 4)\r\n"
                + "    (Balloon B1 10 11 11 21 12 31 hot_air 15)\r\n"
                + "    (Bird pterodactyl 0 100 20 10 50 50 Dinosaur 1)\r\n"
                + "    Leaf with 1 objects (0, 512, 0, 512, 512, 1024) 2\r\n"
                + "    (Drone Air2 100 1010 101 924 2 900 Droners 3)\r\n"
                + "  Leaf with 1 objects (512, 0, 0, 512, 1024, 1024) 1\r\n"
                + "  (Drone Air2 100 1010 101 924 2 900 Droners 3)\r\n"
                + "5 Bintree nodes printed\r\n",
                w.printbintree());

        
        System.out.println(w.printskiplist());
        assertFuzzyEquals(
            "Node has depth 3, Value (null)\r\n"
                + "Node has depth 3, "
                + "Value (Airplane Air1 0 10 1 20 2 30 USAir 717 4)\r\n"
                + "Node has depth 1, "
                + "Value (Drone Air2 100 1010 101 924 2 900 Droners 3)\r\n"
                + "Node has depth 2, "
                + "Value (Balloon B1 10 11 11 21 12 31 hot_air 15)\r\n"
                + "Node has depth 2, "
                + "Value (Bird pterodactyl 0 100 20 10 50 50 Dinosaur 1)\r\n"
                + "4 skiplist nodes printed\r\n",
                w.printskiplist());
                
        
        System.out.println(w.rangeprint("z", "a"));
        assertFuzzyEquals(
            "Found these records in the range a to z\r\n"
                + "Bird pterodactyl 0 100 20 10 50 50 Dinosaur 1\r\n",
                w.rangeprint("a", "z"));
        assertFuzzyEquals(
            "Found these records in the range a to l\r\n",
            w.rangeprint("a", "l"));
        assertNull(w.rangeprint("z", "a"));

        System.out.println(w.collisions());
        assertFuzzyEquals(
            "The following collisions exist in the database:\r\n"
                + "In leaf node (0, 0, 0, 512, 512, 1024) 2\r\n"
                + "(Airplane Air1 0 10 1 20 2 30 USAir 717 4) "
                + "and (Balloon B1 10 11 11 21 12 31 hot_air 15)\r\n"
                + "In leaf node (0, 512, 0, 512, 512, 1024) 2\r\n"
                + "In leaf node (512, 0, 0, 512, 1024, 1024) 1\r\n",
                w.collisions());
        
        System.out.println(w.intersect(0, 0, 0, 1024, 1024, 1024));
        assertFuzzyEquals(
            "The following objects intersect (0 0 0 1024 1024 1024):\r\n"
                + "In Internal node (0, 0, 0, 1024, 1024, 1024) 0\r\n"
                + "In Internal node (0, 0, 0, 512, 1024, 1024) 1\r\n"
                + "In leaf node (0, 0, 0, 512, 512, 1024) 2\r\n"
                + "Airplane Air1 0 10 1 20 2 30 USAir 717 4\r\n"
                + "Balloon B1 10 11 11 21 12 31 hot_air 15\r\n"
                + "Bird pterodactyl 0 100 20 10 50 50 Dinosaur 1\r\n"
                + "In leaf node (0, 512, 0, 512, 512, 1024) 2\r\n"
                + "Drone Air2 100 1010 101 924 2 900 Droners 3\r\n"
                + "In leaf node (512, 0, 0, 512, 1024, 1024) 1\r\n"
                + "5 nodes were visited in the bintree\r\n",
                w.intersect(0, 0, 0, 1024, 1024, 1024));
    }


    // ----------------------------------------------------------
    /**
     * Test syntax: Check various forms of bad input parameters
     *
     * @throws Exception
     */
    public void testBadInput() throws Exception {
        Random rnd = new Random();
        rnd.setSeed(0xCAFEBEEF);
        WorldDB w = new WorldDB(rnd);
        assertFalse(w.add(new AirPlane("a", 1, 1, 1, 1, 1, 1, null, 1, 1)));
        assertFalse(w.add(new AirPlane("a", 1, 1, 1, 1, 1, 1, "Alaska", 0, 1)));
        assertFalse(w.add(new AirPlane("a", 1, 1, 1, 1, 1, 1, "Alaska", 1, 0)));
        assertFalse(w.add(new Balloon(null, 1, 1, 1, 1, 1, 1, "hot", 5)));
        assertFalse(w.add(new Balloon("b", -1, 1, 1, 1, 1, 1, "hot", 5)));
        assertFalse(w.add(new Balloon("b", 1, -1, 1, 1, 1, 1, "hot", 5)));
        assertFalse(w.add(new Balloon("b", 1, 1, -1, 1, 1, 1, "hot", 5)));
        assertFalse(w.add(new Balloon("b", 1, 1, 1, 0, 1, 1, "hot", 5)));
        assertFalse(w.add(new Balloon("b", 1, 1, 1, 1, 0, 1, "hot", 5)));
        assertFalse(w.add(new Balloon("b", 1, 1, 1, 1, 1, 0, "hot", 5)));
        assertFalse(w.add(new Balloon("b", 1, 1, 1, 1, 1, 1, null, 5)));
        assertFalse(w.add(new Balloon("b", 1, 1, 1, 1, 1, 1, "hot", -1)));
        assertFalse(w.add(new Bird("b", 1, 1, 1, 1, 1, 1, null, 5)));
        assertFalse(w.add(new Bird("b", 1, 1, 1, 1, 1, 1, "Ostrich", 0)));
        assertFalse(w.add(new Drone("d", 1, 1, 1, 1, 1, 1, null, 5)));
        assertFalse(w.add(new Drone("d", 1, 1, 1, 1, 1, 1, "Droner", 0)));
        assertFalse(w.add(new Rocket("r", 1, 1, 1, 1, 1, 1, -1, 1.1)));
        assertFalse(w.add(new Rocket("r", 1, 1, 1, 1, 1, 1, 1, -1.1)));
        assertFalse(w.add(
            new AirPlane("a", 2000, 1, 1, 1, 1, 1, "Alaska", 1, 1)));
        assertFalse(w.add(
            new AirPlane("a", 1, 2000, 1, 1, 1, 1, "Alaska", 1, 1)));
        assertFalse(w.add(
            new AirPlane("a", 1, 1, 2000, 1, 1, 1, "Alaska", 1, 1)));
        assertFalse(w.add(
            new AirPlane("a", 1, 1, 1, 2000, 1, 1, "Alaska", 1, 1)));
        assertFalse(w.add(
            new AirPlane("a", 1, 1, 1, 1, 2000, 1, "Alaska", 1, 1)));
        assertFalse(w.add(
            new AirPlane("a", 1, 1, 1, 1, 1, 2000, "Alaska", 1, 1)));
        assertFalse(w.add(
            new AirPlane("a", 1000, 1, 1, 1000, 1, 1, "Alaska", 1, 1)));
        assertFalse(w.add(
            new AirPlane("a", 1, 1000, 1, 1, 1000, 1, "Alaska", 1, 1)));
        assertFalse(w.add(
            new AirPlane("a", 1, 1, 1000, 1, 1, 1000, "Alaska", 1, 1)));
        assertNull(w.delete(null));
        assertNull(w.print(null));
        assertNull(w.rangeprint(null, "a"));
        assertNull(w.rangeprint("a", null));
        assertNull(w.intersect(-1, 1, 1, 1, 1, 1));
        assertNull(w.intersect(1, -1, 1, 1, 1, 1));
        assertNull(w.intersect(1, 1, -1, 1, 1, 1));
        assertNull(w.intersect(1, 1, 1, -1, 1, 1));
        assertNull(w.intersect(1, 1, 1, 1, -1, 1));
        assertNull(w.intersect(1, 1, 1, 1, 1, -1));
        assertNull(w.intersect(2000, 1, 1, 1, 1, 1));
        assertNull(w.intersect(1, 2000, 1, 1, 1, 1));
        assertNull(w.intersect(1, 1, 2000, 1, 1, 1));
        assertNull(w.intersect(1, 1, 1, 2000, 1, 1));
        assertNull(w.intersect(1, 1, 1, 1, 2000, 1));
        assertNull(w.intersect(1, 1, 1, 1, 1, 2000));
        assertNull(w.intersect(1000, 1, 1, 1000, 1, 1));
        assertNull(w.intersect(1, 1000, 1, 1, 1000, 1));
        assertNull(w.intersect(1, 1, 1000, 1, 1, 1000));
    }


    // ----------------------------------------------------------
    /**
     * Test empty: Check various returns from commands on empty database
     *
     * @throws Exception
     */
    public void testEmpty() throws Exception {
        WorldDB w = new WorldDB(null);
        assertNull(w.delete("hello"));
        assertFuzzyEquals("SkipList is empty", w.printskiplist());
        assertFuzzyEquals(
            "E (0, 0, 0, 1024, 1024, 1024) 0\r\n"
                + "1 Bintree nodes printed\r\n",
                w.printbintree());
        assertNull(w.print("hello"));
        assertFuzzyEquals("Found these records in the range begin to end\n",
            w.rangeprint("begin", "end"));
        assertFuzzyEquals("The following collisions exist in the database:\n",
            w.collisions());
        assertFuzzyEquals(
            "The following objects intersect (1, 1, 1, 1, 1, 1)\n" +
                "1 nodes were visited in the bintree\n",
                w.intersect(1, 1, 1, 1, 1, 1));
    }
    
    // ----------------------------------------------------------
    /**
     * Test coverage: Exercise code for coverage
     *
     * @throws Exception
     */
    public void testCoverage() throws Exception {
        WorldDB w = new WorldDB(null);
        assertFalse(w.add(null));
        
        // Test adding object with empty name
        assertFalse(w.add(new Balloon("", 1, 1, 1, 1, 1, 1, "hot", 5)));
        
        // Test delete with empty string
        assertNull(w.delete(""));
        
        // Test print with empty string
        assertNull(w.print(""));
        
        // Test rangeprint with empty strings
        assertNull(w.rangeprint("", "z"));
        assertNull(w.rangeprint("a", ""));
        
        // Test adding many objects to force tree splits
        assertTrue(w.add(new Balloon("obj1", 10, 10, 10, 10, 10, 10, "hot", 5)));
        assertTrue(w.add(new Balloon("obj2", 20, 20, 20, 10, 10, 10, "hot", 5)));
        assertTrue(w.add(new Balloon("obj3", 30, 30, 30, 10, 10, 10, "hot", 5)));
        assertTrue(w.add(new Balloon("obj4", 40, 40, 40, 10, 10, 10, "hot", 5)));
        
        // Test objects in different regions of the tree
        assertTrue(w.add(new Balloon("obj5", 600, 10, 10, 10, 10, 10, "hot", 5)));
        assertTrue(w.add(new Balloon("obj6", 10, 600, 10, 10, 10, 10, "hot", 5)));
        assertTrue(w.add(new Balloon("obj7", 10, 10, 600, 10, 10, 10, "hot", 5)));
        assertTrue(w.add(new Balloon("obj8", 600, 600, 600, 10, 10, 10, "hot", 5)));
        
        // Test printbintree with multiple nodes
        String treeOutput = w.printbintree();
        assertNotNull(treeOutput);
        assertTrue(treeOutput.contains("Bintree nodes printed"));
        
        // Test collisions with non-overlapping objects
        String collisionOutput = w.collisions();
        assertNotNull(collisionOutput);
        assertTrue(collisionOutput.contains("The following collisions exist"));
        
        // Test intersect with specific region
        String intersectOutput = w.intersect(0, 0, 0, 100, 100, 100);
        assertNotNull(intersectOutput);
        assertTrue(intersectOutput.contains("nodes were visited"));
        
        // Test intersect that hits only part of tree
        intersectOutput = w.intersect(500, 500, 500, 200, 200, 200);
        assertNotNull(intersectOutput);
        
        // Test delete and verify removal from both structures
        assertNotNull(w.delete("obj1"));
        assertNull(w.print("obj1"));
        assertNull(w.delete("obj1")); // Already deleted
        
        // Test rangeprint with valid range
        String rangeOutput = w.rangeprint("obj2", "obj5");
        assertNotNull(rangeOutput);
        assertTrue(rangeOutput.contains("Found these records"));
        
        // Test skiplist print
        String skipOutput = w.printskiplist();
        assertNotNull(skipOutput);
        
        // Delete all objects and test empty tree behavior
        w.delete("obj2");
        w.delete("obj3");
        w.delete("obj4");
        w.delete("obj5");
        w.delete("obj6");
        w.delete("obj7");
        w.delete("obj8");
        
        // Test empty tree after deletions
        treeOutput = w.printbintree();
        assertTrue(treeOutput.contains("1 Bintree nodes printed"));
        
        // Test objects that span multiple regions (large bounding boxes)
        assertTrue(w.add(new Drone("bigDrone", 100, 100, 100, 900, 900, 900, "BigBrand", 4)));
        treeOutput = w.printbintree();
        assertNotNull(treeOutput);
        
        // Test intersect with object spanning multiple regions
        intersectOutput = w.intersect(0, 0, 0, 1024, 1024, 1024);
        assertTrue(intersectOutput.contains("bigDrone"));
        
        // Test collisions between overlapping large objects
        assertTrue(w.add(new Balloon("bigBalloon", 200, 200, 200, 500, 500, 500, "mega", 10)));
        collisionOutput = w.collisions();
        assertNotNull(collisionOutput);
        
        // Clean up
        w.delete("bigDrone");
        w.delete("bigBalloon");
        
        // Test adding objects at boundary positions
        assertTrue(w.add(new Balloon("corner1", 0, 0, 0, 1, 1, 1, "tiny", 1)));
        assertTrue(w.add(new Balloon("corner2", 1023, 1023, 1023, 1, 1, 1, "tiny", 1)));
        
        // Test that we can find boundary objects
        assertNotNull(w.print("corner1"));
        assertNotNull(w.print("corner2"));
        
        // Test intersect at boundaries
        intersectOutput = w.intersect(0, 0, 0, 1, 1, 1);
        assertTrue(intersectOutput.contains("corner1"));
        
        intersectOutput = w.intersect(1023, 1023, 1023, 1, 1, 1);
        assertTrue(intersectOutput.contains("corner2"));
    }
    
}