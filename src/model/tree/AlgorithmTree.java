package model.tree;

import model.tree.interfaces.BinaryTree;
import model.tree.interfaces.BinaryTreeNode;
import model.utilities.ArrayList;
import model.utilities.Queue;

import java.io.IOException;

public class AlgorithmTree implements BinaryTree {

    private Algorithm rootNode;

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

        //Print the tree once it is built
        //rootNode.printTree();
    }


    public void insert(BinaryTreeNode nodeToInsert){
        insertImplementation(rootNode, nodeToInsert);
    }


    private void insertImplementation(BinaryTreeNode parentNode, BinaryTreeNode nodeToInsert){
        if(!(parentNode instanceof Algorithm) || !(nodeToInsert instanceof Algorithm))
            throw new IllegalArgumentException();

        if(nodeToInsert.compareTo(parentNode) < 0){ //If the nodeToInsert is less than the parentNode
            if(parentNode.getLeftNode() == null){
                parentNode.setLeftNode(nodeToInsert);
                nodeToInsert.setParentNode(parentNode);
                balanceTree(nodeToInsert);
            }
            else insertImplementation(parentNode.getLeftNode(), nodeToInsert);
        }
        else if(nodeToInsert.compareTo(parentNode) > 0){ //If the nodeToInsert is bigger than the parentNode
            if(parentNode.getRightNode() == null){
                parentNode.setRightNode(nodeToInsert);
                nodeToInsert.setParentNode(parentNode);
                balanceTree(nodeToInsert);
            }
            else insertImplementation(parentNode.getRightNode(), nodeToInsert);
        }
    }

    @Override
    public void balanceTree(BinaryTreeNode nodeToInsert){
        //Get all the parents of the nodeToInsert until root
        //Calculate its height and balance if necessary

        Queue<BinaryTreeNode> parentNodesUntilRoot = new Queue<>(BinaryTreeNode.class);

        BinaryTreeNode parentNode = nodeToInsert.getParentNode();
        while(parentNode != null) {
            parentNodesUntilRoot.add(parentNode);
            parentNode = parentNode.getParentNode();
        }

        while(!parentNodesUntilRoot.isEmpty()){
            boolean rotationPerformed = balanceNode(parentNodesUntilRoot.remove());
            if(rotationPerformed) break;
        }
    }

    /**
     * An algorithm that balance de subtree from the node that is sent to us by parameter
     * @param treeNode the node that we are balancing
     */
    private boolean balanceNode(BinaryTreeNode treeNode) {
        if(!(treeNode instanceof Algorithm)) throw new IllegalArgumentException();
        int balancingFactor = treeNode.calculateBalancingFactor();

        if(balancingFactor < -1){
            //Right subtree is not balanced
            //Should we perform a right-right rotation or right-left?
            int rightChildBalancingFactor = treeNode.getRightNode().calculateBalancingFactor();
            if(rightChildBalancingFactor == -1) //Right-right rotation
                rightRightRotation(treeNode);
            else if(rightChildBalancingFactor == 1) //Right-left rotation
                rightLeftRotation(treeNode);

            return true;
        }
        else if(balancingFactor > 1){
            //Left subtree is not balanced
            //Should we perform a left-left rotation or left-right?
            int leftChildBalancingFactor = treeNode.getLeftNode().calculateBalancingFactor();
            if(leftChildBalancingFactor == -1) //Left-right rotation
                leftRightRotation(treeNode);
            else if(leftChildBalancingFactor == 1) //Left-left rotation
                leftLeftRotation(treeNode);

            return true;
        }

        return false;
    }

    private void leftLeftRotation(BinaryTreeNode node) {
        Algorithm y = (Algorithm) node.getLeftNode();
        if(y.getRightNode() != null){
            node.setLeftNode(y.getRightNode());
            node.getLeftNode().setParentNode(node);
        }
        else node.setLeftNode(null);

        y.setRightNode(node);
        y.setParentNode(node.getParentNode());
        node.setParentNode(y);

        //If y has no parent node, it means it is now the root node
        if(y.getParentNode() == null) rootNode = y;
        else{
            if(y.getParentNode().getLeftNode() == node) y.getParentNode().setLeftNode(y);
            else if(y.getParentNode().getRightNode() == node) y.getParentNode().setRightNode(y);
        }
    }

    private void rightRightRotation(BinaryTreeNode node) {
        Algorithm x = (Algorithm) node.getRightNode();
        if(x.getLeftNode() != null){
            node.setRightNode(x.getLeftNode());
            node.getRightNode().setParentNode(node);
        }
        else node.setRightNode(null);

        x.setLeftNode(node);
        x.setParentNode(node.getParentNode());
        node.setParentNode(x);

        //If x has no parent node, it means it is now the root node
        if(x.getParentNode() == null) rootNode = x;
        else{
            if(x.getParentNode().getLeftNode() == node) x.getParentNode().setLeftNode(x);
            else if(x.getParentNode().getRightNode() == node) x.getParentNode().setRightNode(x);
        }
    }

    private void leftRightRotation(BinaryTreeNode node){
        Algorithm x = (Algorithm) node.getLeftNode(); //estic en x
        node.setLeftNode(x.getRightNode()); //la esquerra de D li assigno la y
        node.getLeftNode().setParentNode(node); //li assigno a la y el seu parent que es la D

        x.setRightNode(node.getLeftNode().getLeftNode()); //li assigno a la dreta de x el node T2
        if(x.getRightNode() != null) x.getRightNode().setParentNode(x);

        node.getLeftNode().setLeftNode(x); //la esquerra de y li assigno la x
        node.getLeftNode().getLeftNode().setParentNode(node.getLeftNode()); //li assigno a la x el seu parent que es la y

        //Finally, we perform a left-left rotation
        leftLeftRotation(node);
    }

    private void rightLeftRotation (BinaryTreeNode node){
        Algorithm y = (Algorithm) node.getRightNode();
        node.setRightNode(y.getLeftNode());
        node.getRightNode().setParentNode(node);

        y.setLeftNode(node.getRightNode().getRightNode());
        if(y.getLeftNode() != null) y.getLeftNode().setParentNode(y);

        node.getRightNode().setRightNode(y);
        node.getRightNode().getRightNode().setParentNode(node.getRightNode());

        //Finally, we perform a right-right rotation
        rightRightRotation(node);
    }


    @Override
    public boolean nodeExists(int id) {
        return getNodeById(id) != null;
    }

    public Algorithm getNodeById(int id){
        return preorderSearchByID(rootNode, id);
    }

    public Algorithm getNodeByTimestamp(int timestamp){
        return binarySearchByTimestamp(rootNode, timestamp);
    }

    public ArrayList<Algorithm> getRangeNodeByTimestamp(int minTimestamp, int maxTimestamp) {
        ArrayList<Algorithm> algorithmList = new ArrayList<>(Algorithm.class);
        return binaryRangeSearchByTimestamp(rootNode, algorithmList, minTimestamp, maxTimestamp);
    }

    private Algorithm preorderSearchByID(Algorithm algorithm, int id){
        if(algorithm.getId() == id) return algorithm;

        if(algorithm.getLeftNode() != null){
            Algorithm solution = preorderSearchByID((Algorithm)algorithm.getLeftNode(), id);
            if(solution != null) return solution;
        }
        if(algorithm.getRightNode() != null){
            return preorderSearchByID((Algorithm)algorithm.getRightNode(), id);
        }
        return null;
    }

    /**
     * Algorithm that implements the binary search of the value we are ordering the tree, the timestamp in our case.
     * @param algorithm the algorithm (the node) we are comparing at the moment
     * @param timestamp the timestamp that we are searching and comparing all the time with the algorithm timestamp
     * @return the algorithm that has the timestamp we want to find or null if the timestamp doesn't exist
     */
    private Algorithm binarySearchByTimestamp(Algorithm algorithm, int timestamp){
        if(algorithm.getTimestamp() == timestamp) return algorithm;

        if (timestamp < algorithm.getTimestamp()) {
            // miro a l'esquerra
            if(algorithm.getLeftNode() != null)
                return binarySearchByTimestamp((Algorithm)algorithm.getLeftNode(), timestamp);

        } else {
            // miro a la dreta
            if(algorithm.getRightNode() != null){
                return binarySearchByTimestamp((Algorithm)algorithm.getRightNode(), timestamp);
            }
        }

        return null;
    }

    /**
     * Algorithm that implements the binary search of a value range given of the timestamp nodes.
     * @param actualAlgorithm the algorithm (the node) we are comparing at the moment
     * @param solutionList the list of algorithms that have the timestamp in the range between min and max Timestamp
     * @param minTimestamp the minimum timestamp that we are searching and comparing with the algorithm timestamp
     * @param maxTimestamp the maximum timestamp that we are searching and comparing with the algorithm timestamp
     * @return the list of algorithms that have the timestamp in the range between min and max Timestamp
     */
    private ArrayList<Algorithm> binaryRangeSearchByTimestamp(Algorithm actualAlgorithm, ArrayList<Algorithm> solutionList, int minTimestamp, int maxTimestamp) {
        //System.out.println(actualAlgorithm.timestampSearchString()); // debugging
        if(actualAlgorithm != null) {
            if(minTimestamp <= actualAlgorithm.getTimestamp()  && actualAlgorithm.getTimestamp() <= maxTimestamp)
                solutionList.add(actualAlgorithm);

            if (minTimestamp > actualAlgorithm.getTimestamp()) {
                // miro a la dreta
                if(actualAlgorithm.getRightNode() != null)
                    binaryRangeSearchByTimestamp((Algorithm)actualAlgorithm.getRightNode(), solutionList, minTimestamp, maxTimestamp);

            } else {
                if ((maxTimestamp < actualAlgorithm.getTimestamp())) {
                    // miro a la esquerra
                    if(actualAlgorithm.getLeftNode() != null)
                        binaryRangeSearchByTimestamp((Algorithm) actualAlgorithm.getLeftNode(), solutionList,minTimestamp, maxTimestamp);

                } else {
                    // haig de mirar a la dreta i a l'esquerra perquè el rang esta entre els 2
                    if(actualAlgorithm.getRightNode() != null)
                        binaryRangeSearchByTimestamp((Algorithm)actualAlgorithm.getRightNode(), solutionList, minTimestamp, maxTimestamp);

                    if(actualAlgorithm.getLeftNode() != null)
                        binaryRangeSearchByTimestamp((Algorithm) actualAlgorithm.getLeftNode(), solutionList, minTimestamp, maxTimestamp);
                }
            }
        }

        return solutionList;
    }

    public void listAlgorithms() {
        inorder(rootNode);
    }

    @Override
    public String remove(int id) {
        Algorithm algorithmToRemove = preorderSearchByID(rootNode, id);
        if(algorithmToRemove == null) return null;


        if (algorithmToRemove.getRightNode() == null && algorithmToRemove.getLeftNode() == null) {
            //Delete current node. It has no child, so no further operations are required
            substitute(algorithmToRemove, null);
        }
        else if(algorithmToRemove.getRightNode() != null && algorithmToRemove.getLeftNode() == null){
            //If we have right node but not left node, substitute its right node for him in the tree
            substitute(algorithmToRemove, algorithmToRemove.getRightNode());
        }
        else if(algorithmToRemove.getRightNode() == null && algorithmToRemove.getLeftNode() != null){
            //If we have left node but not right node, substitute its left node for him in the tree
            substitute(algorithmToRemove, algorithmToRemove.getLeftNode());
        }
        else{
            //If the node has both right and left node, we'll substitute it for the next biggest number
            // (the first in inorder from the left subtree).
            // Take into account that that node can have a child (a right child only). If it has, we need to
            // substitute its right child for him.

            BinaryTreeNode nextBiggestNumber = algorithmToRemove.getRightNode();
            while(nextBiggestNumber.getLeftNode() != null){
                nextBiggestNumber = nextBiggestNumber.getLeftNode();
            }

            //Now that we have the next biggest number, substitute the node we want to remove for him.
            //If the next biggest number has a right child, substitute it for him first
            if(nextBiggestNumber.getRightNode() != null) substitute(nextBiggestNumber, nextBiggestNumber.getRightNode());
            substitute(algorithmToRemove, nextBiggestNumber);

            //Finally, maintain the tree structure by deleting
            // the childs of the nextBiggestNumber and putting to it the childs of the algorithmToRemove
            nextBiggestNumber.setLeftNode(algorithmToRemove.getLeftNode());
            nextBiggestNumber.getLeftNode().setParentNode(nextBiggestNumber);
            nextBiggestNumber.setRightNode(algorithmToRemove.getRightNode());
            nextBiggestNumber.getRightNode().setParentNode(nextBiggestNumber);
        }

        balanceTree(algorithmToRemove);
        return algorithmToRemove.getName();
    }


    @Override
    public void substitute(BinaryTreeNode nodeToSubstitute, BinaryTreeNode nodeSubstitutor){
        if(!(nodeToSubstitute instanceof Algorithm) || (!(nodeSubstitutor instanceof Algorithm) && nodeSubstitutor != null))
            throw new IllegalArgumentException();

        //Remove reference from parent of the substitutor
        if(nodeSubstitutor != null){
            if(nodeSubstitutor.getParentNode().getRightNode() == nodeSubstitutor){
                nodeSubstitutor.getParentNode().setRightNode(null);
            }
            else if(nodeSubstitutor.getParentNode().getLeftNode() == nodeSubstitutor){
                nodeSubstitutor.getParentNode().setLeftNode(null);
            }
        }

        //Substitute
        BinaryTreeNode parentNode = nodeToSubstitute.getParentNode();
        if(parentNode == null){
            //If we have no parent, it means we are in the root node
            rootNode = (Algorithm) nodeSubstitutor;
            if(nodeSubstitutor != null) nodeSubstitutor.setParentNode(null);
        }
        else if(parentNode.getLeftNode() == nodeToSubstitute){
            parentNode.setLeftNode(nodeSubstitutor);
            if(nodeSubstitutor != null) nodeSubstitutor.setParentNode(parentNode);
        }
        else if(parentNode.getRightNode() == nodeToSubstitute){
            parentNode.setRightNode(nodeSubstitutor);
            if(nodeSubstitutor != null) nodeSubstitutor.setParentNode(parentNode);
        }
    }

    /**
     * Method that implements the inorder route changing right and left visiting
     * in order to show from biggest to lowest timestamp values
     * @param treeNode the node we are looking at the moment
     */
    public void inorder(BinaryTreeNode treeNode){
        if(treeNode.getRightNode() != null){
            inorder(treeNode.getRightNode());
        }
        // Mostrem l'algorisme
        System.out.println(treeNode.toPrettyString());

        if (treeNode.getLeftNode() != null){
            inorder(treeNode.getLeftNode());
        }
    }


}
