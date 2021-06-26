package com.zombiecastlerush.gui.screens;

import java.awt.event.KeyEvent;
import asciiPanel.AsciiPanel;

public class LoseScreen implements Screen {

    public void displayOutput(AsciiPanel terminal) {
        String msg = "You lost the game but you will be remembered by the Lord of the Castle.";
        terminal.write(msg, (terminal.getWidthInCharacters() - msg.length()) / 2, terminal.getHeightInCharacters() / 3);
        terminal.writeCenter("-- press [ENTER] to restart --", terminal.getHeightInCharacters() / 3 * 2);
    }

    public Screen respondToUserInput(KeyEvent key) {
        return key.getKeyCode() == KeyEvent.VK_ENTER ? new StartScreen() : this;
    }
}