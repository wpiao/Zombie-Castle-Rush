package com.zombiecastlerush.util;

import com.zombiecastlerush.building.Castle;
import com.zombiecastlerush.entity.Player;

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
        player.setCurrentPosition(castle.getCastleRooms().get("Castle-Hall"));
        while (true) {
            Prompter.movePlayer(player);
        }
    }

    /**
     * TODO: what does stop() provide?
     */
    public void stop() {
        System.out.println("Game stopped here.\n Saving status and releasing resources");
    }
}
