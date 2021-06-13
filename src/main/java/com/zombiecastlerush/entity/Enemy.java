package com.zombiecastlerush.entity;

import com.zombiecastlerush.building.Room;
import com.zombiecastlerush.entity.Role;

public class Enemy extends Role {
    public Enemy(String name) {
        super(name);
    }

    public Enemy(String name, Room room) {
        super(name, room);
    }
}