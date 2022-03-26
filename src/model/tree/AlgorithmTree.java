package model.tree;

import model.tree.interfaces.BinaryTree;
import model.tree.interfaces.BinaryTreeNode;
import model.utilities.ArrayList;

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
            }
            else insertImplementation(parentNode.getLeftNode(), nodeToInsert);
        }
        else if(nodeToInsert.compareTo(parentNode) > 0){ //If the nodeToInsert is bigger than the parentNode
            if(parentNode.getRightNode() == null){
                parentNode.setRightNode(nodeToInsert);
                nodeToInsert.setParentNode(parentNode);
            }
            else insertImplementation(parentNode.getRightNode(), nodeToInsert);
        }
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

            System.out.println("hola");
        }

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
