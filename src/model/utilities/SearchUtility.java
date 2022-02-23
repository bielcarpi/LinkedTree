package model.utilities;

import model.interfaces.Graph;
import model.interfaces.GraphNode;

/**
 * The SearchUtility class provides methods for exploring a Graph in a specific way.
 * <p>The methods of this class will return a Data Structure with the Graph's elements ordered by the search criteria.
 * <p>To use the SearchUtility, the Graph and nodes of the Graph need to implement the interfaces
 * {@link Graph} and {@link GraphNode}
 *
 * @see Graph
 * @see GraphNode
 */
public class SearchUtility {

    /**
     * Given a graph and the first node to explore, it returns a Queue of nodes with the
     * proper order when running a BFS algorithm.
     * <p>Once the BFS ends with the first node to explore
     * <p><b>Important note: </b> The elements of the returned queue will not be the original ones. Instead,
     * they'll be copies of the original elements.
     *
     * @param graph The graph to run the BFS
     * @param node The first node of the Graph
     * @return A queue of {@link GraphNode} representing the order of the BFS Algorithm
     */
    private void disconnectedBfs() {

    }

    /**
     * Given the first node to explore, it returns a Queue of nodes with the
     * proper order when running a BFS algorithm.
     * <p><b>Important note: </b> The elements of the returned queue will not be the original ones. Instead,
     * they'll be copies of the original elements.
     *
     * @param graph The graph to run the BFS
     * @param node The first node of the Graph
     * @return A queue of {@link GraphNode} representing the order of the BFS Algorithm
     */
    public static Queue<GraphNode> bfs(Graph graph, GraphNode node){
        graph = graph.clone(); //Clone the graph passed so as not to modify the original one
        node = node.clone(); //Clone the node passed so as not to modify the original one

        Queue<GraphNode> bfsQueue = new Queue<>(GraphNode.class); //Create a Queue of GraphNodes for the algorithm
        Queue<GraphNode> resultQueue = new Queue<>(GraphNode.class); //Create a Queue of GraphNodes for the result

        bfsQueue.add(node); //Add the first node passed
        node.setVisited(true);

        GraphNode[] adjacents;
        while(bfsQueue.isEmpty()){
            GraphNode tmp = bfsQueue.remove();
            resultQueue.add(tmp); //Every time we explore and pop a Node, add it to the result queue

            adjacents = graph.getAdjacent(tmp);
            for(int i = 0; i < adjacents.length; i++){ //Go through all adjacent nodes
                if(!adjacents[i].isVisited()){ //If the adjacent is not visited...
                    bfsQueue.add(adjacents[i]); //Add id to the queue
                    adjacents[i].setVisited(true); //And set its visited property to true
                }
            }
        };

        return resultQueue;
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
