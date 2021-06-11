package com.zombiecastlerush.building;

import com.zombiecastlerush.entity.Entity;

/**
 * challenge class provides the challenge description and validation
 * TODO: more methods and attributes
 */
public class Challenge extends Entity {

    private boolean cleared;

    public Challenge(String description) {
        //TODO: build our Challenge
        super.setDescription(description);
        this.cleared = false;
    }

    /**
     * TODO: what does validation() provide?
     *
     * @param answer
     * @return
     */
    public void setCleared(boolean answer) {
        this.cleared = answer;
    }

    public boolean isCleared() {
        return cleared;
    }
}
