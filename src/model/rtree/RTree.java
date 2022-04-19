package model.rtree;

import model.rtree.interfaces.RTreeElement;

import javax.swing.*;
import java.util.ArrayList;

public class RTree {

    private final int ORDER;
    private RTreeNode rootNode;

    public RTree(int order){
        ORDER = order;

        rootNode = new RTreeNode(ORDER, true);
        Rectangle r = new Rectangle(new Point(Float.MIN_NORMAL, Float.MAX_VALUE),
                new Point(Float.MAX_VALUE, Float.MIN_VALUE), rootNode, new RTreeNode(ORDER, false));
        rootNode.insert(r);
    }

    public void insert(RTreeElement node){
        RTreeNode currentNode = rootNode;
        while(currentNode.isRectangleNode()){
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

        //Now that we have all the points of the child's, let's calculate the two that are further away
        Point[] maxDistancePoints = Point.getLongestDistance(points);

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
