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
        }catch (IOException e){
            e.printStackTrace();
            graph = null;
        }
    }

}
