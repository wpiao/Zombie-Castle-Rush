package com.zombiecastlerush.gui.screens;

import java.awt.*;
import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;
import com.zombiecastlerush.gui.World;
import com.zombiecastlerush.gui.WorldBuilder;

public class PlayScreen implements Screen {

    private World world;
    private int centerX;
    private int centerY;
    private int screenWidth;
    private int screenHeight;

    public PlayScreen() {
        screenWidth = 90;
        screenHeight = 51;
        createWorld();
    }

    private void createWorld() {
        world = new WorldBuilder(100, 51)
                .makeFloor()
                .build();
    }


    public void displayOutput(AsciiPanel terminal) {
        int left = getScrollX();
        int top = getScrollY();

        //playground
        displayTiles(terminal, left, top);
        //status
        displayStatus(terminal, screenWidth+1, 0);
        //inventory
        displayInventory(terminal, screenWidth+1, (screenHeight-screenHeight%3)/3);
        //display map
        displayMap(terminal, screenWidth+1, (screenHeight-screenHeight%3)*2/3);
        //prompt
        displayDescription(terminal,0,screenHeight);
        //user input
        displayUserInput(terminal,0,terminal.getHeightInCharacters()-3);

        terminal.write('X', centerX - left, centerY - top);


    }


    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_H:
                scrollBy(-1, 0);
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_L:
                scrollBy(1, 0);
                break;
            case KeyEvent.VK_UP:
            case KeyEvent.VK_K:
                scrollBy(0, -1);
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_J:
                scrollBy(0, 1);
                break;

        }

        return this;
    }

    public int getScrollX() {
        return Math.max(0, Math.min(centerX - screenWidth / 2, world.width() - screenWidth));
    }

    public int getScrollY() {
        return Math.max(0, Math.min(centerY - screenHeight / 2, world.height() - screenHeight));
    }

    private void scrollBy(int mx, int my) {
        centerX = Math.max(0, Math.min(centerX + mx, world.width() - 1));
        centerY = Math.max(0, Math.min(centerY + my, world.height() - 1));
    }

    private void displayTiles(AsciiPanel terminal, int left, int top) {
        for (int x = 0; x < screenWidth; x++) {
            for (int y = 0; y < screenHeight; y++) {
                int wx = x + left;
                int wy = y + top;

                terminal.write(world.glyph(wx, wy), x, y, world.color(wx, wy));
            }
        }
    }

    private void displayStatus(AsciiPanel terminal, int right, int top) {
        int length = terminal.getWidthInCharacters() - screenWidth - 2;
        terminal.write(drawLine(length), right, top, Color.ORANGE);
        terminal.write("Status", right, top + 1, Color.green);
        terminal.write("placeholder", right, top + 2, Color.magenta);

    }


    private void displayInventory(AsciiPanel terminal, int right, int middle) {
        int length = terminal.getWidthInCharacters() - screenWidth - 2;
        terminal.write(drawLine(length), right, middle, Color.ORANGE);
        terminal.write("Inventory", right, middle + 1, Color.green);
        terminal.write("placeholder", right, middle + 2, Color.magenta);
    }

    private void displayMap(AsciiPanel terminal, int right, int bottom) {
        int length = terminal.getWidthInCharacters() - screenWidth - 2;
        terminal.write(drawLine(length),right,bottom,Color.orange);
        int height = terminal.getHeightInCharacters();

        for (int i = 0; i < height; i++) {
            terminal.write("|", right-1, i, Color.orange);
        }
        terminal.write("Map",right,bottom+1,Color.green);
        terminal.write("placeholder",right,bottom + 2,Color.magenta);
    }

    private void displayUserInput(AsciiPanel terminal, int left, int i) {
        terminal.write(drawLine(screenWidth),left,i,Color.orange);
        terminal.write("Enter command -> ",left,i+1,Color.red);
    }

    private void displayDescription(AsciiPanel terminal, int left, int bottom) {
        terminal.write("Prompt placeholder",left,bottom+1,Color.RED );
        terminal.write(" ",left,bottom+2,Color.red);
    }

    private String drawLine(int length) {

        String line = "";
        for (int i = 0; i < length; i++) {
            line += "-";
        }
        return line;
    }


}