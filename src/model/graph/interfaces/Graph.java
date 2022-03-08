package model.graph.interfaces;

public interface Graph extends Cloneable{
    /**
     * Clones the Graph
     * @return A clone of the graph
     */
    Graph clone();

    /**
     * Returns the adjacent nodes of the node passed as parameter
     * @param gn The node to be returned its adjacents
     * @return The adjacent nodes of the node passed as parameter
     */
    GraphNode[] getAdjacent(GraphNode gn);


    /**
     * Returns a node of the graph equal than the one passed
     * @return The node of the graph equal than the one passed
     */
    GraphNode getNode(GraphNode gn);

    /**
     * Returns the biggest node of the graph
     * @return The biggest node of the graph
     */
    GraphNode getBiggestNode();

    /**
     * Returns the current Graph
     * @return The current Graph
     */
    GraphNode[] getGraph();

    /**
     * Returns the weight of the aresta between initial and adj nodes
     * @param initial The initial node
     * @param adj The adjacent node
     * @return The weight of its aresta. -1 if the nodes are not connected
     */
    int getArestaWeight(GraphNode initial, GraphNode adj);
}
