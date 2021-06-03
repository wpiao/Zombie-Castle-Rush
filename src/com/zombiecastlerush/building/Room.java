package com.zombiecastlerush.building;

import com.zombiecastlerush.util.Directions;

import java.util.HashMap;
import java.util.Map;

class Room {
    private String name;
    private String description;
    private Map<String,Room> connectedRooms = new HashMap<String,Room>();
    private String [] items;

    //cosntructors
    public Room(String name, String description){
        setName(name);
        setDescription(description);
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

    public Map<String, Room> getConnectedRooms() {
        return connectedRooms;
    }

    public String[] getItems() {
        return items;
    }

    @Override
    public String toString(){
        return getName()+": "+ getDescription() + "Connected Rooms: " + connectedRooms.keySet();
    }

    //Methods
    //add direction as the key and room as the value to the connected rooms Map for this room
    public void addConnectedRooms(String direction, Room room) {
        this.connectedRooms.put(direction,room);
    }

    //need to work on this
    public void setItems(String[] items) {
        this.items = items;
    }
}