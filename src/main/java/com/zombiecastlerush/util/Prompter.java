package com.zombiecastlerush.util;

import com.zombiecastlerush.building.Room;
import com.zombiecastlerush.entity.Player;

import java.util.List;
import java.util.Scanner;
/**
 * static class and methods
 * interacting between users and this game
 * TODO: deploy APIs that supports the web game version
 */
public class Prompter {
    static String getUserInput(String displayMessage , String... args) {
        System.out.println(displayMessage);
        Scanner sc = new Scanner(System.in);
        String result = sc.nextLine();
        return result;
    }

    static void movePlayer(Player player) {
        List<Room> availableRooms = player.getCurrentPosition().getConnectedRooms();
        String moveString = Prompter.getUserInput("\nYou are in " + player.getCurrentPosition() + "\n\nTo go somewhere, please type go and one of the following available locations: " + availableRooms);
        List<String> moveList = Parser.parse(moveString);
        if (moveList != null) {
            player.moveTo(moveList.get(1));
        }
    }
}
