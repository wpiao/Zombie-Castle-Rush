package com.zombiecastlerush.building;

import com.zombiecastlerush.entity.Entity;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@JsonPropertyOrder({"name", "description", "connectedRooms", "challenge", "inventory"})
public class Room extends Entity {
    private String name;
    private String description;
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

    /**
     * @return
     */
    @JsonGetter("connectedRooms")
    public List<String> displayConnectedRooms() {
        List<String> list = new ArrayList<>();
        for (Room r : this.connectedRooms) {
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
    public String toString() {
        return getName();//+ "Connected Rooms: " + connectedRooms.toString(;
    }

    //Methods
    //add room to the connected rooms List for this room
    public void addConnectedRooms(Room... rooms) {
        this.connectedRooms.addAll(Arrays.asList(rooms));
    }
}
