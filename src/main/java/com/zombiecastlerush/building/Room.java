package com.zombiecastlerush.building;

import com.zombiecastlerush.entity.Entity;

import java.util.ArrayList;
import java.util.List;


public class Room extends Entity {
    private String name;
    private String description;
    public Inventory inventory = new Inventory();
    private List<Room> connectedRooms = new ArrayList<>();

    //constructors
    public Room(String name, String description) {
        setName(name);
        setDescription(description);
        setInventory(new Inventory());
    }

    //Setters and Getters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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