package com.zombiecastlerush.gui.entity;

public class SellerAi extends CreatureAi {

    public SellerAi(Creature creature) {
        super(creature);
    }

    public void onUpdate(){
        stayStill();
    }

}