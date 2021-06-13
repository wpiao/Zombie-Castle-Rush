package com.zombiecastlerush.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {

    final static List<String> ALLOWED_ACTIONS = Arrays.asList("go", "look", "pick-up", "drop", "attempt","display","buy","sell");

    public static List<String> parse(String input) {
        List<String> inputWords = Arrays.asList(input.toLowerCase().split(" "));
        List<String> result = ALLOWED_ACTIONS.contains(inputWords.get(0)) ? reduceInputWordsToList(inputWords) : null;
        System.out.println(result);
        return result;
    }

    public static List<String> reduceInputWordsToList(List<String> arr) {
        List<String> list = new ArrayList<>(arr);
        list.removeAll(Arrays.asList("", null, " "));  //removes empty and null elements
        list.replaceAll(x -> x.trim());
        return list;
    }
}