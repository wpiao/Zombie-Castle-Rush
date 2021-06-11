package com.zombiecastlerush.building;

import com.zombiecastlerush.entity.Entity;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * challenge class provides the challenge description and validation
 * TODO: more methods and attributes
 */

@JsonPropertyOrder({"description, inventory, cleared"})
public class Challenge extends Entity {
    private String description;
    public Inventory inventory= new Inventory();
    private boolean cleared;

    public Challenge(String description){
        //TODO: build our Challenge
        super.setDescription(description);
        this.cleared = false;
    }

    /**
     * TODO: what does validation() provide?
     * @param answer
     * @return
     */
    public void setCleared(boolean answer){
        this.cleared = answer;
    }

    public boolean isCleared(){
        return cleared;
    }
}
