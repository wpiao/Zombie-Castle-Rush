package com.zombiecastlerush.gui;

import asciiPanel.AsciiPanel;
import com.zombiecastlerush.util.Parser;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;


public class Command {

    public static String command = "";
    public static List<String> parsedCommands;


    public static void type(KeyEvent key, AsciiPanel terminal, int x, int y) {
        List<Integer> arrowList = Arrays.asList(KeyEvent.VK_UP,KeyEvent.VK_DOWN,KeyEvent.VK_LEFT,KeyEvent.VK_RIGHT);
        if (key != null) {
            int keyCode = key.getKeyCode();
            // common ASCII characters, including letters and numbers. 10 is return and 8 is backspace.
            if (keyCode <= 127 && keyCode != 10) {
                if (keyCode == 8 && command.length() > 0) {
                    //if pressed backspace, delete last character.
                    command = command.substring(0, command.length() - 1);
                } else if (keyCode == 8) {
                    //if command is already empty, press backspace to reset.
                    command = "";
                } else if (keyCode >= 32 && (!arrowList.contains(keyCode))){
                    command+=(char)keyCode;
                }
                terminal.write(command, x, y, Color.MAGENTA);
            }
        }
    }

    public static int choice(String input) {
        int indicator = -1;
        if (input != null && input.length() != 0) {
            parsedCommands = Parser.parse(input);
            if (parsedCommands != null) {
                String action = parsedCommands.get(0).toLowerCase();

                switch (parsedCommands.size()) {
                    case 1:
                        switch (action){
                            case "quit":
                                indicator = 99;
                                break;
                            case "save":
                                indicator = 98;
                                break;
                        }
                        break;
                    case 2:
                        switch (action) {
                            case "attempt":
                                List<String> puzzleSynonym = Arrays.asList("puzzle", "riddle", "question");
                                if (puzzleSynonym.contains(parsedCommands.get(1)))
                                    indicator = 1;
                                break;
                            case "drop":
                                indicator = 2;
                                break;
                            case "pick-up":
                                indicator = 3;
                                break;
                            case "buy":
                                indicator = 4;
                                break;
                            case "sell":
                                indicator = 5;
                                break;
                            case "use":
                                indicator = 6;
                                break;
                        }
                        break;
                }
            }
        }
        return indicator;
    }

}
