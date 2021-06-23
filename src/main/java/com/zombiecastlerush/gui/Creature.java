package com.zombiecastlerush.gui;

import java.awt.Color;

public class Creature {
    private World world;

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

    public Creature(World world, char glyph, Color color, int maxHp, int attack, int defense){
        this.world = world;
        this.glyph = glyph;
        this.color = color;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.attackValue = attack;
        this.defenseValue = defense;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public void moveBy(int mx, int my){
        Creature other = world.creature(x+mx, y+my);

        if (other == null)
            ai.onEnter(x+mx, y+my, world.tile(x+mx, y+my));
        else
            attack(other);
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

    public void modifyHp(int amount) {
        hp += amount;

        if (hp < 1)
            world.remove(this);
    }


}