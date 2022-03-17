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

    /**
     * Function which returns the node with the same timestamp introduced by parameter.
     * @param timestamp: Integer with the representation of the timestamp value that we are searching for.
     * @return The current Algorithm that we are searching for.
     *         Null pointer in case the tree is empty or if there isn't any Algorithm with the current timestamp.
     */
    public Algorithm exactTimestampSearch(int timestamp){
        Algorithm algorithm = rootNode;
        //Check if the tree is empty or not
        if (rootNode != null){
            while (true){
                //Check if the node has an adjacent node
                if (algorithm.getRightNode() != null || algorithm.getLeftNode() != null){
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
        } else {
            return null; //The tree is empty
        }

        algorithm.toPrettyString(); //For debugging purposes
        return algorithm;
    }

    /**
     * Function which returns all the nodes that have a timestamp value between the limits introduced by parameter.
     * @param minTimestamp: Integer with the representation of the timestamp's value for the lower bound.
     * @param maxTimestamp: Integer with the representation of the timestamp's value for the higher bound.
     * @return A list containing all the nodes whose timestamp's value is between the lower and higher bound.
     */
    public ArrayList<Algorithm> rangeTimestampSearch(int minTimestamp, int maxTimestamp){
        Algorithm algorithm = rootNode;
        ArrayList<Algorithm> list = null;
        //Check if the tree is empty or not
        if (rootNode != null){
            while (true){
                //Check if the node has an adjacent node
                if (algorithm.getLeftNode() != null || algorithm.getRightNode() != null){
                    //The node has at least one adjacent node
                    if (algorithm.getTimestamp() > minTimestamp && algorithm.getTimestamp() > maxTimestamp){
                        //Compare with LeftNode
                        algorithm = (Algorithm) algorithm.getLeftNode(); //Update de node we are analyzing
                        if (algorithm.getTimestamp() > minTimestamp && algorithm.getTimestamp() < maxTimestamp){
                            list.add(algorithm); //Addition of the node with timestamp value between the limits
                        }
                    } else {
                        if (algorithm.getTimestamp() < minTimestamp && algorithm.getTimestamp() < maxTimestamp){
                            //Compare with RightNode
                            algorithm = (Algorithm) algorithm.getRightNode(); //Update de node we are analyzing
                            if (algorithm.getTimestamp() > minTimestamp && algorithm.getTimestamp() < maxTimestamp){
                                list.add(algorithm); //Addition of the node with timestamp value between the limits
                            }
                        }
                    }
                } else {
                    //The node doesn't have an adjacent node
                    break;
                }
            }
        } else {
            return null; //The tree is empty
        }

        list.get(0).toPrettyString(); //For debugging purposes
        return list;
    }


}
