package model.graph;

import model.graph.interfaces.GraphNode;
import model.utilities.ArrayList;

import java.util.Arrays;

public class User implements GraphNode {
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
     * Returns a model.graph.User with the ID passed as parameter
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

    /**
     * Checks whether this user has an interest
     * @param interestToCheck The interest to check
     * @return Whether the User has the interest introduced
     */
    public boolean hasInterest(String interestToCheck){
        if(interests != null && interests.length != 0){
            for(String interest: interests)
                if(interest.equals(interestToCheck)) return true;
        }

        return false;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isVisited() {
        return visited;
    }

    /**
     * {@inheritDoc}
     */
    public void setVisited(boolean visited) {
        this.visited = visited;
    }


    /**
     * Compares the current user to another user, using its IDs
     * <p>If the GraphNode to be compared to is not a User, it will throw an {@link IllegalArgumentException}
     *
     * @param that The user to be compared to
     * @return A negative number, 0 or a positive number if this user's ID is
     * less, equal or greater than the other user
     * @throws IllegalArgumentException When the GraphNode to be compared to is not a User
     */
    @Override
    public int compareTo(GraphNode that) {
        if(that.getClass() != this.getClass()) throw new IllegalArgumentException();
        return this.id - ((User)that).id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public User clone() {
        try{
            return (User) super.clone();
        }catch(CloneNotSupportedException e){
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "model.graph.User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", alias='" + alias + '\'' +
                ", interests=" + Arrays.toString(interests) +
                ", follows=" + follows +
                '}';
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toPrettyString() {
        return "\t" + id + " - " + name + " (" + alias + ")\n\tInteressos: " + (interests == null? "Cap": Arrays.toString(interests));
    }

    @Override
    public String dramaToString() {
        return "\t" + id + " - " + name + " (" + alias + ")";
    }

    /**
     * Adds a new follow to this User
     * @param f The follow that is added to the User
     */
    public void addNewFollow(Follow f) {
        if(follows == null) follows = new ArrayList<>(Follow.class);
        follows.add(f);
    }
}
