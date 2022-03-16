package model.tree;

import model.tree.interfaces.BinaryTree;
import model.tree.interfaces.BinaryTreeNode;
import model.utilities.ArrayList;

import java.io.IOException;

public class AlgorithmTree implements BinaryTree {

    private final Algorithm rootNode;

    public AlgorithmTree(String path) throws IOException {

        /**
         * In the {@link ReadTree} class, we don't build the Tree, we build it here.
         * The {@link ReadTree#getAlgorithms()} returns an array with all the algorithms in the file, and
         * we'll insert and build the Tree here.
         * We know it would be best to build the Tree when reading, but we did it this way to see it more clearly
         */

        ReadTree.read(path);
        Algorithm[] algorithms = ReadTree.getAlgorithms();
        rootNode = algorithms[0]; //Add the root element of the tree

        for(Algorithm a: algorithms)
            insert(a); //For each algorithm, insert it to the tree
    }


    public void insert(BinaryTreeNode nodeToInsert){
        insert_implementation(rootNode, nodeToInsert);
    }


    private void insert_implementation(BinaryTreeNode parentNode, BinaryTreeNode nodeToInsert){
        if(!(parentNode instanceof Algorithm) || !(nodeToInsert instanceof Algorithm))
            throw new IllegalArgumentException();

        if(nodeToInsert.compareTo(parentNode) < 0){ //If the nodeToInsert is less than the parentNode
            if(parentNode.getLeftNode() == null) parentNode.setLeftNode(nodeToInsert);
            else insert_implementation(parentNode.getLeftNode(), nodeToInsert);
        }
        else if(nodeToInsert.compareTo(parentNode) > 0){ //If the nodeToInsert is bigger than the parentNode
            if(parentNode.getRightNode() == null) parentNode.setRightNode(nodeToInsert);
            else insert_implementation(parentNode.getRightNode(), nodeToInsert);
        }
    }

    @Override
    public boolean nodeExists(int id) {
        return getNodeById(id) != null;
    }

    public Algorithm getNodeById(int id){
        return preorderSearch(rootNode, id);
    }
    private Algorithm preorderSearch(Algorithm algorithm, int id){
        if(algorithm.getId() == id) return algorithm;

        if(algorithm.getLeftNode() != null){
            Algorithm solution = preorderSearch((Algorithm)algorithm.getLeftNode(), id);
            if(solution != null) return solution;
        }
        if(algorithm.getRightNode() != null){
            return preorderSearch((Algorithm)algorithm.getRightNode(), id);
        }
        return null;
    }

    @Override
    public boolean remove(BinaryTreeNode nodeToRemove) {
        return false;
    }

    public Algorithm exactTimestampSearch(int timestamp){
        Algorithm algorithm = rootNode;
        while(true){
            //Check if the node has an adjacent node
            if (algorithm.getRightNode() != null && algorithm.getLeftNode() != null){
                //The node has at least one adjacent node
                if (algorithm.getTimestamp() > timestamp){
                    //Compare with LeftNode
                    //Update of the node we are currently analyzing (Left in this case)
                    algorithm = (Algorithm) algorithm.getLeftNode();
                } else {
                    if (algorithm.getTimestamp() < timestamp){
                        //Compare with RightNode
                        //Update of the node we are currently analyzing (Right in this case)
                        algorithm = (Algorithm) algorithm.getRightNode();
                    } else {
                        //The node with the current timestamp introduced has been found
                        break;
                    }
                }
            } else {
                //The node doesn't have an adjacent node
                algorithm = null;
                break;
            }
        }
        algorithm.toPrettyString();
        return algorithm;
    }

}
