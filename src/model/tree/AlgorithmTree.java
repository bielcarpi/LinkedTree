package model.tree;

import model.tree.interfaces.BinaryTree;
import model.tree.interfaces.BinaryTreeNode;
import model.utilities.ArrayList;

import java.io.IOException;

public class AlgorithmTree implements BinaryTree {

    ArrayList<Algorithm> tree;

    public AlgorithmTree(String path) throws IOException {
        tree = new ArrayList<>(Algorithm.class);

        ReadTree.read(path);
        Algorithm[] algorithms = ReadTree.getAlgorithms();
        tree.add(algorithms[0]); //Add the root element of the tree

        for(Algorithm a: algorithms)
            insert(tree.get(0), a); //For each algorithm, insert it to the tree
    }

    public void insert(BinaryTreeNode parentNode, BinaryTreeNode nodeToInsert){
        if(!(parentNode instanceof Algorithm) || !(nodeToInsert instanceof Algorithm))
            throw new IllegalArgumentException();

        if(nodeToInsert.compareTo(parentNode) < 0){ //If the nodeToInsert is less than the parentNode
            if(parentNode.getLeftNode() == null) parentNode.setLeftNode(nodeToInsert);
            else insert(parentNode.getLeftNode(), nodeToInsert);
        }
        else if(nodeToInsert.compareTo(parentNode) > 0){ //If the nodeToInsert is bigger than the parentNode
            if(parentNode.getRightNode() == null) parentNode.setRightNode(nodeToInsert);
            else insert(parentNode.getRightNode(), nodeToInsert);
        }
    }

}
