package com.zombiecastlerush.building;

import com.zombiecastlerush.entity.Entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Room extends Entity {
    public Inventory inventory = new Inventory();
    private List<Room> connectedRooms = new ArrayList<>();
    private Challenge challenge;

    //constructors
    public Room(String name, String description) {
        super.setName(name);
        super.setDescription(description);
    }

    public List<Room> getConnectedRooms() {
        return connectedRooms;
    }

    public Challenge getChallenge() {
        return challenge;
    }

    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }

    @Override
    public String toString() {
        return getName();//+ "Connected Rooms: " + connectedRooms.toString(;
    }

    //Methods
    //add room to the connected rooms List for this room
    public void addConnectedRooms(Room... rooms) {
        this.connectedRooms.addAll(Arrays.asList(rooms));
    }
}
