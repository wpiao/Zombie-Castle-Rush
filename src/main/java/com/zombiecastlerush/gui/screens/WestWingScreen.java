package com.zombiecastlerush.gui.screens;

import asciiPanel.AsciiPanel;
import com.zombiecastlerush.gui.*;
import com.zombiecastlerush.gui.entity.Creature;
import com.zombiecastlerush.gui.entity.EntityFactory;
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
    private Screen subscreen;

    public WestWingScreen(Creature player) {
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
        if (player.x >= 14 && player.x <= 19 && player.y == 0) {
            player.y = 49;
        } else if (player.x == 0) {
            player.x = 88;
        }

    }

    private void createWorld() {
        String path = "Resources/Castle/WestWing.txt";
        world = new WorldBuilder(90, 51)
                .design(path)
                .build(this.getClass().getSimpleName());

        EntityFactory entityFactory = new EntityFactory(world);
        for (int i = 0; i < 20; i++) {
            entityFactory.newZombies();
            entityFactory.newFork();
        }

        entityFactory.newSword();
    }

    public void displayOutput(AsciiPanel terminal) {
        Color color = player.inventory().get("map")==null?Color.BLACK:Color.darkGray;
        //playground
        displayTiles(terminal, player, world,screenWidth,screenHeight,color);
        //status
        displayStatus(terminal, screenWidth + 1, 0);
        //inventory
        displayInventory(terminal, screenWidth + 1, (screenHeight - screenHeight % 3) / 3);
        //display map
        displayHint(terminal, screenWidth + 1, (screenHeight - screenHeight % 3) * 2 / 3,screenWidth);
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

    private void displayStatus(AsciiPanel terminal, int right, int top) {
        //draw yellow boundary lines
        int length = terminal.getWidthInCharacters() - screenWidth - 2;
        terminal.write(drawLine(length), right, top, Color.ORANGE);
        terminal.write("Status              $: " + player.getBalance(), right, top + 1, Color.green);

        // display player hp
        String stats = player.hp() < 1 ? "" : String.format("You: %6d/%3d hp", player.hp(), player.maxHp());
        terminal.write(stats, right, top + 3, Color.magenta);

        //if player has an opponent, aka in fight, then display its hp.
        String enemyStats = player.opponent() == null || player.opponent().hp() < 1 ? "" :
                String.format("Zombie: %3d/%3d hp", player.opponent().hp(), player.opponent().maxHp());
        terminal.write(enemyStats, right, top + 4, Color.green);

        String killStats = String.format("Zombies killed: %d", player.killedNumber);
        terminal.write(killStats, right, top + 6, Color.RED);
        int level = player.experience / 10 + 1;

        String lvlStats1 = String.format("EXP: %3d   Lvl: %2d", player.experience, level);
        String lvlStats2 = String.format("Attack: %2d Defense: %2d", player.attackValue(), player.defenseValue());
        terminal.write(lvlStats1, right, top + 8, Color.YELLOW);
        terminal.write(lvlStats2, right, top + 10, Color.YELLOW);

        terminal.write("Equipment: ", right, top + 12, Color.CYAN);

        String equipStats1 = String.format("Weapon:%5s   Acc:%5s", player.weapon == null ?
                "" : player.weapon.name(), player.accs == null ? "" : player.accs.name());
        terminal.write(equipStats1, right, top + 14, Color.CYAN);

        String equipStats2 = String.format("Tool: %5s", player.tool == null ? "" : player.tool.name());
        terminal.write(equipStats2, right, top + 15, Color.CYAN);
    }


    private void displayInventory(AsciiPanel terminal, int right, int middle) {
        int length = terminal.getWidthInCharacters() - screenWidth - 2;
        terminal.write(drawLine(length), right, middle, Color.ORANGE);
        terminal.write("Inventory", right, middle + 1, Color.green);
        for (int i = 0; i < player.inventory().getGuiItems().size(); i++) {
            terminal.write(player.inventory().get(i).name(), right, middle + 3 + i, Color.magenta);
        }

    }

    private void displayUserInput(AsciiPanel terminal, int left, int i) {
        terminal.write(drawLine(screenWidth), left, i, Color.orange);
        terminal.write("Enter command -> ", left, i + 1, Color.red);
        if (subscreen == null) {
            Command.type(key, terminal, 18, i + 1);
        }
    }

    private void displayDescription(AsciiPanel terminal, int left, int bottom) {
        terminal.write("West Wing", left, bottom + 1, Color.RED);
        world.getBoxTile().forEach((point, tile) -> {
            if (player.x == point.x && player.y == point.y) {
                String msg1 = "The box pulses with power. You know not how, but it has a riddle for you,";

                String msg2 = "and it will not let you leave until you have solved it.Perhaps you should attempt puzzle.";
                terminal.write(msg1, left, bottom + 3, Color.magenta);
                terminal.write(msg2, left, bottom + 4, Color.magenta);
            } else {
                String description = Game.castle.getCastleRooms().get("West-Wing").getDescription();
                terminal.write(description, left, bottom + 2, Color.white);
                terminal.write(" ", left, bottom + 3, Color.red);
            }
        });
    }
}