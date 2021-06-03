package com.zombiecastlerush.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {
    public static void main(String[] args) throws Exception {
        new Parser().parse("Go North");
    }

    public static List<String> parse(String input){
        List<String> actions = new ArrayList<>(Arrays.asList("go", "look", "pick-up", "drop"));
        List<String> commands = new ArrayList<>(Arrays.asList(input.toLowerCase().split(" ")));
        System.out.println(actions.contains(commands.get(0)) ? reduceArray((ArrayList<String>) commands) : null);
        return actions.contains(commands.get(0)) ? reduceArray((ArrayList<String>) commands) : null;
    }

    public static ArrayList<String> reduceArray(ArrayList<String>  arr){
        List<String> list = new ArrayList<String>(arr);
        list.removeAll(Arrays.asList("", null, " "));  //removes empty and null elements
        list.replaceAll(x -> x.trim());
        return (ArrayList<String>) list;
    }
}