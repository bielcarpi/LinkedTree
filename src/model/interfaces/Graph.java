package model.interfaces;

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
     * Returns the biggest node of the graph
     * @return The biggest node of the graph
     */
    GraphNode getBiggestNode();

    /**
     * Returns the current Graph
     * @return The current Graph
     */
    GraphNode[] getGraph();
}
