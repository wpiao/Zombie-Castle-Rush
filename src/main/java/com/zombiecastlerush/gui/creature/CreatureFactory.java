package com.zombiecastlerush.gui.creature;

import asciiPanel.AsciiPanel;
import com.zombiecastlerush.gui.layout.World;

public class CreatureFactory {
    private final World world;

    public CreatureFactory(World world){
        this.world = world;
    }

    public Creature newPlayer(){
        Creature player = new Creature(world, '@', AsciiPanel.brightMagenta,100,20,5);
        //world.addAtEmptyLocation(player);
        player.x = 2;
        player.y = 2;
        new PlayerAi(player);
        return player;
    }

    public Creature newZombies(){
        Creature zombie = new Creature(world, 'Z', AsciiPanel.brightGreen,50,10,5);
        world.addAtEmptyLocation(zombie);
        new ZombieAi(zombie, this);
        return zombie;
    }
}