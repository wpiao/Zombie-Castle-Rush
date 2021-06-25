package com.zombiecastlerush.gui.entity;

import com.zombiecastlerush.gui.layout.Path;
import com.zombiecastlerush.gui.layout.Point;

import java.util.List;

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