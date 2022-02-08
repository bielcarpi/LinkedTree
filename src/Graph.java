import java.io.FileNotFoundException;
import java.io.IOException;

public class Graph {
    public Graph(){
        try{
            String fileName = "graphXS.paed";
            ReadGraph.read(fileName);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
