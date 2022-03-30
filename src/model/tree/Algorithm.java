package model.tree;

import model.tree.interfaces.BinaryTreeNode;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Timestamp;

public class Algorithm implements BinaryTreeNode {
    private int id;
    private String name;
    private String language;
    private String cost;
    private int timestamp;

    private BinaryTreeNode rightNode;
    private BinaryTreeNode leftNode;
    private BinaryTreeNode parentNode;
    private int balancingFactor;


    /**
     * algorithmAux is an auxiliar variable for better performance
     * on the method {@link #getAlgorithmWithId(int)} or {@link #getAlgorithmWithTimestamp(int)}
     * Instead of creating an algorithm each time the method is called,
     * we'll cache an algorithm and change its ID
     */
    private static Algorithm algorithmAux;

    public Algorithm(int id, String name, String language, String cost, int timestamp) {
        this.id = id;
        this.name = name;
        this.language = language;
        this.cost = cost;
        this.timestamp = timestamp;
        balancingFactor = -1;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getLanguage() {
        return language;
    }
    public String getCost() {
        return cost;
    }
    public int getTimestamp() {
        return timestamp;
    }

    @Override
    public BinaryTreeNode getRightNode() {
        return rightNode;
    }
    @Override
    public BinaryTreeNode getLeftNode() {
        return leftNode;
    }
    @Override
    public BinaryTreeNode getParentNode(){
        return parentNode;
    }

    @Override
    public void setRightNode(BinaryTreeNode rightNode) {
        this.rightNode = rightNode;
    }
    @Override
    public void setLeftNode(BinaryTreeNode leftNode) {
        this.leftNode = leftNode;
    }
    @Override
    public void setParentNode(BinaryTreeNode parentNode) {
        this.parentNode = parentNode;
    }

    @Override
    public int calculateBalancingFactor(){
        int rightNodeHeight = rightNode == null? 0: exploreNodeHeight((Algorithm) rightNode);
        int leftNodeHeight = leftNode == null? 0: exploreNodeHeight((Algorithm) leftNode);
        return balancingFactor = (leftNodeHeight - rightNodeHeight);
    }
    @Override
    public int getBalancingFactor(){
        return balancingFactor;
    }

    /**
     * Recursive function that returns the height of the current node in the tree
     * @return Returns the height of the current node in the tree
     */
    private static int exploreNodeHeight(Algorithm a){
        int leftSubtreeHeight = 0, rightSubtreeHeight = 0;

        if(a.getLeftNode() != null)
            leftSubtreeHeight = exploreNodeHeight((Algorithm)a.getLeftNode());
        if(a.getRightNode() != null)
            rightSubtreeHeight = exploreNodeHeight((Algorithm)a.getRightNode());

        return Math.max(leftSubtreeHeight, rightSubtreeHeight) + 1;
    }

    @Override
    public String toPrettyString() {
        return id + " - " + name + ": " + language + ", " + cost + " - " + new Timestamp((long) timestamp * 1000) + " --> "+timestamp;
    }

    /**
     * Returns a string representing the node with the timestamp search format
     * @return a string representing the node with the timestamp search format
     */
    public String timestampSearchString() {
        return "S'ha trobat un algorisme... "  + name + ": " + language + ", " + cost + "   timestamp: " + new Timestamp((long) timestamp * 1000); // timestamp for debugging purposes
    }

    @Override
    public int compareTo(BinaryTreeNode o) {
        if(!(o instanceof Algorithm)) throw new IllegalArgumentException();
        return this.timestamp - ((Algorithm)o).timestamp; //Currently comparing timestamps
    }


    /**
     * Returns an Algorithm with the ID passed as parameter
     * <p>To be used only for user comparison, as the algorithm returned
     * is not a valid algorithm (all its attributes are null)
     *
     * @param id The ID of the algorithm to be returned
     * @return An algorithm with the ID specified (to be used for comparison)
     */
    public static Algorithm getAlgorithmWithId(int id) {
        if(algorithmAux == null) return new Algorithm(id, null, null, null, 0);

        algorithmAux.id = id;
        return algorithmAux;
    }

    /**
     * Returns an Algorithm with the timestamp passed as parameter
     * <p>To be used only for user comparison, as the algorithm returned
     * is not a valid algorithm (all its attributes are null)
     *
     * @param timestamp The Timestamp of the algorithm to be returned
     * @return An algorithm with the timestamp specified (to be used for comparison)
     */
    public static Algorithm getAlgorithmWithTimestamp(int timestamp) {
        if(algorithmAux == null) return new Algorithm(0, null, null, null, timestamp);

        algorithmAux.timestamp = timestamp;
        return algorithmAux;
    }

    public String getText(){
        return name + " " + timestamp;
    }

    public void printTree(){
        if (rightNode != null) {
            ((Algorithm)rightNode).printTree(true, "");
        }
        printNodeValue();
        if (leftNode != null) {
            ((Algorithm)leftNode).printTree(false, "");
        }
    }
    private void printNodeValue() {
        System.out.println(getText());
    }
    private void printTree(boolean isRight, String indent){
        if (rightNode != null) {
            ((Algorithm)rightNode).printTree(true, indent + (isRight ? "        " : " |      "));
        }
        System.out.print(indent);
        if (isRight) {
            System.out.print(" /");
        } else {
            System.out.print(" \\");
        }
        System.out.print("----- ");
        printNodeValue();
        if (leftNode != null) {
            ((Algorithm)leftNode).printTree(false, indent + (isRight ? " |      " : "        "));
        }
    }
}
