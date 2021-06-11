package com.zombiecastlerush.role;

import com.zombiecastlerush.building.Room;

public class Enemy extends Role{
    public Enemy(String name) {
        super(name);
    }

    public Enemy(String name, Room room) {
        super(name, room);
    }
}