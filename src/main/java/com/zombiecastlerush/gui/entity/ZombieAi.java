package com.zombiecastlerush.gui.entity;

public class ZombieAi extends CreatureAi {
    private Creature player;
    private EntityFactory factory;


    public ZombieAi(Creature creature, EntityFactory factory) {
        super(creature);
        this.factory = factory;
    }

    public void onUpdate(){
            wander();
    }

}