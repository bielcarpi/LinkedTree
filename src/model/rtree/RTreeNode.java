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

    public static void putRectangles(Rectangle r1, Rectangle r2, ArrayList<Rectangle> childNodeRectangle) {
        //TODO
    }

    public static void putRTreeElements(Rectangle r1, Rectangle r2, ArrayList<RTreeElement> childNodeElements) {
        //TODO
    }

    /**
     * Inserts an RTreeElement on this RTreeNode
     * @param element The element to be inserted
     * @throws IllegalArgumentException when the node is not of type RTreeElement
     * @return Whether the node is full or not (depending on its order)
     */
    public boolean insert(RTreeElement element){
        if(arrayElements == null) throw new IllegalArgumentException();
        arrayElements.add(element);

        return arrayElements.size() == ORDER;
    }

    /**
     * Inserts a Rectangle on this RTreeNode
     * @param rectangle The rectangle to be inserted
     * @throws IllegalArgumentException when the node is not of type Rectangle
     * @return Whether the node is full or not (depending on its order)
     */
    public boolean insert(Rectangle rectangle){
        if(arrayRectangles == null) throw new IllegalArgumentException();
        arrayRectangles.add(rectangle);

        return arrayRectangles.size() == ORDER;
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
        //Si tenim algun rectangle que el punt quedi a dins, agafem aquell rectangle
        for(Rectangle r: arrayRectangles){
            if (p.getX() >= r.getP1().getX() && p.getX() <= r.getP2().getX() &&
                p.getY() >= r.getP1().getY() && p.getY() <= r.getP2().getY()) {
                return r;
            }
        }

        //Si no, agafem el rectangle que hagi de creixer menys per posar-li el punt
        Rectangle bestRectangle = null;
        float minDiff = Float.MAX_VALUE;
        for(Rectangle r: arrayRectangles){
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
}
