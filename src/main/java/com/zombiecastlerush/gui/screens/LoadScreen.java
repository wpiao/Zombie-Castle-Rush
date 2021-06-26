package com.zombiecastlerush.gui.screens;

import java.awt.event.KeyEvent;
import java.io.*;
import java.lang.reflect.InvocationTargetException;

import asciiPanel.AsciiPanel;
import com.zombiecastlerush.gui.entity.Creature;

public class LoadScreen implements Screen {

    public void displayOutput(AsciiPanel terminal) {
        String prompt = new File("Resources/savedData.zombie").exists() ? "  -- press [SPACE] to reload  --" : "";
        terminal.writeCenter("-- press [ENTER] to start new game --" + prompt, terminal.getHeightInCharacters() / 2);
    }

    public Screen respondToUserInput(KeyEvent key) {

        if (key.getKeyCode() == KeyEvent.VK_ENTER) {
            return new StartScreen();
        } else if (key.getKeyCode() == KeyEvent.VK_SPACE && new File("Resources/savedData.zombie").exists()) {

            Creature player = null;
            try {
                FileInputStream fileIn = new FileInputStream("Resources/savedData.zombie");
                ObjectInputStream in = new ObjectInputStream(fileIn);
                player = (Creature) in.readObject();
                in.close();
                fileIn.close();

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            String name = player.world().name();

            try {

                return (Screen) Class.forName("com.zombiecastlerush.gui.screens." + name).getDeclaredConstructor(Creature.class).newInstance(player);
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }


        }
            return this;


    }
}