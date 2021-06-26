package com.zombiecastlerush.gui.layout;

import com.zombiecastlerush.gui.entity.Creature;

import java.io.Serializable;
import java.util.List;

public class Path implements Serializable {

    private static PathFinder pf = new PathFinder();

    private List<Point> points;
    public List<Point> points() { return points; }

    public Path(Creature creature, int x, int y){
        points = pf.findPath(creature,
                new Point(creature.x, creature.y),
                new Point(x, y),
                300);
    }
}