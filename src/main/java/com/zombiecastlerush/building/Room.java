package com.zombiecastlerush.building;

import com.zombiecastlerush.entity.Entity;

import java.util.ArrayList;
import java.util.List;


public class Room extends Entity {
    public Inventory inventory = new Inventory();
    private List<Room> connectedRooms = new ArrayList<>();

    //constructors
    public Room(String name, String description) {
        super.setName(name);
        super.setDescription(description);
        setInventory(new Inventory());
    }

    public List<Room> getConnectedRooms() {
        return connectedRooms;
    }

    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public String toString() {
        return getName();//+ "Connected Rooms: " + connectedRooms.toString(;
    }

    //Methods
    //add room to the connected rooms List for this room
    public void addConnectedRooms(Room room) {
        this.connectedRooms.add(room);
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}