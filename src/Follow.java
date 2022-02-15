public class Follow {
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
        return "Follow{" +
                "idUserFollowed=" + idUserFollowed +
                ", timestamp=" + timestamp +
                ", interactions=" + interactions +
                '}';
    }
}
