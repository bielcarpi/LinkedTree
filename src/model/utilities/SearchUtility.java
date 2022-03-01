package model.utilities;

import model.interfaces.Graph;
import model.interfaces.GraphNode;

import java.lang.reflect.Array;
import java.util.ArrayList;

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
     * @param firstNode The first node of the Graph
     * @param disconnected Whether the user wants to explore the disconnected nodes of the graph or not
     * @param changeInLevel In order to see when there is a change in level (e.g. a node has expanded),
     *                      mark this option as true and whenever there is a change in level a null GraphNode
     *                      will be put to the Queue
     * @return A queue of {@link GraphNode} representing the order of the BFS Algorithm
     */
    public static Queue<GraphNode> bfs(Graph graph, GraphNode firstNode, boolean disconnected, boolean changeInLevel){
        graph = graph.clone(); //Clone the graph passed so as not to modify the original one
        firstNode = firstNode.clone(); //Clone the node passed so as not to modify the original one

        //If the user don't want the disconnected option
        if(!disconnected) return bfsImplementation(graph, firstNode, changeInLevel);

        //If the user wants the disconnected option
        ArrayList<Queue<GraphNode>> queues = new ArrayList<>();
        queues.add(bfsImplementation(graph, firstNode, changeInLevel));

        GraphNode[] nodes = graph.getGraph();
        for(GraphNode gn: nodes){
            if(!gn.isVisited()) queues.add(bfsImplementation(graph, gn, changeInLevel));
        }

        Queue<GraphNode> resultQueue = new Queue<>(GraphNode.class);
        for(int i = 0; i < queues.size(); i++){
            while (!queues.get(i).isEmpty()) resultQueue.add(queues.get(i).remove());
            if(i != queues.size() - 1) //If we're not in the last Queue
                if(changeInLevel) resultQueue.add(null); //Add a null, as we're in next level
        }

        return resultQueue;
    }

    private static Queue<GraphNode> bfsImplementation(Graph graph, GraphNode node, boolean changeInLevel){
        Queue<GraphNode> bfsQueue = new Queue<>(GraphNode.class); //Create a Queue of GraphNodes for the algorithm
        Queue<GraphNode> resultQueue = new Queue<>(GraphNode.class); //Create a Queue of GraphNodes for the result

        bfsQueue.add(node); //Add the first node passed
        node.setVisited(true);

        GraphNode[] adjacents;
        while(!bfsQueue.isEmpty()){
            GraphNode tmp = bfsQueue.remove();
            resultQueue.add(tmp); //Every time we explore and pop a Node, add it to the result queue

            adjacents = graph.getAdjacent(tmp);
            boolean firstAdjacentNotVisited = true;
            for(int i = 0; i < adjacents.length; i++){ //Go through all adjacent nodes
                if(!adjacents[i].isVisited()){ //If the adjacent is not visited...
                    if(firstAdjacentNotVisited && changeInLevel) resultQueue.add(null);
                    firstAdjacentNotVisited = false;
                    bfsQueue.add(adjacents[i]); //Add id to the queue
                    adjacents[i].setVisited(true); //And set its visited property to true
                }
            }
        };

        return resultQueue;
    }
}
