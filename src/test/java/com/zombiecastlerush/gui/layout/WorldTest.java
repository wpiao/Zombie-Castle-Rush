package com.zombiecastlerush.gui.layout;

import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class WorldTest {

    @Test
    public void getBoxTile() {
        String path = "Resources/Castle/CombatHall.txt";
        World world = new WorldBuilder(90, 51)
                .design(path)
                .build();

        Map<Point,Tile> boxTiles =  world.getBoxTile();
        for (Map.Entry<Point,Tile> entry: boxTiles.entrySet()) {
            System.out.println(entry.getKey().toString());

        }
    }
}