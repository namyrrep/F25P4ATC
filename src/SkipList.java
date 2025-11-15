import java.util.Random;
import java.util.Dictionary;

public class SkipList {
    private SkipNode head;
    private int level;
    private int size;
    static private Random ran = new Random(); // Hold the Random class
                                              // object

    public SkipList() {
        head = new SkipNode(null, null, 0);
        level = -1;
        size = 0;
    }


    // Return the (first) matching matching element if one exists, null
    // otherwise
    public Object find(Comparable<String> key) {
        SkipNode x = head; // Dummy header node
        for (int i = level; i >= 0; i--) { // For each level...
            while ((x.forward[i] != null) && (x.forward[i].key().compareTo(
                key) < 0)) { // go forward
                x = x.forward[i]; // Go one last step
            }
        }
        x = x.forward[0]; // Move to actual record, if it exists
        if ((x != null) && (x.key().compareTo(key) == 0)) {
            return x.element();
        } // Got it
        else {
            return null;
        } // Its not there
    }


    // Pick a level using a geometric distribution
    int randomLevel() {
        int lev;
        for (lev = 0; Math.abs(ran.nextInt()) % 2 == 0; lev++) { // ran is
                                                                 // random
                                                                 // generator
            ; // Do nothing
        }
        return lev;
    }


    /** Insert a key, element pair into the skip list */
    public void insert(Comparable key, Object elem) {
        int newLevel = randomLevel(); // New node's level
        if (newLevel > level) { // If new node is deeper
            adjustHead(newLevel); // adjust the header
        }
        // Track end of level
        SkipNode[] update = new SkipNode[level + 1];
        SkipNode x = head; // Start at header node
        for (int i = level; i >= 0; i--) { // Find insert position
            while ((x.forward[i] != null) && (x.forward[i].key().compareTo(
                key) < 0)) {
                x = x.forward[i];
            }
            update[i] = x; // Track end at level i
        }
        x = new SkipNode(key, elem, newLevel);
        for (int i = 0; i <= newLevel; i++) { // Splice into list
            x.forward[i] = update[i].forward[i]; // Who x points to
            update[i].forward[i] = x; // Who points to x
        }
        size++; // Increment dictionary size
    }


    private void adjustHead(int newLevel) {
        SkipNode temp = head;
        head = new SkipNode(null, null, newLevel);
        for (int i = 0; i <= level; i++) {
            head.forward[i] = temp.forward[i];
        }
        level = newLevel;
    }
    
    private class KVPair {
        private Comparable<String> key;
        private Object value;

        public KVPair(Comparable<String> k, Object v) {
            key = k;
            value = v;
        }
        
        public Comparable<String> key() {
            return key;
        }
        public Object value() {
            return value;
        }
    }
        

    private class SkipNode {
        private KVPair rec;
        private SkipNode[] forward;

        public Object element() {
            return rec.value();
        }


        public Comparable<String> key() {
            return rec.key();
        }


        public SkipNode(Comparable<String> key, Object elem, int level) {
            rec = new KVPair(key, elem);
            forward = new SkipNode[level + 1];
            for (int i = 0; i < level; i++) {
                forward[i] = null;
            }
        }


        public String toString() {
            return rec.toString();
        }
    }
}
