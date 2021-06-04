package com.zombiecastlerush.building;

import java.util.HashMap;
import java.util.Map;

public class Castle {
    //create a map of rooms in castle
    private Map<String, Room> castleRooms = new HashMap<>();

    //Ctor
    public Castle(){
        // create rooms
        Room eastWing = new Room("East Wing","This room is on the East side.");
        Room westWing = new Room("West Wing","This room is on the West side.");
        Room castleHall = new Room("Castle Hall","This hall connects the West Wing, East Wing and the Draw Bridge.");
        Room drawBridge = new Room("Draw Bridge","This is the draw bridge");

        //add connected rooms to room
        eastWing.addConnectedRooms(castleHall);
        castleHall.addConnectedRooms(drawBridge);
        castleHall.addConnectedRooms(eastWing);
        castleHall.addConnectedRooms(westWing);
        drawBridge.addConnectedRooms(westWing);
        drawBridge.addConnectedRooms(castleHall);
        westWing.addConnectedRooms(castleHall);
        westWing.addConnectedRooms(drawBridge);

        //Add rooms to castleRooms
        castleRooms.put("eastWing", eastWing);
        castleRooms.put("westWing", westWing);
        castleRooms.put("castleHall", castleHall);
        castleRooms.put("drawBridge", drawBridge);

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