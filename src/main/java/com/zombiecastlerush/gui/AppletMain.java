package com.zombiecastlerush.gui;
import java.applet.Applet;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import asciiPanel.AsciiPanel;
import com.zombiecastlerush.gui.screens.Screen;
import com.zombiecastlerush.gui.screens.StartScreen;

public class AppletMain extends Applet implements KeyListener {

    private AsciiPanel terminal;
    private Screen screen;

    public AppletMain(){
        super();
        terminal = new AsciiPanel();
        terminal.write("welcome to zombie castle rush", 1, 1);
        add(terminal);
        screen = new StartScreen();
        addKeyListener(this);
        repaint();
    }

    public void init(){
        super.init();
        this.setSize(terminal.getWidth() + 20, terminal.getHeight() + 20);
    }

    public void repaint(){
        terminal.clear();
        screen.displayOutput(terminal);
        terminal.repaint();
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