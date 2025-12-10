import java.util.List;

/**
 * Abstract base class for nodes in the BinTree, following the composite pattern.
 */
public abstract class BinNode {

    /**
     * Inserts an AirObject into the tree.
     * @param obj The AirObject to insert.
     * @param region The region of this node.
     * @param level The depth of this node.
     * @return The new node for this position.
     */
    public abstract BinNode insert(AirObject obj, Region region, int level);

    /**
     * Finds an AirObject in the tree.
     * @param target The AirObject to find.
     * @param region The region of this node.
     * @param level The depth of this node.
     * @return The found AirObject, or null.
     */
    public abstract AirObject find(AirObject target, Region region, int level);

    /**
     * Removes an AirObject from the tree.
     * @param target The AirObject to remove.
     * @param region The region of this node.
     * @param level The depth of this node.
     * @return The new node for this position.
     */
    public abstract BinNode remove(AirObject target, Region region, int level);
    
    /**
     * Prints the tree structure.
     * @param buffer The string buffer to append to.
     * @param region The region of this node.
     * @param level The depth of this node.
     * @return The number of nodes printed.
     */
    public abstract int print(StringBuilder buffer, Region region, int level);
}
