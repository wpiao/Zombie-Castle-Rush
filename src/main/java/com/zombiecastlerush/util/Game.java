package com.zombiecastlerush.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zombiecastlerush.building.Castle;
import com.zombiecastlerush.building.Room;
import com.zombiecastlerush.entity.Player;
import com.zombiecastlerush.gui.AppMain;

import javax.swing.*;

import java.awt.*;
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
    public static Castle castle = new Castle();
    private Player player;
    private static Music backgroundMusic = new Music("Resources/sounds/background.wav");

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
        backgroundMusic.loop();
        Prompter.showWelcomeScreen();
        // choose game mode
        Prompter.showGameModeOptions();

        String gameOption = Prompter.chooseGameMode();
        if (gameOption.equals("1")) {
            // console mode
            System.out.println(Parser.GREEN+"Welcome to Zombie Castle Rush!\n" + Parser.ANSI_RESET);
            if (new File("Resources/save.json").exists()) {
                String startType;
                do {
                    startType = Inputs.getUserInput("\nType 'N' for new game or 'C' to continue:").strip().toLowerCase();

                    if (startType.equals("n")) {
                        newGame();
                    } else if (startType.equals("c")) {
                        try {
                            load();
                        } catch (IOException e) {
                            startType = "x";
                            Inputs.getUserInput("Hit Enter to continue.");
                            Prompter.clearScreen();
                        }
                    } else {
                        Inputs.getUserInput("You entered invalid input.\nHit Enter to continue.");
                        Prompter.clearScreen();
                    }
                } while (!startType.equals("n") && !startType.equals("c"));
            } else {
                newGame();
            }
            while (true) {
                GameLogic.advanceGame(player);
            }

        } else if (gameOption.equals("2")) {
            // roguelike mode
            AppMain app = new AppMain();
            //setIcon(app);
            app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            app.setVisible(true);
        }
    }

    void setIcon(AppMain app){
        Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Welcome/icon.png"));
        if (System.getProperty("os.name").toLowerCase().contains("windows")){
            app.setIconImage(icon);
        } else{
            Taskbar taskbar = Taskbar.getTaskbar();
            taskbar.setIconImage(icon);
        }
    }

    void newGame() {
        String userName = Inputs.getUserInput(Parser.PURPLE+ "Please enter your name:" + Parser.ANSI_RESET);
        player = new Player(userName);
        player.setCurrentPosition(castle.getCastleRooms().get("Castle-Hall"));
        Prompter.showInstructions();
    }

    void save() {
        SaveAndLoad.save(castle, player);
    }

    void load() throws IOException {
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
            throw e;
        } catch (IOException e) {
            System.err.println("Error reading save file.");
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * TODO: what does stop() provide?
     */
    public void stop() {
        System.out.println(Parser.CYAN +"Thank you for playing Zombie Castle Rush!" + Parser.ANSI_RESET);
        System.exit(0);
    }

    public static Music getBackgroundMusic() {
        return backgroundMusic;
    }

    public Player getPlayer() {
        return player;
    }
}