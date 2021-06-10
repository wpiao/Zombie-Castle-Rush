package com.zombiecastlerush.building;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder({"name", "description", "connectedRooms", "challenge", "inventory"})
public class Room {
    private String name;
    private String description;
    public Inventory inventory = new Inventory();
    private List<Room> connectedRooms = new ArrayList<>();
    private Challenge challenge;

    //constructors
    public Room(String name, String description){
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

    /**
     *
     * @return
     */
    @JsonGetter("connectedRooms")
    public List<String> displayConnectedRooms(){
        List<String> list = new ArrayList<>();
        for(Room r : this.connectedRooms){
            list.add(r.getName());
        }
        return list;
    }

    public Challenge getChallenge() {
        return challenge;
    }

    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }

    @Override
    public String toString(){
        return getName();//+ "Connected Rooms: " + connectedRooms.toString(;
    }

    //Methods
    //add room to the connected rooms List for this room
    public void addConnectedRooms(Room ...rooms) {
        for(Room room:rooms){
            this.connectedRooms.add(room);
        }
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Inventory getInventory(){
        return this.inventory;
    }
}