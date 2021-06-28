package com.zombiecastlerush.gui.component;

import java.io.Serializable;

public class SellerAi extends CreatureAi implements Serializable {

    public SellerAi(Creature creature) {
        super(creature);
    }

    public void onUpdate(){
        stayStill();
    }

}