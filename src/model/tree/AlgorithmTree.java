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

    public boolean nodeExists(int id){
        //for(int i = 0; i < tree.size(); i++)
        //    if(tree.get(i).getId() == id) return true;

        return false;
    }

    @Override
    public boolean remove(BinaryTreeNode nodeToRemove) {
        return false;
    }

    public Algorithm exactTimestampSearch(int timestamp){
        Algorithm algorithm = tree.get(0);
        while(true){
            //Check if the node has an adjacent node
            if (algorithm.getRightNode() != null && algorithm.getLeftNode() != null){
                //The node has at least one adjacent node
                if (algorithm.getTimestamp() > timestamp){
                    //Compare with LeftNode
                    algorithm = (Algorithm) algorithm.getLeftNode(); //Update of the node we are currently analizing (Left in this case)
                } else {
                    if (algorithm.getTimestamp() < timestamp){
                        //Compare with RightNode
                        algorithm = (Algorithm) algorithm.getRightNode(); //Update of the node we are currently analizing (Right in this case)
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
