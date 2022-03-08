package model.tree;

import model.tree.interfaces.BinaryTreeNode;

public class Algorithm implements BinaryTreeNode {
    private int id;
    private String name;
    private String language;
    private String cost;
    private int timestamp;

    private BinaryTreeNode rightNode;
    private BinaryTreeNode leftNode;

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
}
