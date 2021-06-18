package com.zombiecastlerush.util;

import com.zombiecastlerush.building.*;
import com.zombiecastlerush.entity.Enemy;
import com.zombiecastlerush.entity.Player;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.zombiecastlerush.entity.Role;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * static class and methods
 * interacting between users and this game
 * TODO: deploy APIs that supports the web game version
 */
public class Prompter {
    static void showInstructions() {
        System.out.println("\nGame Instructions:");
        System.out.printf(Parser.GREEN+"%2s %8s %47s %n", "", "Action   ", "       Command to Type"+Parser.ANSI_RESET);
        System.out.printf("%2s %8s %45s %n", "", "----------------------------", "         --------------------------------------------------");
        System.out.printf("%2s %-30s %1s %-10s %n", " 1.", "Go somewhere","|    ", "\"go\" and one of the available locations displayed");
        System.out.printf("%2s %-30s %1s %-10s %n", " 2.", "attempt a puzzle","|    ", "\"attempt puzzle\"");
        System.out.printf("%2s %-30s %1s %-1s %n", " 3.", "display player's status","|    ", "\"display status\"");
        System.out.printf("%2s %-30s %1s %-1s %n", " 4.", "pick-up or drop an item","|    ", "\"pick-up\", \"drop\" and \"item name\"");
        System.out.printf("%2s %-30s %1s %-1s %n", " 5.", "buy an item from the shop","|    ", "\"buy\" and \"item name\"");
        System.out.printf("%2s %-30s %1s %-1s %n", " 6.", "sell an item to the shop","|    ", "\"sell\" and \"item name\"");
        System.out.printf("%2s %-30s %1s %-1s %n", " 7.", "fight a monster","|    ", "\"fight\"");
        System.out.printf("%2s %-30s %1s %-1s %n", " 8.", "display instructions","|    ", "\"help\"");
        System.out.printf("%2s %-30s %1s %-1s %n", " 9.", "quit the game","|    ", "\"quit\"");

        Inputs.getUserInput("\nPress enter to continue...");
        clearScreen();
    }

    public static void displayCurrentScene(Player player) {
        Room currentRoom = player.getCurrentPosition();
        List<Room> availableRooms = currentRoom.getConnectedRooms();
        int numItemsInRoom = currentRoom.getInventory().getItems().size();
        String itemClueText = Parser.GREEN + "pick-up" + Parser.ANSI_RESET + ":";
        if (currentRoom instanceof Shop) {
            itemClueText = Parser.GREEN + "buy" + Parser.ANSI_RESET + " or" + Parser.GREEN + " sell" + Parser.ANSI_RESET + ":";
        }
        String numItemsString = numItemsInRoom > 0 ? numItemsInRoom + " item(s) in here which you can " + itemClueText : "";

        System.out.println("You are in the " + currentRoom + ". " + currentRoom.getDescription() + "\n");
        if (currentRoom.getChallenge() != null && !currentRoom.getChallenge().isCleared()) {
            Challenge currRoomChallenge = currentRoom.getChallenge();
            if (currRoomChallenge instanceof Puzzle) {
                System.out.println("The box pulses with power. You know not how, but it has a riddle for you, and it will not let you leave until you have solved it. Perhaps you should " + Parser.GREEN + "attempt puzzle" + Parser.ANSI_RESET + ".");
            } else if (currRoomChallenge instanceof Combat) {
                System.out.println("A rotting hand reaches and knocks the lid to the ground with a resounding crash. A monster rises from the coffin and fixes its lifeless, pitiless gaze upon you. It's time to " + Parser.GREEN + "fight" + Parser.ANSI_RESET + ".");
            }
        } else {

            String roomInventory = currentRoom.getInventory().toString();
            if (currentRoom instanceof Shop) {
                roomInventory = ((Shop) currentRoom).toStringShopInventory() + "\nYou have $" + player.getAcctBalance();
            }
            System.out.println(numItemsString + " " + roomInventory);

            if (player.getInventory().getItems().size() > 0) {
                String dropOrSellText = (currentRoom instanceof Shop) ? "sell" : "drop";
                System.out.println("You have the following items that you can " + Parser.GREEN + dropOrSellText + Parser.ANSI_RESET + ": " + player.getInventory().toString());
            }
            System.out.println("\nYou can " + Parser.GREEN + "go" + Parser.ANSI_RESET + " to one of the following locations " + availableRooms);
        }
        System.out.print("\nActions applicable: " + sceneContextmenu(currentRoom, player) + "  ");

    }

    public static List<String> sceneContextmenu(Room room, Player player) {
        List<String> actionApplicable = new ArrayList<>(Arrays.asList(
                Parser.GREEN + "go" + Parser.ANSI_RESET,
                Parser.GREEN + "display status" + Parser.ANSI_RESET,
                Parser.GREEN + "help" + Parser.ANSI_RESET,
                Parser.GREEN + "quit" + Parser.ANSI_RESET));

        if (room.getChallenge() instanceof Puzzle && !room.getChallenge().isCleared())
            actionApplicable.add(Parser.GREEN + "attempt puzzle" + Parser.ANSI_RESET);
        if (room instanceof Shop) {
            actionApplicable.add(Parser.GREEN + "buy" + Parser.ANSI_RESET);
            if (player.getInventory().getItems().size() > 0)
                actionApplicable.add(Parser.GREEN + "sell" + Parser.ANSI_RESET);
        }
        if (!(room instanceof Shop) && room.getInventory().getItems().size() > 0) {
            actionApplicable.add(Parser.GREEN + "pick-up" + Parser.ANSI_RESET);
        }
        if (!(room instanceof Shop) && player.getInventory().getItems().size() > 0) {
            actionApplicable.add(Parser.GREEN + "drop" + Parser.ANSI_RESET);
        }
        if (room.getName().equalsIgnoreCase("Combat-Hall") && !room.getChallenge().isCleared())
            actionApplicable.add(Parser.GREEN + "fight" + Parser.ANSI_RESET);
        return actionApplicable;
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();

    }
}