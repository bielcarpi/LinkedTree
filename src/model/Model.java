package model;

import model.graph.Recommendation;
import model.graph.UserGraph;
import model.graph.interfaces.GraphNode;
import model.tree.Algorithm;
import model.tree.AlgorithmTree;
import model.utilities.ArrayList;
import model.utilities.Queue;
import model.utilities.Stack;

import java.io.IOException;
import java.util.List;

public class Model {

    private final UserGraph userGraph;
    private final UserGraph dramaGraph;
    private final AlgorithmTree algorithmTree;

    public Model(final String graphFileName, final String dramaFileName, final String treeFileName)
            throws IOException {
        userGraph = new UserGraph(graphFileName);
        dramaGraph = new UserGraph(dramaFileName);
        algorithmTree = new AlgorithmTree(treeFileName);
    }

    public Queue<GraphNode> exploreNetwork(){
        return userGraph.getNodesBfs();
    }

    public boolean userExists(int id){
        return userGraph.getUser(id) != null;
    }

    public Recommendation[] getFollowRecommendation(int id){
        return userGraph.getFollowRecommendation(id);
    }

    public Stack<GraphNode> contextualizeDrama(){
        return dramaGraph.getNodesTopo();
    }

    public Stack<GraphNode> getContactChain(int userId, int userIdObjective){
        return userGraph.getDijkstra(userId, userIdObjective);
    }

    public boolean algorithmExists(int id){
        return algorithmTree.nodeExists(id);
    }

    public void addNewAlgorithm(int id, String name, String language, String cost, int timestamp){
        if(algorithmExists(id)) throw new IllegalArgumentException();
        algorithmTree.insert(new Algorithm(id, name, language, cost, timestamp));
    }

    public Algorithm searchByTimestamp(int timestamp) {
        return algorithmTree.getNodeByTimestamp(timestamp);
    }

    public ArrayList<Algorithm> searchByRangeTimestamp(int minTimestamp, int maxTimestamp) {
        return algorithmTree.getRangeNodeByTimestamp(minTimestamp, maxTimestamp);
    }

    public void listAlgorithms() {
        algorithmTree.listAlgorithms();
    }

    public String removeAlgorithm(int id){
        return algorithmTree.remove(id);
    }
}
