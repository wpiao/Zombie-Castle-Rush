package com.zombiecastlerush.gui.entity;

import com.zombiecastlerush.gui.layout.Tile;
import com.zombiecastlerush.gui.layout.World;

import java.awt.Color;
import java.util.ArrayList;

public class Creature implements Location {
    private World world;
    public World world(){return  world;}

    private Creature opponent;
    public Creature opponent() { return opponent;}


    public int x;
    public int y;

    private char glyph;
    public char glyph() { return glyph; }

    private Color color;
    public Color color() { return color; }

    private CreatureAi ai;
    public void setCreatureAi(CreatureAi ai) { this.ai = ai; }

    private int maxHp;
    public int maxHp() { return maxHp; }

    private int hp;
    public int hp() { return hp; }

    private int attackValue;
    public int attackValue() { return attackValue; }

    private int defenseValue;
    public int defenseValue() { return defenseValue; }

    private int visionRadius;
    public int visionRadius() { return visionRadius; }

    private Inventory inventory;
    public Inventory inventory() { return inventory; }

    public Creature(World world, char glyph, Color color, int maxHp, int attack, int defense){
        this.world = world;
        this.glyph = glyph;
        this.color = color;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.attackValue = attack;
        this.defenseValue = defense;
        this.visionRadius = 9;
        this.inventory = new Inventory();
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public void moveBy(int dx, int dy){
        if (dx==0 && dy==0)
            return;
        Creature other = world.creature(x+dx, y+dy);

        if (other == null)
            ai.onEnter(x+dx, y+dy, world.tile(x+dx, y+dy));
        else
            attack(other);
    }

    public boolean canSee(int x, int y){
        return ai.canSee(x, y);
    }

    public Tile tile(int x, int y) {
        return world.tile(x, y);
    }

    public void attack(Creature other){
        this.opponent=other;
        int damageToOther = Math.max(0, attackValue() - other.defenseValue());
        damageToOther = (int)(Math.random() * damageToOther) + 1;

        int damageToSelf = Math.max(0, other.attackValue() - defenseValue());
        damageToSelf =  (int)(Math.random() * damageToSelf) + 1;
        this.modifyHp(-damageToSelf);
        other.modifyHp(-damageToOther);
    }

    public void pickup(){
        GuiItem item = world.item(x, y);

        if (!inventory.isFull() && item != null){
            world.remove(x, y);
            inventory.add(item);
        }
    }

    public void drop(GuiItem item){
        inventory.remove(item);
        world.addAtEmptySpace(item, x, y);
    }

    public void modifyHp(int amount) {
        hp += amount;

        if (hp < 1)
            world.remove(this);
    }

    public Creature creature(int x, int y) {
        return world.creature(x, y);
    }

    public void update(){
        ai.onUpdate();
    }
}