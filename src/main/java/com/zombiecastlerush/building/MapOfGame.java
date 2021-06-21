package com.zombiecastlerush.building;

import com.zombiecastlerush.util.AsciiParser;

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
            asciiLines.forEach(System.out::println);
        } catch (IOException exception) {
            System.out.println("There was an error reading the map.");
        }
    }
}