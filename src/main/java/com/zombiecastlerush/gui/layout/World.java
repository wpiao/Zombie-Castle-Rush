package com.zombiecastlerush.gui.layout;

import com.zombiecastlerush.gui.entity.Creature;
import com.zombiecastlerush.gui.entity.Item;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class World {
    private Tile[][] tiles;
    private Item[][] items;

    private int width;
    public int width() { return width; }

    private int height;
    public int height() { return height; }

    private String name;
    public String name(){return name;}

    private List<Creature> creatures;

    public World(Tile[][] tiles){
        this.tiles = tiles;
        this.width = tiles.length;
        this.height = tiles[0].length;
        this.creatures = new ArrayList<>();
        this.items = new Item[width][height];
    }

    public World(Tile[][] tiles, String name){
        this(tiles);
        this.name = name;
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

    public Item item(int x, int y){
        return items[x][y];
    }

    public char glyph(int x, int y){
        Creature creature = creature(x, y);

        if (creature != null)
            return creature.glyph();

        return tile(x, y).glyph();
    }

    public Color color(int x, int y){
        Creature creature = creature(x, y);
        if (creature != null)
            return creature.color();
        return tile(x, y).color();
    }

    public Map<Point,Tile> getBoxTile(){
        Map<Point,Tile> boxTiles = new HashMap<>();
        for(int x = 0; x < width;x++){
            for (int y = 0; y < height; y++){
                if (tile(x,y).isBox()){
                    boxTiles.put(new Point(x,y),tile(x,y));
                }
            }
        }
        return boxTiles;
    }

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

    public void addAtBox(Item item) {

        Map<Point,Tile> boxTiles = getBoxTile();

        for (Map.Entry<Point,Tile> entry: boxTiles.entrySet()) {

            items[entry.getKey().x][entry.getKey().y] = item;
        }
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