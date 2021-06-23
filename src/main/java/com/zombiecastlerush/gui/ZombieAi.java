package com.zombiecastlerush.gui;

public class ZombieAi extends CreatureAi {
    private CreatureFactory factory;

    public ZombieAi(Creature creature, CreatureFactory factory) {
        super(creature);
        this.factory = factory;
    }

    public void onUpdate(){
    }
}
