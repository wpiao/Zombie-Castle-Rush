package com.zombiecastlerush.role;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * singleton roll Controller class
 * TODO: what does the Controller class provide?
 * TODO: can we have a more descriptive class name?
 */
public class RoleController {
    // TODO: Do we only allow two players? Another data structure?
    private List<Thread> players;
    private List<Thread> enemies;

    // singleton controller
    private static RoleController roleController;
    private RoleController(){
        players = new ArrayList<>();
        enemies = new ArrayList<>();
    }

    public static RoleController getInstance(){
        if(Objects.isNull(roleController)){
            roleController = new RoleController();
        }
        return roleController;
    }

    public void createPlayer(int id){
        this.players.add(new Thread(new Player(id)));
    }

    public void createEnemy(int id){
        this.enemies.add(new Thread(new Enemy(id)));
    }

    public void wakeUpEnemies(){
        for(Thread e : enemies){
            e.start();
        }
    }

    public void automatePlayers(){
        for(Thread p : players){
            p.start();
        }
    }
}
