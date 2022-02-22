package model;

import java.io.IOException;

public class Graph {
    private final User[] graph;

    public Graph() throws IOException{
        String fileName = "graphXS.paed";
        ReadGraph.read(fileName);
        graph = ReadGraph.getGraph();

        for(int i = 0; i < graph.length; i++)
            System.out.println(graph[i]);

    }


}
