public class User {
    private int id;
    private String name;
    private String alias;
    private String[] interests;

    public User(int id, String name, String alias, String[] interests){
        this.id = id;
        this.name = name;
        this.alias = alias;
        this.interests = interests;
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

    public boolean hasInterest(String interestToCheck){
        for(String interest: interests)
            if(interest.equals(interestToCheck)) return true;

        return false;
    }

}
