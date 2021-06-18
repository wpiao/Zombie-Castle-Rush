package com.zombiecastlerush.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zombiecastlerush.building.*;
import com.zombiecastlerush.entity.Enemy;
import com.zombiecastlerush.entity.Player;
import com.zombiecastlerush.entity.Role;

import java.util.List;

class GameLogic {
    static void advanceGame(Player player) throws JsonProcessingException {
        Prompter.displayCurrentScene(player);
        Room currentRoom = player.getCurrentPosition();
        String userInput = Inputs.getUserInput("Enter \"help\" if you need help with the commands");
        List<String> userInputList = Parser.parse(userInput);
        Prompter.clearScreen();

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
                                    Inputs.getUserInput("\nYou touch your hands to the box and cannot let go. You feel that the box demands you answer its question. You do not know how or why you are compelled, but you are.\nPress Enter to solve the Puzzle.");
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
                            if (!(currentRoom instanceof Shop)) {
                                for (Item item : currentRoom.getInventory().getItems()) {
                                    if (item.getName().equalsIgnoreCase(userInputList.get(1))) {
                                        player.pickUp(item);
                                        break;
                                    }
                                }
                            } else {
                                System.out.println(Parser.RED + "You can't do that here." + Parser.ANSI_RESET);
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
                                    Inputs.getUserInput("\nPrepare for COMBAT... press enter to continue");
                                    combat(player, new Enemy("Zombie"));
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
            Prompter.showInstructions();
        }
    }

    static void solvePuzzle(Room room) {
        Puzzle puzzle = (Puzzle) room.getChallenge();
        System.out.println(Parser.YELLOW + "Here is your puzzle....Remember you only have " + (3 - puzzle.getAttempts()) + " tries!" + Parser.ANSI_RESET);
        puzzle.attemptPuzzle(Inputs.getUserInput(puzzle.getQuestion()));
        if (puzzle.getAttempts() < 3 && !puzzle.isCleared())
            solvePuzzle(room);
        else if (puzzle.isCleared()) {
            System.out.println(Parser.GREEN + "Right answer. You can now move to the available rooms" + Parser.ANSI_RESET);
            if (puzzle.getInventory().getItems().size() > 0) {
                System.out.println(Parser.GREEN + puzzle.getDescription() + " drops " + puzzle.getInventory().toString() + "\n" + Parser.ANSI_RESET);
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

    public static void combat(Role player, Role enemy) {
        var cleared = player.getCurrentPosition().getChallenge().isCleared();
        if (!cleared) {
            Combat.combat(player, enemy);
            while (player.getHealth() > 0 && enemy.getHealth() > 0) {
                String msg = "what would you like to do, \"fight\" or \"run\"?";
                String combatChoice = Inputs.getUserInput(msg);
                if (combatChoice.equals("fight")) {
                    Combat.combat(player, enemy);
                } else if (combatChoice.equals("run")) {
                    System.out.println("don't be a coward");
                    Combat.enemyAttack(player, enemy);
                }
            }
            if (enemy.getHealth() <= 0 || player.getHealth() <= 0) {
                Room currentPosition = player.getCurrentPosition();
                if (player.getHealth() <= 0) {
                    Inputs.getUserInput("You are dead. Press Enter to continue.");
                    Game.getInstance().stop();
                }
                currentPosition.getChallenge().setCleared(true);
                if (enemy.getHealth() <= 0) {
                    if (currentPosition.isExit()) {
                        Inputs.getUserInput("You have found the exit, killed the last monster, and beaten the game! Press Enter to continue");
                        Game.getInstance().stop();
                    }
                }

            }

        } else {
            System.out.println("Room has no Enemy");
        }
    }

}