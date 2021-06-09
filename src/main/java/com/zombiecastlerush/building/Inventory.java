package com.zombiecastlerush.building;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import com.zombiecastlerush.role.Role;


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

    /**
     * This function can move Item from one Object's List<Item> to another
     * in this iteration, both Room and Role have Inventory reference attribute
     * so not necessary to implement static method in this iteration 2
     * If we decide to use centralized inventory management, this will be static
     * assume a Puzzle can only moveItem to a destination but not from a Object to a Puzzle
     */
    public void moveItem(Item item, Object destination){
        if(Objects.isNull(item) || Objects.isNull(destination)){
            throw new IllegalArgumentException("Invalid null input argument");
        } else {
            if(!this.getItems().contains(item)){
                throw new IllegalArgumentException("Nonexistent Item. Can not relocate it.");
            }
            this.getItems().remove(item); // remove item from current list
            if(destination instanceof Role){
                System.out.println("Move item to Role");
                ((Role) destination).getInventory().addItems(item);
            } else if (destination instanceof Room){
                System.out.println("Move item to Role");
                ((Room) destination).getInventory().addItems(item);
            } else {
                throw new IllegalArgumentException("Invalid destination to retain an Item");
            }
        }
    }
}
