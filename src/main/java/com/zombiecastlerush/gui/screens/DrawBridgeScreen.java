package com.zombiecastlerush.gui.screens;

import asciiPanel.AsciiPanel;
import com.zombiecastlerush.gui.Command;
import com.zombiecastlerush.gui.entity.Creature;
import com.zombiecastlerush.gui.entity.EntityFactory;
import com.zombiecastlerush.gui.layout.World;
import com.zombiecastlerush.gui.layout.WorldBuilder;
import com.zombiecastlerush.util.Game;

import java.awt.*;
import java.awt.event.KeyEvent;

public class DrawBridgeScreen implements Screen {
    private World world;
    private final Creature player;
    private final int screenWidth;
    private final int screenHeight;
    private KeyEvent key;
    private Screen subscreen;

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

        EntityFactory entityFactory = new EntityFactory(world);
        for (int i = 0; i < 16; i++) {
            entityFactory.newZombies();
        }

    }

    private void createWorld() {
        String path = "Resources/Castle/DrawBridge.txt";
        world = new WorldBuilder(90, 51)
                .design(path)
                .build(this.getClass().getSimpleName());
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

        if (subscreen != null)
            subscreen.displayOutput(terminal);


    }


    public Screen respondToUserInput(KeyEvent key) {
        if (subscreen != null) {
            subscreen = subscreen.respondToUserInput(key);
        } else {
            this.key = key;


            int choice = Command.choice(Command.command);
            if (key.getKeyCode() == KeyEvent.VK_ENTER)
                Command.command = "";
            switch (choice) {
                case 1:
                    subscreen = new RiddleScreen(player, this.getClass().getSimpleName());
            }

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
            }
        }

        //if there is no riddle screen, then update creature's movement.
        if (subscreen == null) {
            world.update();
        }

        if (player.hp() < 1) {
            return new LoseScreen();
        }

        return this;
    }


    private void displayTiles(AsciiPanel terminal) {
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

    private void displayStatus(AsciiPanel terminal, int right, int top) {
        //draw yellow boundary lines
        int length = terminal.getWidthInCharacters() - screenWidth - 2;
        terminal.write(drawLine(length), right, top, Color.ORANGE);
        terminal.write("Status", right, top + 1, Color.green);

        // display player hp
        String stats = player.hp() < 1 ? "" : String.format("You: %6d/%3d hp", player.hp(), player.maxHp());
        terminal.write(stats, right, top + 3, Color.magenta);

        //if player has an opponent, aka in fight, then display its hp.
        String enemyStats = player.opponent() == null || player.opponent().hp() < 1 ? "" :
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
        terminal.write("Draw Bridge", left, bottom + 1, Color.RED);

        world.getBoxTile().forEach((point, tile) -> {
            if (player.x == point.x && player.y == point.y) {
                String msg1 = "The box pulses with power. You know not how, but it has a riddle for you,";

                String msg2 = "and it will not let you leave until you have solved it.Perhaps you should attempt puzzle.";
                terminal.write(msg1, left, bottom + 3, Color.magenta);
                terminal.write(msg2, left, bottom + 4, Color.magenta);
            } else {
                String description = Game.castle.getCastleRooms().get("Draw-Bridge").getDescription();
                String msg1 = description.substring(0, description.length() / 3 + 3);
                String msg2 = description.substring(description.length() / 3 + 4, description.length() / 3 * 2 + 6);
                String msg3 = description.substring(description.length() / 3 * 2 + 7);

                terminal.write(msg1, left, bottom + 2, Color.white);
                terminal.write(msg2, left, bottom + 3, Color.white);
                terminal.write(msg3, left, bottom + 4, Color.white);
                terminal.write(" ", left, bottom + 3, Color.red);
            }
        });
    }

    private String drawLine(int length) {

        String line = "";
        for (int i = 0; i < length; i++) {
            line += "-";
        }
        return line;
    }
}