package com.zombiecastlerush.gui.screens;

import asciiPanel.AsciiPanel;

import java.awt.*;
import java.awt.event.KeyEvent;

public class RiddleScreen implements Screen {
    private final int screenWidth;
    private final int screenHeight;

    public RiddleScreen() {
        screenWidth = 90;
        screenHeight = 51;
    }

    public void displayOutput(AsciiPanel terminal) {
        displayUserInput(terminal,0, terminal.getHeightInCharacters() - 10);
        terminal.writeCenter(chooseRandomRiddle(), (screenHeight -10) /2 );

    }

    private void displayUserInput(AsciiPanel terminal, int left, int top) {
        terminal.write(drawLine(terminal.getWidthInCharacters()-1), left, top, Color.orange);
        terminal.write("Enter command -> ", left, top + 1, Color.red);
    }
    private String drawLine(int length) {

        String line = "";
        for (int i = 0; i < length; i++) {
            line += "-";
        }
        return line;
    }


    public Screen respondToUserInput(KeyEvent key) {

        return key.getKeyCode() == KeyEvent.VK_ENTER ? new StartScreen() : this;
    }

    private String chooseRandomRiddle() {
        //placeholder for riddles from external files.
        return "What is (2+2)x(2-2)?";
    }
}
