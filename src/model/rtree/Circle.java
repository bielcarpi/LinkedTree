package model.rtree;

import model.rtree.interfaces.RTreeElement;

public class Circle implements RTreeElement {
    private static final int COLOR_PARAMETER = 10;

    private Point point;
    private final float radius;
    private final String hexColor;

    public Circle(Point point, float radius, String hexColor) {
        this.point = point;
        this.radius = radius;
        this.hexColor = hexColor;
    }

    @Override
    public Point getPoint() {
        return point;
    }

    @Override
    public void setPoint(Point point) {
        this.point = point;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toRangeSearchString() {
        return "\t" + hexColor + " (" + point.getX() + ", " + point.getY() + ") r=" + radius;
    }

    @Override
    public boolean esSemblant(int[] rgb) {
        boolean equal = true;
        int[] circleColor = getRGBFromHex(hexColor);
        for (int i = 0; i < 3; i++) {
            if (Math.abs(circleColor[i] - rgb[i]) > COLOR_PARAMETER){
                equal = false;
            }
        }
        return equal;
    }

    @Override
    public int[] getRGBFromHex(String hex) {
        StringBuilder str = new StringBuilder();
        for (int i = 1; i < hex.length(); i++) {
            str.append(hex.charAt(i));
        }
        String color = str.toString();

        int[] rgb = new int[3];
        for (int i = 0; i < 3; i++) {
            rgb[i] = Integer.parseInt(color.substring(i * 2, i * 2 + 2), 16);
        }
        return rgb;
    }

    public float getRadius() {
        return radius;
    }

    public String getHexColor() {
        return hexColor;
    }
}
