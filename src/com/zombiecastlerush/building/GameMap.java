package com.zombiecastlerush.building;

import java.util.Objects;

/**
 * The Map is a singleton class for this Game
 * it can load a map or all rooms for the whole game
 * it provides a reference of the entry room (the head of this graph)
 */
public class GameMap {
    private Room entryRoom;
    // TODO: Do we need to maintain this reference? what if we have multiple players?
    private Room playerPosition;
    private static GameMap instance;
    private GameMap(){
        //TODO: map initiation
    }

    /**
     * @return the single object of Map class
     */
    public static GameMap getInstance(){
        if(Objects.isNull(GameMap.instance)){
            GameMap.instance = new GameMap();
        }
        return instance;
    }

    /**
     * traverse the map starting from entryRoom
     * the return type need to be adjusted in GUI or web application
     */
    public void displayMap(){
        //TODO: implement a traverse algorithm

        //TODO: Web application can return a json format map for web page to render
    }

    /**
     * loadMap from a text file or database
     * TODO: advance version reads map from database
     */
    public void loadMap(){
        System.out.println("Loading a Map...\nMap loading completed.");
    }

    /**
     * TODO: cheating method that provides direction how to escape
     */
    public void compass(){

    }

    /**
     * TODO: what does getPosition provide?
     * @return
     */
    public Room getPosition(){
        return null;
    }
}
