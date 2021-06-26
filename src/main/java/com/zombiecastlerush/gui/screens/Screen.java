package com.zombiecastlerush.gui.screens;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Map;

import asciiPanel.AsciiPanel;
import com.zombiecastlerush.gui.Command;
import com.zombiecastlerush.gui.entity.Creature;
import com.zombiecastlerush.gui.entity.GuiItem;
import com.zombiecastlerush.gui.layout.World;

public interface Screen {
    void displayOutput(AsciiPanel terminal);

    Screen respondToUserInput(KeyEvent key);

    default void displayHint(AsciiPanel terminal,int x, int y,int screenWidth){
        int length = terminal.getWidthInCharacters() - screenWidth - 2;
        terminal.write(drawLine(length), x, y, Color.orange);
        int height = terminal.getHeightInCharacters();

        for (int i = 0; i < height; i++) {
            terminal.write("|", x - 1, i, Color.orange);
        }
        terminal.write("Hint", x, y + 1, Color.green);
        terminal.write("COMMANDS AVAILABLE   ", x, y+3, Color.magenta);
        terminal.write("[PICK-UP]   ", x, y+5, Color.magenta);
        terminal.write("[DROP][NAME]   ", x, y+7, Color.magenta);
        terminal.write("[USE][NAME]   ", x, y+9, Color.magenta);
        terminal.write("[ATTEMPT][PUZZLE]   ", x, y+11, Color.magenta);
        terminal.write("[BUY][NAME]",x,y+13,Color.magenta);
        terminal.write("[SELL][NAME]", x,y+15, Color.magenta);
        terminal.write(drawLine(length), x, y+16, Color.orange);
    }

    default String drawLine(int length) {

        String line = "";
        for (int i = 0; i < length; i++) {
            line += "-";
        }
        return line;
    }

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
    default void displayInventory(AsciiPanel terminal, int right, int middle, int screenWidth, Creature player) {
        int length = terminal.getWidthInCharacters() - screenWidth - 2;
        terminal.write(drawLine(length), right, middle, Color.ORANGE);
        String max = String.format("Inventory          %d/7",player.inventory().getGuiItems().size());
        terminal.write(max, right, middle + 1, Color.green);
        int i = 0;
        for (Map.Entry<GuiItem, Integer> itemCount : player.inventory().inventoryStats().entrySet()) {

            String stats = String.format("%2d X %-7s     $%.1f", itemCount.getValue(), itemCount.getKey().name(), itemCount.getKey().value());
            terminal.write(stats, right, middle + 3 + i, Color.magenta);
            i += 2;
        }
    }


    default void displayUserInput(AsciiPanel terminal, int left, int i, int screenWidth, Screen subscreen, KeyEvent key) {
        terminal.write(drawLine(screenWidth), left, i, Color.orange);
        terminal.write("Enter command -> ", left, i + 1, Color.red);
        if (subscreen == null) {
            Command.type(key, terminal, 18, i + 1);
        }
    }

    default void displayStatus(AsciiPanel terminal, int right, int top, int screenWidth, Creature player, String enemy) {
        //draw yellow boundary lines
        int length = terminal.getWidthInCharacters() - screenWidth - 2;
        terminal.write(drawLine(length), right, top, Color.ORANGE);
        terminal.write("Status              $: " + player.getBalance(), right, top + 1, Color.green);

        // display player hp
        String stats = player.hp() < 1 ? "" : String.format("You: %6d/%3d hp", player.hp(), player.maxHp());
        terminal.write(stats, right, top + 3, Color.magenta);

        //if player has an opponent, aka in fight, then display its hp.
        String enemyStats = player.opponent() == null || player.opponent().hp() < 1 ? "" :
                String.format("%s: %3d/%3d hp",enemy, player.opponent().hp(), player.opponent().maxHp());
        terminal.write(enemyStats, right, top + 4, Color.green);

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
}