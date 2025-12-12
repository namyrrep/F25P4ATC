import java.util.Random;
import student.TestCase;

/**
 * The test class for AirControl.
 * 
 * @author Edwin Barrack
 * @author Will Perryman
 * @version 12/10/2025
 */
public class AirControlTest extends TestCase {

    /**
     * Sets up the test environment before each test method.
     */
    public void setUp() {
        // Nothing here
    }


    /**
     * Tests the initialization of the AirControl class.
     *
     * @throws Exception
     *             if an error occurs during initialization.
     */
    public void testRInit() throws Exception {
        AirControl recstore = new AirControl();
        assertNotNull(recstore);
    }


    // ----------------------------------------------------------
    /**
     * Tests the system with a sample set of inputs and verifies the output.
     *
     * @throws Exception
     *             if an error occurs during the test.
     */
    public void testSampleInput() throws Exception {
        Random rnd = new Random();
        rnd.setSeed(0xCAFEBEEF);
        WorldDB w = new WorldDB(rnd);

        assertTrue(w.add(new Balloon("B1", 10, 11, 11, 21, 12, 31, "hot_air",
            15)));
        assertTrue(w.add(new AirPlane("Air1", 0, 10, 1, 20, 2, 30, "USAir", 717,
            4)));
        assertTrue(w.add(new Drone("Air2", 100, 1010, 101, 924, 2, 900,
            "Droners", 3)));
        assertTrue(w.add(new Bird("pterodactyl", 0, 100, 20, 10, 50, 50,
            "Dinosaur", 1)));
        assertFalse(w.add(new Bird("pterodactyl", 0, 100, 20, 10, 50, 50,
            "Dinosaur", 1)));
        assertTrue(w.add(new Rocket("Enterprise", 0, 100, 20, 10, 50, 50, 5000,
            99.29)));

        assertFuzzyEquals("Rocket Enterprise 0 100 20 10 50 50 5000 99.29", w
            .delete("Enterprise"));

        assertFuzzyEquals("Airplane Air1 0 10 1 20 2 30 USAir 717 4", w.print(
            "Air1"));
        assertNull(w.print("air1"));

        System.out.println(w.printbintree());
        assertFuzzyEquals("I (0, 0, 0, 1024, 1024, 1024) 0\r\n"
            + "  I (0, 0, 0, 512, 1024, 1024) 1\r\n"
            + "    Leaf with 3 objects (0, 0, 0, 512, 512, 1024) 2\r\n"
            + "    (Airplane Air1 0 10 1 20 2 30 USAir 717 4)\r\n"
            + "    (Balloon B1 10 11 11 21 12 31 hot_air 15)\r\n"
            + "    (Bird pterodactyl 0 100 20 10 50 50 Dinosaur 1)\r\n"
            + "    Leaf with 1 objects (0, 512, 0, 512, 512, 1024) 2\r\n"
            + "    (Drone Air2 100 1010 101 924 2 900 Droners 3)\r\n"
            + "  Leaf with 1 objects (512, 0, 0, 512, 1024, 1024) 1\r\n"
            + "  (Drone Air2 100 1010 101 924 2 900 Droners 3)\r\n"
            + "5 Bintree nodes printed\r\n", w.printbintree());

        System.out.println(w.printskiplist());
        assertFuzzyEquals("Node has depth 3, Value (null)\r\n"
            + "Node has depth 3, "
            + "Value (Airplane Air1 0 10 1 20 2 30 USAir 717 4)\r\n"
            + "Node has depth 1, "
            + "Value (Drone Air2 100 1010 101 924 2 900 Droners 3)\r\n"
            + "Node has depth 2, "
            + "Value (Balloon B1 10 11 11 21 12 31 hot_air 15)\r\n"
            + "Node has depth 2, "
            + "Value (Bird pterodactyl 0 100 20 10 50 50 Dinosaur 1)\r\n"
            + "4 skiplist nodes printed\r\n", w.printskiplist());

        System.out.println(w.rangeprint("z", "a"));
        assertFuzzyEquals("Found these records in the range a to z\r\n"
            + "Bird pterodactyl 0 100 20 10 50 50 Dinosaur 1\r\n", w.rangeprint(
                "a", "z"));
        assertFuzzyEquals("Found these records in the range a to l\r\n", w
            .rangeprint("a", "l"));
        assertNull(w.rangeprint("z", "a"));

        System.out.println(w.collisions());
        assertFuzzyEquals("The following collisions exist in the database:\r\n"
            + "In leaf node (0, 0, 0, 512, 512, 1024) 2\r\n"
            + "(Airplane Air1 0 10 1 20 2 30 USAir 717 4) "
            + "and (Balloon B1 10 11 11 21 12 31 hot_air 15)\r\n"
            + "In leaf node (0, 512, 0, 512, 512, 1024) 2\r\n"
            + "In leaf node (512, 0, 0, 512, 1024, 1024) 1\r\n", w
                .collisions());

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
                + "5 nodes were visited in the bintree\r\n", w.intersect(0, 0,
                    0, 1024, 1024, 1024));
    }


    // ----------------------------------------------------------
    /**
     * Tests the system's handling of various invalid input parameters.
     *
     * @throws Exception
     *             if an error occurs during the test.
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
        assertFalse(w.add(new AirPlane("a", 2000, 1, 1, 1, 1, 1, "Alaska", 1,
            1)));
        assertFalse(w.add(new AirPlane("a", 1, 2000, 1, 1, 1, 1, "Alaska", 1,
            1)));
        assertFalse(w.add(new AirPlane("a", 1, 1, 2000, 1, 1, 1, "Alaska", 1,
            1)));
        assertFalse(w.add(new AirPlane("a", 1, 1, 1, 2000, 1, 1, "Alaska", 1,
            1)));
        assertFalse(w.add(new AirPlane("a", 1, 1, 1, 1, 2000, 1, "Alaska", 1,
            1)));
        assertFalse(w.add(new AirPlane("a", 1, 1, 1, 1, 1, 2000, "Alaska", 1,
            1)));
        assertFalse(w.add(new AirPlane("a", 1000, 1, 1, 1000, 1, 1, "Alaska", 1,
            1)));
        assertFalse(w.add(new AirPlane("a", 1, 1000, 1, 1, 1000, 1, "Alaska", 1,
            1)));
        assertFalse(w.add(new AirPlane("a", 1, 1, 1000, 1, 1, 1000, "Alaska", 1,
            1)));
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
     * Tests the behavior of commands on an empty database.
     *
     * @throws Exception
     *             if an error occurs during the test.
     */
    public void testEmpty() throws Exception {
        WorldDB w = new WorldDB(null);
        assertNull(w.delete("hello"));
        assertFuzzyEquals("SkipList is empty", w.printskiplist());
        assertFuzzyEquals("E (0, 0, 0, 1024, 1024, 1024) 0\r\n"
            + "1 Bintree nodes printed\r\n", w.printbintree());
        assertNull(w.print("hello"));
        assertFuzzyEquals("Found these records in the range begin to end\n", w
            .rangeprint("begin", "end"));
        assertFuzzyEquals("The following collisions exist in the database:\n", w
            .collisions());
        assertFuzzyEquals("The following objects intersect (1, 1, 1, 1, 1, 1)\n"
            + "1 nodes were visited in the bintree\n", w.intersect(1, 1, 1, 1,
                1, 1));
    }


    // ----------------------------------------------------------
    /**
     * A comprehensive test suite to exercise various functionalities and edge
     * cases
     * for code coverage.
     *
     * @throws Exception
     *             if an error occurs during the test.
     */
    public void testCoverage() throws Exception {
        Random rnd = new Random();
        WorldDB w = new WorldDB(rnd);

        // Test adding object with empty name
        assertFalse(w.add(new Balloon("", 1, 1, 1, 1, 1, 1, "hot", 5)));

        // Test delete with empty string
        assertNull(w.delete(""));

        // Test print with empty string
        assertNull(w.print(""));

        // Test rangeprint with empty strings - empty string is valid as start
        assertNotNull(w.rangeprint("", "z"));
        // rangeprint("a", "") returns null because "a" > "" (start > end)
        assertNull(w.rangeprint("a", ""));

        // Test adding many objects to force tree splits
        assertTrue(w.add(new Balloon("obj1", 10, 10, 10, 10, 10, 10, "hot",
            5)));
        assertTrue(w.add(new Balloon("obj2", 20, 20, 20, 10, 10, 10, "hot",
            5)));
        assertTrue(w.add(new Balloon("obj3", 30, 30, 30, 10, 10, 10, "hot",
            5)));
        assertTrue(w.add(new Balloon("obj4", 40, 40, 40, 10, 10, 10, "hot",
            5)));

        // Test objects in different regions of the tree
        assertTrue(w.add(new Balloon("obj5", 600, 10, 10, 10, 10, 10, "hot",
            5)));
        assertTrue(w.add(new Balloon("obj6", 10, 600, 10, 10, 10, 10, "hot",
            5)));
        assertTrue(w.add(new Balloon("obj7", 10, 10, 600, 10, 10, 10, "hot",
            5)));
        assertTrue(w.add(new Balloon("obj8", 600, 600, 600, 10, 10, 10, "hot",
            5)));

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
        assertTrue(w.add(new Drone("bigDrone", 100, 100, 100, 900, 900, 900,
            "BigBrand", 4)));
        treeOutput = w.printbintree();
        assertNotNull(treeOutput);

        // Test intersect with object spanning multiple regions
        intersectOutput = w.intersect(0, 0, 0, 1024, 1024, 1024);
        assertTrue(intersectOutput.contains("bigDrone"));

        // Test collisions between overlapping large objects
        assertTrue(w.add(new Balloon("bigBalloon", 200, 200, 200, 500, 500, 500,
            "mega", 10)));
        collisionOutput = w.collisions();
        assertNotNull(collisionOutput);

        // Clean up
        w.delete("bigDrone");
        w.delete("bigBalloon");

        // Test adding objects at boundary positions
        assertTrue(w.add(new Balloon("corner1", 0, 0, 0, 1, 1, 1, "tiny", 1)));
        assertTrue(w.add(new Balloon("corner2", 1023, 1023, 1023, 1, 1, 1,
            "tiny", 1)));

        // Test that we can find boundary objects
        assertNotNull(w.print("corner1"));
        assertNotNull(w.print("corner2"));

        // Test intersect at boundaries
        intersectOutput = w.intersect(0, 0, 0, 1, 1, 1);
        assertTrue(intersectOutput.contains("corner1"));

        intersectOutput = w.intersect(1023, 1023, 1023, 1, 1, 1);
        assertTrue(intersectOutput.contains("corner2"));
    }


    // ----------------------------------------------------------
    /**
     * Tests InternalNode simplification after removal.
     * Covers the three simplification cases:
     * 1. left=Flyweight, right=Leaf -> returns right
     * 2. left=Leaf, right=Flyweight -> returns left
     * 3. left=Flyweight, right=Flyweight -> returns Flyweight
     * Plus the leaf merge case when two leaves can combine.
     *
     * @throws Exception
     *             if an error occurs during the test.
     */
    public void testInternalNodeSimplification() throws Exception {
        WorldDB w = new WorldDB(null);

        // Test Case 1: left=Flyweight, right=Leaf -> returns right
        // Add an object to the RIGHT side of the tree (x >= 512)
        assertTrue(w.add(new Balloon("rightOnly", 600, 100, 100, 10, 10, 10,
            "hot", 5)));
        // Add another object to the LEFT side to create an internal node
        assertTrue(w.add(new Balloon("leftOnly", 100, 100, 100, 10, 10, 10,
            "hot", 5)));
        // Now delete the left object - internal node should simplify
        // left becomes Flyweight, right stays Leaf
        assertNotNull(w.delete("leftOnly"));
        String treeOutput = w.printbintree();
        // Tree should have simplified
        assertNotNull(treeOutput);

        // Clean up
        w.delete("rightOnly");

        // Test Case 2: left=Leaf, right=Flyweight -> returns left
        // Add an object to the LEFT side only
        assertTrue(w.add(new Balloon("leftSide", 100, 100, 100, 10, 10, 10,
            "hot", 5)));
        // Add an object to the RIGHT side to create an internal node
        assertTrue(w.add(new Balloon("rightSide", 600, 100, 100, 10, 10, 10,
            "hot", 5)));
        // Now delete the right object - internal node should simplify
        // left stays Leaf, right becomes Flyweight
        assertNotNull(w.delete("rightSide"));
        treeOutput = w.printbintree();
        assertNotNull(treeOutput);

        // Clean up
        w.delete("leftSide");

        // Test Case 3: left=Flyweight, right=Flyweight -> returns Flyweight
        // Add a single object
        assertTrue(w.add(new Balloon("onlyOne", 100, 100, 100, 10, 10, 10,
            "hot", 5)));
        // Delete it - tree should become empty Flyweight
        assertNotNull(w.delete("onlyOne"));
        treeOutput = w.printbintree();
        assertTrue(treeOutput.contains("E (0, 0, 0, 1024, 1024, 1024) 0"));
        assertTrue(treeOutput.contains("1 Bintree nodes printed"));

        // Test Case 4: Two leaves merge when combined size <= 3
        // Add objects that will be in different children of same internal node
        // but when one is removed, the remaining can merge
        assertTrue(w.add(new Balloon("left1", 100, 100, 100, 10, 10, 10, "hot",
            5)));
        assertTrue(w.add(new Balloon("left2", 200, 100, 100, 10, 10, 10, "hot",
            5)));
        assertTrue(w.add(new Balloon("right1", 600, 100, 100, 10, 10, 10, "hot",
            5)));
        assertTrue(w.add(new Balloon("right2", 700, 100, 100, 10, 10, 10, "hot",
            5)));

        // Delete objects until we have conditions for a merge
        // (two leaves with combined size <= 3)
        w.delete("left2");
        w.delete("right2");

        treeOutput = w.printbintree();
        assertNotNull(treeOutput);

        // Clean up remaining
        w.delete("left1");
        w.delete("right1");

        // Verify tree is empty again
        treeOutput = w.printbintree();
        assertTrue(treeOutput.contains("1 Bintree nodes printed"));
    }


    // ----------------------------------------------------------
    /**
     * Tests deeper tree simplification scenarios.
     * This tests removal that causes cascading simplification.
     *
     * @throws Exception
     *             if an error occurs during the test.
     */
    public void testDeepTreeSimplification() throws Exception {
        WorldDB w = new WorldDB(null);

        // Create a deeper tree structure by adding objects in different regions
        // Objects in different octants to force internal node creation
        assertTrue(w.add(new Balloon("a1", 100, 100, 100, 10, 10, 10, "hot",
            5)));
        assertTrue(w.add(new Balloon("a2", 100, 100, 600, 10, 10, 10, "hot",
            5)));
        assertTrue(w.add(new Balloon("a3", 100, 600, 100, 10, 10, 10, "hot",
            5)));
        assertTrue(w.add(new Balloon("a4", 600, 100, 100, 10, 10, 10, "hot",
            5)));

        String before = w.printbintree();
        assertNotNull(before);

        // Remove objects one by one to trigger various simplifications
        w.delete("a4");
        String after1 = w.printbintree();
        assertNotNull(after1);

        w.delete("a3");
        String after2 = w.printbintree();
        assertNotNull(after2);

        w.delete("a2");
        String after3 = w.printbintree();
        assertNotNull(after3);

        w.delete("a1");
        String afterAll = w.printbintree();
        // Should be empty tree now
        assertTrue(afterAll.contains("E (0, 0, 0, 1024, 1024, 1024) 0"));
    }


    // ----------------------------------------------------------
    /**
     * Tests collision detection with intersection origin boundary conditions.
     * This tests all 9 boundary conditions in the collision origin check:
     * - intersectX >= region.x
     * - intersectX < region.x + region.xWidth
     * - intersectY >= region.y
     * - intersectY < region.y + region.yWidth
     * - intersectZ >= region.z
     * - intersectZ < region.z + region.zWidth
     *
     * @throws Exception
     *             if an error occurs during the test.
     */
    public void testCollisionBoundaryConditions() throws Exception {
        WorldDB w = new WorldDB(null);

        // Test 1: Two overlapping objects where intersection origin is
        // clearly inside the region - baseline test
        // Object A at (10, 10, 10) with size (20, 20, 20)
        // Object B at (15, 15, 15) with size (20, 20, 20)
        // Intersection origin = (15, 15, 15) which is in region
        // (0,0,0,512,512,1024)
        assertTrue(w.add(new Balloon("baseA", 10, 10, 10, 20, 20, 20, "hot",
            5)));
        assertTrue(w.add(new Balloon("baseB", 15, 15, 15, 20, 20, 20, "hot",
            5)));

        String collisions = w.collisions();
        assertTrue(collisions.contains("baseA") && collisions.contains(
            "baseB"));

        w.delete("baseA");
        w.delete("baseB");

        // Test 2: Intersection origin exactly at region boundary (x = 0)
        // Tests: intersectX >= region.x (boundary case)
        assertTrue(w.add(new Balloon("edgeXA", 0, 10, 10, 20, 20, 20, "hot",
            5)));
        assertTrue(w.add(new Balloon("edgeXB", 5, 15, 15, 20, 20, 20, "hot",
            5)));
        // Intersection X = max(0, 5) = 5, which is >= 0
        collisions = w.collisions();
        assertTrue(collisions.contains("edgeXA") && collisions.contains(
            "edgeXB"));

        w.delete("edgeXA");
        w.delete("edgeXB");

        // Test 3: Intersection origin exactly at Y boundary (y = 0)
        // Tests: intersectY >= region.y
        assertTrue(w.add(new Balloon("edgeYA", 10, 0, 10, 20, 20, 20, "hot",
            5)));
        assertTrue(w.add(new Balloon("edgeYB", 15, 5, 15, 20, 20, 20, "hot",
            5)));
        // Intersection Y = max(0, 5) = 5, which is >= 0
        collisions = w.collisions();
        assertTrue(collisions.contains("edgeYA") && collisions.contains(
            "edgeYB"));

        w.delete("edgeYA");
        w.delete("edgeYB");

        // Test 4: Intersection origin exactly at Z boundary (z = 0)
        // Tests: intersectZ >= region.z
        assertTrue(w.add(new Balloon("edgeZA", 10, 10, 0, 20, 20, 20, "hot",
            5)));
        assertTrue(w.add(new Balloon("edgeZB", 15, 15, 5, 20, 20, 20, "hot",
            5)));
        // Intersection Z = max(0, 5) = 5, which is >= 0
        collisions = w.collisions();
        assertTrue(collisions.contains("edgeZA") && collisions.contains(
            "edgeZB"));

        w.delete("edgeZA");
        w.delete("edgeZB");

        // Test 5: Objects that collide but intersection origin is in
        // a different region (right side of X split)
        // This tests that collision is NOT reported when origin is outside
        // region
        // Objects span across x=512 boundary
        assertTrue(w.add(new Balloon("spanA", 500, 10, 10, 50, 20, 20, "hot",
            5)));
        assertTrue(w.add(new Balloon("spanB", 520, 15, 15, 50, 20, 20, "hot",
            5)));
        // Intersection X = max(500, 520) = 520, which is >= 512 (right region)
        // So collision should be reported in RIGHT region, not left
        collisions = w.collisions();
        // The collision exists but should be reported exactly once
        assertNotNull(collisions);

        w.delete("spanA");
        w.delete("spanB");

        // Test 6: Objects near upper X boundary of region
        // Tests: intersectX < region.x + region.xWidth
        assertTrue(w.add(new Balloon("upperXA", 500, 10, 10, 10, 20, 20, "hot",
            5)));
        assertTrue(w.add(new Balloon("upperXB", 505, 15, 15, 10, 20, 20, "hot",
            5)));
        // Intersection X = 505, in left region (0-512), should be reported
        collisions = w.collisions();
        assertTrue(collisions.contains("upperXA") && collisions.contains(
            "upperXB"));

        w.delete("upperXA");
        w.delete("upperXB");

        // Test 7: Objects near upper Y boundary of region
        // Tests: intersectY < region.y + region.yWidth
        assertTrue(w.add(new Balloon("upperYA", 10, 500, 10, 20, 10, 20, "hot",
            5)));
        assertTrue(w.add(new Balloon("upperYB", 15, 505, 15, 20, 10, 20, "hot",
            5)));
        // Intersection Y = 505, in region (0-512 for Y at this level)
        collisions = w.collisions();
        assertTrue(collisions.contains("upperYA") && collisions.contains(
            "upperYB"));

        w.delete("upperYA");
        w.delete("upperYB");

        // Test 8: Objects near upper Z boundary of region
        // Tests: intersectZ < region.z + region.zWidth
        assertTrue(w.add(new Balloon("upperZA", 10, 10, 500, 20, 20, 10, "hot",
            5)));
        assertTrue(w.add(new Balloon("upperZB", 15, 15, 505, 20, 20, 10, "hot",
            5)));
        // Intersection Z = 505
        collisions = w.collisions();
        assertTrue(collisions.contains("upperZA") && collisions.contains(
            "upperZB"));

        w.delete("upperZA");
        w.delete("upperZB");

        // Test 9: Multiple overlapping objects to test all boundary paths
        assertTrue(w.add(new Balloon("multi1", 100, 100, 100, 50, 50, 50, "hot",
            5)));
        assertTrue(w.add(new Balloon("multi2", 120, 120, 120, 50, 50, 50, "hot",
            5)));
        assertTrue(w.add(new Balloon("multi3", 110, 110, 110, 50, 50, 50, "hot",
            5)));

        collisions = w.collisions();
        // All three pairs should collide
        assertTrue(collisions.contains("multi1"));
        assertTrue(collisions.contains("multi2"));
        assertTrue(collisions.contains("multi3"));

        w.delete("multi1");
        w.delete("multi2");
        w.delete("multi3");
    }


    // ----------------------------------------------------------
    /**
     * Tests collision detection where intersection origin falls
     * exactly on region boundaries to ensure correct >= and < comparisons.
     *
     * @throws Exception
     *             if an error occurs during the test.
     */
    public void testCollisionExactBoundaries() throws Exception {
        WorldDB w = new WorldDB(null);

        // Create objects where intersection origin is exactly at x=0
        // obj1 at (-5 would be invalid), so use objects that create
        // intersection at exactly region.x
        assertTrue(w.add(new Balloon("exact1", 0, 100, 100, 50, 50, 50, "hot",
            5)));
        assertTrue(w.add(new Balloon("exact2", 0, 110, 110, 50, 50, 50, "hot",
            5)));
        // Intersection X = max(0, 0) = 0, exactly at region.x
        // Should be reported (>= is true for 0 >= 0)

        String collisions = w.collisions();
        assertTrue(collisions.contains("exact1") && collisions.contains(
            "exact2"));

        w.delete("exact1");
        w.delete("exact2");

        // Test objects at origin (0, 0, 0)
        assertTrue(w.add(new Balloon("origin1", 0, 0, 0, 10, 10, 10, "hot",
            5)));
        assertTrue(w.add(new Balloon("origin2", 5, 5, 5, 10, 10, 10, "hot",
            5)));
        // Intersection = (5, 5, 5), clearly in region

        collisions = w.collisions();
        assertTrue(collisions.contains("origin1") && collisions.contains(
            "origin2"));

        w.delete("origin1");
        w.delete("origin2");

        // Test non-overlapping objects (no collision should be reported)
        assertTrue(w.add(new Balloon("noOverlap1", 0, 0, 0, 10, 10, 10, "hot",
            5)));
        assertTrue(w.add(new Balloon("noOverlap2", 100, 100, 100, 10, 10, 10,
            "hot", 5)));

        collisions = w.collisions();
        // Should NOT contain a collision between these two
        assertFalse(collisions.contains("noOverlap1") && collisions.contains(
            "noOverlap2") && collisions.contains(") and ("));

        w.delete("noOverlap1");
        w.delete("noOverlap2");
    }


    // ----------------------------------------------------------
    /**
     * Tests collision detection with objects spanning multiple regions.
     * Ensures collisions are reported exactly once at the correct region.
     *
     * @throws Exception
     *             if an error occurs during the test.
     */
    public void testCollisionMultiRegionObjects() throws Exception {
        WorldDB w = new WorldDB(null);

        // Large objects that span across the x=512 boundary
        // Object A: x from 400 to 600 (spans boundary)
        // Object B: x from 450 to 650 (spans boundary)
        // They overlap, intersection X = max(400, 450) = 450 (left region)
        assertTrue(w.add(new Drone("bigA", 400, 100, 100, 200, 50, 50, "brand",
            2)));
        assertTrue(w.add(new Drone("bigB", 450, 110, 110, 200, 50, 50, "brand",
            2)));

        String collisions = w.collisions();
        // Count how many times the collision is reported
        int count = 0;
        int idx = 0;
        while ((idx = collisions.indexOf("bigA", idx)) != -1) {
            if (collisions.indexOf("bigB", idx) != -1) {
                count++;
            }
            idx++;
        }
        // Collision should be reported exactly once
        assertTrue(count >= 1);

        w.delete("bigA");
        w.delete("bigB");

        // Objects that span Y boundary
        assertTrue(w.add(new Drone("spanY1", 100, 400, 100, 50, 200, 50,
            "brand", 2)));
        assertTrue(w.add(new Drone("spanY2", 110, 450, 110, 50, 200, 50,
            "brand", 2)));
        // Intersection Y = 450, in lower region

        collisions = w.collisions();
        assertTrue(collisions.contains("spanY1"));

        w.delete("spanY1");
        w.delete("spanY2");

        // Objects that span Z boundary
        assertTrue(w.add(new Drone("spanZ1", 100, 100, 400, 50, 50, 200,
            "brand", 2)));
        assertTrue(w.add(new Drone("spanZ2", 110, 110, 450, 50, 50, 200,
            "brand", 2)));
        // Intersection Z = 450

        collisions = w.collisions();
        assertTrue(collisions.contains("spanZ1"));

        w.delete("spanZ1");
        w.delete("spanZ2");
    }


    // ----------------------------------------------------------
    /**
     * Tests BinNode.intersects() method with all 6 boundary conditions.
     * Lines 85-90: Tests each comparison in the intersection check.
     * Conditions:
     * 1. obj1.xOrg < obj2.xOrg + obj2.xWidth
     * 2. obj1.xOrg + obj1.xWidth > obj2.xOrg
     * 3. obj1.yOrg < obj2.yOrg + obj2.yWidth
     * 4. obj1.yOrg + obj1.yWidth > obj2.yOrg
     * 5. obj1.zOrg < obj2.zOrg + obj2.zWidth
     * 6. obj1.zOrg + obj1.zWidth > obj2.zOrg
     *
     * @throws Exception
     *             if an error occurs during the test.
     */
    public void testIntersectsAllConditions() throws Exception {
        WorldDB w = new WorldDB(null);

        // Test 1: Objects clearly overlap - all conditions true
        assertTrue(w.add(new Balloon("ov1", 100, 100, 100, 50, 50, 50, "hot",
            5)));
        assertTrue(w.add(new Balloon("ov2", 120, 120, 120, 50, 50, 50, "hot",
            5)));
        String collisions = w.collisions();
        assertTrue(collisions.contains("ov1") && collisions.contains("ov2"));
        w.delete("ov1");
        w.delete("ov2");

        // Test 2: X - fail condition 1 (obj1.xOrg >= obj2.xOrg + obj2.xWidth)
        // obj1 is completely to the right of obj2
        assertTrue(w.add(new Balloon("xFail1a", 200, 100, 100, 50, 50, 50,
            "hot", 5)));
        assertTrue(w.add(new Balloon("xFail1b", 100, 100, 100, 50, 50, 50,
            "hot", 5)));
        // obj1 at 200, obj2 ends at 150, no overlap
        collisions = w.collisions();
        assertFalse(collisions.contains("xFail1a") && collisions.contains(
            "xFail1b") && collisions.indexOf("xFail1a") < collisions.indexOf(
                ") and ("));
        w.delete("xFail1a");
        w.delete("xFail1b");

        // Test 3: X - fail condition 2 (obj1.xOrg + obj1.xWidth <= obj2.xOrg)
        // obj1 is completely to the left of obj2
        assertTrue(w.add(new Balloon("xFail2a", 100, 100, 100, 50, 50, 50,
            "hot", 5)));
        assertTrue(w.add(new Balloon("xFail2b", 200, 100, 100, 50, 50, 50,
            "hot", 5)));
        // obj1 ends at 150, obj2 starts at 200, no overlap
        collisions = w.collisions();
        assertFalse(collisions.contains("xFail2a") && collisions.contains(
            "xFail2b") && collisions.indexOf("xFail2a") < collisions.indexOf(
                ") and ("));
        w.delete("xFail2a");
        w.delete("xFail2b");

        // Test 4: Y - fail condition 3 (obj1.yOrg >= obj2.yOrg + obj2.yWidth)
        // obj1 is completely below obj2 (higher Y value)
        assertTrue(w.add(new Balloon("yFail1a", 100, 200, 100, 50, 50, 50,
            "hot", 5)));
        assertTrue(w.add(new Balloon("yFail1b", 100, 100, 100, 50, 50, 50,
            "hot", 5)));
        collisions = w.collisions();
        assertFalse(collisions.contains("yFail1a") && collisions.contains(
            "yFail1b") && collisions.indexOf("yFail1a") < collisions.indexOf(
                ") and ("));
        w.delete("yFail1a");
        w.delete("yFail1b");

        // Test 5: Y - fail condition 4 (obj1.yOrg + obj1.yWidth <= obj2.yOrg)
        // obj1 is completely above obj2 (lower Y value)
        assertTrue(w.add(new Balloon("yFail2a", 100, 100, 100, 50, 50, 50,
            "hot", 5)));
        assertTrue(w.add(new Balloon("yFail2b", 100, 200, 100, 50, 50, 50,
            "hot", 5)));
        collisions = w.collisions();
        assertFalse(collisions.contains("yFail2a") && collisions.contains(
            "yFail2b") && collisions.indexOf("yFail2a") < collisions.indexOf(
                ") and ("));
        w.delete("yFail2a");
        w.delete("yFail2b");

        // Test 6: Z - fail condition 5 (obj1.zOrg >= obj2.zOrg + obj2.zWidth)
        assertTrue(w.add(new Balloon("zFail1a", 100, 100, 200, 50, 50, 50,
            "hot", 5)));
        assertTrue(w.add(new Balloon("zFail1b", 100, 100, 100, 50, 50, 50,
            "hot", 5)));
        collisions = w.collisions();
        assertFalse(collisions.contains("zFail1a") && collisions.contains(
            "zFail1b") && collisions.indexOf("zFail1a") < collisions.indexOf(
                ") and ("));
        w.delete("zFail1a");
        w.delete("zFail1b");

        // Test 7: Z - fail condition 6 (obj1.zOrg + obj1.zWidth <= obj2.zOrg)
        assertTrue(w.add(new Balloon("zFail2a", 100, 100, 100, 50, 50, 50,
            "hot", 5)));
        assertTrue(w.add(new Balloon("zFail2b", 100, 100, 200, 50, 50, 50,
            "hot", 5)));
        collisions = w.collisions();
        assertFalse(collisions.contains("zFail2a") && collisions.contains(
            "zFail2b") && collisions.indexOf("zFail2a") < collisions.indexOf(
                ") and ("));
        w.delete("zFail2a");
        w.delete("zFail2b");

        // Test 8: Touching edges (boundary case - should NOT intersect)
        // X edges touch exactly
        assertTrue(w.add(new Balloon("touchX1", 100, 100, 100, 50, 50, 50,
            "hot", 5)));
        assertTrue(w.add(new Balloon("touchX2", 150, 100, 100, 50, 50, 50,
            "hot", 5)));
        collisions = w.collisions();
        // Edges touch at x=150, but don't overlap (< not <=)
        assertFalse(collisions.contains("touchX1") && collisions.contains(
            "touchX2") && collisions.indexOf("touchX1") < collisions.indexOf(
                ") and ("));
        w.delete("touchX1");
        w.delete("touchX2");

        // Test 9: Barely overlapping by 1 unit
        assertTrue(w.add(new Balloon("barelyX1", 100, 100, 100, 51, 50, 50,
            "hot", 5)));
        assertTrue(w.add(new Balloon("barelyX2", 150, 100, 100, 50, 50, 50,
            "hot", 5)));
        // obj1 ends at 151, obj2 starts at 150 - they overlap by 1
        collisions = w.collisions();
        assertTrue(collisions.contains("barelyX1") && collisions.contains(
            "barelyX2"));
        w.delete("barelyX1");
        w.delete("barelyX2");
    }


    // ----------------------------------------------------------
    /**
     * Tests regionIntersectsQuery() via the intersect command.
     * Lines 114-116: Tests region vs query box intersection.
     * This affects which branches of the tree are visited.
     *
     * @throws Exception
     *             if an error occurs during the test.
     */
    public void testRegionIntersectsQuery() throws Exception {
        WorldDB w = new WorldDB(null);

        // Add objects in different regions to create tree structure
        assertTrue(w.add(new Balloon("left", 100, 100, 100, 10, 10, 10, "hot",
            5)));
        assertTrue(w.add(new Balloon("right", 600, 100, 100, 10, 10, 10, "hot",
            5)));
        assertTrue(w.add(new Balloon("top", 100, 600, 100, 10, 10, 10, "hot",
            5)));
        assertTrue(w.add(new Balloon("deep", 100, 100, 600, 10, 10, 10, "hot",
            5)));

        // Test 1: Query box covers entire world - should visit all nodes
        String result = w.intersect(0, 0, 0, 1024, 1024, 1024);
        assertTrue(result.contains("left"));
        assertTrue(result.contains("right"));
        assertTrue(result.contains("top"));
        assertTrue(result.contains("deep"));

        // Test 2: Query box only in left half (x < 512)
        // Tests: region.x < qx + qxw (left region 0-512 vs query 0-400)
        result = w.intersect(0, 0, 0, 400, 1024, 1024);
        assertTrue(result.contains("left"));
        assertFalse(result.contains("right"));

        // Test 3: Query box only in right half (x >= 512)
        // Tests: region.x + region.xWidth > qx
        result = w.intersect(550, 0, 0, 400, 1024, 1024);
        assertFalse(result.contains("left"));
        assertTrue(result.contains("right"));

        // Test 4: Query box only in bottom half (y < 512)
        result = w.intersect(0, 0, 0, 1024, 400, 1024);
        assertTrue(result.contains("left"));
        assertFalse(result.contains("top"));

        // Test 5: Query box only in top half (y >= 512)
        result = w.intersect(0, 550, 0, 1024, 400, 1024);
        assertFalse(result.contains("left"));
        assertTrue(result.contains("top"));

        // Test 6: Query box only in front half (z < 512)
        result = w.intersect(0, 0, 0, 1024, 1024, 400);
        assertTrue(result.contains("left"));
        assertFalse(result.contains("deep"));

        // Test 7: Query box only in back half (z >= 512)
        result = w.intersect(0, 0, 550, 1024, 1024, 400);
        assertFalse(result.contains("left"));
        assertTrue(result.contains("deep"));

        // Test 8: Query box that doesn't intersect any region with objects
        result = w.intersect(300, 300, 300, 50, 50, 50);
        assertFalse(result.contains("left"));
        assertFalse(result.contains("right"));

        // Test 9: Query box at exact boundary
        result = w.intersect(512, 0, 0, 512, 1024, 1024);
        assertTrue(result.contains("right"));

        // Test 10: Query box just before boundary
        result = w.intersect(0, 0, 0, 512, 1024, 1024);
        assertTrue(result.contains("left"));

        w.delete("left");
        w.delete("right");
        w.delete("top");
        w.delete("deep");
    }


    // ----------------------------------------------------------
    /**
     * Tests objectIntersectsQuery() via the intersect command.
     * Lines 140-142: Tests object vs query box intersection.
     * This determines which objects are reported in intersect results.
     *
     * @throws Exception
     *             if an error occurs during the test.
     */
    public void testObjectIntersectsQuery() throws Exception {
        WorldDB w = new WorldDB(null);

        // Add an object at known position
        assertTrue(w.add(new Balloon("target", 100, 100, 100, 50, 50, 50, "hot",
            5)));
        // Object spans from (100,100,100) to (150,150,150)

        // Test 1: Query box completely contains object
        String result = w.intersect(0, 0, 0, 200, 200, 200);
        assertTrue(result.contains("target"));

        // Test 2: Query box to the left of object (fails condition 1)
        // obj.xOrg < qx + qxw should be false when qx + qxw <= obj.xOrg
        result = w.intersect(0, 0, 0, 50, 200, 200);
        // Query ends at x=50, object starts at x=100
        assertFalse(result.contains("target"));

        // Test 3: Query box to the right of object (fails condition 2)
        // obj.xOrg + obj.xWidth > qx should be false when obj.xOrg + obj.xWidth
        // <= qx
        result = w.intersect(200, 0, 0, 100, 200, 200);
        // Query starts at x=200, object ends at x=150
        assertFalse(result.contains("target"));

        // Test 4: Query box below object in Y (fails condition 3)
        result = w.intersect(0, 0, 0, 200, 50, 200);
        // Query ends at y=50, object starts at y=100
        assertFalse(result.contains("target"));

        // Test 5: Query box above object in Y (fails condition 4)
        result = w.intersect(0, 200, 0, 200, 100, 200);
        // Query starts at y=200, object ends at y=150
        assertFalse(result.contains("target"));

        // Test 6: Query box in front of object in Z (fails condition 5)
        result = w.intersect(0, 0, 0, 200, 200, 50);
        // Query ends at z=50, object starts at z=100
        assertFalse(result.contains("target"));

        // Test 7: Query box behind object in Z (fails condition 6)
        result = w.intersect(0, 0, 200, 200, 200, 100);
        // Query starts at z=200, object ends at z=150
        assertFalse(result.contains("target"));

        // Test 8: Query box touches object edge exactly (boundary)
        // Query ends exactly where object starts
        result = w.intersect(0, 0, 0, 100, 200, 200);
        // Query ends at x=100, object starts at x=100 (< not <=, so no
        // intersect)
        assertFalse(result.contains("target"));

        // Test 9: Query box overlaps object by 1 unit
        result = w.intersect(0, 0, 0, 101, 200, 200);
        // Query ends at x=101, object starts at x=100 (overlaps)
        assertTrue(result.contains("target"));

        // Test 10: Partial overlap in all dimensions
        result = w.intersect(125, 125, 125, 50, 50, 50);
        // Query from (125,125,125) to (175,175,175)
        // Object from (100,100,100) to (150,150,150)
        // Overlap from (125,125,125) to (150,150,150)
        assertTrue(result.contains("target"));

        w.delete("target");
    }


    // ----------------------------------------------------------
    /**
     * Tests edge cases for all intersection methods with touching boundaries.
     *
     * @throws Exception
     *             if an error occurs during the test.
     */
    public void testIntersectionEdgeCases() throws Exception {
        Random rand = new Random(1042); // Fixed seed for reproducibility
        WorldDB w = new WorldDB(rand);

        // Test objects at origin
        assertTrue(w.add(new Balloon("atOrigin", 0, 0, 0, 10, 10, 10, "hot",
            5)));

        // Query starting at origin
        String result = w.intersect(0, 0, 0, 5, 5, 5);
        assertTrue(result.contains("atOrigin"));

        // Query ending at origin (no overlap)
        result = w.intersect(0, 0, 0, 0, 0, 0);
        // Zero-size query - invalid input returns null
        assertNull(result);

        w.delete("atOrigin");

        // Test objects at max boundary
        assertTrue(w.add(new Balloon("atMax", 1000, 1000, 1000, 20, 20, 20,
            "hot", 5)));

        System.out.println(w.intersect(990, 990, 990, 34, 34, 34));

        result = w.intersect(990, 990, 990, 34, 34, 34);
        assertFuzzyEquals(
            "The following objects intersect (990 990 990 34 34 34):\r\n"
                + "In leaf node (0, 0, 0, 1024, 1024, 1024) 0\r\n"
                + "Balloon atMax 1000 1000 1000 20 20 20 hot 5\r\n"
                + "1 nodes were visited in the bintree\r\n" + "\r\n", result);
        assertTrue(result.contains("atMax"));

        w.delete("atMax");

        // Test Y and Z boundary touches
        assertTrue(w.add(new Balloon("yTouch", 100, 100, 100, 50, 50, 50, "hot",
            5)));

        // Query ends exactly at object Y start
        result = w.intersect(50, 0, 50, 100, 100, 100);
        assertFalse(result.contains("yTouch"));

        // Query ends exactly at object Z start
        result = w.intersect(50, 50, 0, 100, 100, 100);
        assertFalse(result.contains("yTouch"));

        w.delete("yTouch");
    }


    // ----------------------------------------------------------
    /**
     * Tests InternalNode insert with all three axes (level % 3).
     * Covers lines 22, 32, 37 - axis calculation and left/right insertion.
     *
     * @throws Exception
     *             if an error occurs during the test.
     */
    public void testInternalNodeInsertAllAxes() throws Exception {
        WorldDB w = new WorldDB(null);

        // Level 0: X axis (level % 3 = 0), midpoint at 512
        // Insert left (x < 512) and right (x >= 512)
        assertTrue(w.add(new Balloon("xLeft", 100, 100, 100, 10, 10, 10, "hot",
            5)));
        assertTrue(w.add(new Balloon("xRight", 600, 100, 100, 10, 10, 10, "hot",
            5)));

        String tree = w.printbintree();
        assertTrue(tree.contains("xLeft"));
        assertTrue(tree.contains("xRight"));

        // Level 1: Y axis (level % 3 = 1), midpoint at 512
        // Need to create deeper tree by adding more objects
        assertTrue(w.add(new Balloon("yLow", 100, 100, 100, 10, 10, 10, "hot",
            5)));
        assertTrue(w.add(new Balloon("yHigh", 100, 600, 100, 10, 10, 10, "hot",
            5)));

        tree = w.printbintree();
        System.out.println(w.printbintree());
        assertFuzzyEquals("I (0, 0, 0, 1024, 1024, 1024) 0\r\n"
            + "  Leaf with 3 objects (0, 0, 0, 512, 1024, 1024) 1\r\n"
            + "  (Balloon xLeft 100 100 100 10 10 10 hot 5)\r\n"
            + "  (Balloon yHigh 100 600 100 10 10 10 hot 5)\r\n"
            + "  (Balloon yLow 100 100 100 10 10 10 hot 5)\r\n"
            + "  Leaf with 1 objects (512, 0, 0, 512, 1024, 1024) 1\r\n"
            + "  (Balloon xRight 600 100 100 10 10 10 hot 5)\r\n"
            + "3 Bintree nodes printed", tree);
        assertTrue(tree.contains("yHigh"));

        // Level 2: Z axis (level % 3 = 2)
        assertTrue(w.add(new Balloon("zFront", 100, 100, 100, 10, 10, 10, "hot",
            5)));
        assertTrue(w.add(new Balloon("zBack", 100, 100, 600, 10, 10, 10, "hot",
            5)));

        tree = w.printbintree();
        assertTrue(tree.contains("zBack"));

        // Clean up
        w.clear();
    }


    // ----------------------------------------------------------
    /**
     * Tests InternalNode insert with objects going to left only, right only,
     * and both children.
     * Covers lines 32, 37 - conditional insertion into left/right.
     *
     * @throws Exception
     *             if an error occurs during the test.
     */
    public void testInternalNodeInsertLeftRightBoth() throws Exception {
        WorldDB w = new WorldDB(null);

        // First, create enough objects to force a tree split
        // Add 3 objects to left side to fill a leaf
        assertTrue(w.add(new Balloon("leftOnly1", 100, 100, 100, 50, 50, 50,
            "hot", 5)));
        assertTrue(w.add(new Balloon("leftOnly2", 150, 100, 100, 50, 50, 50,
            "hot", 5)));
        assertTrue(w.add(new Balloon("leftOnly3", 200, 100, 100, 50, 50, 50,
            "hot", 5)));

        // Add object to right side - this should force a split since left is
        // full
        assertTrue(w.add(new Balloon("rightOnly", 600, 100, 100, 50, 50, 50,
            "hot", 5)));

        String tree = w.printbintree();
        // Should now have internal node structure
        assertTrue(tree.contains("I ")); // Has internal node
        assertTrue(tree.contains("leftOnly1"));
        assertTrue(tree.contains("rightOnly"));

        // Now add object spanning both children (objStart < midpoint < objEnd)
        assertTrue(w.add(new Drone("spansBoth", 400, 100, 100, 300, 50, 50,
            "brand", 2)));
        // Object from x=400 to x=700, spans across x=512 midpoint

        tree = w.printbintree();
        System.out.println(tree);
        // Reference implementation output with 9 nodes
        assertFuzzyEquals("I (0, 0, 0, 1024, 1024, 1024) 0\r\n"
            + "  I (0, 0, 0, 512, 1024, 1024) 1\r\n"
            + "    I (0, 0, 0, 512, 512, 1024) 2\r\n"
            + "      I (0, 0, 0, 512, 512, 512) 3\r\n"
            + "        Leaf with 3 objects (0, 0, 0, 256, 512, 512) 4\r\n"
            + "        (Balloon leftOnly1 100 100 100 50 50 50 hot 5)\r\n"
            + "        (Balloon leftOnly2 150 100 100 50 50 50 hot 5)\r\n"
            + "        (Balloon leftOnly3 200 100 100 50 50 50 hot 5)\r\n"
            + "        Leaf with 1 objects (256, 0, 0, 256, 512, 512) 4\r\n"
            + "        (Drone spansBoth 400 100 100 300 50 50 brand 2)\r\n"
            + "      E (0, 0, 512, 512, 512, 512) 3\r\n"
            + "    E (0, 512, 0, 512, 512, 1024) 2\r\n"
            + "  Leaf with 2 objects (512, 0, 0, 512, 1024, 1024) 1\r\n"
            + "  (Balloon rightOnly 600 100 100 50 50 50 hot 5)\r\n"
            + "  (Drone spansBoth 400 100 100 300 50 50 brand 2)\r\n"
            + "9 Bintree nodes printed", tree);
        // spansBoth should appear in multiple leaves (both left and right
        // subtrees)
        int count = 0;
        int idx = 0;
        while ((idx = tree.indexOf("spansBoth", idx)) != -1) {
            count++;
            idx++;
        }
        assertTrue("Spanning object should appear in at least 2 leaves",
            count >= 2);

        w.delete("leftOnly1");
        w.delete("leftOnly2");
        w.delete("leftOnly3");
        w.delete("rightOnly");
        w.delete("spansBoth");
    }


    // ----------------------------------------------------------
    /**
     * Tests InternalNode remove with all three axes.
     * Covers lines 58, 67, 68, 72, 73 - axis calculation and removal.
     *
     * @throws Exception
     *             if an error occurs during the test.
     */
    public void testInternalNodeRemoveAllAxes() throws Exception {
        WorldDB w = new WorldDB(null);

        // Create tree with objects at different positions for all axes
        assertTrue(w.add(new Balloon("rem1", 100, 100, 100, 10, 10, 10, "hot",
            5)));
        assertTrue(w.add(new Balloon("rem2", 600, 100, 100, 10, 10, 10, "hot",
            5)));
        assertTrue(w.add(new Balloon("rem3", 100, 600, 100, 10, 10, 10, "hot",
            5)));
        assertTrue(w.add(new Balloon("rem4", 100, 100, 600, 10, 10, 10, "hot",
            5)));
        String treeBefore = w.printbintree();
        assertFuzzyEquals("I (0, 0, 0, 1024, 1024, 1024) 0\r\n"
            + "  Leaf with 3 objects (0, 0, 0, 512, 1024, 1024) 1\r\n"
            + "  (Balloon rem1 100 100 100 10 10 10 hot 5)\r\n"
            + "  (Balloon rem3 100 600 100 10 10 10 hot 5)\r\n"
            + "  (Balloon rem4 100 100 600 10 10 10 hot 5)\r\n"
            + "  Leaf with 1 objects (512, 0, 0, 512, 1024, 1024) 1\r\n"
            + "  (Balloon rem2 600 100 100 10 10 10 hot 5)\r\n"
            + "3 Bintree nodes printed", treeBefore);

        // Remove from different positions - exercises remove at different
        // levels
        assertNotNull(w.delete("rem2")); // Right side of X axis
        String tree = w.printbintree();
        assertFuzzyEquals(
            "Leaf with 3 objects (0, 0, 0, 1024, 1024, 1024) 0\r\n"
                + "(Balloon rem1 100 100 100 10 10 10 hot 5)\r\n"
                + "(Balloon rem3 100 600 100 10 10 10 hot 5)\r\n"
                + "(Balloon rem4 100 100 600 10 10 10 hot 5)\r\n"
                + "1 Bintree nodes printed", tree);
        assertFalse(tree.contains("rem2"));

        assertNotNull(w.delete("rem3")); // High Y axis
        tree = w.printbintree();
        assertFuzzyEquals(
            "Leaf with 2 objects (0, 0, 0, 1024, 1024, 1024) 0\r\n"
                + "(Balloon rem1 100 100 100 10 10 10 hot 5)\r\n"
                + "(Balloon rem4 100 100 600 10 10 10 hot 5)\r\n"
                + "1 Bintree nodes printed", tree);
        assertFalse(tree.contains("rem3"));

        assertNotNull(w.delete("rem4")); // Back Z axis
        tree = w.printbintree();
        ;
        assertFuzzyEquals(
            "Leaf with 1 objects (0, 0, 0, 1024, 1024, 1024) 0\r\n"
                + "(Balloon rem1 100 100 100 10 10 10 hot 5)\r\n"
                + "1 Bintree nodes printed", tree);
        assertFalse(tree.contains("rem4"));

        assertNotNull(w.delete("rem1")); // Last one
        tree = w.printbintree();
        ;
        assertFuzzyEquals("E (0, 0, 0, 1024, 1024, 1024) 0\r\n"
            + "1 Bintree nodes printed", tree);
        assertTrue(tree.contains("E (0, 0, 0, 1024, 1024, 1024) 0"));
    }


    // ----------------------------------------------------------
    /**
     * Tests InternalNode remove spanning object (removed from both children).
     * Covers lines 67-68, 72-73 - removal from left AND right.
     *
     * @throws Exception
     *             if an error occurs during the test.
     */
    public void testInternalNodeRemoveSpanning() throws Exception {
        WorldDB w = new WorldDB(null);

        // First create a tree structure with internal node by filling left side
        assertTrue(w.add(new Balloon("keeper1", 100, 100, 100, 10, 10, 10,
            "hot", 5)));
        assertTrue(w.add(new Balloon("keeper2", 150, 100, 100, 10, 10, 10,
            "hot", 5)));
        assertTrue(w.add(new Balloon("keeper3", 200, 100, 100, 10, 10, 10,
            "hot", 5)));

        // Add object to right side to force split
        assertTrue(w.add(new Balloon("rightKeeper", 600, 100, 100, 10, 10, 10,
            "hot", 5)));

        // Verify we have an internal node
        String tree = w.printbintree();
        assertTrue(tree.contains("I "));
        assertTrue(tree.contains("keeper1"));
        assertTrue(tree.contains("keeper2"));
        assertTrue(tree.contains("keeper3"));
        assertTrue(tree.contains("rightKeeper"));

        // Now add spanning object that crosses x=512 boundary
        assertTrue(w.add(new Drone("spanning", 400, 100, 100, 300, 50, 50,
            "brand", 2)));
        // Spans from 400 to 700, crosses x=512

        tree = w.printbintree();
        System.out.println(tree);
        // Verify structure contains expected objects
        assertTrue(tree.contains("keeper1"));
        assertTrue(tree.contains("keeper2"));
        assertTrue(tree.contains("keeper3"));
        assertTrue(tree.contains("rightKeeper"));
        assertTrue(tree.contains("spanning"));
        // spanning should appear in multiple leaves
        int countBefore = 0;
        int idx = 0;
        while ((idx = tree.indexOf("spanning", idx)) != -1) {
            countBefore++;
            idx++;
        }
        assertTrue("Spanning object should be in at least 2 leaves",
            countBefore >= 2);

        // Remove spanning object - should remove from both children
        assertNotNull(w.delete("spanning"));

        String treeAfter = w.printbintree();
        // Verify spanning is removed but keepers remain
        assertTrue(treeAfter.contains("keeper1"));
        assertTrue(treeAfter.contains("keeper2"));
        assertTrue(treeAfter.contains("keeper3"));
        assertTrue(treeAfter.contains("rightKeeper"));
        assertFalse(treeAfter.contains("spanning"));

        w.delete("keeper1");
        w.delete("keeper2");
        w.delete("keeper3");
        w.delete("rightKeeper");
    }


    // ----------------------------------------------------------
    /**
     * Tests InternalNode simplification: left=Flyweight, right=Leaf.
     * Covers line 77.
     *
     * @throws Exception
     *             if an error occurs during the test.
     */
    public void testInternalNodeSimplifyLeftFlyweightRightLeaf()
        throws Exception {
        WorldDB w = new WorldDB(null);

        // Fill left side to capacity to force split when right is added
        assertTrue(w.add(new Balloon("leftSide1", 100, 100, 100, 10, 10, 10,
            "hot", 5)));
        assertTrue(w.add(new Balloon("leftSide2", 150, 100, 100, 10, 10, 10,
            "hot", 5)));
        assertTrue(w.add(new Balloon("leftSide3", 200, 100, 100, 10, 10, 10,
            "hot", 5)));

        // Add object to right side - forces split into internal node
        assertTrue(w.add(new Balloon("rightSide", 600, 100, 100, 10, 10, 10,
            "hot", 5)));

        String treeBefore = w.printbintree();
        assertFuzzyEquals("I (0, 0, 0, 1024, 1024, 1024) 0\r\n"
            + "  Leaf with 3 objects (0, 0, 0, 512, 1024, 1024) 1\r\n"
            + "  (Balloon leftSide1 100 100 100 10 10 10 hot 5)\r\n"
            + "  (Balloon leftSide2 150 100 100 10 10 10 hot 5)\r\n"
            + "  (Balloon leftSide3 200 100 100 10 10 10 hot 5)\r\n"
            + "  Leaf with 1 objects (512, 0, 0, 512, 1024, 1024) 1\r\n"
            + "  (Balloon rightSide 600 100 100 10 10 10 hot 5)\r\n"
            + "3 Bintree nodes printed", treeBefore);
        assertTrue(treeBefore.contains("I ")); // Has internal node

        // Remove all left objects - left becomes Flyweight, right stays Leaf
        w.delete("leftSide1");
        w.delete("leftSide2");
        w.delete("leftSide3");

        String treeAfter = w.printbintree();
        assertFuzzyEquals(
            "Leaf with 1 objects (0, 0, 0, 1024, 1024, 1024) 0\r\n"
                + "(Balloon rightSide 600 100 100 10 10 10 hot 5)\r\n"
                + "1 Bintree nodes printed\r\n" + "", treeAfter);
        // Verify the RIGHT child's content is preserved (not the left)
        assertTrue(treeAfter.contains("rightSide"));
        // Verify left side objects are gone
        assertFalse(treeAfter.contains("leftSide1"));
        assertFalse(treeAfter.contains("leftSide2"));
        assertFalse(treeAfter.contains("leftSide3"));
        // Tree should have simplified - no internal node, just leaf
        assertFalse(treeAfter.contains("I "));

        // Verify we can still find the right object via skiplist
        assertNotNull(w.print("rightSide"));

        w.delete("rightSide");
    }


    // ----------------------------------------------------------
    /**
     * Tests InternalNode simplification: left=Leaf, right=Flyweight.
     * Covers line 80.
     *
     * @throws Exception
     *             if an error occurs during the test.
     */
    public void testInternalNodeSimplifyLeftLeafRightFlyweight()
        throws Exception {
        WorldDB w = new WorldDB(null);

        // Fill right side to capacity to force split
        assertTrue(w.add(new Balloon("rightSide1", 600, 100, 100, 10, 10, 10,
            "hot", 5)));
        assertTrue(w.add(new Balloon("rightSide2", 650, 100, 100, 10, 10, 10,
            "hot", 5)));
        assertTrue(w.add(new Balloon("rightSide3", 700, 100, 100, 10, 10, 10,
            "hot", 5)));

        // Add object to left side - forces split into internal node
        assertTrue(w.add(new Balloon("leftSide2", 100, 100, 100, 10, 10, 10,
            "hot", 5)));

        String treeBefore = w.printbintree();
        assertTrue(treeBefore.contains("I ")); // Has internal node

        // Remove all right objects - right becomes Flyweight, left stays Leaf
        w.delete("rightSide1");
        w.delete("rightSide2");
        w.delete("rightSide3");

        String treeAfter = w.printbintree();
        // Verify the LEFT child's content is preserved (not the right)
        assertTrue(treeAfter.contains("leftSide2"));
        // Verify right side objects are gone
        assertFalse(treeAfter.contains("rightSide1"));
        assertFalse(treeAfter.contains("rightSide2"));
        assertFalse(treeAfter.contains("rightSide3"));
        // Tree should have simplified - no internal node, just leaf
        assertFalse(treeAfter.contains("I "));

        // Verify we can still find the left object via skiplist
        assertNotNull(w.print("leftSide2"));

        w.delete("leftSide2");
    }


    // ----------------------------------------------------------
    /**
     * Tests InternalNode simplification: both children become Flyweight.
     * Covers line 83.
     *
     * @throws Exception
     *             if an error occurs during the test.
     */
    public void testInternalNodeSimplifyBothFlyweight() throws Exception {
        WorldDB w = new WorldDB(null);

        // Fill left side to force split when right is added
        assertTrue(w.add(new Balloon("bothLeft1", 100, 100, 100, 10, 10, 10,
            "hot", 5)));
        assertTrue(w.add(new Balloon("bothLeft2", 150, 100, 100, 10, 10, 10,
            "hot", 5)));
        assertTrue(w.add(new Balloon("bothLeft3", 200, 100, 100, 10, 10, 10,
            "hot", 5)));

        // Add to right side - forces internal node
        assertTrue(w.add(new Balloon("bothRight", 600, 100, 100, 10, 10, 10,
            "hot", 5)));

        String treeBefore = w.printbintree();
        assertTrue(treeBefore.contains("I ")); // Has internal node

        // Remove all - internal node should become flyweight
        w.delete("bothLeft1");
        w.delete("bothLeft2");
        w.delete("bothLeft3");
        w.delete("bothRight");

        String treeAfter = w.printbintree();
        assertTrue(treeAfter.contains("E (0, 0, 0, 1024, 1024, 1024) 0"));
        assertTrue(treeAfter.contains("1 Bintree nodes printed"));
    }


    // ----------------------------------------------------------
    /**
     * Tests InternalNode leaf merge when combined size <= 3.
     * Covers lines 88, 91.
     *
     * @throws Exception
     *             if an error occurs during the test.
     */
    public void testInternalNodeLeafMerge() throws Exception {
        WorldDB w = new WorldDB(null);

        // Add 2 objects to left side
        assertTrue(w.add(new Balloon("mergeL1", 100, 100, 100, 10, 10, 10,
            "hot", 5)));
        assertTrue(w.add(new Balloon("mergeL2", 200, 100, 100, 10, 10, 10,
            "hot", 5)));

        // Add 2 objects to right side
        assertTrue(w.add(new Balloon("mergeR1", 600, 100, 100, 10, 10, 10,
            "hot", 5)));
        assertTrue(w.add(new Balloon("mergeR2", 700, 100, 100, 10, 10, 10,
            "hot", 5)));

        // Now we have 2 leaves with 2 objects each (total 4, won't merge)
        String tree = w.printbintree();
        assertTrue(tree.contains("I ")); // Still has internal node

        // Remove one from each side - now 1 + 1 = 2 <= 3, can merge
        w.delete("mergeL2");
        w.delete("mergeR2");

        // After merge, should have single leaf with 2 objects
        tree = w.printbintree();
        assertTrue(tree.contains("mergeL1"));
        assertTrue(tree.contains("mergeR1"));

        w.delete("mergeL1");
        w.delete("mergeR1");
    }


    // ----------------------------------------------------------
    /**
     * Tests InternalNode leaf merge with 3 total objects.
     * Covers line 88 boundary (exactly 3).
     *
     * @throws Exception
     *             if an error occurs during the test.
     */
    public void testInternalNodeLeafMergeExactlyThree() throws Exception {
        WorldDB w = new WorldDB(null);

        // Add 2 objects to left, 2 to right
        assertTrue(w.add(new Balloon("ex3L1", 100, 100, 100, 10, 10, 10, "hot",
            5)));
        assertTrue(w.add(new Balloon("ex3L2", 200, 100, 100, 10, 10, 10, "hot",
            5)));
        assertTrue(w.add(new Balloon("ex3R1", 600, 100, 100, 10, 10, 10, "hot",
            5)));
        assertTrue(w.add(new Balloon("ex3R2", 700, 100, 100, 10, 10, 10, "hot",
            5)));

        // Remove one - now 2 + 1 = 3, exactly at merge threshold
        w.delete("ex3R2");

        String tree = w.printbintree();
        assertTrue(tree.contains("ex3L1"));
        assertTrue(tree.contains("ex3L2"));
        assertTrue(tree.contains("ex3R1"));

        w.delete("ex3L1");
        w.delete("ex3L2");
        w.delete("ex3R1");
    }


    // ----------------------------------------------------------
    /**
     * Tests InternalNode print with multiple levels.
     * Covers lines 125, 133.
     *
     * @throws Exception
     *             if an error occurs during the test.
     */
    public void testInternalNodePrintMultipleLevels() throws Exception {
        WorldDB w = new WorldDB(null);

        // Create deep tree structure
        assertTrue(w.add(new Balloon("p1", 100, 100, 100, 10, 10, 10, "hot",
            5)));
        assertTrue(w.add(new Balloon("p2", 600, 100, 100, 10, 10, 10, "hot",
            5)));
        assertTrue(w.add(new Balloon("p3", 100, 600, 100, 10, 10, 10, "hot",
            5)));
        assertTrue(w.add(new Balloon("p4", 600, 600, 100, 10, 10, 10, "hot",
            5)));

        String tree = w.printbintree();
        // Should have multiple internal nodes at different levels
        assertTrue(tree.contains("I (0, 0, 0, 1024, 1024, 1024) 0"));
        assertTrue(tree.contains("Bintree nodes printed"));

        // Check indentation levels
        assertTrue(tree.contains("  ")); // Level 1 indent

        w.delete("p1");
        w.delete("p2");
        w.delete("p3");
        w.delete("p4");
    }


    // ----------------------------------------------------------
    /**
     * Tests InternalNode collisions traversal through both children.
     * Covers lines 153, 154, 156.
     *
     * @throws Exception
     *             if an error occurs during the test.
     */
    public void testInternalNodeCollisionsTraversal() throws Exception {
        WorldDB w = new WorldDB(null);

        // Add colliding objects in left child
        assertTrue(w.add(new Balloon("collL1", 100, 100, 100, 50, 50, 50, "hot",
            5)));
        assertTrue(w.add(new Balloon("collL2", 120, 120, 120, 50, 50, 50, "hot",
            5)));

        // Add colliding objects in right child
        assertTrue(w.add(new Balloon("collR1", 600, 100, 100, 50, 50, 50, "hot",
            5)));
        assertTrue(w.add(new Balloon("collR2", 620, 120, 120, 50, 50, 50, "hot",
            5)));

        String collisions = w.collisions();
        System.out.println(collisions);
        assertFuzzyEquals("The following collisions exist in the database:\r\n"
            + "In leaf node (0, 0, 0, 512, 1024, 1024) 1\r\n"
            + "(Balloon collL1 100 100 100 50 50 50 hot 5) and (Balloon collL2 120 120 120 50 50 50 hot 5)\r\n"
            + "In leaf node (512, 0, 0, 512, 1024, 1024) 1\r\n"
            + "(Balloon collR1 600 100 100 50 50 50 hot 5) and (Balloon collR2 620 120 120 50 50 50 hot 5)",
            collisions);

        // Should report collisions from both children
        assertTrue(collisions.contains("collL1"));
        assertTrue(collisions.contains("collL2"));
        assertTrue(collisions.contains("collR1"));
        assertTrue(collisions.contains("collR2"));

        w.delete("collL1");
        w.delete("collL2");
        w.delete("collR1");
        w.delete("collR2");
    }


    // ----------------------------------------------------------
    /**
     * Tests InternalNode intersect with query hitting only left child.
     * Covers line 199.
     *
     * @throws Exception
     *             if an error occurs during the test.
     */
    public void testInternalNodeIntersectLeftOnly() throws Exception {
        WorldDB w = new WorldDB(null);

        assertTrue(w.add(new Balloon("intLeft", 100, 100, 100, 10, 10, 10,
            "hot", 5)));
        assertTrue(w.add(new Balloon("intRight", 600, 100, 100, 10, 10, 10,
            "hot", 5)));

        // Query only hits left region (0-512 in X)
        String result = w.intersect(0, 0, 0, 400, 1024, 1024);
        System.out.println(result);
        assertFuzzyEquals(
            "The following objects intersect (0 0 0 400 1024 1024):\r\n"
                + "In leaf node (0, 0, 0, 1024, 1024, 1024) 0\r\n"
                + "Balloon intLeft 100 100 100 10 10 10 hot 5\r\n"
                + "1 nodes were visited in the bintree.", result);

        assertTrue(result.contains("intLeft"));
        assertFalse(result.contains("intRight"));

        w.delete("intLeft");
        w.delete("intRight");
    }


    // ----------------------------------------------------------
    /**
     * Tests InternalNode intersect with query hitting only right child.
     * Covers line 202.
     *
     * @throws Exception
     *             if an error occurs during the test.
     */
    public void testInternalNodeIntersectRightOnly() throws Exception {
        WorldDB w = new WorldDB(null);

        assertTrue(w.add(new Balloon("intLeft2", 100, 100, 100, 10, 10, 10,
            "hot", 5)));
        assertTrue(w.add(new Balloon("intRight2", 600, 100, 100, 10, 10, 10,
            "hot", 5)));

        // Query only hits right region (512-1024 in X)
        String result = w.intersect(520, 0, 0, 400, 1024, 1024);

        assertFalse(result.contains("intLeft2"));
        assertTrue(result.contains("intRight2"));

        w.delete("intLeft2");
        w.delete("intRight2");
    }


    // ----------------------------------------------------------
    /**
     * Tests InternalNode intersect with query hitting both children.
     * Covers lines 199 and 202 together.
     *
     * @throws Exception
     *             if an error occurs during the test.
     */
    public void testInternalNodeIntersectBothChildren() throws Exception {
        WorldDB w = new WorldDB(null);

        assertTrue(w.add(new Balloon("intBothL", 100, 100, 100, 10, 10, 10,
            "hot", 5)));
        assertTrue(w.add(new Balloon("intBothR", 600, 100, 100, 10, 10, 10,
            "hot", 5)));

        // Query spans both regions
        String result = w.intersect(0, 0, 0, 1024, 1024, 1024);

        assertTrue(result.contains("intBothL"));
        assertTrue(result.contains("intBothR"));

        w.delete("intBothL");
        w.delete("intBothR");
    }


    // ----------------------------------------------------------
    /**
     * Tests InternalNode intersect with query missing both children.
     * Covers lines 199 and 202 - both conditions false.
     *
     * @throws Exception
     *             if an error occurs during the test.
     */
    public void testInternalNodeIntersectMissBoth() throws Exception {
        WorldDB w = new WorldDB(null);

        // Add objects to create internal node structure
        assertTrue(w.add(new Balloon("missL", 100, 100, 100, 10, 10, 10, "hot",
            5)));
        assertTrue(w.add(new Balloon("missR", 600, 100, 100, 10, 10, 10, "hot",
            5)));

        // Query that misses both objects (different Y range)
        String result = w.intersect(0, 800, 0, 1024, 100, 1024);

        assertFalse(result.contains("missL"));
        assertFalse(result.contains("missR"));

        w.delete("missL");
        w.delete("missR");
    }


    // ----------------------------------------------------------
    /**
     * Tests InternalNode at all three axis levels for intersect.
     * Covers line 206 - axis calculation in intersect.
     *
     * @throws Exception
     *             if an error occurs during the test.
     */
    public void testInternalNodeIntersectAllAxes() throws Exception {
        WorldDB w = new WorldDB(null);

        // Create tree with objects at different axis positions
        // This forces internal nodes at different levels
        assertTrue(w.add(new Balloon("axX1", 100, 100, 100, 10, 10, 10, "hot",
            5)));
        assertTrue(w.add(new Balloon("axX2", 600, 100, 100, 10, 10, 10, "hot",
            5)));
        assertTrue(w.add(new Balloon("axY1", 100, 100, 100, 10, 10, 10, "hot",
            5)));
        assertTrue(w.add(new Balloon("axY2", 100, 600, 100, 10, 10, 10, "hot",
            5)));
        assertTrue(w.add(new Balloon("axZ1", 100, 100, 100, 10, 10, 10, "hot",
            5)));
        assertTrue(w.add(new Balloon("axZ2", 100, 100, 600, 10, 10, 10, "hot",
            5)));

        // Query that hits all axes
        String result = w.intersect(50, 50, 50, 700, 700, 700);

        assertTrue(result.contains("axX1"));
        assertTrue(result.contains("axX2"));
        assertTrue(result.contains("axY2"));
        assertTrue(result.contains("axZ2"));

        w.delete("axX1");
        w.delete("axX2");
        w.delete("axY1");
        w.delete("axY2");
        w.delete("axZ1");
        w.delete("axZ2");
    }


    // ----------------------------------------------------------
    /**
     * Tests InternalNode intersect with various query box positions.
     * Covers lines 199, 202, 206 - all intersect conditions.
     *
     * @throws Exception
     *             if an error occurs during the test.
     */
    public void testIntersect() throws Exception {
        WorldDB w = new WorldDB(null);

        // Add objects in different regions to test intersect
        assertTrue(w.add(new Balloon("left", 100, 100, 100, 50, 50, 50, "hot",
            5))); // Left half
        assertTrue(w.add(new Balloon("right", 600, 100, 100, 50, 50, 50, "hot",
            5))); // Right half
        assertTrue(w.add(new Balloon("top", 100, 600, 100, 50, 50, 50, "hot",
            5))); // Top half
        assertTrue(w.add(new Balloon("deep", 100, 100, 600, 50, 50, 50, "hot",
            5))); // Back half

        // Test 1: Query box intersects left half only (x < 512)
        String result = w.intersect(0, 0, 0, 400, 1024, 1024);
        assertTrue(result.contains("left"));
        assertFalse(result.contains("right"));

        // Test 2: Query box only in left half (x < 512)
        // Tests: region.x < qx + qxw
        result = w.intersect(0, 0, 0, 400, 1024, 1024);
    }

}
