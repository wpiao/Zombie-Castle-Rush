package com.zombiecastlerush.building;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Castle {
    //create a map of rooms in castle
    private Map<Room, List<Room>> castleRooms = new HashMap<Room, List<Room>>();

    //Ctor
    Castle(){
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
        castleRooms.put(eastWing,eastWing.getConnectedRooms());
        castleRooms.put(westWing,westWing.getConnectedRooms());
        castleRooms.put(castleHall,castleHall.getConnectedRooms());
        castleRooms.put(drawBridge,drawBridge.getConnectedRooms());

    }

    //getter
    public Map<Room, List<Room>> getCastleRooms() {
        return castleRooms;
    }

    @Override
    public String toString(){
        return castleRooms.keySet().toString();
    }
}