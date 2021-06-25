package com.zombiecastlerush.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {
    public static final String RED = "\033[0;31m";     // RED
    public static final String GREEN = "\u001B[32m"; //GREEN
    public static final String YELLOW = "\u001B[33m"; //YELLOW
    public static final String CYAN = "\u001B[36m";
    public static final String PURPLE = "\u001B[35m";
    public static final String BLUE = "\u001B[34m";
    public static final String ANSI_RESET = "\u001B[0m";

    final static List<String> ALLOWED_ACTIONS = Arrays.asList("use","go", "look", "pick-up", "drop", "attempt", "display", "quit", "buy", "sell", "fight", "save", "show");

    public static List<String> parse(String input) {
        List<String> inputWords = Arrays.asList(input.toLowerCase().split(" "));
        List<String> result = ALLOWED_ACTIONS.contains(inputWords.get(0)) ? reduceInputWordsToList(inputWords) : null;
        return result;
    }

    public static List<String> reduceInputWordsToList(List<String> arr) {
        List<String> list = new ArrayList<>(arr);
        list.removeAll(Arrays.asList("", null, " "));  //removes empty and null elements
        list.replaceAll(x -> x.trim());
        return list;
    }

    public static String insertLineBreaks(String text) {
        List<String> lines = new ArrayList<>();
        if (text.length() > 50) {
            int breakPoint = text.indexOf(" ", 40);
            String line = text.substring(0, breakPoint);
            lines.add(line);
            text = text.substring(breakPoint+1);
            text = insertLineBreaks(text);
        }
        lines.add(text);
        return String.join("\n", lines);
    }

}