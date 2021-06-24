package com.zombiecastlerush.building;

import com.zombiecastlerush.util.AsciiParser;
import com.zombiecastlerush.util.Game;
import com.zombiecastlerush.util.Parser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
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
}