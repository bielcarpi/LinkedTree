package model.rtree;

import model.rtree.interfaces.RTreeElement;

import java.io.IOException;
import java.util.ArrayList;

public class RTree {

    private RTreeNode rootNode;

    private final int MINIMUM_NODE_SIZE;
    private final int ORDER;

    public RTree(int order, String path) throws IOException {
        ORDER = order;
        MINIMUM_NODE_SIZE = (order * 30)/100;

        //Create the first node (the root node), which will contain a rectangle
        // that occupies the whole space
        rootNode = new RTreeNode(ORDER, true);
        Rectangle r = new Rectangle(new Point(Float.MIN_NORMAL, Float.MIN_VALUE),
                new Point(Float.MAX_VALUE, Float.MAX_VALUE), rootNode, new RTreeNode(ORDER, false));
        rootNode.insert(r); //Phantom node

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
        boolean full = currentNode.insert(element); //true - overflow

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
            //TODO: A resize is necessary once a rectangle has been removed. Call shrink()
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

    /**
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

    /**
     * Method that searches the node where contains the point we are searching
     * @param actualNode, the node that we are currently comparing
     * @param searchingPoint, the point that we are searching
     * @return the RTreeNode of points where the searchingPoint is contained
     */
    private RTreeNode pointNodeSearch(RTreeNode actualNode, Point searchingPoint) {
        if (actualNode.isRectangleNode()) {
            ArrayList<Rectangle> actualNodeRec = actualNode.getRectangles();

            for (int i = 0; i < actualNodeRec.size(); i++) {
                if (searchingPoint.getX() >= actualNodeRec.get(i).getP1().getX() &&
                        searchingPoint.getX() <= actualNodeRec.get(i).getP2().getX() &&
                        searchingPoint.getY() >= actualNodeRec.get(i).getP1().getY() &&
                        searchingPoint.getY() <= actualNodeRec.get(i).getP2().getY()) {
                    // The Point is inside the rectangle
                    return pointNodeSearch(actualNodeRec.get(i).getChildNode(), searchingPoint);
                }
            }

            return null;
        }
        else{
            // We are at the point level
            return actualNode;
        }
    }

    /**
     * Method that gets the current area that we want to paint depending on the dataset we're working with.
     * @return Array of float with the representation of the weight and height of the rectangle to paint.
     */
    private float[] getSearchingArea() {
        float[] values = rootNode.getLimitBoundaries();

        return new float[]{
                Math.abs(values[0] - values[1]) ,
                Math.abs(values[2] - values[3])};
    }

    /**
     * Method that evaluates the limit of the rectangle to paint (considering the dimensions obtained in the
     * getSearchingArea function) and locates in the space having the point introduced by parameter as the
     * center of the current rectangle.
     * @param centerPoint Current point with the representation of the center of the rectangle
     * @return Array of elements representing the points which are located inside that rectangle
     */
    public ArrayList<RTreeElement> getPointsByProximity(Point centerPoint) {
        float[] dimensions = getSearchingArea(); //Gets the weight and height of the current rectangle

        //Using the center point we can locate the corners of the rectangle that we want to paint
        Point[] range = new Point[2];

        //Asignation of the diferent coordenates of the rectangle to paint
        float minX = dimensions[0]/2 - centerPoint.getX();
        float maxX = dimensions[0]/2 + centerPoint.getX();
        float minY = dimensions[1]/2 - centerPoint.getY();
        float maxY = dimensions[1]/2 + centerPoint.getY();

        //Configuration of the current rectangle
        range[0] = new Point(minX, minY);
        range[1] = new Point(maxX, maxY);

        ArrayList<RTreeElement> area = makeRangeSearch(range);
        return area;
    }

    /**
     * Method that evaluates if the points obtained in the getSearchByProximity function have similar
     * color to the one introduced in the circle by parameter.
     * @param circle Circle introduced by the user to locate the painting area.
     * @return Array of elements which must be painted after passing the proximity and similarity filter.
     */
    public ArrayList<RTreeElement> getPointsBySimilarity(Circle circle) {
        ArrayList<RTreeElement> solutions = new ArrayList<>();

        return getPointsByProximity(circle.getPoint());

        /*
        int[] color = circle.getRGBFromHex(circle.getHexColor());

        for (int i = 0; i < proximity.size(); i++) {
            if (proximity.get(i).esSemblant(color)){
                solutions.add(proximity.get(i));
            }
        }

        return solutions;
         */
    }

    /**
     * Method that implements the deletion of a given point
     * @param pointToRemove the point we want to remove
     * @return true if the point has correctly deleted and false if not
     */
    public boolean delete(Point pointToRemove){
        RTreeNode nodeWithThePoint = pointNodeSearch(rootNode, pointToRemove);
        ArrayList<RTreeElement> elements = nodeWithThePoint.getElements();
        boolean found = false;

        for (int i = 0; i < elements.size() && !found; i++) {
            if (pointToRemove.equals(elements.get(i).getPoint())) {
                //If we find the point we want to delete, delete it
                found = true;

                nodeWithThePoint.remove(elements.get(i));

                // We see if the node fulfill the minimum capacity
                boolean underflow = makesUnderflow(nodeWithThePoint);
                if(underflow){
                    while (underflow) {
                        // reinsert the points that are on the nodeWithThePoint
                    /*
                    nodeWithThePoint.getParentRectangle().getCurrentNode().remove(nodeWithThePoint.getParentRectangle());
                    for(RTreeElement e: elements)
                        insert(e);
                     */
                        //TODO: Make it work for both elements and rectangles

                        nodeWithThePoint = nodeWithThePoint.getParentRectangle().getCurrentNode();
                        underflow = makesUnderflow(nodeWithThePoint);
                    }
                }
            }
        }

        return found;
    }

    /**
     * Method that checks if a node has fewer elements than the minimum elements established
     * @param node the node we are checking
     * @return true if the node has fewer elements than the minimum elements established and false if not
     */
    private boolean makesUnderflow(RTreeNode node){
        return false;
        //We consider that the underflow is produced when the array of nodes has equal or less that its 30% of capacity.
        /*
        if(node.isRectangleNode()){
            return node.getRectangles().size() <= MINIMUM_NODE_SIZE;
        }

        return node.getElements().size() <= MINIMUM_NODE_SIZE;
         */
    }


    /**
     * Method that does the visualization of the rTree in swing
     * @return
     */
    public boolean visualize(){
        return RTreeVisualization.start(this);
    }

    protected RTreeNode getRootNode(){
        return rootNode;
    }
}