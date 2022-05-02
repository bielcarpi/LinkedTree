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
        ArrayList<Rectangle> childNodeRectangles = currentNode.getRectangles();
        ArrayList<RTreeElement> childNodeElements = currentNode.getElements();

        Rectangle r1, r2;
        if(childNodeRectangles != null){ //If we have rectangles
            Rectangle[] farthestRectangles = Rectangle.getFarthestRectangles(childNodeRectangles);

            //With these two rectangles, we'll create the two new rectangles
            r1 = new Rectangle(farthestRectangles[0].getP1(), farthestRectangles[0].getP2(), parentNode,
                    new RTreeNode(ORDER, true));
            r2 = new Rectangle(farthestRectangles[1].getP1(), farthestRectangles[1].getP2(), parentNode,
                    new RTreeNode(ORDER, true));

            //Now that we have the rectangles created, we'll need to put the elements we had into them
            childNodeRectangles.remove(farthestRectangles[0]);
            childNodeRectangles.remove(farthestRectangles[1]);
            RTreeNode.putRectangles(r1, r2, farthestRectangles[0], farthestRectangles[1], childNodeRectangles);
        }
        else{ //If we have RTreeElements
            RTreeElement[] farthestElements = Rectangle.getFarthestElements(childNodeElements);

            //With these two elements, we'll create the two new rectangles
            r1 = new Rectangle(farthestElements[0].getPoint(), farthestElements[0].getPoint(), parentNode,
                    new RTreeNode(ORDER, false));
            r2 = new Rectangle(farthestElements[1].getPoint(), farthestElements[1].getPoint(), parentNode,
                    new RTreeNode(ORDER, false));

            //Now that we have the rectangles created, we'll need to put the elements we had into them
            childNodeElements.remove(farthestElements[0]);
            childNodeElements.remove(farthestElements[1]);
            RTreeNode.putRTreeElements(r1, r2, farthestElements[0], farthestElements[1], childNodeElements);
        }


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

    public boolean visualize(){
        return RTreeVisualization.start(this);
    }

    protected RTreeNode getRootNode(){
        return rootNode;
    }
}
