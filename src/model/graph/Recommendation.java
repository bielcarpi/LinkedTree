package model.graph;

import model.graph.interfaces.GraphNode;
import model.utilities.ArrayList;
import model.utilities.SortUtility;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;


/**
 * The class Recommendation represents a follow recommendation for a {@link User}
 * <p>In order to get the recommendations for a particular {@link User}, use {@link #getRecommendations(User, UserGraph)}
 *
 * @see User
 * @see UserGraph
 */
public class Recommendation{

    /**
     * This is the number of max recommendations that the method {@link #getRecommendations(User, UserGraph)}
     * will return
     */
    private static final int MAX_NUM_RECOMMENDATIONS = 10;
    /**
     * This is the number of random Users that the algorithm will look at to find Users to recommend
     */
    private static final int RANDOM_RECOMMENDATIONS = 5000;

    /**
     * The score will add up to this constant for each common interest
     */
    private static final int COMMON_INTEREST_SCORE = 25;
    /**
     * The score will add up to this constant if the recommended user follows the user
     * that wants the recommendation
     */
    private static final int RECOMMENDED_USER_FOLLOWS_HIM_SCORE = 50;
    /**
     * The score will add up to this constant if the user that wants the recommendation
     * follows someone who follows the recommended user
     */
    private static final int COMMON_FRIENDS_SCORE = 100;

    private final User recommendedUser;
    private final boolean followsUser; //Whether follows the user that wants the recommendations or not
    private final int commonInterests;
    private final User[] commonFriends;
    private final int score;
    private final String reasons; //String containing the reasons why the user is recommended

    private Recommendation(User recommendedUser, User[] commonFriends, boolean followsUser, int commonInterests){
        this.recommendedUser = recommendedUser;
        this.commonFriends = commonFriends;
        this.followsUser = followsUser;
        this.commonInterests = commonInterests;

        score = (commonFriends == null? 0: commonFriends.length) * COMMON_FRIENDS_SCORE + (followsUser? RECOMMENDED_USER_FOLLOWS_HIM_SCORE: 0)
                + commonInterests * COMMON_INTEREST_SCORE;
        reasons = prettyReasons();
    }

    /**
     * Returns an Array of Recommendations for the user specified
     * <p>The array will have a max length of 10
     * @param user The user that wants recommendations
     * @param userGraph The graph of users to explore for recommendations
     * @return Array of Recommendations for the user specified (with max length of 10)
     */
    public static Recommendation[] getRecommendations(User user, UserGraph userGraph){

        /*
         * Internal working of the algorithm:
         *
         * It is not necessary to explore the whole graph for recommendations, as we want to
         * be efficient.
         * Our social network gives the most importance to common friends (The user that wants the
         * recommendation follows somebody that follows another user, which will be the
         * possible recommendation)
         *
         *
         * It can be the case where the User doesn't follow anybody (or little people) and
         * anybody follows him. Then, we'll explore a random part of the graph
         * (RANDOM_RECOMMENDATIONS nodes, to be precise) and those Users will be given a score
         * for recommendation (depending again on common interests and users who follow him)
         */

        ArrayList<Recommendation> recommendations = new ArrayList<>(Recommendation.class);
        ArrayList<User> possibleUsersToRecommend = new ArrayList<>(User.class);

        GraphNode[] userFollows = userGraph.getAdjacent(user);

        if(userFollows.length != 0){
            for(GraphNode u: userFollows){ //Loop through users who the user follows
                GraphNode[] uf = userGraph.getAdjacent(u); //Get the follows of the user that the user follows
                for(GraphNode u2: uf){
                    if(!isInsideArray((User) u2, possibleUsersToRecommend))
                        possibleUsersToRecommend.add((User) u2); //Add all these users to possibleUsersToRecommend
                }
            }
        }


        //If the possibleUsersToRecommend found until now is not enough, let's explore a
        // random part of the graph and add it to the possibleUsersToRecommend
        if(possibleUsersToRecommend.size() < MAX_NUM_RECOMMENDATIONS){
            User[] graph = (User[]) userGraph.getGraph();
            int randomUserToStartRecommendations;
            if(graph.length < RANDOM_RECOMMENDATIONS)
                randomUserToStartRecommendations = 0;
            else
                randomUserToStartRecommendations = new Random().nextInt(graph.length);

            for(int i = randomUserToStartRecommendations, k = 0; i < graph.length && k < RANDOM_RECOMMENDATIONS; i++, k++){
                if (!graph[i].equals(user) && !checkIfUserFollowsAnother(user, graph[i])
                        && !isInsideArray(graph[i], possibleUsersToRecommend)) {
                    possibleUsersToRecommend.add(graph[i]);
                }
            }
        }


        //Now that we have the possibleUsersToRecommend, let's create each Recommendation
        for(int i = 0; i < possibleUsersToRecommend.size(); i++){
            Recommendation r = new Recommendation(possibleUsersToRecommend.get(i),
                    getCommonFriends(user, possibleUsersToRecommend.get(i), userGraph),
                    checkIfUserFollowsAnother(possibleUsersToRecommend.get(i), user),
                    checkCommonInterests(user, possibleUsersToRecommend.get(i)));
            recommendations.add(r);
        }


        //Are there any recommendations with score = 0? Delete them before ordering
        for(int i = 0, d = 0; (i-d) < recommendations.size(); i++){
            if(recommendations.get(i - d).score == 0){
                recommendations.remove(i - d);
                d++;
            }
        }


        //Build a native array of recommendations from the ArrayList of recommendations
        Recommendation[] recommendationsNativeArray = new Recommendation[recommendations.size()];
        for(int i = 0; i < recommendationsNativeArray.length; i++)
            recommendationsNativeArray[i] = recommendations.get(i);

        //Order the array by score
        SortUtility.quickSort(recommendationsNativeArray, new Comparator<Recommendation>() {
            @Override
            public int compare(Recommendation o1, Recommendation o2) {
                return o1.score - o2.score;
            }
        });

        //Return the Recommendations. MAX_NUM_RECOMMENDATIONS or less
        return recommendationsNativeArray.length <= MAX_NUM_RECOMMENDATIONS?
                recommendationsNativeArray:
                Arrays.copyOfRange(recommendationsNativeArray, 0, MAX_NUM_RECOMMENDATIONS - 1) ;
    }

    /**
     * Gets the common friends of two users
     * @param u1 the first user
     * @param u2 the second user
     * @param userGraph the graph with all the users
     * @return an array of users representing the common friends between u1 and u2
     */
    private static User[] getCommonFriends(User u1, User u2, UserGraph userGraph){
        //The u1 follows some users. How many of these users follow u2??
        GraphNode[] u1Follows = userGraph.getAdjacent(u1);

        ArrayList<User> commonFriends = new ArrayList<>(User.class);
        for(int i = 0; i < u1Follows.length; i++){
            ArrayList<Follow> follows = ((User) u1Follows[i]).getFollows(); //The follows of a User that you follow
            if(follows != null){ //If there are follows...
                for(int j = 0; j < follows.size(); j++){ //We want to know if some follow is u2
                    if(u2.getId() == follows.get(j).getIdUserFollowed()){
                        commonFriends.add((User) u1Follows[i]); //If some follow is u2, add that user to common friends
                        break;
                    }
                }
            }
        }

        User[] commonFriendsNativeArray = new User[commonFriends.size()];
        for(int i = 0; i < commonFriendsNativeArray.length; i++)
            commonFriendsNativeArray[i] = commonFriends.get(i);

        return commonFriendsNativeArray;
    }

    /**
     * Returns if a user is inside of an array of users
     * @param u a user
     * @param array the array of users
     * @return true if the user is inside of the array of users and false if not
     */
    private static boolean isInsideArray(User u, ArrayList<User> array){
        for(int i = 0; i < array.size(); i++)
            if(array.get(i).equals(u)) return true;

        return false;
    }

    private static boolean checkIfUserFollowsAnother(User u1, User u2){
        boolean userFollowsAnother = false;
        ArrayList<Follow> follows = u1.getFollows();
        if(follows == null) return false;

        for(int i = 0; i < follows.size(); i++){
            if(follows.get(i).getIdUserFollowed() == u2.getId()){
                userFollowsAnother = true;
                break;
            }
        }

        return userFollowsAnother;
    }

    private static int checkCommonInterests(User u1, User u2){
        int commonInterests = 0;
        String[] u1Interests = u1.getInterests();
        if(u1Interests != null && u1Interests.length != 0){
            for(String interest: u1Interests)
                if(u2.hasInterest(interest)) commonInterests++;
        }

        return commonInterests;
    }

    @Override
    public String toString() {
        return recommendedUser.toPrettyString() +
                "\n\tMotius: " + reasons;
    }


    private String prettyReasons(){
        StringBuilder sb = new StringBuilder();

        if(commonInterests != 0){
            sb.append("Compartiu ").append(commonInterests).append(" interessos");
        }

        if(followsUser){
            if(!sb.isEmpty()) sb.append(" - ");
            sb.append("Et segueix");
        }

        if(commonFriends != null && commonFriends.length != 0){
            if(!sb.isEmpty()) sb.append(" - ");
            sb.append("Seguit per ").append(commonFriends.length).append(": ");
            for(User u: commonFriends)
                sb.append(u.getAlias()).append(", ");
            sb.deleteCharAt(sb.length() - 2);
        }

        return sb.toString();
    }
}
