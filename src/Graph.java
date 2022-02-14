import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

public class Graph {
    private static User[] users;
    private static Follow[] follows;

    public Graph(){
        try{
            String fileName = "graphXS.paed";
            ReadGraph.read(fileName);
            users = ReadGraph.getUsers();
            follows = ReadGraph.getFollows();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
