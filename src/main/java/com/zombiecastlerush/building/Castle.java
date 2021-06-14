package com.zombiecastlerush.building;

import java.util.HashMap;
import java.util.Map;

public class Castle {
    //create a map of rooms in castle
    private Map<String, Room> castleRooms = new HashMap<>();

    //Ctor
    public Castle() {
        // create rooms
        Room eastWing = new Room("East-Wing", "This room is on the East side.");
        Room westWing = new Room("West-Wing", "This room is on the West side.");
        Room castleHall = new Room("Castle-Hall", "This hall connects the West Wing, East Wing and the Draw Bridge.");
        Room drawBridge = new Room("Draw-Bridge", "This is the draw bridge");
        Room combatHall = new Room("Combat-Hall", "The hall where souls are laid to rest.");

        //add connected rooms to room
        eastWing.addConnectedRooms(castleHall, combatHall);
        castleHall.addConnectedRooms(drawBridge, eastWing, westWing);
        drawBridge.addConnectedRooms(westWing, castleHall);
        westWing.addConnectedRooms(castleHall, drawBridge);
        combatHall.addConnectedRooms(eastWing);

        //add Challenge to room
        eastWing.setChallenge(new Puzzle("East-Wing-Puzzle", "What is (2+2) X (2-2)?", "0"));
        eastWing.getChallenge().getInventory().addItems(new Item("Knife", "This is a knife"));
        westWing.setChallenge(new Puzzle("West-Wing-Puzzle", "What is (2+2) X (2-2)?", "0"));
        castleHall.setChallenge(new Puzzle("Castle-Hall-Puzzle", "What is (2+2) X (2-2)?", "0"));
        castleHall.getChallenge().getInventory().addItems(new Item("Fork", "This is a fork"));
        drawBridge.setChallenge(new Puzzle("Draw-Bridge-Puzzle", "What is (2+2) X (2-2)?", "0"));
        combatHall.setChallenge(new Combat("Life or Death Battle"));

        //Add rooms to castleRooms
        castleRooms.put(eastWing.getName(), eastWing);
        castleRooms.put(westWing.getName(), westWing);
        castleRooms.put(castleHall.getName(), castleHall);
        castleRooms.put(drawBridge.getName(), drawBridge);
        castleRooms.put(combatHall.getName(), combatHall);

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