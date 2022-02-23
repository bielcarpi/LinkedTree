package model;

import model.interfaces.Graph;

import java.io.IOException;

public class UserGraph implements Graph {
    private final User[] graph;

    public UserGraph() throws IOException{
        String fileName = "graphXS.paed";
        ReadGraph.read(fileName);
        graph = ReadGraph.getGraph();

        for (User user : graph) System.out.println(user);
    }


    /**
     * Returns the most followed user of the structure
     * in order to start reading or sorting the nodes of the Graph.
     * @param graph: The Graph from which we want to get the most followed user.
     * @return The most followed user with the index or position specified.
     */
    private User mostFollowedUser (User[] graph){
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


}
