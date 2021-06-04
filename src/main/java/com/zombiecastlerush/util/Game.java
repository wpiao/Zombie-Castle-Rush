package com.zombiecastlerush.util;


/**
 * singleton class Game
 * it provides access to a Map and a role Controller
 */
public class Game {
    private static Game game;

    private Game(){
    }

    public static Game getInstance(){
        if(Game.game == null){
            Game.game = new Game();
        }
        return Game.game;
    }

    /**
     * TODO: What does start() provide?
     */
    public void start(){
        System.out.println("Game started here...");
    }

    /**
     * TODO: what does stop() provide?
     */
    public void stop() {
        // TODO: stop game and release all connected resources
        System.out.println("Game stopped here.\n Saving status and releasing resources");
    }
}
