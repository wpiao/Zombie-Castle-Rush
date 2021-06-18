package com.zombiecastlerush.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zombiecastlerush.building.Castle;
import com.zombiecastlerush.entity.Player;
import com.zombiecastlerush.gui.AppMain;

import javax.swing.*;

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
        Prompter.showWelcomeScreen();
        // choose game mode
        Prompter.showGameModeOptions();

        String gameOption = Prompter.chooseGameMode();
        if (gameOption.equals("1")) {
            // console mode
            String userName = Inputs.getUserInput("Welcome to Zombie Castle Rush! \n\nPlease enter your name:");
            player = new Player(userName);
            player.setCurrentPosition(castle.getCastleRooms().get("Castle-Hall"));
            Prompter.showInstructions();
            while (true) {
                GameLogic.advanceGame(player);
            }
        } else if (gameOption.equals("2")) {
            // roguelike mode
            AppMain app = new AppMain();
            app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            app.setVisible(true);
        }
    }

    /**
     * TODO: what does stop() provide?
     */
    public void stop() {
        System.out.println("Thank you for playing Zombie Castle Rush!");
        System.exit(0);
    }
}