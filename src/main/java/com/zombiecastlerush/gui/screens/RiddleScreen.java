package com.zombiecastlerush.gui.screens;

import asciiPanel.AsciiPanel;
import com.zombiecastlerush.gui.Creature;
import com.zombiecastlerush.gui.Riddle;

import java.awt.*;
import java.awt.event.KeyEvent;

public class RiddleScreen implements Screen {
    private final int screenWidth;
    private final int screenHeight;
    private final Creature player;


    private KeyEvent key;

    public RiddleScreen(Creature player) {
        screenWidth = 90;
        screenHeight = 51;
        Riddle.answer = "";
        this.player = player;
    }

    public void displayOutput(AsciiPanel terminal) {
        displayUserInput(terminal, 0, terminal.getHeightInCharacters() - 10);
        terminal.writeCenter(Riddle.chooseRandomRiddle(), (screenHeight - 10) / 2);

    }

    private void displayUserInput(AsciiPanel terminal, int left, int top) {
        terminal.write(drawLine(terminal.getWidthInCharacters() - 1), left, top, Color.orange);
        terminal.write("Answer is -> ", left, top + 1, Color.red);

        if (key!=null) {
            int keyCode = key.getKeyCode();
            if (keyCode <= 126 && keyCode != 10) {
                if (keyCode == 8 && Riddle.answer.length() > 0) {
                    Riddle.answer = Riddle.answer.substring(0, Riddle.answer.length() - 1);
                } else if (keyCode == 8) {
                    Riddle.answer = "";
                } else {
                    Riddle.answer += (char) keyCode;
                }
                terminal.write(Riddle.answer, left, top + 3, Color.MAGENTA);
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
        if (Riddle.answer.equals(Riddle.solution) && key.getKeyCode() == KeyEvent.VK_ENTER) {
            Riddle.answer = "";
            return new CastleHallScreen(player);
        } else return this;
    }
}
