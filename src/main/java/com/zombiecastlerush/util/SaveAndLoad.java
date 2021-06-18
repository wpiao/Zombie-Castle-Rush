package com.zombiecastlerush.util;

// import Jackson

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zombiecastlerush.building.Castle;
import com.zombiecastlerush.entity.Player;

import java.io.File;
import java.io.IOException;

/**
 * Writes the current state of a Castle and Player object to a save.json file.
 * Also reads that file to recreate the objects.
 */
class SaveAndLoad {
    private static final String saveLocation = "Resources/save.json";

    /**
     * Saves the Castle and Player objects to a json file.
     * {
     *     "castle": {
     *         <Castle object>
     *     },
     *     "player": {
     *         <Player object>
     *     }
     * }
     * @param castle The Castle object used in Game.java
     * @param player The Player object used in Game.java
     */
    static void save(Castle castle, Player player) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode castleNode = mapper.valueToTree(castle);
            JsonNode playerNode = mapper.valueToTree(player);
            ObjectNode root = mapper.createObjectNode();
            root.set("castle", castleNode);
            root.set("player", playerNode);
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(saveLocation), root);
        } catch (IOException e) {
            System.err.println("There was an error creating save file.");
        }
    }
}