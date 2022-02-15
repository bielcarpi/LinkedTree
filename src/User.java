import java.util.ArrayList;
import java.util.Arrays;

public class User implements Comparable<User>{
    private int id;
    private String name;
    private String alias;
    private String[] interests;
    private ArrayList<Follow> follows;

    private static User userAux;

    public User(int id, String name, String alias, String[] interests){
        this.id = id;
        this.name = name;
        this.alias = alias;
        this.interests = interests;
    }

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
