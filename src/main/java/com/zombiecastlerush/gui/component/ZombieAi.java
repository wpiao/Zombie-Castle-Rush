package com.zombiecastlerush.gui.component;

import java.io.Serializable;

public class ZombieAi extends CreatureAi implements Serializable {
    private Creature player;
    private final EntityFactory factory;


    public ZombieAi(Creature creature, EntityFactory factory) {
        super(creature);
        this.factory = factory;
    }

    public void onUpdate(){
            wander();
    }

}