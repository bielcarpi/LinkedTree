package model.utilities;

import model.graph.interfaces.Graph;
import model.graph.interfaces.GraphNode;

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

    /**
     * Method that topologically orders a graph (in a stack) such that if A-->B, A must come before B.
     * @param g The graph to run the topoSort
     * @return The stack of nodes with the sorting already done
     */
    public static Stack<GraphNode> topoSort(Graph g) {
        Stack<GraphNode> stack = new Stack<>(GraphNode.class);
        g = g.clone();
        GraphNode n;

        // Visitem tots els nodes
        for (int i = 0; i < g.getGraph().length; i++) {
            if (!g.getGraph()[i].isVisited()){
                n = g.getGraph()[i];
                visit(g, n, stack);
            }
        }

        return stack;
    }

    /**
     * Method used to visit a series of nodes by going through their successors.
     * @param g The graph to run the topoSort
     * @param n The node that we are currently visiting
     * @param stack The stack of nodes doing the sorting
     */
    private static void visit(Graph g, GraphNode n, Stack<GraphNode> stack) {
        GraphNode s;
        // Visitem els successors del node que estem visitant
        // per cada node del numero de successors del node
        GraphNode[] successors = g.getAdjacent(n);
        for (int i = 0; i < successors.length; i++) {
            s = successors[i];
            if (!s.isVisited()) {
                visit(g, s, stack);
            }
        }

        n.setVisited(true);
        stack.add(n);
    }

    /**
     *
     * @param graph The graph where the search will take place
     * @param nInitial The initial Node
     * @param nFinal The final Node
     * @return The path from nInitial to nFinal
     */
    public static Stack<GraphNode> dijkstra(Graph graph, GraphNode nInitial, GraphNode nFinal){
        int numberOfNodes = 0;
        graph = graph.clone();
        nInitial = graph.getNode(nInitial);
        nFinal = graph.getNode(nFinal);

        int newCost;

        // We start from the initial node
        DijkstraNode nActual = new DijkstraNode(nInitial, null, 0);
        ArrayList<DijkstraNode> dijkstraNodes = new ArrayList<>();
        dijkstraNodes.add(nActual);

        boolean hasUpdated;

        do{
            // For each adjacent node
            GraphNode[] adj = graph.getAdjacent(nActual.node);

            for (int i = 0; i < adj.length; i++) {
                if (!adj[i].isVisited()) {
                    numberOfNodes++; //we increse the number of nodes that we currently evaluate
                    // Cost/Weight
                    newCost = nActual.costToReachNode + graph.getArestaWeight(nActual.node, adj[i]);

                    if(getFromArray(dijkstraNodes, adj[i]) == null){
                        // Addition of the node
                        DijkstraNode dn = new DijkstraNode(adj[i], nActual.node, newCost);
                        dijkstraNodes.add(dn);
                    }
                    else{
                        DijkstraNode dn = getFromArray(dijkstraNodes, adj[i]);
                        if (newCost < dn.costToReachNode) { //If newCost is less than current, update it
                            dn.costToReachNode = newCost; //Update cost to new cost
                            dn.path = nActual.node; //Update path
                        }
                    }
                }
            }

            nActual.node.setVisited(true);

            hasUpdated = false; //Check whether we have updated nActual or not. If we don't update it, we've ended the exploration without solution
            int minValue = Integer.MAX_VALUE; //Minimum value of the non-visited distance
            for (int i = 0; i < dijkstraNodes.size(); i++) {
                //If node is not visited AND it has the best minValue, update actual
                if (!dijkstraNodes.get(i).node.isVisited()) {
                    //Update nNode
                    if (dijkstraNodes.get(i).costToReachNode < minValue){
                        minValue = dijkstraNodes.get(i).costToReachNode;
                        nActual = dijkstraNodes.get(i);
                        hasUpdated = true;
                    }
                }
            }

        } while(!nFinal.isVisited() && hasUpdated);

        // Reconstruction of the path followed
        if (!nFinal.isVisited()) return null;

        Stack<GraphNode> path = new Stack<>(GraphNode.class);
        DijkstraNode dn = getFromArray(dijkstraNodes, nFinal);
        while(true){
            path.add(dn.node);
            dn = getFromArray(dijkstraNodes, dn.path);

            if(dn.node == nInitial){
                path.add(dn.node);
                break;
            }
        }
        System.out.println("Number of nodes evaluated: " + numberOfNodes);
        return path;
    }

    /**
     * Returns a node that is equals to n inside the array
     * @param array the array of DijkstraNode
     * @param n the graph node
     * @return A node that is equals to n inside the array
     */
    private static DijkstraNode getFromArray(ArrayList<DijkstraNode> array, GraphNode n){
        //System.out.println("LLARGADA: " + array.size());
        for(int i = 0; i < array.size(); i++)
            if(array.get(i).equals(n)) return array.get(i);

        return null;
    }

    /**
     * Class to save the node, path and cost of the actual node
     */
    private static class DijkstraNode {
        GraphNode node;
        GraphNode path;
        int costToReachNode;

        private DijkstraNode(GraphNode node, GraphNode path, int costToReachNode){
            this.node = node;
            this.path = path;
            this.costToReachNode = costToReachNode;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null) return false;
            return node.compareTo((GraphNode)o) == 0;
        }
    }
}
