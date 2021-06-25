package com.zombiecastlerush.gui.screens;
import java.awt.*;
import java.awt.event.KeyEvent;
import asciiPanel.AsciiPanel;
import com.zombiecastlerush.gui.entity.Creature;
import com.zombiecastlerush.gui.layout.World;

public interface Screen {
    void displayOutput(AsciiPanel terminal);

    Screen respondToUserInput(KeyEvent key);

    default void displayHint(AsciiPanel terminal,int x, int y,int screenWidth){
        int length = terminal.getWidthInCharacters() - screenWidth - 2;
        terminal.write(drawLine(length), x, y, Color.orange);
        int height = terminal.getHeightInCharacters();

        for (int i = 0; i < height; i++) {
            terminal.write("|", x - 1, i, Color.orange);
        }
        terminal.write("Hint", x, y + 1, Color.green);
        terminal.write("COMMANDS AVAILABLE   ", x, y+3, Color.magenta);
        terminal.write("[PICK-UP]   ", x, y+5, Color.magenta);
        terminal.write("[DROP][NAME]   ", x, y+7, Color.magenta);
        terminal.write("[USE][NAME]   ", x, y+9, Color.magenta);
        terminal.write("[ATTEMPT][PUZZLE]   ", x, y+11, Color.magenta);
    }

    default String drawLine(int length) {

        String line = "";
        for (int i = 0; i < length; i++) {
            line += "-";
        }
        return line;
    }

    default void displayTiles(AsciiPanel terminal, Creature player, World world,int screenWidth, int screenHeight){
        for (int x = 0; x < screenWidth; x++) {
            for (int y = 0; y < screenHeight; y++) {

                if (player.canSee(x, y)) {
                    terminal.write(world.glyph(x, y), x, y, world.color(x, y));
                } else {
                    terminal.write(world.glyph(x, y), x, y, Color.black);
                }
            }
        }
    }
}
