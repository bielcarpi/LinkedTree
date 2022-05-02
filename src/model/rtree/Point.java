package model.rtree;

public class Point {
    private final float x;
    private final float y;

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

    /**
     * Method that evaluates the distance between two points
     * @param point1 First point
     * @param point2 Second point
     * @return The module of the vector that represents the absolute distance between the two points
     */
    public static float evaluateDistance(Point point1, Point point2){
        return (float) Math.sqrt((point2.x - point1.x)*(point2.x - point1.x) +
                (point2.y - point1.y)*(point2.y - point1.y));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Float.compare(point.x, x) == 0 && Float.compare(point.y, y) == 0;
    }
}
