package com.zombiecastlerush.gui.layout;

import java.io.File;
import java.io.FileNotFoundException;
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

    public WorldBuilder design(String path) {

        File infile = new File(path);
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
                        case '|':
                            tiles[i][j] = Tile.CASTLE_VER_DOOR;
                            break;
                        case ']':
                            tiles[i][j] = Tile.ROOM_VER_DOOR;
                            break;
                        case '-':
                            tiles[i][j] = Tile.ROOM_HOR_DOOR;
                            break;
                        case '_':
                            tiles[i][j] = Tile.CASTLE_HOR_DOOR;
                            break;
                        case '≡':
                            tiles[i][j] = Tile.BOX;
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