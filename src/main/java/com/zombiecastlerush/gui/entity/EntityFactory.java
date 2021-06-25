package com.zombiecastlerush.gui.entity;

import asciiPanel.AsciiPanel;
import com.zombiecastlerush.gui.layout.World;

public class EntityFactory {
    private final World world;

    public EntityFactory(World world){
        this.world = world;
    }

    public Creature newPlayer(){
        Creature player = new Creature(world, '@', AsciiPanel.brightMagenta,100,20,5,0);
        //world.addAtEmptyLocation(player);
        player.x = 2;
        player.y = 2;
        new PlayerAi(player);
        return player;
    }

    public Creature newZombies(){
        Creature zombie = new Creature(world, 'Z', AsciiPanel.brightGreen,30,10,5,0);
        world.addAtEmptyLocation(zombie);
        new ZombieAi(zombie, this);
        return zombie;
    }

    public Creature newAggZombies(Creature player){
        Creature aggZombie = new Creature(world, 'Z', AsciiPanel.brightCyan, 10, 20, 10, 0);
        world.addNextBox(aggZombie);
        new AggZombieAi(aggZombie, player);
        return aggZombie;
    }

    public Creature newSeller(Creature player){
        Creature seller = new Creature(world, '$', AsciiPanel.brightYellow, 999, 0, 999,0, 9999.0);
        world.addNextBox(seller);
        seller.inventory().add(new GuiItem('P', AsciiPanel.brightRed, "Potion"));
        seller.inventory().add(new GuiItem('1', AsciiPanel.brightRed, "Sword"));
        return seller;
    }

    public GuiItem newSword(){
        GuiItem sword = new GuiItem('1', AsciiPanel.brightRed, "Sword");
        sword.modifyAttackValue(10);
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
        knife.modifyAttackValue(5);
        world.addAtEmptyLocation(knife);
        return knife;
    }

    public GuiItem newVase(){
        GuiItem vase = new GuiItem('V', AsciiPanel.brightRed, "Vase");
        world.addAtEmptyLocation(vase);
        return vase;
    }

    public GuiItem newPotion(){
        GuiItem potion = new GuiItem('P', AsciiPanel.brightRed, "Potion");
        world.addAtEmptyLocation(potion);
        return potion;
    }

    public GuiItem newLighter(){
        GuiItem lighter = new GuiItem('L', AsciiPanel.brightRed, "Lighter");
        lighter.modifyVisionRadius(3);
        world.addAtEmptyLocation(lighter);
        return lighter;
    }

    public GuiItem newHelmet(){
        GuiItem helmet = new GuiItem('H', AsciiPanel.brightRed, "Helmet");
        helmet.modifyDefenseValue(20);
        world.addAtEmptyLocation(helmet);
        return helmet;
    }

    public GuiItem newTorch(){
        GuiItem torch = new GuiItem('T', AsciiPanel.brightRed, "Torch");
        torch.modifyVisionRadius(6);
        world.addAtEmptyLocation(torch);
        return torch;
    }

    public GuiItem newMap(){
        GuiItem map = new GuiItem('M', AsciiPanel.brightRed, "Map");
        world.addAtEmptyLocation(map);
        return map;
    }
}