package com.zombiecastlerush.gui.screens;

import asciiPanel.AsciiPanel;
import com.zombiecastlerush.building.Challenge;
import com.zombiecastlerush.building.Puzzle;
import com.zombiecastlerush.gui.creature.Creature;
import com.zombiecastlerush.gui.RiddleFactory;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Locale;

public class RiddleScreen implements Screen {
    private final int screenWidth;
    private final int screenHeight;
    private final Creature player;
    private Puzzle riddle;
    private KeyEvent key;


    public RiddleScreen(Creature player, String screenName) {
        screenWidth = 90;
        screenHeight = 51;
        RiddleFactory.answer = "";
        this.player = player;
        this.riddle = RiddleFactory.generateRiddle(screenName);
    }


    public void displayOutput(AsciiPanel terminal) {
        terminal.clear();

        displayUserInput(terminal, 0, terminal.getHeightInCharacters() - 10);
        terminal.writeCenter(riddle.getQuestion(), (screenHeight - 10) / 2);
        terminal.repaint();

    }

    private void displayUserInput(AsciiPanel terminal, int x, int y) {
        terminal.write(drawLine(terminal.getWidthInCharacters() - 1), x, y, Color.orange);
        terminal.write("Answer is -> ", x, y + 1, Color.red);


        if (key!=null) {
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

    private String drawLine(int length) {

        String line = "";
        for (int i = 0; i < length; i++) {
            line += "-";
        }
        return line;
    }


    public Screen respondToUserInput(KeyEvent key) {
        this.key = key;
        if (RiddleFactory.answer.equals(riddle.getSolution().toUpperCase()) && key.getKeyCode() == KeyEvent.VK_ENTER) {
            RiddleFactory.answer = "";
            return null;
        } else return this;
    }
}
