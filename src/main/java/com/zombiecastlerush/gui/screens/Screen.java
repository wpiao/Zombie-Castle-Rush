package com.zombiecastlerush.gui.screens;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Map;

import asciiPanel.AsciiPanel;
import com.zombiecastlerush.gui.Command;
import com.zombiecastlerush.gui.component.Creature;
import com.zombiecastlerush.gui.component.GuiItem;
import com.zombiecastlerush.gui.layout.World;

public interface Screen {
    /**
     * display the content of the terminal
     * @param terminal the displaying terminal
     */
    void displayOutput(AsciiPanel terminal);

    /**
     * implementation of key listeners
     * @param key user input keystroke
     * @return new screen
     */
    Screen respondToUserInput(KeyEvent key);

    /**
     * default interface method to display the hint section of the terminal
     * @param terminal the displaying terminal
     * @param x starting x coordinates
     * @param y starting y coordinates
     * @param screenWidth terminal tiles section width
     */
    default void displayHint(AsciiPanel terminal,int x, int y,int screenWidth){
        int length = terminal.getWidthInCharacters() - screenWidth - 2;
        terminal.write(drawLine(length), x, y, Color.orange);
        int height = terminal.getHeightInCharacters();

        for (int i = 0; i < height; i++) {
            terminal.write("|", x - 1, i, Color.orange);
        }

        terminal.write("Commands", x, y+1, Color.green);
        terminal.write("[ATTEMPT][PUZZLE]",x,y+3,Color.magenta);
        terminal.write("[BUY][NAME]   ", x, y+5, Color.magenta);
        terminal.write("[DROP][NAME]   ", x, y+7, Color.magenta);
        terminal.write("[PICK-UP]   ", x, y+9, Color.magenta);
        terminal.write("[SAVE]   ", x, y+11, Color.magenta);
        terminal.write("[SELL][NAME]",x,y+13,Color.magenta);
        terminal.write("[USE][NAME]", x,y+15, Color.magenta);
        terminal.write(drawLine(length), x, y+16, Color.orange);

    }

    /**
     * default helper method to draw lines in terminal
     * @param length the length of the line
     * @return combined string of dashes
     */
    default String drawLine(int length) {

        return "-".repeat(length);
    }

    /**
     * default interface method to display the tiles section of the terminal
     * @param terminal the displaying terminal
     * @param player player creature across different screens
     * @param world the current world which the player is in
     * @param screenWidth tiles section width
     * @param screenHeight tiles section height
     * @param color the color of the displaying tiles
     */
    default void displayTiles(AsciiPanel terminal, Creature player, World world,int screenWidth, int screenHeight, Color color){
        for (int x = 0; x < screenWidth; x++) {
            for (int y = 0; y < screenHeight; y++) {

                if (player.canSee(x, y)) {
                    terminal.write(world.glyph(x, y, player), x, y, world.color(x, y));
                } else {
                    terminal.write(world.glyph(x, y, player), x, y, color);
                }
            }
        }
    }

    /**
     * default interface method to display the inventory section of the terminal
     * @param terminal the displaying terminal
     * @param x starting x coordinates
     * @param y starting y coordinates
     * @param screenWidth tiles section width
     * @param player player creature across different screens
     */
    default void displayInventory(AsciiPanel terminal, int x, int y, int screenWidth, Creature player) {
        int length = terminal.getWidthInCharacters() - screenWidth - 2;
        terminal.write(drawLine(length), x, y, Color.ORANGE);
        String max = String.format("Inventory          %d/7",player.inventory().getGuiItems().size());
        terminal.write(max, x, y + 1, Color.green);
        int i = 0;
        for (Map.Entry<GuiItem, Integer> itemCount : player.inventory().inventoryStats().entrySet()) {

            String stats = String.format("%2d X %-7s     $%.1f", itemCount.getValue(), itemCount.getKey().name(), itemCount.getKey().value());
            terminal.write(stats, x, y + 3 + i, Color.magenta);
            i += 2;
        }
    }

    /**
     * default interface method to display the user input section of the terminal
     * @param terminal the displaying terminal
     * @param x starting x coordinates
     * @param y starting y coordinates
     * @param screenWidth tiles section width
     * @param subscreen the nested screen inside of a screen
     * @param key user input keystroke
     */
    default void displayUserInput(AsciiPanel terminal, int x, int y, int screenWidth, Screen subscreen, KeyEvent key) {
        terminal.write(drawLine(screenWidth), x, y, Color.orange);
        terminal.write("Enter command -> ", x, y + 1, Color.red);
        if (subscreen == null) {
            Command.type(key, terminal, 18, y + 1);
        }
    }

    /**
     * default interface method to display the status section of the terminal
     * @param terminal the displaying terminal
     * @param x starting x coordinates
     * @param y starting y coordinates
     * @param screenWidth tiles section width
     * @param player player creature across different screens
     * @param enemy  player's opponent's name
     */
    default void displayStatus(AsciiPanel terminal, int x, int y, int screenWidth, Creature player, String enemy) {
        //draw yellow boundary lines
        int length = terminal.getWidthInCharacters() - screenWidth - 2;
        terminal.write(drawLine(length), x, y, Color.ORANGE);
        terminal.write("Status              $: " + player.getBalance(), x, y + 1, Color.green);

        // display player hp
        String stats = player.hp() < 1 ? "" : String.format("You: %6d/%3d hp", player.hp(), player.maxHp());
        terminal.write(stats, x, y + 3, Color.magenta);

        //if player has an opponent, aka in fight, then display its hp.
        String enemyStats = player.opponent() == null || player.opponent().hp() < 1 ? "" :
                String.format("%s: %3d/%3d hp",enemy, player.opponent().hp(), player.opponent().maxHp());
        terminal.write(enemyStats, x, y + 4, Color.green);

        String killStats = String.format("Zombies killed: %d", player.killedNumber);
        terminal.write(killStats, x, y + 6, Color.RED);
        int level = player.experience / 10 + 1;

        player.setInitialAttackValue( 20 + (level-1) * 2);
        player.setInitialDefenseValue(5 + level-1);
        player.attackValue = player.getInitialAttackValue() + (player.weapon == null? 0:player.weapon.attackValue());
        player.defenseValue = player.getInitialDefenseValue() + (player.accs == null? 0:player.accs.defenseValue());

        String lvlStats1 = String.format("EXP:  %3d  Lvl: %6d", player.experience, level);
        String lvlStats2 = String.format("Attack: %2d Defense: %2d", player.attackValue(), player.defenseValue());
        terminal.write(lvlStats1, x, y + 8, Color.YELLOW);
        terminal.write(lvlStats2, x, y + 10, Color.YELLOW);

        String equipStats1 = String.format("Weapon:%8s   Acc:%5s", player.weapon == null ?
                "" : player.weapon.name(), player.accs == null ? "" : player.accs.name());
        terminal.write(equipStats1, x, y + 12, Color.CYAN);

        String equipStats2 = String.format("Tool: %9s", player.tool == null ? "" : player.tool.name());
        terminal.write(equipStats2, x, y + 14, Color.CYAN);
    }

    /**
     * default interface method to display the mini map section of the terminal
     * @param terminal the displaying terminal
     * @param x starting x coordinates
     * @param y starting y coordinates
     * @param cx x coordinates of current block of location
     * @param cy x coordinates of current block of location
     */
    default void displayMap(AsciiPanel terminal, int x, int y, int cx, int cy){

        terminal.write("Map", x, y, Color.green);
        terminal.write((char)178,x+12, y, Color.red);
        terminal.write("You are here", x+14,y, Color.red);

        terminal.write((char)178,x+9,y+2,Color.PINK);
        terminal.write((char)186,x+9,y+3,Color.PINK);
        terminal.write((""+(char)178+(char)205+(char)178+(char)205+(char)178+(char)205+(char)178),
                x+7,y+4,Color.PINK);

        terminal.write((""+(char)186+" " + (char)186),x+7,y+5,Color.PINK);
        terminal.write((""+(char)200+(char)178+(char)188),x+7,y+6,Color.PINK);
        terminal.write((char)178,cx, cy, Color.red);
    }
}