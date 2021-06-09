package com.zombiecastlerush.building;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a model class to hold inventory items
 */
public class Inventory {

    private List<Item> items = new ArrayList<>();

    /**
     * Initializes a newly created empty Inventory Object
     */
    public Inventory() {
    }

    /**
     * Gets the Object inventory list
     * @return a <code> list </code> of Object inventory items
     */
    public List<Item> getItems() {
        return items;
    }

    public void addItems(Item... itemsToBeAdded) {
        for (Item item : itemsToBeAdded) {
            items.add(item);
        }
    }

    public void deleteItems(Item... itemsToBeDeleted) {
        for (Item item : itemsToBeDeleted) {
            items.remove(item);
        }
    }

    public void transferItem(Inventory fromInv, Inventory toInv, Item... item) {
        fromInv.deleteItems(item);
        toInv.addItems(item);
    }
}