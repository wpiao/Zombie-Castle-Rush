package com.zombiecastlerush.gui;


import javax.swing.*;

import asciiPanel.AsciiFont;
import asciiPanel.AsciiPanel;
import com.zombiecastlerush.gui.screens.Screen;
import com.zombiecastlerush.gui.screens.StartScreen;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class AppMain extends JFrame implements KeyListener {
    private final AsciiPanel terminal;
    private Screen screen;


    public AppMain(){
        super();
        terminal = new AsciiPanel(120,60,AsciiFont.TALRYTH_15_15);

        add(terminal);
        pack();
        screen = new StartScreen();
        addKeyListener(this);
        repaint();
    }


    public static void main(String[] args) {
        AppMain app = new AppMain();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);
    }

    private static String readWelcome(){
        String path = "Resources/Welcome/welcome.txt";
        String welcome = null;
        try{
            welcome = Files.readString(Paths.get(path));
         } catch (IOException e) {
            e.printStackTrace();
        }
        return welcome;
    }


    @Override
    public void repaint(){
        terminal.clear();
        screen.displayOutput(terminal);
        super.repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        screen = screen.respondToUserInput(e);
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}