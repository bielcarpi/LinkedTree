package model.tree.interfaces;

public interface BinaryTree {

    /**
     * Inserts a BinaryTreeNode to the Tree
     * @param nodeToInsert The node to insert
     */
    void insert(BinaryTreeNode nodeToInsert);

    /**
     * Removes a BinaryTreeNode of the Tree, matching the one passed as parameter
     * @param id the id of the node equal to the one that wants to be removed
     * @return Whether the node has been removed or not
     */
    boolean remove(int id);

    /**
     * Given the id of a Node, return whether it exists or not
     * @param id Whether a Node with this id exists or not
     */
    boolean nodeExists(int id);
}
