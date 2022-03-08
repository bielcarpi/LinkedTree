package model.graph.interfaces;

public interface GraphNode extends Cloneable, Comparable<GraphNode> {

    /**
     * Checks whether the Node is visited or not
     * @return Whether the node is visited or not
     */
    boolean isVisited();

    /**
     * Sets the visited property of the Node
     * @param visited Whether the node is visited or not
     */
    void setVisited(boolean visited);

    /**
     * Clones the node
     * @return A clone of the node
     */
    GraphNode clone();

    /**
     * Returns a pretty string representing the node
     * @return A pretty string representing the node
     */
    String toPrettyString();

    String dramaToString();


}
