package com.zombiecastlerush.gui.screens;

import java.awt.event.KeyEvent;
import asciiPanel.AsciiPanel;

public class StartScreen implements Screen {

    public void displayOutput(AsciiPanel terminal) {
        String banner = "Welcome to Zombie Castle Rush";
        terminal.write(banner,(terminal.getWidthInCharacters()-banner.length())/2,terminal.getHeightInCharacters()/3);
        terminal.writeCenter("-- press [enter] to start --", terminal.getHeightInCharacters()/3*2);
    }

    public Screen respondToUserInput(KeyEvent key) {
        return key.getKeyCode() == KeyEvent.VK_ENTER ? new PlayScreen() : this;
    }
}
