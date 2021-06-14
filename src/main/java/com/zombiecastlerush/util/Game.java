package com.zombiecastlerush.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zombiecastlerush.building.Castle;
import com.zombiecastlerush.entity.Player;

/**
 * singleton class Game
 * it provides access to a Map and a role Controller
 */
public class Game {
    private static Game game;
    private Castle castle = new Castle();
    private Player player;

    private Game() {
    }

    public static Game getInstance() {
        if (Game.game == null) {
            Game.game = new Game();
        }
        return Game.game;
    }

    /**
     * TODO: What does start() provide?
     */
    public void start() throws JsonProcessingException {
        String userName = Prompter.getUserInput("Welcome to Zombie Castle Rush! \n\nPlease enter your name");
        player = new Player(userName);
        player.setCurrentPosition(castle.getCastleRooms().get("Castle-Hall"));
        showInstructions();

        while (true) {
            Prompter.advanceGame(player);
        }
    }

    /**
     * TODO: what does stop() provide?
     */
    public void stop() {
        System.out.println("Thank you for playing Zombie Castle Rush!");
        System.exit(0);
    }

    public void showInstructions() {
        System.out.println("\nGame Instructions:");
        System.out.printf(Parser.GREEN+"%2s %8s %43s %n", "", "Action   ", "       Command to Type"+Parser.ANSI_RESET);
        System.out.printf("%2s %8s %45s %n", "", "----------------------------", "         --------------------------------------------------");
        System.out.printf("%2s %-30s %1s %-10s %n", " 1.", "Go somewhere","|    ", "\"go\" and one of the available locations displayed");
        System.out.printf("%2s %-30s %1s %-10s %n", " 2.", "attempt a puzzle","|    ", "\"attempt puzzle\"");
        System.out.printf("%2s %-30s %1s %-1s %n", " 3.", "display player's status","|    ", "\"display status\"");
        System.out.printf("%2s %-30s %1s %-1s %n", " 4.", "pick-up or drop an item","|    ", "\"pick-up\", \"drop\" and \"item name\"");
        System.out.printf("%2s %-30s %1s %-1s %n", " 5.", "buy an item from the shop","|    ", "\"buy\" and \"item name\"");
        System.out.printf("%2s %-30s %1s %-1s %n", " 6.", "sell an item to the shop","|    ", "\"sell\" and \"item name");
        System.out.printf("%2s %-30s %1s %-1s %n", " 7.", "fight a monster","|    ", "\"fight\"");
        System.out.printf("%2s %-30s %1s %-1s %n", " 8.", "Go somewhere","|    ", "\"go\" and one of the available locations displayed");
        System.out.printf("%2s %-30s %1s %-1s %n", " 9.", "Go somewhere","|    ", "\"go\" and one of the available locations displayed");
        System.out.printf("%2s %-30s %1s %-1s %n", "10.", "display instructions","|    ", "\"help\"");
        System.out.printf("%2s %-30s %1s %-1s %n", "11.", "quit the game","|    ", "\"quit\"");

        System.out.println("\n1. To go somewhere, please type go and one of the available locations displayed");
        System.out.println("2. To attempt a puzzle, please type \"attempt puzzle\"");
        System.out.println("3. To display player's status, please type \"display status\"");
        System.out.println("4. To pick-up or drop an item, please type \"pick-up\", \"drop\" and \"item name\"");
        System.out.println("5. To buy an item from the shop, please type \"buy\" and \"item name\"");
        System.out.println("6. To sell an item to the shop, please type \"sell\" and \"item name");
        System.out.println("7. Enter \"help\" to display the commands");
        System.out.println("8. Enter \"quit\" to quit the game");
        Prompter.getUserInput("\nPress enter to continue...");
        Prompter.clearScreen();
    }
}