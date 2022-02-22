import java.io.IOException;

public class Graph {
    private User[] graph;

    public Graph(){
        try{
            String fileName = "graphXS.paed";
            ReadGraph.read(fileName);
            graph = ReadGraph.getGraph();

            for(int i = 0; i < graph.length; i++)
                System.out.println(graph[i]);

            System.out.println();
            User tmp = mostFollowedUser(graph);
            System.out.println(tmp.toString());
            
            //Comencem a recorre el graph des del usuari 0.
            //TODO: Parlarho amb el pol, no segur que el que he fet esta be
            //dfs(graph, graph[0]);
            //disconnectedDfs();

        }catch (IOException e){
            e.printStackTrace();
            graph = null;
        }
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


    public void disconnectedDfs() {
        //controlar quan graph[i].getFollows() == null
        for (int i = 0; i < (graph[i].getFollows() == null ? 0: graph[i].getFollows().size()); i++) {
            if (!graph[i].isVisited()){
                dfs(graph, graph[i]);
            }
        }
    }

    private void dfs(User[] graph, User node) {
        node.setVisited(true); //Marquem com a visitat
        //Fer les operacions necessàries
        System.out.println(node.toString());
        for (int i = 0; i < node.getFollows().size(); i++) {
            if (!graph[i].isVisited() && (graph[i].getId() == node.getFollows().get(i).getIdUserFollowed())){
                dfs(graph, graph[i]);
            }
        }
    }

}
