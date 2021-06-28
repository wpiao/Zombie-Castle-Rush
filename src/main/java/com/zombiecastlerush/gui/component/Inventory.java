package com.zombiecastlerush.gui.component;

import java.io.Serializable;
import java.util.*;

public class Inventory implements Serializable {

    private final List<GuiItem> items;
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

    public Map<GuiItem, Integer> inventoryStats() {

        Map<GuiItem, Integer> freq = new HashMap<>();
        Set<GuiItem> uniqueItems = new HashSet<>(getGuiItems());
        for (GuiItem item : uniqueItems) {
            freq.put(item, Collections.frequency(getGuiItems(), item));
        }
        return freq;
    }




}