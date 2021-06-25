package com.zombiecastlerush.gui.screens;

import java.awt.event.KeyEvent;
import java.lang.invoke.SwitchPoint;

import asciiPanel.AsciiPanel;

public class WinScreen implements Screen {

    public void displayOutput(AsciiPanel terminal) {
        String msg = "Congratulations! You've beaten the Lord and won the game.";
        terminal.write(msg, (terminal.getWidthInCharacters() - msg.length()) / 2, terminal.getHeightInCharacters() / 3);
        terminal.writeCenter("-- press [ENTER] to restart --", terminal.getHeightInCharacters() / 3 * 2);
        terminal.writeCenter("-- press [ESC] to leave --",terminal.getHeightInCharacters() / 3 * 2 + 2);
    }

    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()){
            case KeyEvent.VK_ENTER:
                return new StartScreen();
            case KeyEvent.VK_ESCAPE:

                System.exit(0);
            default:
                return this;
        }

    }
}