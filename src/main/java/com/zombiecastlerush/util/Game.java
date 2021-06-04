package com.zombiecastlerush.util;


/**
 * singleton class Game
 * it provides access to a Map and a role Controller
 */
public class Game {
    private static Game game;

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
    }

    /**
     * TODO: what does stop() provide?
     */
    public void stop() {
        System.out.println("Game stopped here.\n Saving status and releasing resources");
    }

}
