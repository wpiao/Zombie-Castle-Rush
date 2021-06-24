package com.zombiecastlerush.gui.screens;

import asciiPanel.AsciiPanel;
import com.zombiecastlerush.gui.creature.Creature;
import com.zombiecastlerush.gui.creature.CreatureFactory;
import com.zombiecastlerush.gui.layout.World;
import com.zombiecastlerush.gui.layout.WorldBuilder;

import java.awt.*;
import java.awt.event.KeyEvent;

public class StartScreen implements Screen {

    private World world;
    public final Creature player;
    private final int screenWidth;
    private final int screenHeight;

    public StartScreen() {
        screenWidth = 90;
        screenHeight = 51;
        createWorld();
        CreatureFactory creatureFactory = new CreatureFactory(world);
        player = creatureFactory.newPlayer();
    }

    private void createWorld() {
        String path = "Resources/Castle/Castle.txt";
        world = new WorldBuilder(100, 51)
                .design(path)
                .build(this.getClass().getSimpleName());
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
        displayHint(terminal, screenWidth + 1, (screenHeight - screenHeight % 3) * 2 / 3);
        //prompt
        displayDescription(terminal, 0, screenHeight);
        //user input
        displayUserInput(terminal, 0, terminal.getHeightInCharacters() - 3);

        terminal.write(player.glyph(), player.x - left, player.y - top, player.color());
    }


    public Screen respondToUserInput(KeyEvent key) {
        if ((player.x == 52 || player.x == 53) && player.y == 47){
            return new CastleHallScreen(player);
        }else {
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

        return this;
    }


    private void displayTiles(AsciiPanel terminal, int left, int top) {
        for (int x = 0; x < screenWidth; x++) {
            for (int y = 0; y < screenHeight; y++) {
                int wx = x + left;
                int wy = y + top;

                terminal.write(world.glyph(wx, wy), x, y, world.color(wx, wy));
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
    }

    private void displayDescription(AsciiPanel terminal, int left, int bottom) {
        String msg1 = "Welcome to the Zombie Castle. There is only one way to survive.";
        String msg2 = "Find the Lord and beat him.";
        String msg3 = "Please enter the castle gate to begin your journey";
        terminal.write(msg1, left, bottom + 1, Color.white);
        terminal.write(msg2, left, bottom + 3, Color.white);
        terminal.write(msg3, left, bottom + 5, Color.white);
    }

    private String drawLine(int length) {

        String line = "";
        for (int i = 0; i < length; i++) {
            line += "-";
        }
        return line;
    }


}