package com.zombiecastlerush.gui.entity;

import com.zombiecastlerush.gui.entity.GuiItem;

import java.util.ArrayList;
import java.util.List;

public class Inventory extends com.zombiecastlerush.building.Inventory {

    private List<GuiItem> items;
    public List<GuiItem> getGuiItems() { return items; }
    public GuiItem get(int i) { return items.get(i); }
    private final int MAX = 7;

    public Inventory(){
        items = new ArrayList<>();
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