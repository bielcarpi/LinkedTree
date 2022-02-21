import java.util.ArrayList;
import java.util.Arrays;

public class User implements Comparable<User>{
    private int id;
    private String name;
    private String alias;
    private String[] interests;
    private ArrayList<Follow> follows;

    private boolean visited;

    /**
     * userAux is an auxiliar variable for better performance
     * on the method {@link #getUserWithId(int)}
     * Instead of creating a user each time the method is called,
     * we'll cache a user and change its ID
     */
    private static User userAux;

    public User(int id, String name, String alias, String[] interests){
        this.id = id;
        this.name = name;
        this.alias = alias;
        this.interests = interests;
    }

    /**
     * Returns a User with the ID passed as parameter
     * <p>To be used only for user comparison, as the user returned
     * is not a valid user (all its attributes are null)
     *
     * @param id The ID of the user to be returned
     * @return A user with the ID specified
     */
    public static User getUserWithId(int id) {
        if(userAux == null) return new User(id, null, null, null);

        userAux.id = id;
        return userAux;
    }

    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getAlias(){
        return alias;
    }
    public String[] getInterests(){
        return interests;
    }
    public ArrayList<Follow> getFollows(){
        return follows;
    }

    public boolean hasInterest(String interestToCheck){
        for(String interest: interests)
            if(interest.equals(interestToCheck)) return true;

        return false;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    /**
     * Compares the current user to another user, using its IDs
     * @param that The user to be compared to
     * @return A negative number, 0 or a positive number if this user's ID is
     * less, equal or greater than the other user
     */
    @Override
    public int compareTo(User that) {
        return this.id - that.id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", alias='" + alias + '\'' +
                ", interests=" + Arrays.toString(interests) +
                ", follows=" + follows +
                '}';
    }

    public void setNewFollow(Follow f) {
        if(follows == null) follows = new ArrayList<>();
        follows.add(f);
    }
}
