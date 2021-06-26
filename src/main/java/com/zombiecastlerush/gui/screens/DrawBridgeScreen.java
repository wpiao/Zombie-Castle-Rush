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
        }

        //set player current world
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
                .build(this.getClass().getSimpleName());

        EntityFactory entityFactory = new EntityFactory(world);
        for (int i = 0; i < 16; i++) {
            entityFactory.newZombies();
            entityFactory.newVase();
        }

        entityFactory.newSword();
    }


    public void displayOutput(AsciiPanel terminal) {

        Color color = player.inventory().get("map")==null?Color.BLACK:Color.darkGray;
        //playground
        displayTiles(terminal, player, world, screenWidth, screenHeight,color);
        //status
        displayStatus(terminal, screenWidth + 1, 0,screenWidth,player,"Zombie");
        //inventory
        displayInventory(terminal, screenWidth + 1, (screenHeight - screenHeight % 3) / 3, screenWidth, player);
        //display hint
        displayHint(terminal, screenWidth + 1, (screenHeight - screenHeight % 3) * 2 / 3,screenWidth);
        //display map
        displayMap(terminal,screenWidth+1,(screenHeight - screenHeight % 3) * 2 / 3 + 17);
        //prompt
        displayDescription(terminal, 0, screenHeight);
        //user input
        displayUserInput(terminal, 0, terminal.getHeightInCharacters() - 3, screenWidth, subscreen, key);

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
            if (key.getKeyCode() == KeyEvent.VK_ENTER) {
                Command.command = "";
                switch (choice) {
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
                }
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

    private void displayMap(AsciiPanel terminal, int x, int y){

        terminal.write("Map", x, y, Color.green);
        terminal.write((char)178,x+9,y+2,Color.PINK);
        terminal.write((char)186,x+9,y+3,Color.PINK);
        terminal.write((""+(char)178+(char)205+(char)178+(char)205+(char)178+(char)205+(char)178),
                x+7,y+4,Color.PINK);
        terminal.write((""+(char)186+" " + (char)186),x+7,y+5,Color.PINK);
        terminal.write((""+(char)200+(char)178+(char)188),x+7,y+6,Color.PINK);

    }
}