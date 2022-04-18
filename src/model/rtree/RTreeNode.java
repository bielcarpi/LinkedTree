package model.rtree;

import model.rtree.interfaces.RTreeElement;

public class RTreeNode {

    private RTreeElement[] arrayElements;
    private Rectangle[] arrayRectangles;
    private int currentPosition;
    private Rectangle parentRectangle;

    public RTreeNode(int order, boolean rectangleNode){
        if(rectangleNode){
            arrayRectangles = new Rectangle[order + 1];
        } else {
            arrayElements = new RTreeElement[order + 1];
        }

        currentPosition = -1;
    }

    public boolean insert(RTreeElement element){
        if(arrayElements == null) throw new IllegalArgumentException();
        currentPosition++;
        arrayElements[currentPosition] = element;

        return currentPosition == arrayElements.length;
    }

    public boolean insert(Rectangle rectangle){
        if(arrayRectangles == null) throw new IllegalArgumentException();
        currentPosition++;
        arrayRectangles[currentPosition] = rectangle;

        return currentPosition == arrayElements.length;
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
        for(int i = 0; i <= currentPosition; i++){
            if (p.getX() >= arrayRectangles[i].getP1().getX() && p.getX() <= arrayRectangles[i].getP2().getX() &&
                p.getY() >= arrayRectangles[i].getP1().getY() && p.getY() <= arrayRectangles[i].getP2().getY()) {
                return arrayRectangles[i];
            }
        }

        //Si no, agafem el rectangle que hagi de creixer menys per posar-li el punt
        int bestRectangle = -1;
        float minDiff = Float.MAX_VALUE;
        for(int i = 0; i <= currentPosition; i++){
            float currentDiff = arrayRectangles[i].getAreaDiffWithNewPoint(p);
            if(currentDiff < minDiff){ //If the current rectangle is the best until now, update
                minDiff = currentDiff;
                bestRectangle = i;
            }
        }

        //Retorna el rectangle que ha de creixer menys per posar-li el punt
        return arrayRectangles[bestRectangle];
    }
}
