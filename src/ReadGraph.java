import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class ReadGraph {

    private static User[] users;
    private static Follow[] follows;

    public static void read(String fileName) throws FileNotFoundException {
        BufferedReader br = new BufferedReader(new FileReader("Graphs/" + fileName));

        String currentLine = br.readLine();
        int numUsers = Integer.parseInt(currentLine);
        users = new User[numUsers];

        for(int i = 0; i < numUsers; i++){
            currentLine = br.readLine();
            //Tractem usuaris
        }


        currentLine = br.readLine();
        int numFollows = Integer.parseInt(currentLine);
        follows = new Follow[numFollows];

        for(int i = 0; i < numFollows; i++){
            currentLine = br.readLine();
            //Tractem follows
        }
    }

    public static User[] getUsers(){
        return users;
    }
    public static Follow[] getFollows(){
        return follows;
    }
}
