package model.rtree;


import model.rtree.interfaces.RTreeElement;

import java.util.ArrayList;

/**
 * The class Rectangle represents a rectangle from the {@link RTree}
 * <p>It is built upon two points, and will always have a child {@link RTreeNode}
 *
 * @see RTree
 * @see RTreeNode
 */
public class Rectangle {
    private Point p1; //Bottom left corner
    private Point p2; //Upper right corner
    private final RTreeNode childNode;
    private RTreeNode currentNode;

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

    public void setCurrentNode(RTreeNode node){
        this.currentNode = node;
    }


    /**
     * Returns a point that represents the center of this rectangle
     * @return A point that represents the rectangle's center
     */
    public Point getCenter(){
        float x = (p2.getX() - p1.getX())/2 + p1.getX();
        float y = (p1.getY() - p2.getY())/2 + p2.getY();
        return new Point(x, y);
    }

    /**
     * Given a {@link Point}, grow this rectangle to fill it
     * @param p The point that will now be contained within this rectangle
     */
    public void grow(Point p){
        //If the point is already contained within the rectangle, do nothing
        if(p.getX() >= p1.getX() && p.getX() <= p2.getX() && p.getY() >= p1.getY() && p.getY() <= p2.getY())
            return;

        //If not, grow...
        Point[] points = new Point[]{p1, p2, p};
        Point[] newRectangle = getRectangle(points);
        p1 = newRectangle[0];
        p2 = newRectangle[1];
    }

    /**
     * Given a {@link Rectangle}, grow this rectangle to fill it
     * @param r The rectangle that will now be contained within this rectangle
     */
    public void grow(Rectangle r){
        Point[] points = new Point[]{p1, p2, r.p1, r.p2};
        Point[] newRectangle = getRectangle(points);
        p1 = newRectangle[0];
        p2 = newRectangle[1];
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
     * @param p A theoretical point that were to be inserted
     * @return The difference between the current area and the area that this rectangle
     * would have if a point p were to be inserted
     */
    public float getAreaDiffWithNewPoint(Point p){
        Point[] newRectanglePoints = getRectangle(new Point[]{p1, p2, p});
        return getArea(newRectanglePoints[0], newRectanglePoints[1]) - getArea(p1, p2);
    }


    public static Rectangle[] getFarthestRectangles(ArrayList<Rectangle> rectangles){
        Rectangle[] farthestRectangles = new Rectangle[2];
        float farthestDistance = 0.0f, currentDistance;

        for(Rectangle r1: rectangles){
            for(Rectangle r2: rectangles){
                currentDistance = Point.evaluateDistance(r1.getCenter(), r2.getCenter());
                if(currentDistance > farthestDistance){
                    farthestRectangles[0] = r1;
                    farthestRectangles[1] = r2;
                    farthestDistance = currentDistance;
                }
            }
        }

        return farthestRectangles;
    }


    public static RTreeElement[] getFarthestElements(ArrayList<RTreeElement> elements){
        RTreeElement[] farthestElements = new RTreeElement[2];
        float farthestDistance = 0.0f, currentDistance;

        for(RTreeElement e1: elements){
            for(RTreeElement e2: elements){
                currentDistance = Point.evaluateDistance(e1.getPoint(), e2.getPoint());
                if(currentDistance > farthestDistance){
                    farthestElements[0] = e1;
                    farthestElements[1] = e2;
                    farthestDistance = currentDistance;
                }
            }
        }

        return farthestElements;
    }


}
