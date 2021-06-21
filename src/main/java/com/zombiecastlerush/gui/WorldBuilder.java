package com.zombiecastlerush.gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class WorldBuilder {
    private int width;
    private int height;
    private Tile[][] tiles;

    public WorldBuilder(int width, int height) {
        this.width = width;
        this.height = height;
        this.tiles = new Tile[width][height];
    }

    public World build() {
        return new World(tiles);
    }

//    private WorldBuilder randomizeTiles() {
//        for (int x = 0; x < width; x++) {
//            for (int y = 0; y < height; y++) {
//                tiles[x][y] = Math.random() < 0.5 ? Tile.FLOOR : Tile.WALL;
//            }
//        }
//        return this;
//    }
//
//    private WorldBuilder smooth(int times) {
//        Tile[][] tiles2 = new Tile[width][height];
//        for (int time = 0; time < times; time++) {
//
//            for (int x = 0; x < width; x++) {
//                for (int y = 0; y < height; y++) {
//                    int floors = 0;
//                    int rocks = 0;
//
//                    for (int ox = -1; ox < 2; ox++) {
//                        for (int oy = -1; oy < 2; oy++) {
//                            if (x + ox < 0 || x + ox >= width || y + oy < 0
//                                    || y + oy >= height)
//                                continue;
//
//                            if (tiles[x + ox][y + oy] == Tile.FLOOR)
//                                floors++;
//                            else
//                                rocks++;
//                        }
//                    }
//                    tiles2[x][y] = floors >= rocks ? Tile.FLOOR : Tile.WALL;
//                }
//            }
//            tiles = tiles2;
//        }
//        return this;
//    }

    public WorldBuilder design() {
        //read file from txt and transform into 2D Character array
        //map 2d array into tile object

        //tiles[50][25] = Tile.BOUNDS;
        File infile = new File("Resources/Castle/Castle.txt");
        char[] lineArr;
        try {
            Scanner sc = new Scanner(infile);
            char[][] charArr = new char[height][width];
            for(int i = 0; i< height;i++){
                lineArr = sc.nextLine().toCharArray();
                charArr[i] = lineArr;
            }
        char[][] tileArr = new char[width][height];
            for(int i =0;i<width;i++){
                for (int j=0;j<height;j++){
                    tileArr[i][j] = charArr[j][i];
                    switch (tileArr[i][j]){
                        case '▓':
                            tiles[i][j] = Tile.HEAVY_WALL;
                            break;
                        case '░':
                            tiles[i][j] = Tile.LIGHT_WALL;
                            break;
                        case '▒':
                            tiles[i][j] = Tile.MID_WALL;
                            break;
                        case '▄':
                            tiles[i][j] = Tile.BOT_SOLID_BLOCK;
                            break;
                        case '▀':
                            tiles[i][j] = Tile.TOP_SOLID_BLOCK;
                            break;
                        case '█':
                            tiles[i][j] = Tile.FULL_SOLID_BLOCK;
                            break;
                        default:
                            tiles[i][j] = Tile.FLOOR;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }




        return this;
    }

}