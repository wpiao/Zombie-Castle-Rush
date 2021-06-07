package com.zombiecastlerush.building;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: a Shop, a Room or a Player can HAS-A Inventory
 */
public class Inventory {
    private List<Item> items = new ArrayList<>();

    public Inventory(Item item){
        addItems(item);
    }

    public List<Item> getItems(){
        return items;
    }

    public void addItems(Item item){

    }

    public void deleteItems(Item item){
        items.remove(item);
    }
}
