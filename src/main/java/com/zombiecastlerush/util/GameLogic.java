package com.zombiecastlerush.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zombiecastlerush.building.*;
import com.zombiecastlerush.entity.Enemy;
import com.zombiecastlerush.entity.Player;
import com.zombiecastlerush.entity.Role;

import java.util.List;

import static com.zombiecastlerush.building.MapOfGame.readMap;

class GameLogic {
    static void advanceGame(Player player) throws JsonProcessingException {
        Prompter.displayCurrentScene(player);
        Room currentRoom = player.getCurrentPosition();
        String userInput = Inputs.getUserInput(Parser.BLUE+"Enter \"help\" if you need help with the commands"+ Parser.ANSI_RESET);
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
                                    Inputs.getUserInput(Parser.GREEN+"\nYou touch your hands to the box and cannot let go. You feel that the box demands you answer its question. You do not know how or why you are compelled, but you are.\nPress Enter to solve the Puzzle." + Parser.ANSI_RESET);
                                    solvePuzzle(currentRoom);
                                } else
                                    System.out.println("There is no puzzle in the room");
                            }
                            break;
                        case "show":
                            if(userInputList.get(1).equals("map")){
                                readMap();
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
                                    Inputs.getUserInput(Parser.CYAN+"\nPrepare for COMBAT... press enter to continue" + Parser.ANSI_RESET);
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
                        case "save":
                            Game.getInstance().save();
                            break;
                        case "quit":
                            Game.getBackgroundMusic().close();
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
        puzzle.attemptPuzzle(Inputs.getUserInput(Parser.YELLOW+puzzle.getQuestion() + Parser.ANSI_RESET));
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
            Game.getBackgroundMusic().close();
            Game.getInstance().stop();
        }
    }

    public static void combat(Role player, Role enemy) {
        var cleared = player.getCurrentPosition().getChallenge().isCleared();
        if (!cleared) {
            Combat.combat(player, enemy);
            while (player.getHealth() > 0 && enemy.getHealth() > 0) {
                String msg =Parser.YELLOW +"what would you like to do, \"fight\" or \"run\"?" + Parser.ANSI_RESET;
                String combatChoice = Inputs.getUserInput(msg).toLowerCase();
                if (combatChoice.equals("fight")) {
                    Combat.combat(player, enemy);
                } else if (combatChoice.equals("run")) {
                    System.out.println(Parser.RED +"don't be a coward" + Parser.ANSI_RESET);
                    Combat.enemyAttack(player, enemy);
                }
            }
            if (enemy.getHealth() <= 0 || player.getHealth() <= 0) {
                Room currentPosition = player.getCurrentPosition();
                if (player.getHealth() <= 0) {
                    System.out.println(Parser.RED+"You are dead."+ Parser.ANSI_RESET);
                    MapOfGame.loseArt();
                    Game.getBackgroundMusic().close();
                    Game.getInstance().stop();
                }
                currentPosition.getChallenge().setCleared(true);
                if (enemy.getHealth() <= 0) {
                    if (currentPosition.isExit()) {
                        System.out.println(Parser.CYAN+"You have found the exit, killed the last monster, and beaten the game!" + Parser.ANSI_RESET);
                        MapOfGame.winArt();
                        Game.getBackgroundMusic().close();
                        Game.getInstance().stop();
                    }
                }

            }

        } else {
            System.out.println("Room has no Enemy");
        }
    }
}