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

    public static float evaluateDistance(Point point1, Point point2){
        return (float) Math.sqrt((point2.x - point1.x)*(point2.x - point1.x) +
                (point2.y - point1.y)*(point2.y - point1.y));
    }

}
