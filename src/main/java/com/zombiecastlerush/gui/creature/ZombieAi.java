package com.zombiecastlerush.gui.creature;

import com.zombiecastlerush.gui.creature.Creature;
import com.zombiecastlerush.gui.creature.CreatureAi;
import com.zombiecastlerush.gui.creature.CreatureFactory;

public class ZombieAi extends CreatureAi {
    private CreatureFactory factory;

    public ZombieAi(Creature creature, CreatureFactory factory) {
        super(creature);
        this.factory = factory;
    }

    public void onUpdate(){
    }
}
