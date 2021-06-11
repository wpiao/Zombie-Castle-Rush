package com.zombiecastlerush.entity;

import com.fasterxml.jackson.annotation.*;
import com.zombiecastlerush.building.Inventory;
import com.zombiecastlerush.building.Item;
import com.zombiecastlerush.building.Room;

/**
 * base class for all roles
 * TODO: add more functions and description
 */
@JsonPropertyOrder({"id", "name", "room", "health"})
public class Role extends Entity {
    private final int MAX_HEALTH = 100;
    private final int MIN_HEALTH = 0;
    private Room room;
    public Inventory inventory = new Inventory();
    private int health; // range from 0-100

    // cannot have a Role without name
    private Role() {
        this.health = MAX_HEALTH;
        this.room = null;
        this.inventory = new Inventory();
    }

    public Role(String name) {
        this();
    }

    public Role(String name, Room room) {
        this(name);
        this.room = room;
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
        this.setHealth(Math.max((this.getHealth() - points), MIN_HEALTH));
    }

    @JsonGetter("room")
    public Room getCurrentPosition() {
        return this.room;
    }

    /**
     * change current room where this Role is located
     *
     * @param room room reference
     */
    @JsonSetter("room")
    public void setCurrentPosition(Room room) {
        this.room = room;
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

    public Inventory getInventory() {
        return inventory;
    }

    // Inventory methods
    public Item pickUp(Item item) {
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

    public Item drop(Item item) {
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

    public void dropAll() {
        for (Item item : this.inventory.getItems()) {
            this.getCurrentPosition().inventory.addItems(item);
        }
        this.inventory.deleteAllItems();
    }
}

