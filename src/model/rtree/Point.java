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

    private float evaluateDistance(Point point1, Point point2){
        float dist = 0;
        dist = (float) Math.sqrt((point2.x - point1.x)*(point2.x - point1.x) +
                (point2.y - point1.y)*(point2.y - point1.y));
        return dist;
    }

    public static Point[] getLongestDistance(Point[] points){
        Point[] fartherPoints = new Point[2];

        float currentDistance = 0.0F;
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points.length; j++) {
                if (evaluateDistance(points[i],points[j]) > currentDistance){
                    fartherPoints[0] = points[i];
                    fartherPoints[1] = points[j];
                    currentDistance = evaluateDistance(points[i], points[j]);
                }
            }
        }
        return fartherPoints;
    }
}
