package model.tree;

import model.graph.User;
import model.tree.interfaces.BinaryTreeNode;

public class Algorithm implements BinaryTreeNode {
    private int id;
    private String name;
    private String language;
    private String cost;
    private int timestamp;

    private BinaryTreeNode rightNode;
    private BinaryTreeNode leftNode;

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
    public void setRightNode(BinaryTreeNode rightNode) {
        this.rightNode = rightNode;
    }
    @Override
    public void setLeftNode(BinaryTreeNode leftNode) {
        this.leftNode = leftNode;
    }

    @Override
    public String toPrettyString() {
        return id + " " + name + " " + language + " " + cost + " " + timestamp;
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
}
