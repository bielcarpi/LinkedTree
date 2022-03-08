package model.tree.interfaces;

public interface BinaryTree {

    /**
     * Inserts a BinaryTreeNode to the Tree, given the parent node
     * @param parentNode The parent node
     * @param nodeToInsert The node to insert
     */
    public void insert(BinaryTreeNode parentNode, BinaryTreeNode nodeToInsert);
}
