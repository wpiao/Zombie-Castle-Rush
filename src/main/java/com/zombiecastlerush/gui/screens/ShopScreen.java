package com.zombiecastlerush.gui.screens;

import asciiPanel.AsciiPanel;
import com.zombiecastlerush.gui.Command;
import com.zombiecastlerush.gui.entity.Creature;
import com.zombiecastlerush.gui.layout.World;
import com.zombiecastlerush.gui.layout.WorldBuilder;
import com.zombiecastlerush.util.Game;

import java.awt.*;
import java.awt.event.KeyEvent;

public class ShopScreen implements Screen{
    private World world;
    private final Creature player;
    private final int screenWidth;
    private final int screenHeight;
    private KeyEvent key;

    public ShopScreen(Creature player) {
        //add previous world to world list.
        player.worldList().put(player.world().name(),player.world());

        this.player = player;
        screenWidth = 90;
        screenHeight = 51;
        //if player hasn't explored this world yet..
        if (!player.worldList().containsKey(this.getClass().getSimpleName())){
            //create world of tiles from external file
            createWorld();
        }else{
            this.world = player.worldList().get(this.getClass().getSimpleName());
        }

        //set player current world
        player.setWorld(world);
        if (player.x <= 45 && player.x >= 40 && player.y == 0){
            player.y = 49;
        }
    }

    private void createWorld() {
        String path = "Resources/Castle/Shop.txt";
        world = new WorldBuilder(90, 51)
                .design(path)
                .build(this.getClass().getSimpleName());
    }

    public void displayOutput(AsciiPanel terminal) {

        //playground
        displayTiles(terminal, player, world,screenWidth,screenHeight);
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
        if (player.x <= 45 && player.x >= 40 && player.y == 50) {
            return new CastleHallScreen(player);
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

            world.update();
            if(player.hp() < 1){return new LoseScreen();}

            return this;
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
        for (int i = 0; i < player.inventory().getGuiItems().size(); i++) {
            terminal.write(player.inventory().get(i).name(), right, middle + 3 + i, Color.magenta);
        }
    }

    private void displayHint(AsciiPanel terminal, int right, int bottom) {
        int length = terminal.getWidthInCharacters() - screenWidth - 2;
        terminal.write(drawLine(length), right, bottom, Color.orange);
        int height = terminal.getHeightInCharacters();

        for (int i = 0; i < height; i++) {
            terminal.write("|", right - 1, i, Color.orange);
        }
        terminal.write("Hint", right, bottom + 1, Color.green);
        terminal.write("Buy and Sell", right, bottom + 2, Color.magenta);
    }

    private void displayUserInput(AsciiPanel terminal, int left, int i) {
        terminal.write(drawLine(screenWidth), left, i, Color.orange);
        terminal.write("Enter command -> ", left, i + 1, Color.red);
        Command.type(key, terminal, 18, i + 1);
    }

    private void displayDescription(AsciiPanel terminal, int left, int bottom) {
        terminal.write("Shop", left, bottom + 1, Color.RED);
        String description = Game.castle.getCastleRooms().get("Shop").getDescription();
        String msg1 = description.substring(0,75);
        String msg2 = description.substring(75);

        terminal.write(msg1, left, bottom + 2, Color.white);
        terminal.write(msg2, left, bottom + 3, Color.white);
        terminal.write(" ", left, bottom + 3, Color.red);
    }
}