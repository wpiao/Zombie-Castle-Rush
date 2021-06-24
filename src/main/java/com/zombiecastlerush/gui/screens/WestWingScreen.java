package com.zombiecastlerush.gui.screens;

import asciiPanel.AsciiPanel;
import com.zombiecastlerush.gui.*;
import com.zombiecastlerush.gui.creature.Creature;
import com.zombiecastlerush.gui.creature.CreatureFactory;
import com.zombiecastlerush.gui.layout.World;
import com.zombiecastlerush.gui.layout.WorldBuilder;
import com.zombiecastlerush.util.Game;

import java.awt.*;
import java.awt.event.KeyEvent;

public class WestWingScreen implements Screen {
    private World world;
    private final Creature player;
    private final int screenWidth;
    private final int screenHeight;
    private KeyEvent key;

    public WestWingScreen(Creature player) {
        this.player = player;
        screenWidth = 90;
        screenHeight = 51;
        createWorld();
        player.setWorld(world);
        if (player.x >=14 && player.x <= 19 && player.y == 0){
            player.y = 49;
        } else if (player.x == 0) {
            player.x = 88;
        }

        CreatureFactory creatureFactory = new CreatureFactory(world);
        for (int i = 0; i < 20; i++){
            creatureFactory.newZombies();
        }
    }

    private void createWorld() {
        String path = "Resources/Castle/WestWing.txt";
        world = new WorldBuilder(90, 51)
                .design(path)
                .build();
    }


    public void displayOutput(AsciiPanel terminal) {

        //playground
        displayTiles(terminal);
        //status
        displayStatus(terminal, screenWidth + 1, 0);
        //inventory
        displayInventory(terminal, screenWidth + 1, (screenHeight - screenHeight % 3) / 3);
        //display map
        displayHint(terminal, screenWidth + 1, (screenHeight - screenHeight % 3) * 2 / 3);
        //prompt
        displayDescription(terminal, 0, screenHeight);
        //user input
        displayUserInput(terminal, 0, terminal.getHeightInCharacters() - 3);

        terminal.write(player.glyph(), player.x, player.y, player.color());
    }


    public Screen respondToUserInput(KeyEvent key) {
        this.key = key;
        if (player.x == 89 && (player.y == 17 || player.y == 18 || player.y == 19)) {
            return new CastleHallScreen(player);
        } else if (player.x <= 19 && player.x >= 14 && player.y == 50) {
            return new DrawBridgeScreen(player);
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
        }

        world.update();
        if(player.hp() < 1){return new LoseScreen();}

        return this;
    }

    private void displayTiles(AsciiPanel terminal) {
        for (int x = 0; x < screenWidth; x++) {
            for (int y = 0; y < screenHeight; y++) {

                if (player.canSee(x, y)){
                    Creature creature = world.creature(x, y);
                    if (creature != null)
                        terminal.write(creature.glyph(), creature.x, creature.y, creature.color());
                    else
                        terminal.write(world.glyph(x, y), x, y, world.color(x, y));
                } else {
                    terminal.write(world.glyph(x, y), x, y, Color.darkGray);
                }
            }
        }
    }

    private void displayStatus(AsciiPanel terminal, int right, int top) {
        //draw yellow boundary lines
        int length = terminal.getWidthInCharacters() - screenWidth - 2;
        terminal.write(drawLine(length), right, top, Color.ORANGE);
        terminal.write("Status", right, top + 1, Color.green);

        // display player hp
        String stats = player.hp() < 1 ? "":String.format("You: %6d/%3d hp", player.hp(), player.maxHp());
        terminal.write(stats, right, top + 3, Color.magenta);

        //if player has an opponent, aka in fight, then display its hp.
        String enemyStats = player.opponent() == null || player.opponent().hp() < 1 ? "":
                String.format("Zombie: %3d/%3d hp", player.opponent().hp(), player.opponent().maxHp());
        terminal.write(enemyStats, right, top + 4, Color.green);
    }


    private void displayInventory(AsciiPanel terminal, int right, int middle) {
        int length = terminal.getWidthInCharacters() - screenWidth - 2;
        terminal.write(drawLine(length), right, middle, Color.ORANGE);
        terminal.write("Inventory", right, middle + 1, Color.green);
        terminal.write("placeholder", right, middle + 2, Color.magenta);
    }

    private void displayHint(AsciiPanel terminal, int right, int bottom) {
        int length = terminal.getWidthInCharacters() - screenWidth - 2;
        terminal.write(drawLine(length), right, bottom, Color.orange);
        int height = terminal.getHeightInCharacters();

        for (int i = 0; i < height; i++) {
            terminal.write("|", right - 1, i, Color.orange);
        }
        terminal.write("Hint", right, bottom + 1, Color.green);
        terminal.write("placeholder", right, bottom + 2, Color.magenta);
    }

    private void displayUserInput(AsciiPanel terminal, int left, int i) {
        terminal.write(drawLine(screenWidth), left, i, Color.orange);
        terminal.write("Enter command -> ", left, i + 1, Color.red);
        Command.type(key, terminal, 18, i + 1);
    }

    private void displayDescription(AsciiPanel terminal, int left, int bottom) {
        terminal.write("West Wing", left, bottom + 1, Color.RED);
        String description = Game.castle.getCastleRooms().get("West-Wing").getDescription();
        terminal.write(description, left, bottom + 2, Color.white);
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