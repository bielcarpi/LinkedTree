package model.rtree;

import model.rtree.interfaces.RTreeElement;

import java.util.ArrayList;

public class RTreeNode {

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

    public boolean insert(RTreeElement element){
        if(arrayElements == null) throw new IllegalArgumentException();
        arrayElements.add(element);

        return arrayElements.size() == ORDER;
    }

    public boolean insert(Rectangle rectangle){
        if(arrayRectangles == null) throw new IllegalArgumentException();
        arrayRectangles.add(rectangle);

        return arrayRectangles.size() == ORDER;
    }

    public boolean remove(Rectangle rectangle){
        if(arrayRectangles == null) return false;
        return arrayRectangles.remove(rectangle);
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
}
