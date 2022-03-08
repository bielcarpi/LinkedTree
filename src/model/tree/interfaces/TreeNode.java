package model.tree.interfaces;

public interface TreeNode {

    /**
     * Returns the right node
     * @return The right node
     */
    TreeNode getRightNode();

    /**
     * Returns the left node
     * @return The left node
     */
    TreeNode getLeftNode();

    /**
     * Sets the right node
     * @param rightNode The right node
     */
    void setRightNode(TreeNode rightNode);
    /**
     * Sets the left node
     * @param leftNode The left node
     */
    void setLeftNode(TreeNode leftNode);
}
