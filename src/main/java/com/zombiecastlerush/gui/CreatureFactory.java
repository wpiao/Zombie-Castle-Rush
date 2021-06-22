package com.zombiecastlerush.gui;

import asciiPanel.AsciiPanel;

public class CreatureFactory {
    private final World world;

    public CreatureFactory(World world){
        this.world = world;
    }

    public Creature newPlayer(){
        Creature player = new Creature(world, '@', AsciiPanel.brightMagenta);
        //world.addAtEmptyLocation(player);
        player.x = 2;
        player.y = 2;
        new PlayerAi(player);
        return player;
    }
}