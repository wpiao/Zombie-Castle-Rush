package com.zombiecastlerush.util;

import java.util.Scanner;

class Inputs {
    public static String getUserInput(String displayMessage) {
        System.out.printf(displayMessage + "\n> ");
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }
}