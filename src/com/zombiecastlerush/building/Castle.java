package com.zombiecastlerush.building;

import java.util.ArrayList;
import java.util.List;

class Castle {
    //create a list of rooms in castle
    List<Room>  castleRooms = new ArrayList<Room>();

    //Ctor
    Castle(){
        // create rooms
        Room eastWing = new Room("East Wing","This room is on the East side.");
        Room westWing = new Room("West Wing","This room is on the West side.");
        Room castleHall = new Room("Castle Hall","This hall connects the West Wing, East Wing and the Draw Bridge.");
        Room drawBridge = new Room("Draw Bridge","This is the draw bridge");

        //add connected rooms to room
        eastWing.addConnectedRooms("West",castleHall);
        castleHall.addConnectedRooms("South",drawBridge);
        castleHall.addConnectedRooms("East",eastWing);
        castleHall.addConnectedRooms("West",westWing);
        drawBridge.addConnectedRooms("North-West",westWing);
        drawBridge.addConnectedRooms("North",castleHall);
        westWing.addConnectedRooms("East",castleHall);
        westWing.addConnectedRooms("South-East",drawBridge);

        //Add rooms to castleRooms
        castleRooms.add(eastWing);
        castleRooms.add(westWing);
        castleRooms.add(castleHall);
        castleRooms.add(drawBridge);

    }

    @Override
    public String toString(){
        return castleRooms.toString();
    }


}