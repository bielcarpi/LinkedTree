package model;

import model.graph.Recommendation;
import model.graph.UserGraph;
import model.graph.interfaces.GraphNode;
import model.tree.AlgorithmTree;
import model.utilities.Queue;
import model.utilities.Stack;

import java.io.IOException;

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
}
