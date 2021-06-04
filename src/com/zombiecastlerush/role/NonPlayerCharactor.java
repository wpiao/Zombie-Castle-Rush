package com.zombiecastlerush.role;

import com.zombiecastlerush.building.Room;

/**
 * TODO: a NPC runs a shop and provides necessary functions
 */
class NonPlayerCharactor extends Role{
    public NonPlayerCharactor(int id) {
        super(id);
    }

    public NonPlayerCharactor(int id, String name) {
        super(id, name);
    }

    public NonPlayerCharactor(int id, String name, Room room) {
        super(id, name, room);
    }
}
