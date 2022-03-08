package model.graph;

import model.utilities.SortUtility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

public class ReadGraph {

    private static final int INTEREST_FIELD = 3;
    private static User[] users;

    public static void read(String fileName) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader("Graphs/" + fileName));

        String currentLine = br.readLine();
        int numUsers = Integer.parseInt(currentLine);
        users = new User[numUsers];

        for(int i = 0; i < numUsers; i++){
            currentLine = br.readLine();
            //Tractem usuaris
            String[] userFields = currentLine.split(";");
            String[] interests = userFields.length == 4? userFields[INTEREST_FIELD].split(","): null;
            users[i] = new User(Integer.parseInt(userFields[0]), userFields[1], userFields[2], interests);
        }

        //Once we have the array of users, let's order it using the QuickSort we implemented in the last practice
        SortUtility.quickSort(users, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o2.getId() - o1.getId();
            }
        });

        currentLine = br.readLine();
        int numFollows = Integer.parseInt(currentLine);

        for(int i = 0; i < numFollows; i++){
            currentLine = br.readLine();
            //Tractem follows
            String[] followFields = currentLine.split(";");
            Follow f = new Follow(Integer.parseInt(followFields[1]), Integer.parseInt(followFields[2]), Integer.parseInt(followFields[3]));
            int userIndex = Arrays.binarySearch(users, User.getUserWithId(Integer.parseInt(followFields[0])));
            users[userIndex].addNewFollow(f);
        }
    }

    public static User[] getGraph(){
        return users;
    }

}
