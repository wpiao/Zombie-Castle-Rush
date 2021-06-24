package com.zombiecastlerush.gui.entity;

import java.awt.Color;

public class GuiItem extends com.zombiecastlerush.building.Item implements Location {

    private char glyph;
    public char glyph() { return glyph; }

    private Color color;
    public Color color() { return color; }

    private String name;
    public String name() { return name; }

    public GuiItem(char glyph, Color color, String name){
        super();
        this.glyph = glyph;
        this.color = color;
        this.name = name;
    }
}