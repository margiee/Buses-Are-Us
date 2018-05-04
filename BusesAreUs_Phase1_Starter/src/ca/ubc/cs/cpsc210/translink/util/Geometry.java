package ca.ubc.cs.cpsc210.translink.util;

import java.awt.geom.Line2D;

/**
 * Compute relationships between points, lines, and rectangles represented by LatLon objects
 */
public class Geometry {
    /**
     * Return true if the point is inside of, or on the boundary of, the rectangle formed by northWest and southeast
     * @param northWest         the coordinate of the north west corner of the rectangle
     * @param southEast         the coordinate of the south east corner of the rectangle
     * @param point             the point in question
     * @return                  true if the point is on the boundary or inside the rectangle
     */
    public static boolean rectangleContainsPoint(LatLon northWest, LatLon southEast, LatLon point) {
        boolean betweenLatitude = between(northWest.getLongitude(), southEast.getLongitude(), point.getLongitude());
        boolean betweenLongitude = between(southEast.getLatitude(), northWest.getLatitude(), point.getLatitude());
        return betweenLatitude && betweenLongitude;
    }

    /**
     * Return true if the rectangle intersects the line
     * @param northWest         the coordinate of the north west corner of the rectangle
     * @param southEast         the coordinate of the south east corner of the rectangle
     * @param src               one end of the line in question
     * @param dst               the other end of the line in question
     * @return                  true if any point on the line is on the boundary or inside the rectangle
     */
    public static boolean rectangleIntersectsLine(LatLon northWest, LatLon southEast, LatLon src, LatLon dst) {
        // if src or dst is inside the rectangle return true
        if (rectangleContainsPoint(northWest, southEast, src) || rectangleContainsPoint(northWest, southEast, dst)) {
            return true;
        }
        // line made from src and dst
        // latitude -> y-value
        // longitude -> x-value
        double ySrc = src.getLatitude();
        double xSrc = src.getLongitude();
        double yDst = dst.getLatitude();
        double xDst = dst.getLongitude();
        // Line2D.double: a line segment with double coordinates
        Line2D line = new Line2D.Double(xSrc, xDst, ySrc, yDst);

        // check if the line intersects any of the lines of the rectangle
        double yNW = northWest.getLatitude();
        double xNW = northWest.getLongitude();
        double ySE = southEast.getLatitude();
        double xSE = southEast.getLongitude();

        Line2D top = new Line2D.Double(xNW, yNW, xSE, yNW);
        Line2D bottom = new Line2D.Double(xNW, ySE, xSE, ySE);
        Line2D left = new Line2D.Double(xNW, yNW, xNW, ySE);
        Line2D right = new Line2D.Double(xSE, yNW, xSE, ySE);

        if (top.intersectsLine(line) || bottom.intersectsLine(line)
                || left.intersectsLine(line) || right.intersectsLine(line)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * A utility method that you might find helpful in implementing the two previous methods
     * Return true if x is >= lwb and <= upb
     * @param lwb      the lower boundary
     * @param upb      the upper boundary
     * @param x         the value in question
     * @return          true if x is >= lwb and <= upb
     */
    private static boolean between(double lwb, double upb, double x) {
        return lwb <= x && x <= upb;
    }
}
