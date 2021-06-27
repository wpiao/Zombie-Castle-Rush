package com.zombiecastlerush.gui.component;

import com.zombiecastlerush.gui.layout.Tile;

import java.io.Serializable;

public class PlayerAi extends CreatureAi implements Serializable {

    public PlayerAi(Creature creature) {
        super(creature);
    }

    public void onEnter(int x, int y, Tile tile) {
        if (tile.isGround() || tile.isDoor() || tile.isBox()) {
            creature.x = x;
            creature.y = y;
        }

    }

}