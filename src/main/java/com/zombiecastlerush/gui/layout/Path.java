package com.zombiecastlerush.gui.layout;

import com.zombiecastlerush.gui.component.Creature;

import java.io.Serializable;
import java.util.List;

public class Path implements Serializable {

    private static final PathFinder pf = new PathFinder();

    private final List<Point> points;
    public List<Point> points() { return points; }

    public Path(Creature creature, int x, int y){
        points = pf.findPath(creature,
                new Point(creature.x, creature.y),
                new Point(x, y),
                300);
    }
}