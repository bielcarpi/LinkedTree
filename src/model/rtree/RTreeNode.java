package model.rtree;

import model.rtree.interfaces.RTreeElement;

import java.util.ArrayList;

/**
 * The class RTreeNode represents a node of the {@link RTree}
 * <p>There can be two types of node, depending on what that node contains:
 * <ol>
 *     <li>Rectangle Node</li>
 *     <li>{@link RTreeElement} Node</li>
 * </ol>
 *
 * @see RTree
 * @see Rectangle
 * @see RTreeElement
 */
public class RTreeNode {

    //Internally, the node is an arraylist of either Rectangles or RTreeElements
    private ArrayList<RTreeElement> arrayElements;
    private ArrayList<Rectangle> arrayRectangles;
    private final int ORDER;
    private Rectangle parentRectangle;

    public RTreeNode(int order, boolean rectangleNode){
        ORDER = order;

        if(rectangleNode){
            arrayRectangles = new ArrayList<>();
        } else {
            arrayElements = new ArrayList<>();
        }
    }

    /**
     * Given two rectangles and an ArrayList of rectangles, the rectangles of the ArrayList
     * will be inserted in the rectangle (r1 or r2) that fits best.
     * <p>The method {@link #getBestRectangle(Point)} will be used, and the point of the Rectangles
     * in the ArrayList considered will be its center.
     *
     * @param r1 The first option to insert the childNodeRectangles
     * @param r2 The second option to insert the childNodeRectangles
     * @param r1Builder The rectangle that constructed r1 (to be inserted directly into r1)
     * @param r2Builder The rectangle that constructed r2 (to be inserted directly into r2)
     * @param childNodeRectangle An ArrayList containing the Rectangles to be inserted in either r1 or r2
     */
    public static void putRectangles(Rectangle r1, Rectangle r2, Rectangle r1Builder, Rectangle r2Builder, ArrayList<Rectangle> childNodeRectangle) {
        ArrayList<Rectangle> possibleRectangles = new ArrayList<>();
        possibleRectangles.add(r1);
        possibleRectangles.add(r2);

        r1.getChildNode().insert(r1Builder);
        r1Builder.setCurrentNode(r1.getChildNode());
        r2.getChildNode().insert(r2Builder);
        r2Builder.setCurrentNode(r2.getChildNode());

        //For each childNodeRectangle, insert it to either r1 or r2 (the best one)
        for(Rectangle r: childNodeRectangle){
            Rectangle bestRectangle = getBestRectangle(r.getCenter(), possibleRectangles);
            bestRectangle.getChildNode().insert(r);
            r.setCurrentNode(bestRectangle.getChildNode());
        }
    }

    /**
     * Given two rectangles and an ArrayList of RTreeElements, the latter ones will be inserted
     * in the rectangle (r1 or r2) that fits best.
     * <p>The method {@link #getBestRectangle(Point)} will be used
     *
     * @param r1 The first option to insert the childNodeElements
     * @param r2 The second option to insert the childNodeElements
     * @param r1ElementBuilder The element that constructed r1 (to be inserted directly into r1)
     * @param r2ElementBuilder The element that constructed r2 (to be inserted directly into r2)
     * @param childNodeElements An ArrayList containing the Elements to be inserted in either r1 or r2
     */
    public static void putRTreeElements(Rectangle r1, Rectangle r2, RTreeElement r1ElementBuilder, RTreeElement r2ElementBuilder, ArrayList<RTreeElement> childNodeElements) {
        ArrayList<Rectangle> possibleRectangles = new ArrayList<>();
        possibleRectangles.add(r1);
        possibleRectangles.add(r2);

        r1.getChildNode().insert(r1ElementBuilder);
        r2.getChildNode().insert(r2ElementBuilder);

        //For each childNodeElement, insert it to either r1 or r2 (the best one)
        for(RTreeElement e: childNodeElements)
            getBestRectangle(e.getPoint(), possibleRectangles).getChildNode().insert(e);
    }

    /**
     * Inserts an RTreeElement on this RTreeNode
     * @param element The element to be inserted
     * @throws IllegalArgumentException when the node is not of type RTreeElement
     * @return Whether the node is full or not (depending on its order)
     */
    public boolean insert(RTreeElement element){
        if(arrayElements == null) throw new IllegalArgumentException();

        Rectangle parent = parentRectangle; //Current parent rectangle of this RTreeNode
        //Grow recursively all the parent rectangles
        while(parent != null){
            parent.grow(element.getPoint()); //If we aren't in the root node, grow
            parent = parent.getCurrentNode().getParentRectangle();
        }
        arrayElements.add(element);

        return arrayElements.size() == ORDER + 1;
    }

    /**
     * Inserts a Rectangle on this RTreeNode
     * @param rectangle The rectangle to be inserted
     * @throws IllegalArgumentException when the node is not of type Rectangle
     * @return Whether the node is full or not (depending on its order)
     */
    public boolean insert(Rectangle rectangle){
        if(arrayRectangles == null) throw new IllegalArgumentException();

        Rectangle parent = parentRectangle; //Current parent rectangle of this RTreeNode
        //Grow recursively all the parent rectangles
        while(parent != null){
            parent.grow(rectangle); //If we aren't in the root node, grow
            parent = parent.getCurrentNode().getParentRectangle();
        }
        arrayRectangles.add(rectangle);

        return arrayRectangles.size() == ORDER + 1;
    }

    /**
     * Removes a Rectangle from this RTreeNode
     * @param rectangle A reference to the rectangle to be removed
     * @return Whether the rectangle has been removed or not
     */
    public boolean remove(Rectangle rectangle){
        if(arrayRectangles == null) return false;
        return arrayRectangles.remove(rectangle);
    }

    /**
     * Given a point, if this RTreeNode is a Rectangle tree node, it will return the rectangle
     * which is best for inserting that point.
     * <p>If there is a rectangle that would contain the point, it will return it. If not, it will
     * return the rectangle that needs to grow less to contain the point
     *
     * @param p The point
     * @return The best rectangle contained in this RTreeNode, given the point
     */
    public Rectangle getBestRectangle(Point p){
        if(arrayRectangles == null) throw new IllegalArgumentException();
        return getBestRectangle(p, arrayRectangles);
    }
    private static Rectangle getBestRectangle(Point p, ArrayList<Rectangle> possibleRectangles){
        //Si tenim algun rectangle que el punt quedi a dins, agafem aquell rectangle
        for(Rectangle r: possibleRectangles){
            if (p.getX() >= r.getP1().getX() && p.getX() <= r.getP2().getX() &&
                    p.getY() >= r.getP1().getY() && p.getY() <= r.getP2().getY()) {
                return r;
            }
        }

        //Si no, agafem el rectangle que hagi de creixer menys per posar-li el punt
        Rectangle bestRectangle = null;
        float minDiff = Float.MAX_VALUE;
        for(Rectangle r: possibleRectangles){
            float currentDiff = r.getAreaDiffWithNewPoint(p);
            if(currentDiff < minDiff){ //If the current rectangle is the best until now, update
                minDiff = currentDiff;
                bestRectangle = r;
            }
        }

        //Retorna el rectangle que ha de creixer menys per posar-li el punt
        return bestRectangle;
    }


    public ArrayList<Rectangle> getRectangles() {
        return arrayRectangles;
    }

    public ArrayList<RTreeElement> getElements() {
        return arrayElements;
    }

    public boolean isRectangleNode(){
        return arrayRectangles != null;
    }

    public Rectangle getParentRectangle() {
        return parentRectangle;
    }

    public void setParentRectangle(Rectangle parentRectangle) {
        this.parentRectangle = parentRectangle;
    }


    /**
     * Returns the limit boundaries of the rectangles contained in this RTreeNode, in order:
     * <ol>
     *     <li>[0] -> minX</li>
     *     <li>[1] -> maxX</li>
     *     <li>[2] -> minY</li>
     *     <li>[3] -> maxY</li>
     * </ol>
     * <p>E.g. if the node contains 6 rectangles, the method will return its minX, maxX, minY and maxX
     * of the zone contained by the rectangles
     * @return The limit boundaries of the rectangles contained in this RTreeNode
     */
    public float[] getLimitBoundaries() {
        if(arrayRectangles == null) throw new IllegalArgumentException();
        float minX = Float.MAX_VALUE;
        float maxX = Float.MIN_VALUE;
        float minY = Float.MAX_VALUE;
        float maxY = Float.MIN_VALUE;

        for(Rectangle r: arrayRectangles){
            if(r.getP1().getX() < minX) minX = r.getP1().getX();
            if(r.getP2().getX() > maxX) maxX = r.getP2().getX();
            if(r.getP1().getY() < minY) minY = r.getP1().getY();
            if(r.getP2().getY() > maxY) maxY = r.getP2().getY();
        }

        return new float[]{minX, maxX, minY, maxY};
    }

}
