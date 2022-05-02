package model.rtree;

public class Point {
    private float x;
    private float y;

    public Point(float x, float y){
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY(){
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    /**
     * Method that evaluates the distance between two points
     * @param point1 First point
     * @param point2 Second point
     * @return The module of the vector that represents the absolute distance between the two points
     */
    private static float evaluateDistance(Point point1, Point point2){
        return (float) Math.sqrt((point2.x - point1.x)*(point2.x - point1.x) +
                (point2.y - point1.y)*(point2.y - point1.y));
    }

}
