package com.zombiecastlerush.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zombiecastlerush.building.Castle;
import com.zombiecastlerush.building.Room;
import com.zombiecastlerush.entity.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * singleton class Game
 * it provides access to a Map and a role Controller
 */
public class Game {
    private static Game game;
    private Castle castle = new Castle();
    private Player player;

    private Game() {
    }

    public static Game getInstance() {
        if (Game.game == null) {
            Game.game = new Game();
        }
        return Game.game;
    }

    /**
     * TODO: What does start() provide?
     */
    public void start() throws JsonProcessingException {
        System.out.println("Welcome to Zombie Castle Rush!\n");
        String startType;
        do {
            startType = Inputs.getUserInput("\nType 'N' for new game or 'C' to continue:").strip().toLowerCase();

            if (startType.equals("n")) {
                String userName = Inputs.getUserInput("Please enter your name:");
                player = new Player(userName);
                player.setCurrentPosition(castle.getCastleRooms().get("Castle-Hall"));
                Prompter.showInstructions();
            } else if (startType.equals("c")) {
                load();
            } else {
                Inputs.getUserInput("You entered invalid input.\nHit Enter to continue.");
                Prompter.clearScreen();
            }
        } while(!startType.equals("n") && !startType.equals("c"));

        while (true) {
            GameLogic.advanceGame(player);
        }
    }

    void save() {
        SaveAndLoad.save(castle, player);
    }

    void load() {
        try {
            File saveFile = new File(SaveAndLoad.getSaveLocation());
            ObjectMapper mapper = new ObjectMapper();

            JsonNode castleNode = mapper.readTree(saveFile).get("castle");
            castle = mapper.readValue(castleNode.toString(), Castle.class);

            for (Room room: castle.getCastleRooms().values()) {
                for (String roomName : room.getConnectedRoomNames()) {
                    room.addConnectedRooms(castle.getCastleRooms().get(roomName));
                }
            }

            JsonNode playerNode = mapper.readTree(saveFile).get("player");
            player = mapper.readValue(playerNode.toString(), Player.class);
            player.setCurrentPosition(castle.getCastleRooms().get(player.getCurrentPosition().getName()));
        } catch (FileNotFoundException e) {
            System.err.println("Save file not found.");
        } catch (IOException e) {
            System.err.println("Error reading save file.");
            System.out.println();
            e.printStackTrace(); // remove for final build.
        }
    }

    /**
     * TODO: what does stop() provide?
     */
    public void stop() {
        System.out.println("Thank you for playing Zombie Castle Rush!");
        System.exit(0);
    }

    public void checkConnections() {
        for (Room room : castle.getCastleRooms().values()) {
            System.out.println(room.getName());
            System.out.println(room.getConnectedRooms() + "\n");
        }
    }
}