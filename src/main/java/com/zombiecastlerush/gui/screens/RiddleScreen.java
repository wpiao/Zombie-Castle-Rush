package com.zombiecastlerush.gui.screens;

import asciiPanel.AsciiPanel;
import com.zombiecastlerush.building.Puzzle;
import com.zombiecastlerush.gui.component.Creature;
import com.zombiecastlerush.gui.component.GuiItem;
import com.zombiecastlerush.gui.component.RiddleFactory;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class RiddleScreen implements Screen {
    private final int screenWidth;
    private final int screenHeight;
    private final Creature player;
    private Puzzle riddle;
    private KeyEvent key;
    private int numOfAttempts;
    private boolean itemPickedUp;
    private static final Map<String, String> castleScreenConverter = new HashMap<>() {{
        put("CastleHallScreen", "Castle-Hall");
        put("CombatHallScreen", "Combat-Hall");
        put("DrawBridgeScreen", "Draw-Bridge");
        put("EastWingScreen", "East-Wing");
        put("WestWingScreen", "West-Wing");
        put("ShopScreen", "Shop");
    }};

    public RiddleScreen(Creature player) {
        screenWidth = 90;
        screenHeight = 51;
        RiddleFactory.answer = "";
        this.player = player;

        this.riddle = RiddleFactory.generateRiddle(castleScreenConverter.get(player.world().name()));
    }

    public RiddleScreen(Creature player, String screenName) {
        this(player);
        this.riddle = RiddleFactory.generateRiddle(castleScreenConverter.get(screenName));
    }

    public void displayOutput(AsciiPanel terminal) {
        terminal.clear();

        displayUserInput(terminal, 0, terminal.getHeightInCharacters() - 10);
        if (riddle.getQuestion().length() > 80) {
            terminal.writeCenter(riddle.getQuestion().substring(0, 79), (screenHeight - 10) / 2);
            terminal.writeCenter(riddle.getQuestion().substring(79), (screenHeight - 10) / 2 + 2);
        } else {
            terminal.writeCenter(riddle.getQuestion(), (screenHeight - 10) / 2);
        }
        String msg;
        if (numOfAttempts <= 3) {
            msg = String.format("You have attempted %d %s. You only have 3 attempts to get the reward.",
                    numOfAttempts, numOfAttempts > 1 ? "times" : "time");
        } else {
            msg = "You can continue to try but your reward is vanished or type quit to exit.";
        }
        terminal.writeCenter(msg, (screenHeight - 10) / 2 + 8, Color.cyan);
        if (itemPickedUp) {
            terminal.writeCenter("An item is dropped in your inventory.", (screenHeight - 10) / 2 + 16, Color.GREEN);
        }
        terminal.repaint();

    }

    private void displayUserInput(AsciiPanel terminal, int x, int y) {
        terminal.write(drawLine(terminal.getWidthInCharacters() - 1), x, y, Color.orange);
        terminal.write("Answer is -> ", x, y + 1, Color.red);

        if (key != null) {
            int keyCode = key.getKeyCode();
            if (keyCode <= 126 && keyCode != 10) {
                if (keyCode == 8 && RiddleFactory.answer.length() > 0) {
                    RiddleFactory.answer = RiddleFactory.answer.substring(0, RiddleFactory.answer.length() - 1);
                } else if (keyCode == 8) {
                    RiddleFactory.answer = "";
                } else {
                    RiddleFactory.answer += (char) keyCode;
                }
                terminal.write(RiddleFactory.answer, x, y + 3, Color.MAGENTA);
            }
        }
    }

    public Screen respondToUserInput(KeyEvent key) {
        this.key = key;
        if (RiddleFactory.answer.equalsIgnoreCase("quit")) {
            return null;
        }
        if (key.getKeyCode() == KeyEvent.VK_ENTER) {
            numOfAttempts++;
            if (numOfAttempts <= 3) {
                if (RiddleFactory.answer.equals(riddle.getSolution().toUpperCase())) {
                    GuiItem item = player.world().item(player.x, player.y);
                    if (!player.inventory().getGuiItems().contains(item)) {
                        player.pickup();
                        itemPickedUp = true;
                    }
                    return null;
                }
            } else if (RiddleFactory.answer.equals(riddle.getSolution().toUpperCase())) {
                // attempted more than 3 times, the reward vanish.
                player.world().remove(player.x, player.y);
                return null;
            }else{
                player.world().remove(player.x, player.y);
            }
            RiddleFactory.answer = "";
        }
        return this;
    }
}
