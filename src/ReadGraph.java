import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReadGraph {

    private static final int INTEREST_FIELD = 3;
    private static User[] users;
    private static Follow[] follows;

    public static void read(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("Graphs/" + fileName));

        String currentLine = br.readLine();
        int numUsers = Integer.parseInt(currentLine);
        users = new User[numUsers];

        for(int i = 0; i < numUsers; i++){
            currentLine = br.readLine();
            //Tractem usuaris
            String[] userFields = currentLine.split(";");
            String[] interests = userFields[INTEREST_FIELD].split(",");
            users[i] = new User(Integer.parseInt(userFields[0]), userFields[1], userFields[2], interests);
        }


        currentLine = br.readLine();
        int numFollows = Integer.parseInt(currentLine);
        follows = new Follow[numFollows];

        for(int i = 0; i < numFollows; i++){
            currentLine = br.readLine();
            //Tractem follows
            String[] followFields = currentLine.split(";");
            follows[i] = new Follow(Integer.parseInt(followFields[0]), Integer.parseInt(followFields[1]), Integer.parseInt(followFields[2]), Integer.parseInt(followFields[3]));
        }
    }

    public static User[] getUsers(){
        return users;
    }
    public static Follow[] getFollows(){
        return follows;
    }
}
