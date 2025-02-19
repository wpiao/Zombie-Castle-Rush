package com.zombiecastlerush.gui.layout;

import com.zombiecastlerush.gui.component.Creature;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class PathFinder implements Serializable {
    private final ArrayList<Point> open;
    private final ArrayList<Point> closed;
    private final HashMap<Point, Point> parents;
    private final HashMap<Point,Integer> totalCost;

    public PathFinder() {
        this.open = new ArrayList<Point>();
        this.closed = new ArrayList<Point>();
        this.parents = new HashMap<Point, Point>();
        this.totalCost = new HashMap<Point, Integer>();
    }

    //use A* algorithm to find optimal least cost path to the target.
    // reference: https://en.wikipedia.org/wiki/A*_search_algorithm
    public ArrayList<Point> findPath(Creature creature, Point start, Point end, int maxTries) {
        open.clear();
        closed.clear();
        parents.clear();
        totalCost.clear();

        open.add(start);

        for (int tries = 0; tries < maxTries && open.size() > 0; tries++){
            Point closest = getClosestPoint(end);

            open.remove(closest);
            closed.add(closest);

            if (closest.equals(end))
                return createPath(start, closest);
            else
                checkNeighbors(creature, end, closest);
        }
        return null;
    }

    private Point getClosestPoint(Point end) {
        Point closest = open.get(0);
        for (Point other : open){
            if (totalCost(other, end) < totalCost(closest, end))
                closest = other;
        }
        return closest;
    }

    private int totalCost(Point from, Point to) {
        if (totalCost.containsKey(from))
            return totalCost.get(from);

        int cost = costToGetTo(from) + heuristicCost(from, to);
        totalCost.put(from, cost);
        return cost;
    }

    private int heuristicCost(Point from, Point to) {
        return Math.max(Math.abs(from.x - to.x), Math.abs(from.y - to.y));
    }

    private int costToGetTo(Point from) {
        return parents.get(from) == null ? 0 : (1 + costToGetTo(parents.get(from)));
    }

    private ArrayList<Point> createPath(Point start, Point end) {
        ArrayList<Point> path = new ArrayList<>();

        while (!end.equals(start)) {
            path.add(end);
            end = parents.get(end);
        }

        Collections.reverse(path);
        return path;
    }

    private void checkNeighbors(Creature creature, Point end, Point closest) {
        for (Point neighbor : closest.neighbors8()) {
            if (closed.contains(neighbor)
                    || !creature.canEnter(neighbor.x, neighbor.y)
                    && !neighbor.equals(end))
                continue;

            if (open.contains(neighbor))
                reParentNeighborIfNecessary(closest, neighbor);
            else
                reParentNeighbor(closest, neighbor);
        }
    }

    private void reParentNeighborIfNecessary(Point closest, Point neighbor) {
        Point originalParent = parents.get(neighbor);
        double currentCost = costToGetTo(neighbor);
        reParent(neighbor, closest);
        double reparentCost = costToGetTo(neighbor);

        if (reparentCost < currentCost)
            open.remove(neighbor);
        else
            reParent(neighbor, originalParent);
    }


    private void reParent(Point child, Point parent){
        parents.put(child, parent);
        totalCost.remove(child);
    }

    private void reParentNeighbor(Point closest, Point neighbor) {
        reParent(neighbor, closest);
        open.add(neighbor);
    }

}