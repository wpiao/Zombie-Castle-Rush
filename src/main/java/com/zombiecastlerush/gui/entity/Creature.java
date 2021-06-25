package com.zombiecastlerush.gui.entity;

import com.zombiecastlerush.gui.layout.Tile;
import com.zombiecastlerush.gui.layout.World;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Creature implements Location {
    private int initialAttackValue;
    private int initialDefenseValue;
    private int initialVisionRadius;
    private World world;

    public World world() {
        return world;
    }

    private Map<String, World> exploredWorldList;

    public Map<String, World> worldList() {
        return exploredWorldList;
    }

    private Creature opponent;

    public Creature opponent() {
        return opponent;
    }

    public int killedNumber;
    public int experience;


    public int x;
    public int y;

    private char glyph;

    public char glyph() {
        return glyph;
    }

    private Color color;

    public Color color() {
        return color;
    }

    private CreatureAi ai;

    public void setCreatureAi(CreatureAi ai) {
        this.ai = ai;
    }

    private int maxHp;

    public int maxHp() {
        return maxHp;
    }

    private int hp;

    public int hp() {
        return hp;
    }

    public int attackValue;

    public int attackValue() {
        return attackValue;
    }

    public void setAttackValue(int attackValue) {
        this.attackValue = attackValue;
    }

    public int defenseValue;

    public int defenseValue() {
        return defenseValue;
    }

    public void setDefenseValue(int defenseValue) {
        this.defenseValue = defenseValue;
    }

    private int visionRadius;

    public int visionRadius() {
        return visionRadius;
    }

    public void setVisionRadius(int visionRadius) {
        this.visionRadius = visionRadius;
    }

    public int getInitialAttackValue() {
        return initialAttackValue;
    }

    public int getInitialDefenseValue() {
        return initialDefenseValue;
    }

    public int getInitialVisionRadius() {
        return initialVisionRadius;
    }

    public void setInitialAttackValue(int initialAttackValue) {
        this.initialAttackValue = initialAttackValue;
    }

    public void setInitialDefenseValue(int initialDefenseValue) {
        this.initialDefenseValue = initialDefenseValue;
    }

    public void setInitialVisionRadius(int initialVisionRadius) {
        this.initialVisionRadius = initialVisionRadius;
    }

    private Inventory inventory;

    public Inventory inventory() {
        return inventory;
    }

    public GuiItem weapon;

    public GuiItem accs;

    public GuiItem tool;


    public Creature(World world, char glyph, Color color, int maxHp, int attack, int defense, int killedNumber) {
        this.world = world;
        this.glyph = glyph;
        this.color = color;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.attackValue = attack;
        this.defenseValue = defense;
        this.visionRadius = 9;
        this.inventory = new Inventory();
        exploredWorldList = new HashMap<>();
        this.killedNumber = killedNumber;
        this.experience = 0;
        this.weapon = null;
        this.accs = null;
        this.tool = null;
        this.initialAttackValue = attack;
        this.initialDefenseValue = defense;
        this.initialVisionRadius = 9;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public void moveBy(int dx, int dy) {
        if (dx == 0 && dy == 0)
            return;
        Creature other = world.creature(x + dx, y + dy);

        if (other == null)
            ai.onEnter(x + dx, y + dy, world.tile(x + dx, y + dy));
        else
            attack(other);
    }

    public boolean canSee(int x, int y) {
        return ai.canSee(x, y);
    }

    public Tile tile(int x, int y) {
        return world.tile(x, y);
    }

    public void attack(Creature other) {
        this.opponent = other;
        int damageToOther = Math.max(0, attackValue() - other.defenseValue());
        damageToOther = (int) (Math.random() * damageToOther) + 1;

        int damageToSelf = Math.max(0, other.attackValue() - defenseValue());
        damageToSelf = (int) (Math.random() * damageToSelf) + 1;
        this.modifyHp(-damageToSelf);
        other.modifyHp(-damageToOther);
        if (other.hp() < 1) {
            this.killedNumber++;
        }
    }

    public void pickup() {
        GuiItem item = world.item(x, y);

        if (!inventory.isFull() && item != null) {
            world.remove(x, y);
            inventory.add(item);
        }
    }

    public void drop(GuiItem item) {
        if (item != null) {
            inventory.remove(item);
            world.addAtPlayer(item, x, y);
            if (item.attackValue() > 1) {
                this.weapon = null;
                this.attackValue = initialAttackValue;
            }
            if (item.defenseValue() > 1) {
                this.accs = null;
                this.defenseValue = initialDefenseValue;
            }

            if (item.name().equalsIgnoreCase("torch") || item.name().equalsIgnoreCase("lighter")) {
                if (inventory.inventoryStats().get(item) == null) {
                    this.tool = null;
                    this.visionRadius = initialVisionRadius;
                }

            }

        }
    }

    public void use(GuiItem item) {
        if (this.inventory().getGuiItems().contains(item)) {
            if (item.name().equalsIgnoreCase("knife") || item.name().equalsIgnoreCase("sword")) {
                this.weapon = item;
                this.attackValue = initialAttackValue + item.attackValue();
            }

            if (item.name().equalsIgnoreCase("helmet")) {
                this.accs = item;
                this.defenseValue = initialDefenseValue + item.defenseValue();
            }

            if (item.name().equalsIgnoreCase("lighter") || item.name().equalsIgnoreCase("torch")) {
                this.tool = item;
                this.visionRadius = initialVisionRadius + item.visionRadius();
            }

            if (item.name().equalsIgnoreCase("potion")) {
                this.maxHp = (experience / 10 + 1) * 10 + 100;
                this.hp = maxHp();
            }
        }
    }

    public void modifyHp(int amount) {
        hp += amount;

        if (hp < 1) {
            world.remove(this);

        }
        experience++;
    }

    public Creature creature(int x, int y) {
        return world.creature(x, y);
    }

    public void update() {

        ai.onUpdate();
    }
}