package model;

public class Follow {
    /**
     * Note: For better performance, instead of saving the ID of the user followed
     * we should save the reference to the User directly.
     * Although our solution is not the best, we've optimized it and each time
     * we need to find a user with its id we use binary search (as the graph is ordered
     * using quickSort)
     */
    private int idUserFollowed;
    private int timestamp;
    private int interactions;

    public Follow(int idUserFollowed, int timestamp, int interactions){
        this.idUserFollowed = idUserFollowed;
        this.timestamp = timestamp;
        this.interactions = interactions;
    }

    public int getIdUserFollowed() {
        return idUserFollowed;
    }
    public int getTimestamp() {
        return timestamp;
    }
    public int getInteractions() {
        return interactions;
    }

    @Override
    public String toString() {
        return "model.Follow{" +
                "idUserFollowed=" + idUserFollowed +
                ", timestamp=" + timestamp +
                ", interactions=" + interactions +
                '}';
    }
}
