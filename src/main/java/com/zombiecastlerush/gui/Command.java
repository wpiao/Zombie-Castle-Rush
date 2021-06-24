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
        if (input != null && input.length() != 0) {
            List<String> commands = Parser.parse(input);
            if (commands != null) {
                String action = commands.get(0).toLowerCase();

                switch (commands.size()) {
                    case 2:
                        switch (action) {
                            case "attempt":
                                List<String> puzzleSynonym = Arrays.asList("puzzle", "riddle", "question");
                                if (puzzleSynonym.contains(commands.get(1))) { return 1; }
                                break;

                        }


                }
            }
        }
        return -1;
    }

}
