package com.zombiecastlerush.entity;

import com.zombiecastlerush.building.Inventory;

public class Entity {
    private String name;
    private Inventory inventory = new Inventory();
    private String description;

    public Inventory getInventory() {
        return inventory;
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

    public String getName() {
        return name;
    }
}