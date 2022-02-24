package model;

import model.interfaces.Graph;
import model.interfaces.GraphNode;
import model.utilities.ArrayList;
import model.utilities.Queue;
import model.utilities.SearchUtility;
import model.utilities.Tuple;

import java.io.IOException;
import java.util.Arrays;

public class UserGraph implements Graph {
    private final User[] graph;

    public UserGraph(String fileName) throws IOException{
        ReadGraph.read(fileName); //Use the ReadGraph helper class to read the file
        graph = ReadGraph.getGraph(); //Load the Graph created from the text file
    }

    public Queue<GraphNode> getNodesBfs(){
        return SearchUtility.bfs(this, getBiggestNode(), true, true);
    }

    /**
     * Given an ID from a User, it returns an ordered array with {@link Recommendation} objects,
     * from best to worse
     * @param id The ID of the user that wants to know its recommendations
     * @return An array with {@link Recommendation} objects, ordered from best to worse recommendation
     * @throws IllegalArgumentException If there is no user with that ID
     *
     * @see Recommendation
     */
    public Recommendation[] getFollowRecommendation(int id){
        User u = getUser(id);
        if(u == null) throw new IllegalArgumentException();

        return Recommendation.getRecommendations(u, this);
    }

    //TODO: Revisar si aquesta funció esta ben feta. Demana l'usuari que segueix a més gent, no el que te més followers
    /**
     * Returns the most followed user of the structure
     * in order to start reading or sorting the nodes of the Graph.
     * @return The most followed user with the index or position specified.
     */
    public User getBiggestNode(){
        int index = 0, lenght = 0;
        //We start the algorithm by saving the number of followers of the first user in order to start comparing
        for (int i = 0; i < graph.length; i++) {
            //Check if the user has any follower or not.
            if (graph[i].getFollows() != null){
                //In case there are more than one user with the same number of followers (User 1 and User 2),
                //we always keep the User 1 as the one we are comparing with.
                if (lenght < graph[i].getFollows().size()){
                    //Update of the index of the most followed user:
                    index = i;
                    //Update of the number of followers to keep comparing:
                    lenght = graph[i].getFollows().size();
                }
            }
            //In case it has no followers, we skip him.
        }
        return  graph[index];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GraphNode[] getGraph() {
        return graph;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Graph clone() {
        try{
            return (Graph) super.clone();
        }catch(CloneNotSupportedException e){
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GraphNode[] getAdjacent(GraphNode gn) {
        if(gn.getClass() != User.class) throw new IllegalArgumentException();

        User u = getUser(((User)gn).getId());
        if(u == null) return null; //If the GraphNode does not exist, return null

        ArrayList<Follow> follows = u.getFollows();
        GraphNode[] adjacentNodes = new GraphNode[follows == null? 0: follows.size()];

        for(int i = 0; i < adjacentNodes.length; i++)
            adjacentNodes[i] = getUser(follows.get(i).getIdUserFollowed());

        return adjacentNodes;
    }

    /**
     * Quick method to get a user from the graph using its id, using binary search
     * @param id The id of the user that wants to be found
     * @return The User within the graph with the ID introduced
     */
    public User getUser(int id) {
        int userIndex = Arrays.binarySearch(graph, User.getUserWithId(id));
        return userIndex >= 0? graph[userIndex]: null;
    }
}
