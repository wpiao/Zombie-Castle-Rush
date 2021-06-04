package com.zombiecastlerush.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {

    final static List<String> ALLOWED_ACTIONS = Arrays.asList("go", "look", "pick-up", "drop");

    public static List<String> parse(String input){
        List<String> inputWords = Arrays.asList(input.toLowerCase().split(" "));
        System.out.println(ALLOWED_ACTIONS.contains(inputWords.get(0)) ? reduceInputWordsToList(inputWords) : null);
        return ALLOWED_ACTIONS.contains(inputWords.get(0)) ? reduceInputWordsToList(inputWords) : null;
    }

    public static List<String> reduceInputWordsToList(List<String>  arr){
        List<String> list = new ArrayList<>(arr);
        list.removeAll(Arrays.asList("", null, " "));  //removes empty and null elements
        list.replaceAll(x -> x.trim());
        return list;
    }
}