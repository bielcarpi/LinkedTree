package model.tree.interfaces;

public interface BinaryTreeNode extends Comparable<BinaryTreeNode> {

    /**
     * Returns the right node
     * @return The right node
     */
    BinaryTreeNode getRightNode();

    /**
     * Returns the left node
     * @return The left node
     */
    BinaryTreeNode getLeftNode();

    /**
     * Sets the right node
     * @param rightNode The right node
     */
    void setRightNode(BinaryTreeNode rightNode);
    /**
     * Sets the left node
     * @param leftNode The left node
     */
    void setLeftNode(BinaryTreeNode leftNode);

    /**
     * Returns a pretty string representing the node
     * @return A pretty string representing the node
     */
    String toPrettyString();
}
