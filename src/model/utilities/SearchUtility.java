package model.utilities;

import model.User;

/**
 * The SearchUtility class provides methods for exploring a Graph in a specific way.
 * <p>The methods of this class will return a Data Structure with the Graph's elements ordered.
 */
public class SearchUtility {

    private void disconnectedBFS() {

    }

    public void bfs(User[] graph, User node){
        Queue<User> queue = new Queue<>(User.class);
        queue.add(node);
        node.setVisited(true);

        while (!queue.isEmpty()){
            User tmp = queue.remove();
            for (int i = 0; i < graph.length; i++) {
                for (int j = 0; j < graph[j].getFollows().size(); j++) {
                    if (!graph[j].isVisited()){
                        queue.add(graph[j]);
                        graph[j].setVisited(true);
                    }
                }
            }
        }
    }


    /*public void disconnectedDfs(User[] graph) {
        //Comencem a recorre el graph des del usuari 0.
        //TODO: Parlarho amb el pol, no segur que el que he fet esta be
        //dfs(graph, graph[0]);

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
    }*/

}
