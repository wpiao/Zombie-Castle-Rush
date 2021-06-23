package com.zombiecastlerush.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zombiecastlerush.building.Castle;
import com.zombiecastlerush.entity.Player;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SaveLoadTest {
    @Test
    public void convertOjbectToNodeTest() {
        Castle castle = new Castle();
        Player player = new Player("Test");
        player.setCurrentPosition(castle.getCastleRooms().get("Castle-Hall"));
        SaveAndLoad.save(castle, player);
    }

    @Test
    public void gameSaveTest() {
        Game game = Game.getInstance();
        game.save();
    }

    @Test
    public void loadGameTest() {
        try {
            File saveFile = new File(SaveAndLoad.getSaveLocation());
            ObjectMapper mapper = new ObjectMapper();
            JsonNode castleNode = mapper.readTree(saveFile).get("castle");
            System.out.println(castleNode.toString());
            Castle castle = mapper.readValue(castleNode.toString(), Castle.class);
            System.out.println(castle.getCastleRooms());
            /*JsonNode playerNode = mapper.readTree(saveFile).get("player");
            Player player = mapper.readValue(playerNode.asText(), Player.class);*/
        } catch (FileNotFoundException e) {
            System.err.println("Save file not found.");
        } catch (IOException e) {
            System.err.println("Error reading save file.");
            System.out.println();
            e.printStackTrace(); // remove for final build.
        }
    }

    @Test
    public void gameLoad() {
        //Game.getInstance().load();
        //Game.getInstance().checkConnections();
    }
}