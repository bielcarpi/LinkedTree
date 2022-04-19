package model.rtree;

public class Rectangle {
    private Point p1; //Bottom left corner
    private Point p2; //Upper right corner
    private RTreeNode currentNode, childNode;

    public Rectangle(Point p1, Point p2, RTreeNode currentNode, RTreeNode childNode){
        this.p1 = p1;
        this.p2 = p2;

        this.currentNode = currentNode;
        this.childNode = childNode;
        childNode.setParentRectangle(this);
    }

    public Point getP1() {
        return p1;
    }

    public Point getP2 () {
        return p2;
    }

    public void setP1(Point p1) {
        this.p1 = p1;
    }

    public void setP2(Point p2) {
        this.p2 = p2;
    }

    public static float getArea(Point p1, Point p2){
        // width * height
        return (p2.getX() - p1.getX()) * (p2.getY() - p1.getY());
    }

    public RTreeNode getChildNode() {
        return childNode;
    }

    public RTreeNode getCurrentNode() {
        return currentNode;
    }

    /**
     * Given the points that need to be inside a rectangle, the method
     * returns the points p1 and p2 of the rectangle.
     *
     * @param points The points that need to be inside the rectangle
     * @return The points p1 and p2 of the rectangle
     */
    public static Point[] getRectangle(Point[] points){
        float xMin  = Float.MAX_VALUE, yMin = Float.MAX_VALUE, xMax = Float.MIN_VALUE, yMax = Float.MIN_VALUE;
        for(int i = 0; i < points.length; i++) {
            if (points[i].getX() < xMin) {
                xMin = points[i].getX();
            }

            if (points[i].getY() < yMin) {
                yMin = points[i].getY();
            }

            if (points[i].getX() > xMax) {
                xMax = points[i].getX();
            }

            if (points[i].getY() > yMax) {
                yMax = points[i].getY();
            }
        }
        Point[] newPoints = new Point[2];
        newPoints[0] = new Point(xMin, yMin);
        newPoints[1] = new Point(xMax, yMax);
        return newPoints;
    }

    /**
     * Returns the difference between the current area and the area that this rectangle
     * would have if a point p were to be inserted
     *
     * @param p A theoretical point inserted
     * @return The difference between the current area and the area that this rectangle
     * would have if a point p were to be inserted
     */
    public float getAreaDiffWithNewPoint(Point p){
        Point[] newRectanglePoints = getRectangle(new Point[]{p1, p2, p});
        return getArea(newRectanglePoints[0], newRectanglePoints[1]) - getArea(p1, p2);
    }

}
