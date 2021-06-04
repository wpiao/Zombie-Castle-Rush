package com.zombiecastlerush.util;


import com.zombiecastlerush.role.RoleController;

/**
 * singleton class Game
 * it provides access to a Map and a role Controller
 */
public class Game {
    private RoleController rollController;
    private Map map;
    private static Game game;

    private Game(){
        this.initiateController();
        this.initiateMap();
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
        this.rollController.wakeUpEnemies();
        this.rollController.automatePlayers();
    }

    /**
     * TODO: what does stop() provide?
     */
    public void stop() {
        // TODO: stop game and release all connected resources
        System.out.println("Game stopped here.\n Saving status and releasing resources");
    }

    /**
     * TODO: initiate the roll Controller
     */
    private void initiateController(){
        this.rollController = RoleController.getInstance();
        this.rollController.createEnemy(1);
        this.rollController.createEnemy(2);
        this.rollController.createPlayer(1);
    }

    /**
     * TODO: initiate the Map
     */
    private void initiateMap(){
        this.map = Map.getInstance();
        this.map.loadMap();
    }
}
