package com.zombiecastlerush.gui.entity;

import com.zombiecastlerush.gui.entity.GuiItem;

import java.util.ArrayList;
import java.util.List;

public class Inventory {

    private List<GuiItem> items;
    public List<GuiItem> getGuiItems() { return items; }

    private final int MAX = 7;

    public Inventory(){
        items = new ArrayList<>();
    }

    public GuiItem get(int i){
        return items.get(i);
    }

    public GuiItem get(String name) {
        for (GuiItem item:getGuiItems()) {
            if(item.name().equalsIgnoreCase(name)){
                return item;
            }
        }
        return null;
    }

    public void add(GuiItem item){
        items.add(item);
    }

    public boolean isFull(){

        return items.size() == MAX;
    }

    public void remove(GuiItem item) {
        items.remove(item);
    }
}