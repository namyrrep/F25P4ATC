import java.util.Random;

/**
 * This is our generic skipList
 * @author Edwin Barrack
 * @author Will Perryman
 * @version 12/10/2025
 * @param <K>
 * @param <E>
 */
class SkipList<K extends Comparable<K>, E> {
    private SkipNode<K, E> head;
    private int level;
    private int size;
    private final Random ran; // replace inline initialization for seeding

    public SkipList(Random r) { // new constructor for deterministic tests
        head = new SkipNode<>(null, null, 0);
        level = -1;
        size = 0;
        ran = (r == null) ? new Random() : r;
    }


    /**
     * Return the (first) matching matching element if 
     * one exists, null otherwise
     * @param key
     * @return E
     */
    public E find(K key) {
        SkipNode<K, E> x = head; // Dummy header node
        for (int i = level; i >= 0; i--) // For each level...
            while ((x.forward[i] != null) && (x.forward[i].key().compareTo(
                key) < 0)) // go forward
                x = x.forward[i]; // Go one last step
        x = x.forward[0]; // Move to actual record, if it exists
        if ((x != null) && (x.key().compareTo(key) == 0))
            return x.element(); // Got it
        else
            return null; // Its not there
    }


    /**
     * Pick a level using a geometric distribution
     * @return int
     */
    private int randomLevel() {
        int lev = 0;
        while (Math.abs(ran.nextInt()) % 2 == 0) {
            lev++;
        }
        return lev;
    }


    /** 
     * Insert a key, element pair into the skip list 
     * @param key
     * @param elem
     */
    @SuppressWarnings("unchecked")
    public void insert(K key, E elem) {
        int newLevel = randomLevel();
        if (newLevel > level)
            adjustHead(newLevel);
        SkipNode<K, E>[] update = new SkipNode[level + 1];
        SkipNode<K, E> x = head;
        for (int i = level; i >= 0; i--) {
            while ((x.forward[i] != null) && (x.forward[i].key().compareTo(
                key) < 0))
                x = x.forward[i];
            update[i] = x;
        }
        x = new SkipNode<>(key, elem, newLevel);
        for (int i = 0; i <= newLevel; i++) {
            x.forward[i] = update[i].forward[i];
            update[i].forward[i] = x;
        }
        size++;
    }

    /**
     * Removes an item from the skipList
     * @param key
     * @return E
     */
    @SuppressWarnings("unchecked")
    public E remove(K key) {
        SkipNode<K, E>[] update = new SkipNode[level + 1];
        SkipNode<K, E> x = head;
        for (int i = level; i >= 0; i--) {
            while ((x.forward[i] != null) && (x.forward[i].key().compareTo(
                key) < 0))
                x = x.forward[i];
            update[i] = x;
        }
        x = x.forward[0];
        if ((x != null) && (x.key().compareTo(key) == 0)) {
            for (int i = 0; i <= level; i++) {
                if (update[i].forward[i] != x)
                    break;
                update[i].forward[i] = x.forward[i];
            }
            size--;
            return x.element();
        }
        else {
            return null;
        }
    }

    /**
     * Returns the number of elements in our skipList
     * @return int
     */
    public int size() {
        return size;
    }

    /**
     * Prints all elements between the start and end
     * character in the alphabet.
     * @param start
     * @param end
     * @return String
     */
    @SuppressWarnings("unchecked")
    public String rangePrint(String start, String end) {
        StringBuilder sb = new StringBuilder();

        SkipNode<K, E> x = head;

        // Step 1: Move to first node >= start
        for (int i = level; i >= 0; i--) {
            while (x.forward[i] != null && x.forward[i].key().compareTo(
                (K)start) < 0) {
                x = x.forward[i];
            }
        }

        // Step 2: Start scanning from bottom level
        x = x.forward[0];

        int count = 0;

        // Step 3: Print until key > end
        while (x != null && x.key().compareTo((K)end) <= 0) {
            sb.append(x.element().toString()).append("\r\n");
            count++;
            x = x.forward[0];
        }
        return sb.toString();
    }

    /**
     * Prints the entire skipList
     * @return String
     */
    @SuppressWarnings("unchecked")
    public String printSkip() {
        if (size == 0)
            return "SkipList is empty";
        StringBuilder sb = new StringBuilder();
        SkipNode<K, E> x = head;
        int count = 0;
        int headerDepth = x.forward.length; // corrected (was length - 1)
        sb.append("Node has depth ").append(headerDepth).append(
            ", Value (null)\r\n");
        x = x.forward[0];
        while (x != null) {
            int depth = x.forward.length; // corrected
            sb.append("Node has depth ").append(depth).append(", Value (")
                .append(x.element().toString()).append(")\r\n");
            count++;
            x = x.forward[0];
        }
        sb.append(count).append(" skiplist nodes printed\r\n");
        return sb.toString();
    }

    /**
     * Private method that helps adjust the level of the head
     * @param newLevel
     */
    @SuppressWarnings("unchecked")
    private void adjustHead(int newLevel) {
        SkipNode<K, E> temp = head;
        head = new SkipNode<>(null, null, newLevel);
        for (int i = 0; i <= level; i++)
            head.forward[i] = temp.forward[i];
        level = newLevel;
    }
    
    /**
     * Used for storing the elements and their values
     * @param <K2>
     * @param <E2>
     */
    class KVPair<K2 extends Comparable<K2>, E2> {
        private final K2 key;
        private final E2 value;
        
        /**
         * Constructor for the pair
         * @param k
         * @param v
         */
        public KVPair(K2 k, E2 v) {
            key = k;
            value = v;
        }

        /**
         * Returns the key
         * @return K2 key
         */
        public K2 key() {
            return key;
        }

        /**
         * returns the value
         * @return E2 value
         */
        public E2 value() {
            return value;
        }

        /**
         * This is the overridden toString() method for the
         * KVPair class.
         * @return String
         */
        @Override
        public String toString() {
            return "(" + key.toString() + "," + value.toString() + ")";
        }
    }

    /**
     * This is the generic skipNode class
     * @param <K2>
     * @param <E2>
     */
    class SkipNode<K2 extends Comparable<K2>, E2> {
        private final KVPair<K2, E2> rec;
        private SkipNode<K2, E2>[] forward;
        
        /**
         * Returns the nodes element
         * @return E2 rec.value
         */
        public E2 element() {
            return rec.value();
        }

        /**
         * Returns the nodes key
         * @return K2 rec.key()
         */
        public K2 key() {
            return rec.key();
        }

        /**
         * This is the constructor for the SkipNode
         * @param key
         * @param elem
         * @param level
         */
        @SuppressWarnings("unchecked")
        public SkipNode(K2 key, E2 elem, int level) {
            rec = new KVPair<>(key, elem);
            forward = new SkipNode[level + 1];
            for (int i = 0; i < level; i++)
                forward[i] = null;
        }

        /**
         * This is the overridden toString() method for skipNode
         * @return
         */
        @Override
        public String toString() {
            return rec.toString();
        }
    }
}
