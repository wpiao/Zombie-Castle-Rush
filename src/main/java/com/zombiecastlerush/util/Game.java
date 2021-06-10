package com.zombiecastlerush.util;

import com.zombiecastlerush.building.Castle;
import com.zombiecastlerush.role.Player;

/**
 * singleton class Game
 * it provides access to a Map and a role Controller
 */
public class Game {
    private static Game game;
    private Castle castle = new Castle();
    private Player player;

    private Game(){
    }

    public static Game getInstance(){
        if (Game.game == null){
            Game.game = new Game();
        }
        return Game.game;
    }

    /**
     * TODO: What does start() provide?
     */
    public void start(){
        System.out.println("Game started here...");
        Prompter.getUserInput("Welcome to Zombie Castle Rush! Press enter to continue.");
        String userName = Prompter.getUserInput("Please enter your name");
        player = new Player(userName);
        showInstructions();
        Prompter.getUserInput("\nPress enter to continue.");
        player.setCurrentPosition(castle.getCastleRooms().get("Castle-Hall"));
        while (true) {
            Prompter.controller(player);
        }
    }

    /**
     * TODO: what does stop() provide?
     */
    public void stop() {
        System.out.println("Game stopped here.\nSaving status and releasing resources");
        System.exit(0);
    }

    public void showInstructions(){
        System.out.print("\nGame Instructions:");
        System.out.println("\n1. To go somewhere, please type go and one of the available locations displayed");
        System.out.println("2. To attempt a puzzle, please type \"attempt puzzle\"");
    }
}