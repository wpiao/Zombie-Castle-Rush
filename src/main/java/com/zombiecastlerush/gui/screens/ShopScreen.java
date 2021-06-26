package com.zombiecastlerush.gui.screens;

import asciiPanel.AsciiPanel;
import com.zombiecastlerush.gui.Command;
import com.zombiecastlerush.gui.entity.Creature;
import com.zombiecastlerush.gui.entity.EntityFactory;
import com.zombiecastlerush.gui.entity.GuiItem;
import com.zombiecastlerush.gui.layout.World;
import com.zombiecastlerush.gui.layout.WorldBuilder;
import com.zombiecastlerush.util.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;

public class ShopScreen implements Screen {
    private World world;
    private final Creature player;
    private Creature seller;
    private final int screenWidth;
    private final int screenHeight;
    private KeyEvent key;
    private Screen subscreen;

    public ShopScreen(Creature player) {
        //add previous world to world list.
        player.worldList().put(player.world().name(), player.world());
        this.player = player;
        screenWidth = 90;
        screenHeight = 51;
        //if player hasn't explored this world yet..
        if (!player.worldList().containsKey(this.getClass().getSimpleName())) {
            //create world of tiles from external file
            createWorld();
        } else {
            this.world = player.worldList().get(this.getClass().getSimpleName());
            seller = world.getCreatures().get(0);
        }

        //set player current world
        player.setWorld(world);

        if (player.x <= 45 && player.x >= 40 && player.y == 0) {
            player.y = 49;
        }
    }

    private void createWorld() {
        String path = "Resources/Castle/Shop.txt";
        world = new WorldBuilder(90, 51)
                .design(path)
                .build(this.getClass().getSimpleName());
        EntityFactory factory = new EntityFactory(world);
        this.seller = factory.newSeller();
    }

    public void displayOutput(AsciiPanel terminal) {
        Color color = Color.darkGray;
        //playground
        displayTiles(terminal, player, world, screenWidth, screenHeight, color);
        //status
        displayStatus(terminal, screenWidth + 1, 0,screenWidth,player,"Zombie");
        //inventory
        displayInventory(terminal, screenWidth + 1, (screenHeight - screenHeight % 3) / 3, screenWidth, player);
        //display map
        displayHint(terminal, screenWidth + 1, (screenHeight - screenHeight % 3) * 2 / 3);
        //prompt
        displayDescription(terminal, 0, screenHeight);
        //user input
        displayUserInput(terminal, 0, terminal.getHeightInCharacters() - 3, screenWidth, subscreen, key);

        terminal.write(player.glyph(), player.x, player.y, player.color());

        if (subscreen != null) {
            subscreen.displayOutput(terminal);
        }
    }

    public Screen respondToUserInput(KeyEvent key) {
        if (subscreen != null) {
            subscreen = subscreen.respondToUserInput(key);
        } else {
            this.key = key;

            int choice = Command.choice(Command.command);
            if (key.getKeyCode() == KeyEvent.VK_ENTER) {
                Command.command = "";
                switch (choice) {
                    case 1:
                        // player.worldList().put(player.world().name(), player.world());
                        try {
                            FileOutputStream fileOut = new FileOutputStream("Resources/savedData.txt");
                            ObjectOutputStream out = new ObjectOutputStream(fileOut);
                            out.writeObject(player);
                            out.close();
                            fileOut.close();
                            System.out.print("Serialized data is saved in resources");
                        } catch (IOException i) {
                            i.printStackTrace();
                        }
                        break;
                    case 2: //pick-up
                        player.pickup();
                        break;
                    case 3: //attempt puzzle
                        if (player.world().tile(player.x, player.y).isBox()) {
                            subscreen = new RiddleScreen(player, this.getClass().getSimpleName());
                        }
                        break;
                    case 4: // drop items
                        String itemName = Command.parsedCommands.get(1);
                        player.drop(player.inventory().get(itemName));
                        break;
                    case 7: //use
                        String useItemName = Command.parsedCommands.get(1);
                        player.use(player.inventory().get(useItemName));
                        break;
                    case 5: //buy
                        String itemNameToBuy = Command.parsedCommands.get(1);
                        if (seller.inventory().get(itemNameToBuy) != null && player.world().tile(player.x, player.y).isBox()) {
                            player.buyFrom(seller, seller.inventory().get(itemNameToBuy));
                        }
                        break;
                    case 6: //sell
                        String itemNameToSell = Command.parsedCommands.get(1);
                        if (player.inventory().get(itemNameToSell) != null && player.world().tile(player.x, player.y).isBox()) {
                            player.sellTo(seller, player.inventory().get(itemNameToSell));
                        }
                        break;
                }
            }

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
                    case KeyEvent.VK_ESCAPE:
                        System.exit(0);
                        break;
                }
            }
        }

        //if there is no riddle screen, then update creature's movement.
        if (subscreen == null) {
            world.update();
        }


        return this;
    }

    private void displayHint(AsciiPanel terminal, int right, int bottom) {
        int length = terminal.getWidthInCharacters() - screenWidth - 2;
        terminal.write(drawLine(length), right, bottom, Color.orange);
        int height = terminal.getHeightInCharacters();

        for (int i = 0; i < height; i++) {
            terminal.write("|", right - 1, i, Color.orange);
        }
        terminal.write("Store", right, bottom + 1, Color.green);

        int i = 0;
        for (Map.Entry<GuiItem, Integer> itemCount : seller.inventory().inventoryStats().entrySet()) {
            String stats = String.format("%2d X %7s      $%.1f", itemCount.getValue(), itemCount.getKey().name(), itemCount.getKey().price());
            terminal.write(stats, right, bottom + 3 + i, Color.magenta);
            i += 2;
        }
    }

    private void displayDescription(AsciiPanel terminal, int left, int bottom) {
        terminal.write("Shop", left, bottom + 1, Color.RED);
        String description = Game.castle.getCastleRooms().get("Shop").getDescription();
        String msg1 = description.substring(0, 75);
        String msg2 = description.substring(75);

        terminal.write(msg1, left, bottom + 2, Color.white);
        terminal.write(msg2, left, bottom + 3, Color.white);
        terminal.write(" ", left, bottom + 3, Color.red);
    }
}