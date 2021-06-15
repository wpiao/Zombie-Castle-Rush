package com.zombiecastlerush.util;

import com.zombiecastlerush.building.Combat;
import com.zombiecastlerush.building.Room;
import com.zombiecastlerush.entity.Enemy;
import com.zombiecastlerush.building.Shop;
import com.zombiecastlerush.entity.Player;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.zombiecastlerush.building.Item;
import com.zombiecastlerush.building.Puzzle;
import com.zombiecastlerush.entity.Role;

import java.util.List;
import java.util.Scanner;

/**
 * static class and methods
 * interacting between users and this game
 * TODO: deploy APIs that supports the web game version
 */
public class Prompter {
    public static String getUserInput(String displayMessage) {
        System.out.println(displayMessage);
        Scanner sc = new Scanner(System.in);
        String result = sc.nextLine();
        return result;
    }

    static void advanceGame(Player player) throws JsonProcessingException {
        displayCurrentScene(player);
        Room currentRoom = player.getCurrentPosition();
        String userInput = Prompter.getUserInput("\nEnter \"help\" if you need help with the commands");
        List<String> userInputList = Parser.parse(userInput);
        clearScreen();

        if (userInputList != null) {
            String action = userInputList.get(0);

            switch (userInputList.size()) {
                case 2:
                    switch (action) {
                        case "go":
                            player.moveTo(userInputList.get(1));
                            break;
                        case "attempt":
                            if (userInputList.get(1).equals("puzzle")) {
                                if (currentRoom.getChallenge() != null && currentRoom.getChallenge() instanceof Puzzle && !currentRoom.getChallenge().isCleared()) {
                                    getUserInput("\nYou've dared to attempt the Puzzle... press enter to solve the Puzzle");
                                    solvePuzzle(currentRoom);
                                } else
                                    System.out.println("There is no puzzle in the room");
                            }
                            break;
                        case "display":
                            if (userInputList.get(1).equalsIgnoreCase("status"))
                                System.out.println(player.displayStatus());
                            break;
                        case "pick-up":
                            for (Item item : currentRoom.getInventory().getItems()) {
                                if (item.getName().equalsIgnoreCase(userInputList.get(1))) {
                                    player.pickUp(item);
                                    break;
                                }
                            }
                            break;
                        case "drop":
                            for (Item item : player.getInventory().getItems()) {
                                if (item.getName().equalsIgnoreCase(userInputList.get(1))) {
                                    player.drop(item);
                                    break;
                                }
                            }
                        case "buy":
                            if (currentRoom instanceof Shop) {
                                for (Item item : currentRoom.getInventory().getItems()) {
                                    if (item.getName().equalsIgnoreCase(userInputList.get(1))) {
                                        ((Shop) currentRoom).sellItemToPlayer(player, item);
                                        break;
                                    }
                                }
                            }
                            break;
                        case "sell":
                            if (currentRoom instanceof Shop) {
                                for (Item item : player.getInventory().getItems()) {
                                    if (item.getName().equalsIgnoreCase(userInputList.get(1))) {
                                        ((Shop) currentRoom).buyItemFromPlayer(player, item);
                                        break;
                                    }
                                }
                            }
                            break;
                    }

                case 1:
                    switch (action) {
                        case "fight":

                            if (!currentRoom.getChallenge().isCleared() && userInputList.get(0).equals("fight")) {
                                if (currentRoom.getChallenge() != null && currentRoom.getChallenge() instanceof Combat && !currentRoom.getChallenge().isCleared()) {
                                    getUserInput("\nPrepare for COMBAT... press enter to continue");
                                    combat(player, new Enemy("Zombie"), action);
                                } else {
                                    System.out.println("There is no Monster in the room");
                                    break;
                                }
                            } else {
                                System.out.println("There is no Monster in the room");
                                break;
                            }
                            break;
                        case "quit":
                            Game.getInstance().stop();
                            break;
                    }
                    break;
            }
        } else {
            Game.getInstance().showInstructions();
        }
    }

    static void solvePuzzle(Room room) {
        Puzzle puzzle = (Puzzle) room.getChallenge();
        System.out.println(Parser.YELLOW + "Here is your puzzle....Remember you only have " + (3 - puzzle.getAttempts()) + " tries!" + Parser.ANSI_RESET);
        puzzle.attemptPuzzle(getUserInput(puzzle.getQuestion()));
        if (puzzle.getAttempts() < 3 && !puzzle.isCleared())
            solvePuzzle(room);
        else if (puzzle.isCleared()) {
            System.out.println(Parser.GREEN + "Right answer. You can now move to the available rooms");
            if (puzzle.getInventory().getItems().size() > 0) {
                System.out.println(puzzle.getDescription() + " drops " + puzzle.getInventory().toString() + "\n" + Parser.ANSI_RESET);
                puzzle.getInventory().transferItem(
                        puzzle.getInventory(),
                        room.getInventory(),
                        puzzle.getInventory().getItems().toArray(new Item[0])
                );
            }
        } else {
            System.out.println(Parser.RED + "Wrong Answer!! You have had your chances...You failed...Game Over!!!" + Parser.ANSI_RESET);
            Game.getInstance().stop();
        }
    }

    public static void combat(Role player, Role enemy, String action) {
        var cleared = player.getCurrentPosition().getChallenge().isCleared();
        if (!cleared) {
            Combat.combat(player, enemy);
            while (player.getHealth() > 0 && enemy.getHealth() > 0) {
                String msg = "what would you like to do, \"fight\" or \"run\"?";
                String combatChoice = Prompter.getUserInput(msg);
                if (combatChoice.equals("fight")) {
                    Combat.combat(player, enemy);
                } else if (combatChoice.equals("run")) {
                    System.out.println("don't be a coward");
                    Combat.enemyFight(player, enemy);
                }
            }
            if (enemy.getHealth() <= 0 || player.getHealth() <= 0) {
                Room currentPosition = player.getCurrentPosition();
                if (player.getHealth() <= 0) {
                    Prompter.getUserInput("You are dead. Press Enter to continue.");
                    Game.getInstance().stop();
                }
                currentPosition.getChallenge().setCleared(true);
                if (enemy.getHealth() <= 0) {
                    if (currentPosition.isExit()) {
                        Prompter.getUserInput("You have found the exit, killed the last monster, and beaten the game! Press Enter to continue");
                        Game.getInstance().stop();
                    }
                }

            }

        } else {
            System.out.println("Room has no Enemy");
        }
    }

    public static void displayCurrentScene(Player player) {
        Room currentRoom = player.getCurrentPosition();
        List<Room> availableRooms = currentRoom.getConnectedRooms();
        int numItemsInRoom = currentRoom.getInventory().getItems().size();
        String numItemsString = numItemsInRoom > 0 ? numItemsInRoom + " items." : "0 items.";

        System.out.println("You are in the " + currentRoom + ". " + currentRoom.getDescription());
        if (currentRoom.getChallenge() != null && !currentRoom.getChallenge().isCleared()) {
            String currRoomChallenge = currentRoom.getChallenge().getDescription();
            System.out.println("The Room has " + currRoomChallenge + " and " + numItemsString);
            System.out.println("After the " + currRoomChallenge + " is solved, you can go to one of the following available locations: " + availableRooms);
        } else {
            String roomInventory = currentRoom.getInventory().toString();
            if (currentRoom instanceof Shop)
                roomInventory = ((Shop) currentRoom).toStringShopInventory() + "\nYou've $" + player.getAcctBalance();
            System.out.println("The " + currentRoom.getClass().getSimpleName() + " has " + numItemsString + " " + roomInventory +
                    "\nYou have the following items: " + player.getInventory().toString() +
                    "\nYou can go to one of the following locations " + availableRooms);
        }
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();

    }
}