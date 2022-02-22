package model.utilities;

import model.User;

public class SearchUtility {

    public void disconnectedDfs(User[] graph) {
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
    }
}
