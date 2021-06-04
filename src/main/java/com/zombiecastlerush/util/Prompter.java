package com.zombiecastlerush.util;

import java.util.Scanner;
/**
 * static class and methods
 * interacting between users and this game
 * TODO: deploy APIs that supports the web game version
 */
public class Prompter {
    static String getUserInput(String displayMessage , String... args){
        System.out.println(displayMessage);
        Scanner sc = new Scanner(System.in);
        String result = sc.nextLine();
        return result;
    }
}
