package com.zombiecastlerush.gui.screens;

import asciiPanel.AsciiPanel;
import com.zombiecastlerush.gui.Command;
import com.zombiecastlerush.gui.Creature;
import com.zombiecastlerush.gui.World;
import com.zombiecastlerush.gui.WorldBuilder;
import com.zombiecastlerush.util.Game;

import java.awt.*;
import java.awt.event.KeyEvent;

public class DrawBridgeScreen implements Screen {
    private World world;
    private final Creature player;
    private final int screenWidth;
    private final int screenHeight;
    private KeyEvent key;

    public DrawBridgeScreen(Creature player) {
        this.player = player;
        screenWidth = 90;
        screenHeight = 51;
        createWorld();
        player.setWorld(world);
        if (player.x <= 76 && player.x >= 71 && player.y == 50) {
            player.y = 1;
        } else if (player.x <= 19 && player.x >= 14 && player.y == 50) {
            player.y = 1;
        }

    }

    private void createWorld() {
        String path = "Resources/Castle/DrawBridge.txt";
        world = new WorldBuilder(90, 51)
                .design(path)
                .build();
    }


    public void displayOutput(AsciiPanel terminal) {
        int left = 0;
        int top = 0;

        //playground
        displayTiles(terminal, left, top);
        //status
        displayStatus(terminal, screenWidth + 1, 0);
        //inventory
        displayInventory(terminal, screenWidth + 1, (screenHeight - screenHeight % 3) / 3);
        //display map
        displayMap(terminal, screenWidth + 1, (screenHeight - screenHeight % 3) * 2 / 3);
        //prompt
        displayDescription(terminal, 0, screenHeight);
        //user input
        displayUserInput(terminal, 0, terminal.getHeightInCharacters() - 3);

        terminal.write(player.glyph(), player.x - left, player.y - top, player.color());


    }


    public Screen respondToUserInput(KeyEvent key) {
        this.key = key;
        if (player.x <= 76 && player.x >= 71 && player.y == 0) {
            return new CastleHallScreen(player);
        } else if (player.x <= 19 && player.x >= 14 && player.y == 0) {
            return new WestWingScreen(player);
        } else {
            switch (key.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    player.moveBy(-1, 0);
                    break;
                case KeyEvent.VK_RIGHT:
                    player.moveBy(1, 0);
                    break;
                case KeyEvent.VK_UP:
                    player.moveBy(0, -1);
                    break;
                case KeyEvent.VK_DOWN:
                    player.moveBy(0, 1);
                    break;

            }
                return this;

        }
    }


    private void displayTiles(AsciiPanel terminal, int left, int top) {
        for (int x = 0; x < screenWidth; x++) {
            for (int y = 0; y < screenHeight; y++) {
                int wx = x + left;
                int wy = y + top;

                if (player.canSee(wx, wy)){
                    Creature creature = world.creature(wx, wy);
                    if (creature != null)
                        terminal.write(creature.glyph(), creature.x - left, creature.y - top, creature.color());
                    else
                        terminal.write(world.glyph(wx, wy), x, y, world.color(wx, wy));
                } else {
                    terminal.write(world.glyph(wx, wy), x, y, Color.darkGray);
                }
            }
        }
    }

    private void displayStatus(AsciiPanel terminal, int right, int top) {
        int length = terminal.getWidthInCharacters() - screenWidth - 2;
        terminal.write(drawLine(length), right, top, Color.ORANGE);
        terminal.write("Status", right, top + 1, Color.green);
        terminal.write("placeholder", right, top + 2, Color.magenta);

    }


    private void displayInventory(AsciiPanel terminal, int right, int middle) {
        int length = terminal.getWidthInCharacters() - screenWidth - 2;
        terminal.write(drawLine(length), right, middle, Color.ORANGE);
        terminal.write("Inventory", right, middle + 1, Color.green);
        terminal.write("placeholder", right, middle + 2, Color.magenta);
    }

    private void displayMap(AsciiPanel terminal, int right, int bottom) {
        int length = terminal.getWidthInCharacters() - screenWidth - 2;
        terminal.write(drawLine(length), right, bottom, Color.orange);
        int height = terminal.getHeightInCharacters();

        for (int i = 0; i < height; i++) {
            terminal.write("|", right - 1, i, Color.orange);
        }
        terminal.write("Map", right, bottom + 1, Color.green);
        terminal.write("placeholder", right, bottom + 2, Color.magenta);
    }

    private void displayUserInput(AsciiPanel terminal, int left, int i) {
        terminal.write(drawLine(screenWidth), left, i, Color.orange);
        terminal.write("Enter command -> ", left, i + 1, Color.red);
        Command.type(key, terminal, 18, i + 1);

    }

    private void displayDescription(AsciiPanel terminal, int left, int bottom) {
        terminal.write("Draw Bridge", left, bottom + 1, Color.RED);
        //String description = Game.castle.getCastleRooms().get("Draw-Bridge").getDescription();
        //terminal.write(description, left, bottom + 2, Color.magenta);
        terminal.write(" ", left, bottom + 3, Color.red);
    }

    private String drawLine(int length) {

        String line = "";
        for (int i = 0; i < length; i++) {
            line += "-";
        }
        return line;
    }
}