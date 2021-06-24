package com.zombiecastlerush.gui.layout;

import java.awt.Color;
import asciiPanel.AsciiPanel;

public enum Tile {
    FLOOR((char)250, AsciiPanel.white),
    LIGHT_WALL((char)176, AsciiPanel.green),
    MID_WALL((char)177,AsciiPanel.green),
    HEAVY_WALL((char)178,AsciiPanel.green),

    BOT_SOLID_BLOCK((char)220,AsciiPanel.green),
    TOP_SOLID_BLOCK((char)223, AsciiPanel.green),
    FULL_SOLID_BLOCK((char)219,AsciiPanel.green),

    CASTLE_VER_DOOR((char)124,AsciiPanel.red),
    CASTLE_HOR_DOOR((char)95, AsciiPanel.red),
    ROOM_HOR_DOOR((char)45,AsciiPanel.red),
    ROOM_VER_DOOR((char)93,AsciiPanel.red),

    BOX((char)240,AsciiPanel.brightWhite),

    BOUNDS('x', AsciiPanel.brightBlack);

    private char glyph;
    public char glyph() { return glyph; }

    private Color color;
    public Color color() { return color; }

    Tile(char glyph, Color color){
        this.glyph = glyph;
        this.color = color;
    }

    public boolean isGround() {
        return this.glyph() == 250;
    }

    public boolean isDoor() {
        return this.glyph() < 128;
    }

    public boolean isBox(){
        return this.glyph() == 240;
    }
}

