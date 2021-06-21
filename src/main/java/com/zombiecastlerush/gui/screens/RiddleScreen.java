package com.zombiecastlerush.gui.screens;

import asciiPanel.AsciiPanel;

import java.awt.event.KeyEvent;

public class RiddleScreen implements Screen {
    private final int screenWidth;
    private final int screenHeight;

    public RiddleScreen() {
        screenWidth = 90;
        screenHeight = 51;
    }

    public void displayOutput(AsciiPanel terminal) {

        terminal.writeCenter(chooseRandomRiddle(), screenHeight /2 );
    }

    public Screen respondToUserInput(KeyEvent key) {
        return key.getKeyCode() == KeyEvent.VK_ENTER ? new StartScreen() : this;
    }

    private String chooseRandomRiddle() {
        //placeholder for riddles from external files.
        return "What is (2+2)x(2-2)?";
    }
}
