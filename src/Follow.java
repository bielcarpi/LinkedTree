public class Follow {
    private int idUserFollows;
    private int idUserFollowed;
    private int timestamp;
    private int interactions;

    public Follow(int idUserFollows, int idUserFollowed, int timestamp, int interactions){
        this.idUserFollows = idUserFollows;
        this.idUserFollowed = idUserFollowed;
        this.timestamp = timestamp;
        this.interactions = interactions;
    }

    public int getIdUserFollows() {
        return idUserFollows;
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
}
