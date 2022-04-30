package model.rtree;

import java.util.ArrayList;

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
     * Given an ArrayList of {@link Point}, this method returns the two points
     * that are farther away.
     * @param points The ArrayList of points to compare
     * @return An array with positions 0 and 1, with the farthest points
     */
    public static Point[] getFarthestPoints(ArrayList<Point> points){
        Point[] fartherPoints = new Point[2];
        float fartherDistance = 0.0f;

        for (int i = 0; i < points.size(); i++) {
            for (int j = 0; j < points.size(); j++) {
                float currentDistance = evaluateDistance(points.get(i), points.get(j));
                if (currentDistance > fartherDistance){
                    fartherPoints[0] = points.get(i);
                    fartherPoints[1] = points.get(j);
                    fartherDistance = currentDistance;
                }
            }
        }
        return fartherPoints;
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
