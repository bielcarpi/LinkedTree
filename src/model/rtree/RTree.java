package model.rtree;

import model.rtree.interfaces.RTreeElement;

public class RTree {

    private final int ORDER;
    private RTreeNode rootNode;

    public RTree(int order){
        ORDER = order;

        rootNode = new RTreeNode(ORDER, true);
        Rectangle r = new Rectangle(new Point(Float.MIN_NORMAL, Float.MAX_VALUE),
                new Point(Float.MAX_VALUE, Float.MIN_VALUE), new RTreeNode(ORDER, false));
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

        //If the node is full, we need to divide the parent in two rectangles!
        if(full){

        }
    }

    public void delete(RTreeElement node){
    }





}
