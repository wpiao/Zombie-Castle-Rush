package com.zombiecastlerush.building;

import com.zombiecastlerush.util.AsciiParser;
import com.zombiecastlerush.util.Game;
import com.zombiecastlerush.util.Parser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapOfGame {
    private static final Path path = Path.of("./Resources/ascii/", "map.txt");

    public static void readMap() {
        try {
            List<String> asciiLines = AsciiParser.parse(path);
            String mapRoomName = null;
            switch (Game.getInstance().getPlayer().getCurrentPosition().getName()) {
                case "East-Wing":
                    mapRoomName = "EastWing";
                    break;
                case "West-Wing":
                    mapRoomName = "WestWing";
                    break;
                case "Castle-Hall":
                    mapRoomName = "CastleHall";
                    break;
                case "Draw-Bridge":
                    mapRoomName = "DrawBridge";
                    break;
                case "Combat-Hall":
                    mapRoomName = "CombatHall";
                    break;
                case "Shop":
                    mapRoomName = "Shop";
            }
            for (String line : asciiLines) {
                if (line.contains(mapRoomName)) {
                    line = line.replace(mapRoomName, Parser.YELLOW + mapRoomName + Parser.ANSI_RESET);
                }
                System.out.println(line);
            }
        } catch (IOException exception) {
            System.out.println("There was an error reading the map.");
        }
    }
    public static void winArt(){
        String win = null;
        try {
            win = new String(Files.readAllBytes(Paths.get("Resources/ascii/win.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            System.out.println(Parser.YELLOW + win + Parser.ANSI_RESET);
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void loseArt(){
        String lost = null;
        try {
            lost = new String(Files.readAllBytes(Paths.get("Resources/ascii/lose.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            System.out.println(Parser.RED + lost + Parser.ANSI_RESET);
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}