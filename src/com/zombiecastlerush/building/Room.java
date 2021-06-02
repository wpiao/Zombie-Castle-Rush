package com.zombiecastlerush.building;

import com.zombiecastlerush.util.Directions;

import java.util.List;

public class Room {
    private int id;
    // TODO: list or hash map for more than 4 rooms
    private Room[] connectedRooms;

    public Room(){
        connectedRooms = new Room[4];
        this.id = -1;
    }

    public Room(int id){
        this();
        this.id = id;
    }

    /**
     * TODO: what service does addRoom provide?
     * @param anotherRoom
     * @param direction
     */
    public void addRoom(Room anotherRoom, Directions direction){
        // TODO: switch statement to connect another room to this Room
    }

    public int getId(){
        return this.id;
    }

    @Override
    public String toString(){
        return "Room #" + getId();
    }
}
