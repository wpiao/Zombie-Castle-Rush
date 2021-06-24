package com.zombiecastlerush.gui.entity;

public class Inventory {

    private Item[] items;
    public Item[] getItems() { return items; }
    public Item get(int i) { return items[i]; }

    public Inventory(int max){
        items = new Item[max];
    }
}