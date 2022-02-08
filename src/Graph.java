import java.io.FileNotFoundException;

public class Graph {

    public Graph(){
        try{
            ReadGraph.read("graphXS.paed");
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

}
