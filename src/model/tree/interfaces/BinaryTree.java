package model.tree.interfaces;

public interface BinaryTree {

    /**
     * Inserts a BinaryTreeNode to the Tree
     * @param nodeToInsert The node to insert
     */
    void insert(BinaryTreeNode nodeToInsert);

    /**
     * Given the id of a Node, return whether it exists or not
     * @param id Whether a Node with this id exists or not
     */
    boolean nodeExists(int id);
}
