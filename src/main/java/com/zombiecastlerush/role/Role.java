package com.zombiecastlerush.role;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zombiecastlerush.building.Inventory;
import com.zombiecastlerush.building.Item;
import com.zombiecastlerush.building.Room;

/**
 * base class for all roles
 * TODO: add more functions and description
 */
@JsonPropertyOrder({"name", "health", "inventory", "currentRoom"})
public class Role {
    private final int MAX_HEALTH = 100;
    private final int MIN_HEALTH = 0;
    private String name;
    private Room currentRoom;
    public Inventory inventory = new Inventory();
    private int health; // range from 0-100

    // cannot have a Role without name
    private Role() {
        this.health = MAX_HEALTH;
        this.currentRoom = null;
        this.name = null;
        this.inventory = new Inventory();
    }

    public Role(String name) {
        this();
        this.name = name;
    }

    public Role(String name, Room currentRoom) {
        this(name);
        this.currentRoom = currentRoom;
    }

    /**
     * increase this role's health points
     * health range 0 - 100
     *
     * @param points how many points are increased
     */
    public void increaseHealth(int points) {
        if (points < 0) {
            throw new IllegalArgumentException("Invalid negative health points");
        }
        this.setHealth((points + this.getHealth()) > MAX_HEALTH ? MAX_HEALTH : points + this.getHealth());
    }

    /**
     * decrease this role's health points
     *
     * @param points how many points are decreased
     */
    public void decreaseHealth(int points) {
        if (points < 0) {
            throw new IllegalArgumentException("Invalid negative health points");
        }
        this.setHealth((this.getHealth() - points) < MIN_HEALTH ? MIN_HEALTH : this.getHealth() - points);
    }

    @JsonGetter("currentRoom")
    public Room getCurrentPosition() {
        return this.currentRoom;
    }

    /**
     * change current room where this Role is located
     *
     * @param room room reference
     */
    @JsonSetter("currentRoom")
    public void setCurrentPosition(Room room) {
        this.currentRoom = room;
    }

    public int getHealth() {
        return this.health;
    }

    /**
     * validate health range 0 - 100
     *
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

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    // Inventory methods
    public Item pickUp (Item item){
        for (Item existingItem : this.getCurrentPosition().inventory.getItems()) {
            System.out.println(existingItem.getName());
            if (item.equals(existingItem)) {
                this.inventory.addItems(item);
                this.getCurrentPosition().inventory.deleteItems(item);
                System.out.println(item.getName() + " picked up by " + this.getName());
                return item;
            }
        }
        return null;
    }

    public Item drop (Item item){
        for (Item existingItem : this.inventory.getItems()) {
            if (item.equals(existingItem)) {
                this.getCurrentPosition().inventory.addItems(item);
                this.inventory.deleteItems(item);
                System.out.println(item.getName() + " dropped by " + this.getName());
                return item;
            }
        }
        return null;
    }

    public void dropAll(){
        for (Item item : this.inventory.getItems()) {
            this.getCurrentPosition().inventory.addItems(item);
        }
        this.inventory.deleteAllItems();
    }

    /**
     * display Role's status in Json format {"name", "currentRoom", "health", "inventory"}
     * @return String Json format status {"name", "currentRoom", "health", "inventory"}
     * @throws JsonProcessingException
     */
    public String displayStatus() throws JsonProcessingException {
        return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
    }
}

