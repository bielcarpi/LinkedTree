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
     * @return The name of the removed node or {@code null} if no node has been removed
     */
    String remove(int id);

    /**
     * Substitutes in the Tree the nodeToSubstitute for the nodeSubstitutor
     * @param nodeToSubstitute The node to substitute in the Tree
     * @param nodeSubstitutor The node that will substitute it in the Tree
     */
    void substitute(BinaryTreeNode nodeToSubstitute, BinaryTreeNode nodeSubstitutor);

    /**
     * Given the id of a Node, return whether it exists or not
     * @param id Whether a Node with this id exists or not
     */
    boolean nodeExists(int id);
}
