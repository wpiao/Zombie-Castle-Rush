package com.zombiecastlerush.gui;

import java.awt.Color;
import asciiPanel.AsciiPanel;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

public enum Tile {
    FLOOR((char)250, AsciiPanel.white),
    LIGHT_WALL((char)176, AsciiPanel.green),
    MID_WALL((char)177,AsciiPanel.green),
    HEAVY_WALL((char)178,AsciiPanel.green),

    BOT_SOLID_BLOCK((char)220,AsciiPanel.green),
    TOP_SOLID_BLOCK((char)223, AsciiPanel.green),
    FULL_SOLID_BLOCK((char)219,AsciiPanel.green),

    BOUNDS('@', AsciiPanel.brightBlack);

    private char glyph;
    public char glyph() { return glyph; }

    private Color color;
    public Color color() { return color; }

    Tile(char glyph, Color color){
        this.glyph = glyph;
        this.color = color;
    }
}

