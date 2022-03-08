package model.tree;

import model.tree.interfaces.TreeNode;

public class Algorithm implements TreeNode {
    private int id;
    private String name;
    private String language;
    private String cost;
    private int timestamp;

    private TreeNode rightNode;
    private TreeNode leftNode;

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
    public TreeNode getRightNode() {
        return rightNode;
    }
    @Override
    public TreeNode getLeftNode() {
        return leftNode;
    }

    @Override
    public void setRightNode(TreeNode rightNode) {
        this.rightNode = rightNode;
    }
    @Override
    public void setLeftNode(TreeNode leftNode) {
        this.leftNode = leftNode;
    }
}
