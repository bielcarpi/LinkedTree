package model.rtree;

import model.rtree.interfaces.RTreeElement;

import java.util.Arrays;

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

    /**
     * {@inheritDoc}
     */
    @Override
    public String toRangeSearchString() {
        return "\t" + hexColor + " (" + point.getX() + ", " + point.getY() + ") r=" + radius;
    }

    public float getRadius() {
        return radius;
    }

    public String getHexColor() {
        return hexColor;
    }
}
