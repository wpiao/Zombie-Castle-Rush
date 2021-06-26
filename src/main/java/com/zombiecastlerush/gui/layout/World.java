package com.zombiecastlerush.gui.layout;

import com.zombiecastlerush.gui.entity.Creature;
import com.zombiecastlerush.gui.entity.GuiItem;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class World implements Serializable {
    private Tile[][] tiles;
    private GuiItem[][] guiItems;

    private int width;

    public int width() {
        return width;
    }

    private int height;

    public int height() {
        return height;
    }

    private String name;

    public String name() {
        return name;
    }

    private List<Creature> creatures;
    public List<Creature> getCreatures(){
        return creatures;
    }

    public World(Tile[][] tiles) {
        this.tiles = tiles;
        this.width = tiles.length;
        this.height = tiles[0].length;
        this.creatures = new ArrayList<>();
        this.guiItems = new GuiItem[width][height];
    }

    public World(Tile[][] tiles, String name) {
        this(tiles);
        this.name = name;
    }

    public Creature creature(int x, int y) {
        for (Creature c : creatures) {
            if (c.x == x && c.y == y)
                return c;
        }
        return null;
    }


    public Tile tile(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height)
            return Tile.BOUNDS;
        else
            return tiles[x][y];
    }

    public GuiItem item(int x, int y) {
        return guiItems[x][y];
    }

    public char glyph(int x, int y, Creature player) {
        Creature creature = creature(x, y);
        GuiItem item = item(x, y);

        if (player.canSee(x, y)) {
            if (creature != null)
                return creature.glyph();

            if (item != null && !tile(x, y).isBox()) {
                return item.glyph();
            }
        }

        return tile(x, y).glyph();
    }

    public Color color(int x, int y) {
        Creature creature = creature(x, y);
        GuiItem item = item(x, y);
        if (creature != null) {
            return creature.color();
        }
        if (item != null && !tile(x, y).isBox()) {
            return item.color();
        }
        return tile(x, y).color();
    }

    public Map<Point, Tile> getBoxTile() {
        Map<Point, Tile> boxTiles = new HashMap<>();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (tile(x, y).isBox()) {
                    boxTiles.put(new Point(x, y), tile(x, y));
                }
            }
        }
        return boxTiles;
    }

    public void addAtEmptyLocation(Creature creature) {
        int x;
        int y;

        do {
            x = (int) (Math.random() * width);
            y = (int) (Math.random() * height);
        }
        while ((!tile(x, y).isGround() && !tile(x, y).isDoor()) || creature(x, y) != null);

        creature.x = x;
        creature.y = y;
        creatures.add(creature);
    }

    public void addAtEmptyLocation(GuiItem item) {
        int x;
        int y;

        do {
            x = (int) (Math.random() * width);
            y = (int) (Math.random() * height);
        }
        while (!tile(x, y).isGround() || item(x, y) != null);

        guiItems[x][y] = item;
    }

    public void addAtBox(GuiItem guiItem) {

        Map<Point, Tile> boxTiles = getBoxTile();

        for (Map.Entry<Point, Tile> entry : boxTiles.entrySet()) {

            guiItems[entry.getKey().x][entry.getKey().y] = guiItem;
        }
    }

    public void addNextBox(Creature creature) {

        Map<Point, Tile> boxTiles = getBoxTile();

        for (Map.Entry<Point, Tile> entry : boxTiles.entrySet()) {

            creature.x = entry.getKey().x;
            creature.y = entry.getKey().y - 1;
            creatures.add(creature);
        }
    }



    public void addAtPlayer(GuiItem item, int x, int y) {
        if (item == null)
            return;


        List<Point> points = new ArrayList<Point>();
        List<Point> checked = new ArrayList<Point>();

        points.add(new Point(x, y));

        while (!points.isEmpty()) {
            Point p = points.remove(0);
            checked.add(p);

            if (!tile(p.x, p.y).isGround())
                continue;

            if (guiItems[p.x][p.y] == null) {
                guiItems[p.x][p.y] = item;
                return;
            } else {
                List<Point> neighbors = p.neighbors8();
                neighbors.removeAll(checked);
                points.addAll(neighbors);
            }
        }
    }

    public void update() {
        List<Creature> toUpdate = new ArrayList<>(creatures);
        for (Creature creature : toUpdate) {
            creature.update();
        }
    }

    public void remove(int x, int y) {
        guiItems[x][y] = null;
    }

    public void remove(Creature other) {
        creatures.remove(other);
    }
}