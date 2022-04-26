package model.rtree;

import model.rtree.interfaces.RTreeElement;
import model.tree.Algorithm;
import model.tree.ReadTree;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

public class RTree {

    private final int ORDER;
    private RTreeNode rootNode;

    public RTree(int order, String path) throws IOException {
        ORDER = order;

        //Create the first node (the root node), which will contain a rectangle
        // that occupies the whole space
        rootNode = new RTreeNode(ORDER, true);
        Rectangle r = new Rectangle(new Point(Float.MIN_NORMAL, Float.MIN_VALUE),
                new Point(Float.MAX_VALUE, Float.MAX_VALUE), rootNode, new RTreeNode(ORDER, false));
        rootNode.insert(r);

        /**
         * In the {@link ReadRTree} class, we don't build the RTree, we build it here.
         * The {@link ReadRTree#getCircles()} returns an array with all the circles in the file, and
         * we'll insert and build the RTree here.
         * We know it would be best to build the RTree when reading, but we did it this way to see it more clearly
         */

        ReadRTree.read(path);
        Circle[] circles = ReadRTree.getCircles();

        for(Circle c: circles)
            insert(c); //For each circle, insert it to the RTree

        System.out.println("Done");
    }

    public void insert(RTreeElement element){
        RTreeNode currentNode = rootNode;
        while(currentNode.isRectangleNode()){ //While we're in a rectangle node, get the next best rectangle
            Rectangle r = currentNode.getBestRectangle(element.getPoint());
            currentNode = r.getChildNode();
        }

        //Once we're out the loop, the currentNode is a RTreeElement node, so we can insert the point
        boolean full = currentNode.insert(element);

        //If the node is full, we need to divide the parent in two rectangle
        // If the node of the parent is full too, do it again! Recursively
        while(full){
            full = divideParent(currentNode);
            if(full) currentNode = currentNode.getParentRectangle().getCurrentNode();
        }
    }

    /**
     * Given a full RTreeNode (that is of size {@link #ORDER}), this method will
     * divide its parent rectangle in two
     *
     * @param currentNode The current Node that is full
     * @return Whether the next parent node is also full or not
     */
    private boolean divideParent(RTreeNode currentNode){
        Rectangle parentRectangle = currentNode.getParentRectangle();

        //If the parent rectangle is null, it means we are in the root node.
        // The root node doesn't have a parent rectangle, so we'll need to create a new parent node.
        // Else, get the node of the parent rectangle
        RTreeNode parentNode;
        if(parentRectangle == null){
            rootNode = new RTreeNode(ORDER, true);
            parentNode = rootNode;
        }
        else{
            parentNode = parentRectangle.getCurrentNode();
            parentNode.remove(parentRectangle); //Remove the rectangle that had its child node full from the parentNode
        }

        //Get from the current node (to be deleted, together with its parent rectangle), its elements (either elements or rectangles)
        ArrayList<Rectangle> childNodeRectangle = currentNode.getRectangles();
        ArrayList<RTreeElement> childNodeElements = currentNode.getElements();

        ArrayList<Point> points = new ArrayList<>(); //We need to get the two points with max distance,
                                                     // in order to create the new two rectangles
        if(childNodeRectangle != null){ //If we have rectangles
            for(Rectangle r: childNodeRectangle){
                points.add(r.getP1());
                points.add(r.getP2());
            }
        }
        else{ //If we have RTreeElements
            for(RTreeElement e: childNodeElements)
                points.add(e.getPoint());
        }

        //Now that we have all the points of the child's, let's calculate the two that are farther away
        Point[] maxDistancePoints = Point.getFarthestPoints(points);

        //With these two points, we'll create the two new rectangles
        Rectangle r1 = new Rectangle(maxDistancePoints[0], maxDistancePoints[0], parentNode,
                new RTreeNode(ORDER, childNodeRectangle != null));
        Rectangle r2 = new Rectangle(maxDistancePoints[1], maxDistancePoints[1], parentNode,
                new RTreeNode(ORDER, childNodeRectangle != null));


        //Now that we have the rectangles created, we'll need to put the elements we had into them
        if(childNodeRectangle != null) //If we have rectangles
            RTreeNode.putRectangles(r1, r2, childNodeRectangle);
        else //If we have RTreeElements
            RTreeNode.putRTreeElements(r1, r2, childNodeElements);


        //Finally, insert the two new rectangles into the node
        //In the second insertion we'll check if the parent node is full
        parentNode.insert(r1);
        return parentNode.insert(r2);
    }

    /**
     * Method that searches all the points inside a given area
     * @param searchingArea the area (rectangle formed by two points) where we are making the rangeSearch
     * @return the list of points (RTreeElements) that are inside the area searched
     */
    public ArrayList<RTreeElement> makeRangeSearch(Point[] searchingArea) {
        ArrayList<RTreeElement> solutionList = new ArrayList<>();
        rangeSearch(rootNode, solutionList, searchingArea);
        return solutionList;
    }

    /*
     * Method that searches all the points given an area
     * @param actualNode, the node that we are currently comparing
     * @param solutionList, the list of points (RTreeElements) that are inside the area
     * @param searchingArea, the area (rectangle formed by two points) where we are searching the points inside it
     */
    private void rangeSearch(RTreeNode actualNode, ArrayList<RTreeElement> solutionList, Point[] searchingArea) {

        if (actualNode.isRectangleNode()) {
            ArrayList<Rectangle> actualNodeRec = actualNode.getRectangles();

            for (int i = 0; i < actualNodeRec.size(); i++) {
                if (searchingArea[0].getX() <= actualNodeRec.get(i).getP2().getX() &&
                        searchingArea[1].getX() >= actualNodeRec.get(i).getP1().getX() &&
                        searchingArea[1].getY() >= actualNodeRec.get(i).getP1().getY() &&
                        searchingArea[0].getY() <= actualNodeRec.get(i).getP2().getY()) {
                    //The Rectangles are overlapping
                    rangeSearch(actualNodeRec.get(i).getChildNode(), solutionList, searchingArea);
                }
            }
        } else {
            // We are at the point level
            ArrayList<RTreeElement> lastNode = actualNode.getElements();
            for (int i = 0; i < lastNode.size(); i++) {
                if (lastNode.get(i).getPoint().getX() >= searchingArea[0].getX() &&
                        lastNode.get(i).getPoint().getX() <= searchingArea[1].getX() &&
                        lastNode.get(i).getPoint().getY() >= searchingArea[0].getY() &&
                        lastNode.get(i).getPoint().getY() <= searchingArea[1].getY()) {
                    // The Point is inside the area range
                    solutionList.add(lastNode.get(i));
                }
            }
        }
    }

    
    public void delete(RTreeElement node){
    }

    public boolean visualize(){
        return RTreeVisualization.start(this);
    }

    protected RTreeNode getRootNode(){
        return rootNode;
    }
}
