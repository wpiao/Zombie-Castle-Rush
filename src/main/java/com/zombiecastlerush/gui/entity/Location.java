package com.zombiecastlerush.gui.entity;

public interface Location {
    public int x = 0;
    public int y = 0;
    public int[] location = new int[2];

    public default int[] getLocation(){
        location[0] = x;
        location[1] = y;
        return location;
    };

}
