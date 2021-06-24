package com.zombiecastlerush.gui.creature;

import com.zombiecastlerush.gui.layout.Tile;
import com.zombiecastlerush.gui.screens.RiddleScreen;

public class PlayerAi extends CreatureAi {

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