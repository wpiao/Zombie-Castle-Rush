package com.zombiecastlerush.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zombiecastlerush.building.*;
import com.zombiecastlerush.role.Player;

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

    static void controller(Player player) throws JsonProcessingException {
        Room currentRoom = player.getCurrentPosition();
        List<Room> availableRooms = currentRoom.getConnectedRooms();
        int numItemsInRoom = currentRoom.getInventory().getItems().size();
        String itemsInRoomString = numItemsInRoom > 0 ? numItemsInRoom + " items." : "0 items.";

        System.out.println("You are in " + currentRoom + ". "+ currentRoom.getDescription());
        if(currentRoom.getChallenge()!=null && !currentRoom.getChallenge().isCleared()){
            String currRoomChallenge = currentRoom.getChallenge().getDescription();
            System.out.println("The room has " + currRoomChallenge + " and " + itemsInRoomString);
            System.out.println("After the "+ currRoomChallenge +" is solved, you can go to one of the following available locations: " + availableRooms);
        }
        else {
            System.out.println("The room has " + itemsInRoomString+"\nYou can go to one of the following locations " + availableRooms);
        }

        String userInput = Prompter.getUserInput("\nEnter \"help\" if you need help with the commands");
        List<String> userInputList = Parser.parse(userInput);
        if (userInputList!=null && userInputList.size()==2) {
            String action =userInputList.get(0);
            switch (action){
                case "go":
                    player.moveTo(userInputList.get(1));
                    break;
                case "attempt":
                    if(userInputList.get(1).equals("puzzle")){
                        getUserInput("\nYou've dared to attempt the Puzzle.....Press enter to continue");
                        attemptPuzzle(currentRoom);
                    }
                    break;
                case "display":
                    if(userInputList.get(1).equalsIgnoreCase("status"))
                        System.out.println(player.displayStatus());
                    break;
            }
        }
        else
            Game.getInstance().showInstructions();
    }

    static void attemptPuzzle(Room room){
        Puzzle puzzle = (Puzzle) room.getChallenge();
        System.out.println("Here is your puzzle....Remember you only have " + (3-puzzle.getAttempts()) + " tries!");
        puzzle.attemptPuzzle(getUserInput(puzzle.getQuestion()));
        if(puzzle.getAttempts()<3 && !puzzle.isCleared())
            attemptPuzzle(room);
        else if(puzzle.isCleared()) {
            System.out.println("Right answer. You can now move to the available rooms");
            if(puzzle.inventory.getItems().size()>0){
                System.out.println( puzzle.getDescription() + " drops " + puzzle.inventory.toString() +"\n");
                puzzle.inventory.transferItem(puzzle.inventory,room.inventory,puzzle.inventory.getItems().toArray(new Item[0]));
            }
        }
        else {
            System.out.println("Wrong Answer!! You have had your chances...You failed...Game Over!!!");
            Game.getInstance().stop();
        }


    }
}