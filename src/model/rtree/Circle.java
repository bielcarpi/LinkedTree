package model.rtree;

import model.rtree.interfaces.RTreeNode;

public class Circle implements RTreeNode {
    private float x;
    private float y;
    private float radius;
    private String hexColor;

    public Circle(float x, float y, float radius, String hexColor) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.hexColor = hexColor;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getRadius() {
        return radius;
    }

    public String getHexColor() {
        return hexColor;
    }
}
