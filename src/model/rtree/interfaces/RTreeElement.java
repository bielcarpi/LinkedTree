package model.rtree.interfaces;

import model.rtree.Point;

public interface RTreeElement {
    Point getPoint();

    String toRangeSearchString();

    /**
     * Given a hex format color, this method converts that format into rgb format color.
     * @param hex String with the representation of the hex format color.
     * @return Array of integers with the representation of the rgb format color.
     */
    int[] getRGBfromHex(String hex);

    boolean esSemblant(int[] rgb);
}
