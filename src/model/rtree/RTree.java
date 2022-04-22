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
        Rectangle r = new Rectangle(new Point(Float.MIN_NORMAL, Float.MAX_VALUE),
                new Point(Float.MAX_VALUE, Float.MIN_VALUE), rootNode, new RTreeNode(ORDER, false));
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
    }

    public void insert(RTreeElement node){
        RTreeNode currentNode = rootNode;
        while(currentNode.isRectangleNode()){ //While we're in a rectangle node, get the next best rectangle
            Rectangle r = currentNode.getBestRectangle(node.getPoint());
            currentNode = r.getChildNode();
        }

        //Once we're out the loop, the currentNode is a RTreeElement node, so we can insert the point
        boolean full = currentNode.insert(node);

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
        RTreeNode parentNode = parentRectangle.getCurrentNode();
        parentNode.remove(parentRectangle); //Remove the rectangle that had its child node full from the parentNode

        //Get from the current node (to be deleted, together with its parent), its elements (either elements or rectangles)
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
        //TODO
        if(childNodeRectangle != null) //If we have rectangles
            RTreeNode.putRectangles(r1, r2, childNodeRectangle);
        else //If we have RTreeElements
            RTreeNode.putRTreeElements(r1, r2, childNodeElements);


        //Finally, insert the two new rectangles into the node
        //In the second insertion we'll check if the parent node is full
        parentNode.insert(r1);
        return parentNode.insert(r2);
    }

    public ArrayList<RTreeElement> makeRangeSearch(Point[] searchingArea) {
        return rangeSearch(rootNode.getParentRectangle(), new ArrayList<>(), searchingArea);
    }

    public ArrayList<RTreeElement> rangeSearch(Rectangle actualRectangle, ArrayList<RTreeElement> solutionList, Point[] searchingArea) {

        if (actualRectangle.getChildNode().isRectangleNode()) {
            ArrayList<Rectangle> actualNode = actualRectangle.getChildNode().getRectangles();

            for (int i = 0; i < actualNode.size() - 1; i++) {
                if (searchingArea[0].getX() <= actualNode.get(i).getP2().getX() &&
                        searchingArea[1].getX() >= actualNode.get(i).getP1().getX() &&
                        searchingArea[1].getY() >= actualNode.get(i).getP1().getY() &&
                        searchingArea[0].getY() <= actualNode.get(i).getP2().getY()) {
                    //The Rectangles are overlapping
                    rangeSearch(actualNode.get(i), solutionList, searchingArea);
                }
            }
        } else {
                // We are at the point level
                ArrayList<RTreeElement> lastNode = actualRectangle.getChildNode().getElements();

                for (int i = 0; i < lastNode.size() - 1; i++) {
                    if (lastNode.get(i).getPoint().getX() >= searchingArea[0].getX() &&
                            lastNode.get(i).getPoint().getX() <= searchingArea[1].getX() &&
                            lastNode.get(i).getPoint().getY() >= searchingArea[0].getY() &&
                            lastNode.get(i).getPoint().getY() <= searchingArea[1].getY()) {
                        // The Point is inside the area range
                        solutionList.add(lastNode.get(i));
                    }
                }
            }

        return solutionList;
    }

    
    public void delete(RTreeElement node){
    }

    public void visualize(){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                RTreeVisualization visualization = new RTreeVisualization(rootNode);
            }
        });
    }
}
