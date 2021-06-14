package com.zombiecastlerush.building;

import java.util.*;

public class Castle {
    //create a map of rooms in castle
    private Map<String, Room> castleRooms = new HashMap<>();

    //Ctor
    public Castle() {
        // create rooms
        Room eastWing = new Room("East-Wing", "This room is on the East side.");
        Room westWing = new Room("West-Wing", "This room is on the West side.");
        Room castleHall = new Room("Castle-Hall", "This hall connects the West Wing, East Wing, the Draw Bridge and the Shop");
        Room drawBridge = new Room("Draw-Bridge", "This is the draw bridge");
        Shop shop = new Shop("Shop", "Welcome to our shop!");

        //add connected rooms to room
        eastWing.addConnectedRooms(castleHall);
        castleHall.addConnectedRooms(drawBridge, eastWing, westWing, shop);
        drawBridge.addConnectedRooms(westWing, castleHall);
        westWing.addConnectedRooms(castleHall, drawBridge);
        shop.addConnectedRooms(castleHall);

        //add Challenge to room
        eastWing.setChallenge(new Puzzle("East-Wing-Puzzle", "What is (2+2) X (2-2)?", "0"));
        eastWing.getChallenge().getInventory().addItems(new Item("Knife", "This is a knife", 25.0));
        westWing.setChallenge(new Puzzle("West-Wing-Puzzle", "What is (2+2) X (2-2)?", "0"));
        castleHall.setChallenge(new Puzzle("Castle-Hall-Puzzle", "What is (2+2) X (2-2)?", "0"));
        castleHall.getChallenge().getInventory().addItems(new Item("Fork", "This is a fork", 5.0));
        drawBridge.setChallenge(new Puzzle("Draw-Bridge-Puzzle", "What is (2+2) X (2-2)?", "0"));

        //add items to Rooms inventory
        shop.getInventory().addItems(
                new Item("Sword", "This is the sword of Destiny, made up of the Valyrian Steel", 100.0),
                new Item("Helmet", "This is the ultimate shield which will be carried by Captain America in the distant future", 50.0),
                new Item("Potion", "Drinking this potion will restore your health", 100.0)
        );

        //Add rooms to castleRooms
        castleRooms.put(eastWing.getName(), eastWing);
        castleRooms.put(westWing.getName(), westWing);
        castleRooms.put(castleHall.getName(), castleHall);
        castleRooms.put(drawBridge.getName(), drawBridge);
        castleRooms.put(shop.getName(), shop);

    }

    //getter
    public Map<String, Room> getCastleRooms() {
        return castleRooms;
    }

    @Override
    public String toString() {
        return castleRooms.keySet().toString();
    }
}