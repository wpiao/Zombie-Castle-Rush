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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class CombatHallScreen implements Screen {
    private World world;
    private final Creature player;
    private final int screenWidth;
    private final int screenHeight;
    private KeyEvent key;
    private Screen subscreen;
    private Creature lord;

    public CombatHallScreen(Creature player) {
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
        if (player.x == 89 && (player.y == 17 || player.y == 18 || player.y == 19)) {
            player.x = 1;
        }

    }

    private void createWorld() {
        String path = "Resources/Castle/CombatHall.txt";
        world = new WorldBuilder(90, 51)
                .design(path)
                .build(this.getClass().getSimpleName());
        EntityFactory entityFactory = new EntityFactory(world);
        this.lord = entityFactory.newAggZombies(player);
    }

    public void displayOutput(AsciiPanel terminal) {

        Color color =  Color.darkGray;
        //playground
        displayTiles(terminal, player, world, screenWidth, screenHeight, color);
        //status
        displayStatus(terminal, screenWidth + 1, 0,screenWidth);
        //inventory
        displayInventory(terminal, screenWidth + 1, (screenHeight - screenHeight % 3) / 3, screenWidth, player);
        //display hint
        displayHint(terminal, screenWidth + 1, (screenHeight - screenHeight % 3) * 2 / 3);
        //display map
        displayMap(terminal,screenWidth+1,(screenHeight - screenHeight % 3) * 2 / 3 + 17);
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
                }
            }
            if (player.x == 0 && (player.y == 17 || player.y == 18 || player.y == 19)) {
                return new EastWingScreen(player);
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

        if (player.hp() < 1) {
            return new LoseScreen();
        }

        if ( lord.hp() < 1){

            return new WinScreen();
        }

        return this;
    }

    private void displayStatus(AsciiPanel terminal, int right, int top, int screenWidth) {
        //draw yellow boundary lines
        int length = terminal.getWidthInCharacters() - screenWidth - 2;
        terminal.write(drawLine(length), right, top, Color.ORANGE);
        terminal.write("Status              $: " + player.getBalance(), right, top + 1, Color.green);

        // display player hp
        String stats = player.hp() < 1 ? "" : String.format("You: %6d/%3d hp", player.hp(), player.maxHp());
        terminal.write(stats, right, top + 3, Color.magenta);

        //if player has an opponent, aka in fight, then display its hp.
        String enemyStats = String.format("Lord:   %3d/%3d hp", lord.hp(), lord.maxHp());
        terminal.write(enemyStats, right, top + 4, Color.PINK);

        String killStats = String.format("Zombies killed: %d", player.killedNumber);
        terminal.write(killStats, right, top + 6, Color.RED);
        int level = player.experience / 10 + 1;

        player.setInitialAttackValue( 20 + (level-1) * 2);
        player.setInitialDefenseValue(5 + level-1);
        player.attackValue = player.getInitialAttackValue() + (player.weapon == null? 0:player.weapon.attackValue());
        player.defenseValue = player.getInitialDefenseValue() + (player.accs == null? 0:player.accs.defenseValue());

        String lvlStats1 = String.format("EXP:  %3d  Lvl: %6d", player.experience, level);
        String lvlStats2 = String.format("Attack: %2d Defense: %2d", player.attackValue(), player.defenseValue());
        terminal.write(lvlStats1, right, top + 8, Color.YELLOW);
        terminal.write(lvlStats2, right, top + 10, Color.YELLOW);

        String equipStats1 = String.format("Weapon:%8s   Acc:%5s", player.weapon == null ?
                "" : player.weapon.name(), player.accs == null ? "" : player.accs.name());
        terminal.write(equipStats1, right, top + 12, Color.CYAN);

        String equipStats2 = String.format("Tool: %9s", player.tool == null ? "" : player.tool.name());
        terminal.write(equipStats2, right, top + 14, Color.CYAN);
    }

    private void displayHint(AsciiPanel terminal, int right, int bottom) {
        int length = terminal.getWidthInCharacters() - screenWidth - 2;
        terminal.write(drawLine(length), right, bottom, Color.orange);
        int height = terminal.getHeightInCharacters();

        for (int i = 0; i < height; i++) {
            terminal.write("|", right - 1, i, Color.orange);
        }
        terminal.write("Hint", right, bottom + 1, Color.green);
        terminal.write("Beat the Lord to Win", right+5, bottom + 7, Color.magenta);
        terminal.write(drawLine(length), right, bottom+16, Color.orange);
    }

    private void displayMap(AsciiPanel terminal, int x, int y){

        terminal.write("Map", x, y, Color.green);
        terminal.write((char)178,x+12, y, Color.red);
        terminal.write("You are here", x+14,y, Color.red);

        terminal.write((char)178,x+9,y+2,Color.PINK);
        terminal.write((char)186,x+9,y+3,Color.PINK);
        terminal.write((""+(char)178+(char)205+(char)178+(char)205+(char)178+(char)205+(char)178),
                x+7,y+4,Color.PINK);
        terminal.write((char)178,x+13, y+4, Color.red);
        terminal.write((""+(char)186+" " + (char)186),x+7,y+5,Color.PINK);
        terminal.write((""+(char)200+(char)178+(char)188),x+7,y+6,Color.PINK);

    }

    private void displayDescription(AsciiPanel terminal, int left, int bottom) {
        terminal.write("Combat Hall", left, bottom + 1, Color.RED);
        String description = Game.castle.getCastleRooms().get("Combat-Hall").getDescription();
        String msg1 = description.substring(0, description.length() / 3);
        String msg2 = description.substring(description.length() / 3 + 1, description.length() / 3 * 2);
        String msg3 = description.substring(description.length() / 3 * 2 + 1);

        terminal.write(msg1, left, bottom + 2, Color.white);
        terminal.write(msg2, left, bottom + 3, Color.white);
        terminal.write(msg3, left, bottom + 4, Color.white);

        terminal.write(" ", left, bottom + 5, Color.red);
    }

}