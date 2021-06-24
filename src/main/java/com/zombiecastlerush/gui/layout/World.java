package com.zombiecastlerush.gui.layout;

import com.zombiecastlerush.gui.creature.Creature;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class World {
    private Tile[][] tiles;
    private int width;
    public int width() { return width; }

    private int height;
    public int height() { return height; }

    private List<Creature> creatures;

    public World(Tile[][] tiles){
        this.tiles = tiles;
        this.width = tiles.length;
        this.height = tiles[0].length;
        this.creatures = new ArrayList<>();
    }

    public Creature creature(int x, int y){
        for (Creature c : creatures){
            if (c.x == x && c.y == y)
                return c;
        }
        return null;
    }

    public Tile tile(int x, int y){
        if (x < 0 || x >= width || y < 0 || y >= height)
            return Tile.BOUNDS;
        else
            return tiles[x][y];
    }

    public char glyph(int x, int y){
        return tile(x, y).glyph();
    }

    public Color color(int x, int y){
        return tile(x, y).color();
    }
//    public void dig(int x, int y) {
//        if (tile(x,y).isDiggable())
//            tiles[x][y] = Tile.FLOOR;
//    }

    public void addAtEmptyLocation(Creature creature){
        int x;
        int y;

        do {
            x = (int)(Math.random() * width);
            y = (int)(Math.random() * height);
        }
        while ((!tile(x,y).isGround()&&!tile(x,y).isDoor())||creature(x,y) != null);

        creature.x = x;
        creature.y = y;
        creatures.add(creature);
    }

    public void update(){
        List<Creature> toUpdate = new ArrayList<>(creatures);
        for (Creature creature : toUpdate){
            creature.update();
        }
    }

    public void remove(Creature other) {
        creatures.remove(other);
    }
}