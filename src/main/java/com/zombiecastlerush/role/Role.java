package com.zombiecastlerush.role;

import com.fasterxml.jackson.annotation.*;
import com.zombiecastlerush.building.Inventory;
import com.zombiecastlerush.building.Room;

/**
 * base class for all roles
 * TODO: add more functions and description
 */
@JsonPropertyOrder({"id", "name", "room", "health"})
class Role {
    private final int MAX_HEALTH = 100;
    private final int MIN_HEALTH = 0;
    private String name;
    private Room room;
    public Inventory inventory;
    private int health; // range from 0-100
    //TODO: inventory list

    // cannot have a Role without name
    private Role(){
        this.health = MAX_HEALTH;
        this.room = null;
        this.name = null;
    }

    public Role(String name){
        this();
        this.name = name;
    }

    public Role(String name, Room room){
        this(name);
        this.room = room;
    }

    /**
     * increase this role's health points
     * health range 0 - 100
     * @param points how many points are increased
     */
    public void increaseHealth(int points){
        if(points < 0 ){
            throw new IllegalArgumentException("Invalid negative health points");
        }
        this.setHealth((points + this.getHealth()) > MAX_HEALTH ? MAX_HEALTH : points + this.getHealth());
    }

    /**
     * decrease this role's health points
     * @param points how many points are decreased
     */
    public void decreaseHealth(int points){
        if(points < 0 ){
            throw new IllegalArgumentException("Invalid negative health points");
        }
        this.setHealth((this.getHealth() - points) < MIN_HEALTH ? MIN_HEALTH : this.getHealth()-points);
    }

    @JsonGetter("room")
    public Room getCurrentPosition(){
        return this.room;
    }

    /**
     * change current room where this Role is located
     * @param room room reference
     */
    @JsonSetter("room")
    public void setCurrentPosition(Room room){
        this.room = room;
    }

    public int getHealth(){
        return this.health;
    }

    /**
     * validate health range 0 - 100
     * @param points input health points
     */
    public void setHealth(int points) {
        if (points > MAX_HEALTH || points < MIN_HEALTH) {
            throw new IllegalArgumentException(String.format("Invalid health range; health range from %d to %d\n", MIN_HEALTH, MAX_HEALTH));
        } else {
            this.health = points;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
