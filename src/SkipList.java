import java.util.Random;

class SkipList<K extends Comparable<K>, E> {
    private SkipNode<K, E> head;
    private int level;
    private int size;
    private final Random ran = new Random(); // Hold the Random class object

    public SkipList() {
        head = new SkipNode<>(null, null, 0);
        level = -1;
        size = 0;
    }

    // Return the (first) matching matching element if one exists, null otherwise
    public E find(K key) {
        SkipNode<K, E> x = head; // Dummy header node
        for (int i = level; i >= 0; i--) // For each level...
            while ((x.forward[i] != null) && (x.forward[i].key().compareTo(key) < 0)) // go forward
                x = x.forward[i]; // Go one last step
        x = x.forward[0]; // Move to actual record, if it exists
        if ((x != null) && (x.key().compareTo(key) == 0)) return x.element(); // Got it
        else return null; // Its not there
    }

    // Pick a level using a geometric distribution
    private int randomLevel() {
        int lev = 0;
        while(Math.abs(ran.nextInt()) % 2 == 0) {
            lev++;
        }
        return lev;
    }

    /** Insert a key, element pair into the skip list */
    @SuppressWarnings("unchecked")
    public void insert(K key, E elem) {
        int newLevel = randomLevel(); // New node's level
        if (newLevel > level) // If new node is deeper
            adjustHead(newLevel); // adjust the header
        // Track end of level
        SkipNode<K, E>[] update = new SkipNode[level + 1];
        SkipNode<K, E> x = head; // Start at header node
        for (int i = level; i >= 0; i--) { // Find insert position
            while ((x.forward[i] != null) && (x.forward[i].key().compareTo(key) < 0))
                x = x.forward[i];
            update[i] = x; // Track end at level i
        }
        x = new SkipNode<>(key, elem, newLevel);
        for (int i = 0; i <= newLevel; i++) { // Splice into list
            x.forward[i] = update[i].forward[i]; // Who x points to
            update[i].forward[i] = x; // Who points to x
        }
        size++; // Increment dictionary size
    }

    private void adjustHead(int newLevel) {
        SkipNode<K, E> temp = head;
        head = new SkipNode<>(null, null, newLevel);
        for (int i = 0; i <= level; i++)
            head.forward[i] = temp.forward[i];
        level = newLevel;
    }

    class KVPair<K2 extends Comparable<K2>, E2> {
        private final K2 key;
        private final E2 value;

        public KVPair(K2 k, E2 v) {
            key = k;
            value = v;
        }  

        public K2 key() {
            return key;
        }

        public E2 value() {
            return value;
        }
        
        @Override
        public String toString() {
            return "(" + key.toString() + "," + value.toString() + ")";
        }
    }

    class SkipNode<K2 extends Comparable<K2>, E2> {
        private final KVPair<K2, E2> rec;
        private SkipNode<K2, E2>[] forward;

        public E2 element() {
            return rec.value();
        }

        public K2 key() {
            return rec.key();
        }

        @SuppressWarnings("unchecked")
        public SkipNode(K2 key, E2 elem, int level) {
            rec = new KVPair<>(key, elem);
            forward = new SkipNode[level + 1];
            for (int i = 0; i < level; i++)
                forward[i] = null;
        }

        @Override
        public String toString() {
            return rec.toString();
        }
    }
}
