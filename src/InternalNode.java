/**
 * Represents an internal node in the BinTree, which has two children.
 * 
 * @author Edwin Barrack
 * @author Will Perryman
 * @version 12/10/2025
 */
public final class InternalNode extends BinNode {

    private BinNode left = FlyweightNode.getInstance();
    private BinNode right = FlyweightNode.getInstance();

    /**
     * Inserts an AirObject into the appropriate child node(s).
     *
     * @param obj
     *            The AirObject to insert.
     * @param region
     *            The region this node represents.
     * @param level
     *            The depth of this node in the tree.
     * @return This node after insertion.
     */
    @Override
    public BinNode insert(AirObject obj, Region region, int level) {
        int axis = level % 3;
        int midpoint = region.midpoint(axis);

        // Get the object's extent along this axis
        int objStart = BinTree.getAxisStart(obj, axis);
        int objEnd = BinTree.getAxisEnd(obj, axis);

        // Insert into left child if object's bounding box intersects left
        // region
        if (objStart < midpoint) {
            left = left.insert(obj, region.leftChild(axis), level + 1);
        }
        // Insert into right child if object's bounding box intersects right
        // region
        if (objEnd > midpoint) {
            right = right.insert(obj, region.rightChild(axis), level + 1);
        }
        return this;
    }


    /**
     * Removes an AirObject from the child node(s) and simplifies the tree if
     * possible.
     *
     * @param target
     *            The AirObject to remove.
     * @param region
     *            The region this node represents.
     * @param level
     *            The depth of this node in the tree.
     * @return The new root for this subtree, which could be a child node if
     *         this node becomes redundant.
     */
    @Override
    public BinNode remove(AirObject target, Region region, int level) {
        int axis = level % 3;
        int midpoint = region.midpoint(axis);

        // Get the object's extent along this axis
        int objStart = BinTree.getAxisStart(target, axis);
        int objEnd = BinTree.getAxisEnd(target, axis);

        // Remove from left child if object's bounding box intersects left
        // region
        if (objStart < midpoint) {
            left = left.remove(target, region.leftChild(axis), level + 1);
        }
        // Remove from right child if object's bounding box intersects right
        // region
        if (objEnd > midpoint) {
            right = right.remove(target, region.rightChild(axis), level + 1);
        }

        // Attempt to collapse child subtrees into leaves when they became small
//        if (left instanceof InternalNode) {
//            left = tryCollapseSubtree((InternalNode)left);
//        }
//        if (right instanceof InternalNode) {
//            right = tryCollapseSubtree((InternalNode)right);
//        }

        // Collapse to single leaf if one side is empty
        if (left instanceof LeafNode && right instanceof FlyweightNode) {
            return left;
        }
        if (left instanceof FlyweightNode && right instanceof LeafNode) {
            return right;
        }

        // Check if two leaves can be merged
        if (left instanceof LeafNode && right instanceof LeafNode) {
            LeafNode leftLeaf = (LeafNode)left;
            LeafNode rightLeaf = (LeafNode)right;

            // Count unique objects (some may be duplicated across both leaves)
            int uniqueCount = countUniqueObjects(leftLeaf, rightLeaf);

            if (uniqueCount <= 3) {
                return mergeLeaves(leftLeaf, rightLeaf);
            }
        }

        return this;
    }


    /**
     * Tries to collapse an internal subtree into a single leaf if it contains
     * 3 or fewer unique objects. If not possible, returns the internal node.
     * 
     * @param internal
     *            The internal node to try to collapse.
     * @return A leaf if collapse is possible, otherwise the internal node.
     */
    private BinNode tryCollapseSubtree(InternalNode internal) {
        // Collect all unique objects from the subtree
        java.util.Set<String> seenNames = new java.util.HashSet<>();
        java.util.List<AirObject> allObjects = new java.util.ArrayList<>();
//        collectObjects(internal, allObjects, seenNames);

        // If 3 or fewer unique objects, create a single leaf
// if (allObjects.size() <= 3) {
// LeafNode merged = new LeafNode();
// for (AirObject obj : allObjects) {
// merged.addObject(obj);
// }
// return merged;
// }

        // Can't collapse, return the internal node as-is
        return internal;
    }


//    /**
//     * Recursively collects all unique objects from a subtree.
//     * 
//     * @param node
//     *            The current node.
//     * @param objects
//     *            The list to add objects to.
//     * @param seenNames
//     *            Set of names already seen (to avoid duplicates).
//     */
//    private void collectObjects(
//        BinNode node,
//        java.util.List<AirObject> objects,
//        java.util.Set<String> seenNames) {
//        if (node instanceof FlyweightNode) {
//            return;
//        }
//        if (node instanceof LeafNode) {
//            LeafNode leaf = (LeafNode)node;
//            for (AirObject obj : leaf.getObjects()) {
//                // if (!seenNames.contains(obj.getName())) {
//                seenNames.add(obj.getName());
//                objects.add(obj);
//                // }
//            }
//        }
//        else {
//            InternalNode internal = (InternalNode)node;
//            collectObjects(internal.left, objects, seenNames);
//            collectObjects(internal.right, objects, seenNames);
//        }
//    }


    /**
     * Counts the number of unique objects between two leaves.
     * Objects with the same name are considered duplicates.
     * 
     * @param leftLeaf
     *            The left leaf.
     * @param rightLeaf
     *            The right leaf.
     * @return The count of unique objects.
     */
    private int countUniqueObjects(LeafNode leftLeaf, LeafNode rightLeaf) {
        int uniqueCount = leftLeaf.getSize();
        for (AirObject obj : rightLeaf.getObjects()) {
            boolean isDuplicate = false;
            for (AirObject leftObj : leftLeaf.getObjects()) {
                if (leftObj.getName().equals(obj.getName())) {
                    isDuplicate = true;
                    break;
                }
            }
            if (!isDuplicate) {
                uniqueCount++;
            }
        }
        return uniqueCount;
    }


    /**
     * Merges two leaf nodes into one, avoiding duplicate objects.
     * 
     * @param leftLeaf
     *            The left leaf.
     * @param rightLeaf
     *            The right leaf.
     * @return A new merged leaf node.
     */
    private LeafNode mergeLeaves(LeafNode leftLeaf, LeafNode rightLeaf) {
        LeafNode merged = new LeafNode();
        for (AirObject obj : leftLeaf.getObjects()) {
            merged.addObject(obj);
        }
        for (AirObject obj : rightLeaf.getObjects()) {
            boolean isDuplicate = false;
            for (AirObject mergedObj : merged.getObjects()) {
                if (mergedObj.getName().equals(obj.getName())) {
                    isDuplicate = true;
                    break;
                }
            }
            if (!isDuplicate) {
                merged.addObject(obj);
            }
        }
        return merged;
    }


    /**
     * Recursively prints the contents of this internal node and its children.
     *
     * @param buffer
     *            The StringBuilder to append the output to.
     * @param region
     *            The region this node represents.
     * @param level
     *            The depth of this node for indentation.
     * @return The total number of nodes in this subtree.
     */
    @Override
    public int print(StringBuilder buffer, Region region, int level) {
        indent(buffer, level);
        buffer.append("I ").append(region.toString()).append(" ").append(level)
            .append("\n");
        int nodes = 1;
        int axis = level % 3;
        nodes += left.print(buffer, region.leftChild(axis), level + 1);
        nodes += right.print(buffer, region.rightChild(axis), level + 1);
        return nodes;
    }


    private void indent(StringBuilder buffer, int level) {
        for (int i = 0; i < level; i++) {
            buffer.append("  ");
        }
    }


    /**
     * Recursively checks for collisions in the child nodes.
     *
     * @param sb
     *            The StringBuilder to append collision reports to.
     * @param region
     *            The region this node represents.
     * @param level
     *            The depth of this node in the tree.
     * @return The total number of collisions found in this subtree.
     */
    @Override
    public int collisions(StringBuilder sb, Region region, int level) {
        int axis = level % 3;
        Region leftRegion = region.leftChild(axis);
        Region rightRegion = region.rightChild(axis);
        int leftCollisions = left.collisions(sb, leftRegion, level + 1);
        int rightCollisions = right.collisions(sb, rightRegion, level + 1);
        return leftCollisions + rightCollisions;
    }


    /**
     * Recursively finds intersections within the child nodes.
     *
     * @param sb
     *            The StringBuilder to append the intersecting objects to.
     * @param region
     *            The region this node represents.
     * @param level
     *            The depth of this node in the tree.
     * @param qx
     *            The x-coordinate of the query box.
     * @param qy
     *            The y-coordinate of the query box.
     * @param qz
     *            The z-coordinate of the query box.
     * @param qxw
     *            The width of the query box.
     * @param qyw
     *            The height of the query box.
     * @param qzw
     *            The depth of the query box.
     * @return The total number of nodes visited in this subtree.
     */
    @Override
    public int intersect(
        StringBuilder sb,
        Region region,
        int level,
        int qx,
        int qy,
        int qz,
        int qxw,
        int qyw,
        int qzw) {
        sb.append("In Internal node ").append(region.toString()).append(" ")
            .append(level).append("\n");
        int nodesVisited = 1;
        int axis = level % 3;
        Region leftRegion = region.leftChild(axis);
        Region rightRegion = region.rightChild(axis);
        if (regionIntersectsQuery(leftRegion, qx, qy, qz, qxw, qyw, qzw)) {
            nodesVisited += left.intersect(sb, leftRegion, level + 1, qx, qy,
                qz, qxw, qyw, qzw);
        }
        if (regionIntersectsQuery(rightRegion, qx, qy, qz, qxw, qyw, qzw)) {
            nodesVisited += right.intersect(sb, rightRegion, level + 1, qx, qy,
                qz, qxw, qyw, qzw);
        }
        return nodesVisited;
    }
}
