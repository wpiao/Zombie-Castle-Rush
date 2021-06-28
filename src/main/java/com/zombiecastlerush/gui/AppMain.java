package com.zombiecastlerush.gui;
import javax.swing.*;

import asciiPanel.AsciiFont;
import asciiPanel.AsciiPanel;
import com.zombiecastlerush.gui.screens.LoadScreen;
import com.zombiecastlerush.gui.screens.Screen;
import com.zombiecastlerush.gui.screens.StartScreen;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class AppMain extends JFrame implements KeyListener {
    private final AsciiPanel terminal;
    private Screen screen;


    public AppMain(){
        super();
        terminal = new AsciiPanel(120,60,AsciiFont.TALRYTH_15_15);
        add(terminal);
        pack();
        screen = new LoadScreen();
        addKeyListener(this);
        repaint();
    }

    @Override
    public void repaint(){
        terminal.clear();
        screen.displayOutput(terminal);
        super.repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        screen = screen.respondToUserInput(e);
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}