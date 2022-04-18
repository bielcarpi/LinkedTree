package model.rtree;

import model.rtree.interfaces.RTreeElement;

public class Circle implements RTreeElement {
    private Point point;
    private float radius;
    private String hexColor;

    public Circle(Point point, float radius, String hexColor) {
        this.point = point;
        this.radius = radius;
        this.hexColor = hexColor;
    }

    @Override
    public Point getPoint() {
        return point;
    }

    public float getRadius() {
        return radius;
    }

    public String getHexColor() {
        return hexColor;
    }
}
