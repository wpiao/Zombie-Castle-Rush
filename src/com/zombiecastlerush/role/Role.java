package com.zombiecastlerush.role;

import com.zombiecastlerush.building.Room;

/**
 * base class for all roles
 * TODO: add more functions and description
 */
class Role {
    private int id;
    private Room room;

    public Role(int id){
        this.id = id;
        this.room = null;
    }

    public Role(int id, Room room){
        this(id);
        this.room = room;
    }

    public Room getCurrentPosition(){
        return this.room;
    }

    public void setCurrentPosition(Room room){
        this.room = room;
    }

    public int getId(){
        return this.id;
    }
}
