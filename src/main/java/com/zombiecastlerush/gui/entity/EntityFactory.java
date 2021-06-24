package com.zombiecastlerush.gui.entity;

import asciiPanel.AsciiPanel;
import com.zombiecastlerush.gui.layout.World;

public class EntityFactory {
    private final World world;

    public EntityFactory(World world){
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
        Creature zombie = new Creature(world, 'Z', AsciiPanel.brightGreen,30,10,5);
        world.addAtEmptyLocation(zombie);
        new ZombieAi(zombie, this);
        return zombie;
    }

    public GuiItem newSword(){
        GuiItem sword = new GuiItem('1', AsciiPanel.brightRed, "Sword");
        world.addAtBox(sword);
        return sword;
    }

    public GuiItem newFork(){
        GuiItem fork = new GuiItem('F', AsciiPanel.brightRed, "Fork");
        world.addAtEmptyLocation(fork);
        return fork;
    }

    public GuiItem newSpoon(){
        GuiItem spoon = new GuiItem('S', AsciiPanel.brightRed, "Spoon");
        world.addAtEmptyLocation(spoon);
        return spoon;
    }

    public GuiItem newKnife(){
        GuiItem knife = new GuiItem('K', AsciiPanel.brightRed, "Knife");
        world.addAtEmptyLocation(knife);
        return knife;
    }

    public GuiItem newVase(){
        GuiItem vase = new GuiItem('V', AsciiPanel.brightRed, "Vase");
        world.addAtEmptyLocation(vase);
        return vase;
    }

    public GuiItem newPotion(){
        GuiItem potion = new GuiItem('P', AsciiPanel.brightMagenta, "Potion");
        world.addAtEmptyLocation(potion);
        return potion;
    }
}