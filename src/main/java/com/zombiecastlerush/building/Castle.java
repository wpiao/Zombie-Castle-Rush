package com.zombiecastlerush.building;

import java.util.HashMap;
import java.util.Map;

public class Castle {
    //create a map of rooms in castle
    private Map<String, Room> castleRooms = new HashMap<>();

    //Ctor
    public Castle(){
        // create rooms
        Room eastWing = new Room("East-Wing","This room is on the East side.");
        Room westWing = new Room("West-Wing","This room is on the West side.");
        Room castleHall = new Room("Castle-Hall","This hall connects the West Wing, East Wing and the Draw Bridge.");
        Room drawBridge = new Room("Draw-Bridge","This is the draw bridge");

        //add connected rooms to room
        eastWing.addConnectedRooms(castleHall);
        castleHall.addConnectedRooms(drawBridge,eastWing,westWing);
        drawBridge.addConnectedRooms(westWing,castleHall);
        westWing.addConnectedRooms(castleHall,drawBridge);

        //add Challenge to room
        eastWing.setChallenge(new Puzzle("East-Wing Puzzle","What is (2+2) X (2-2)?","0"));
        eastWing.getChallenge().inventory.addItems(new Item("Mr.Big Potato","This is a very big potato"));
        westWing.setChallenge(new Puzzle("West-Wing Puzzle","What is (2+2) X (2-2)?","0"));
        castleHall.setChallenge(new Puzzle("Caste-Hall Puzzle","What is (2+2) X (2-2)?","0"));
        castleHall.getChallenge().inventory.addItems(new Item("Mr. Small Potato","This is a very small potato"));
        drawBridge.setChallenge(new Puzzle("Draw-Brdige Puzzle","What is (2+2) X (2-2)?","0"));

        //Add rooms to castleRooms
        castleRooms.put(eastWing.getName(), eastWing);
        castleRooms.put(westWing.getName(), westWing);
        castleRooms.put(castleHall.getName(), castleHall);
        castleRooms.put(drawBridge.getName(), drawBridge);

    }

    //getter
    public Map<String, Room> getCastleRooms() {
        return castleRooms;
    }

    @Override
    public String toString(){
        return castleRooms.keySet().toString();
    }
}