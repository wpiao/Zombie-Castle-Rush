package com.zombiecastlerush.gui.layout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Line implements Iterable<Point>, Serializable {
    private final List<Point> points;
    public List<Point> getPoints() { return points; }

    /**
     * use Bresenham's line algorithm reference:
     * https://en.wikipedia.org/wiki/Bresenham's_line_algorithm
     * @param x0 point 0 x coordinate
     * @param y0 point 0 y coordinate
     * @param x1 point 1 x coordinate
     * @param y1 point 1 y coordinate
     */
    public Line(int x0, int y0, int x1, int y1) {
        points = new ArrayList<>();

        int dx = Math.abs(x1-x0);
        int dy = Math.abs(y1-y0);

        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;
        int err = dx-dy;

        while (x0!=x1 || y0!= y1){
            points.add(new Point(x0, y0));

            int e2 = err * 2;
            if (e2 > -dx) {
                err -= dy;
                x0 += sx;
            }
            if (e2 < dx){
                err += dx;
                y0 += sy;
            }
        }
    }

    @Override
    public Iterator<Point> iterator() {
        return points.iterator();
    }
}