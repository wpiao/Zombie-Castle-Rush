package com.zombiecastlerush.gui.entity;

import asciiPanel.AsciiPanel;
import com.zombiecastlerush.gui.layout.World;

public class EntityFactory {
    private  World world;

    public EntityFactory(World world){
        this.world = world;
    }

    public Creature newPlayer(){
        Creature player = new Creature(world, '@', AsciiPanel.brightMagenta,100,20,5,0,50.0);
        player.x = 5;
        player.y = 5;
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

    public Creature newSeller(){
        Creature seller = new Creature(world, '$', AsciiPanel.brightYellow, 999, 0, 999,0, 9999.0);
        world.addNextBox(seller);
        new SellerAi(seller);
        seller.inventory().add(newSellorPotion());
        seller.inventory().add(newSellerSword());
        seller.inventory().add(newSellerHelmet());
        return seller;
    }

    public GuiItem newSword(){
        GuiItem sword = new GuiItem('1', AsciiPanel.brightRed, "Sword",75,100);
        sword.modifyAttackValue(10);
        world.addAtBox(sword);
        return sword;
    }

    public GuiItem newSellerSword(){
        GuiItem sword = new GuiItem('1', AsciiPanel.brightRed, "Sword",75,100);
        sword.modifyAttackValue(10);
        return sword;
    }

    public GuiItem newFork(){
        GuiItem fork = new GuiItem('F', AsciiPanel.brightRed, "Fork",10,15);
        world.addAtEmptyLocation(fork);
        return fork;
    }

    public GuiItem newSpoon(){
        GuiItem spoon = new GuiItem('S', AsciiPanel.brightRed, "Spoon",10,15);
        world.addAtEmptyLocation(spoon);
        return spoon;
    }

    public GuiItem newKnife(){
        GuiItem knife = new GuiItem('K', AsciiPanel.brightRed, "Knife",10,15);
        knife.modifyAttackValue(5);
        world.addAtEmptyLocation(knife);
        return knife;
    }

    public GuiItem newVase(){
        GuiItem vase = new GuiItem('V', AsciiPanel.brightRed, "Vase",5,10);
        world.addAtEmptyLocation(vase);
        return vase;
    }

    public GuiItem newPotion(){
        GuiItem potion = new GuiItem('P', AsciiPanel.brightRed, "Potion",75,100);
        world.addAtEmptyLocation(potion);
        return potion;
    }

    public GuiItem newSellorPotion(){
        GuiItem potion = new GuiItem('P', AsciiPanel.brightRed, "Potion",75,100);
        return potion;
    }

    public GuiItem newLighter(){
        GuiItem lighter = new GuiItem('L', AsciiPanel.brightRed, "Lighter",10,15);
        lighter.modifyVisionRadius(3);
        world.addAtEmptyLocation(lighter);
        return lighter;
    }

    public GuiItem newHelmet(){
        GuiItem helmet = new GuiItem('H', AsciiPanel.brightRed, "Helmet",35,50);
        helmet.modifyDefenseValue(20);
        world.addAtEmptyLocation(helmet);
        return helmet;
    }

    public GuiItem newSellerHelmet(){
        GuiItem helmet = new GuiItem('H', AsciiPanel.brightRed, "Helmet",35,50);
        helmet.modifyDefenseValue(20);
        return helmet;
    }

    public GuiItem newTorch(){
        GuiItem torch = new GuiItem('T', AsciiPanel.brightRed, "Torch",20,25);
        torch.modifyVisionRadius(6);
        world.addAtEmptyLocation(torch);
        return torch;
    }

    public GuiItem newMap(){
        GuiItem map = new GuiItem('M', AsciiPanel.brightRed, "Map",5,10);
        world.addAtEmptyLocation(map);
        return map;
    }
}