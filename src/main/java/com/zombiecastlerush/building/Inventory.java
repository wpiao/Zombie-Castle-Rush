package com.zombiecastlerush.building;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: a Shop, a Room or a Player can HAS-A Inventory
 */
public class Inventory {
    private List<Item> items = new ArrayList<>();

    public Inventory(){
    }

    public void setItems(List<Item> list){
        this.items = list;
    }

    public List<Item> getItems(){
        return items;
    }

    public void addItems(Item item){
        items.add(item);

    }

    public void deleteItems(Item item){
        items.remove(item);
    }
}
