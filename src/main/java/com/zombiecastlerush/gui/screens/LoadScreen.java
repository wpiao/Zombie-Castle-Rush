package com.zombiecastlerush.gui.screens;

import java.awt.event.KeyEvent;
import java.io.File;

import asciiPanel.AsciiPanel;

public class LoadScreen implements Screen {

    public void displayOutput(AsciiPanel terminal) {
        String prompt = new File("Resources/savedData.json").exists() ? "  -- press [SPACE] to reload  --" : "";
        terminal.writeCenter("-- press [ENTER] to start new game --" + prompt, terminal.getHeightInCharacters() / 2);
    }

    public Screen respondToUserInput(KeyEvent key) {

        if (key.getKeyCode() == KeyEvent.VK_ENTER) {
            return new StartScreen();
        } else if (key.getKeyCode() == KeyEvent.VK_SPACE && new File("Resources/savedData.json").exists()) {

            //todo load from saved file
            Screen savedScreen = null;


            return savedScreen;
        } else {
            return this;
        }
    }
}