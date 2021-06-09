package com.zombiecastlerush.building;

/**
 * challenge class provides the challenge description and validation
 * TODO: more methods and attributes
 */
class Challenge {
    private String description;
    private boolean cleared;

    public Challenge(String description){
        //TODO: build our Challenge
        this.description=description;
        this.cleared = false;
    }

    public String getDescription(){
        return this.description;
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
