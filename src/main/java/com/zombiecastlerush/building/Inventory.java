package com.zombiecastlerush.building;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.zombiecastlerush.entity.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * This is a model class to hold inventory items
 */
@JsonPropertyOrder({"items"})
public class Inventory {

    private List<Item> items = new ArrayList<>();

    /**
     * Initializes a newly created empty Inventory Object
     */
    public Inventory() {
    }

    /**
     * Gets the Object inventory list
     *
     * @return a <code> list </code> of Object inventory items
     */
    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> list) {
        this.items = list;
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

    public void deleteAllItems() {
        items.clear();
    }

    /**
     * This function can move Item from one Object's List<Item> to another
     * in this iteration, both Room and Role have Inventory reference attribute
     * so not necessary to implement static method in this iteration 2
     * If we decide to use centralized inventory management, this will be static
     * assume a Puzzle can only moveItem to a destination but not from a Object to a Puzzle
     */
    public void moveItem(Item item, Entity destination) {
        if (Objects.isNull(item) || Objects.isNull(destination)) {
            throw new IllegalArgumentException("Invalid null input argument");
        } else {
            if (!this.getItems().contains(item)) {
                throw new IllegalArgumentException("Nonexistent Item. Can not relocate it.");
            }
            this.getItems().remove(item); // remove item from current list
            destination.getInventory().addItems(item);
        }
    }

    @Override
    public String toString() {
        String listString = items.stream().map(Item::getName)
                .collect(Collectors.joining(", "));
        return listString;
    }
}
