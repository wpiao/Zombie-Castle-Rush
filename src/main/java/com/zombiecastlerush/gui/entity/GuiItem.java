package com.zombiecastlerush.gui.entity;

import java.awt.Color;
import java.util.Objects;

public class GuiItem {

    private char glyph;
    public char glyph() { return glyph; }

    private Color color;
    public Color color() { return color; }

    private String name;
    public String name() { return name; }

    private double resellValue;
    public double value(){return resellValue;}

    private double retailValue;
    public double price(){return retailValue;}

    private int attackValue;
    public int attackValue() { return attackValue; }
    public void modifyAttackValue(int amount) { attackValue += amount; }

    private int defenseValue;
    public int defenseValue() { return defenseValue; }
    public void modifyDefenseValue(int amount) { defenseValue += amount; }

    private int visionRadius;
    public int visionRadius() { return visionRadius; }
    public void modifyVisionRadius(int amount) { visionRadius += amount; }


    public GuiItem(char glyph, Color color, String name){
        super();
        this.glyph = glyph;
        this.color = color;
        this.name = name;
    }

    public GuiItem (char glyph, Color color, String name, double resellValue, double retailValue){
        this(glyph, color, name);
        this.resellValue = resellValue;
        this.retailValue = retailValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GuiItem guiItem = (GuiItem) o;
        return name.equals(guiItem.name());
    }

    @Override
    public int hashCode() {
        return Objects.hash(glyph, color, name, attackValue, defenseValue, visionRadius);
    }
}