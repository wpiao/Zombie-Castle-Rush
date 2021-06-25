package com.zombiecastlerush.gui.entity;

import com.zombiecastlerush.gui.layout.Tile;

public class SellerAi extends CreatureAi {

    public SellerAi(Creature creature) {
        super(creature);
    }

    public void onUpdate(){
        stayStill();
    }

}