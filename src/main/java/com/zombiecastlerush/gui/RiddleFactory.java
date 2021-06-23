package com.zombiecastlerush.gui;

import com.zombiecastlerush.building.Challenge;
import com.zombiecastlerush.building.Puzzle;
import com.zombiecastlerush.building.Room;
import com.zombiecastlerush.gui.screens.Screen;
import com.zombiecastlerush.util.Game;

import java.util.HashMap;
import java.util.Map;

public class RiddleFactory {
    public static String answer = "";

    public static Puzzle generateRiddle(String name) {
        Map<String, Puzzle> riddles = new HashMap<>();
             Map<String, Room> rooms = Game.castle.getCastleRooms();

             for(Map.Entry<String,Room> room : rooms.entrySet()){
                 if (room.getValue().getChallenge() instanceof Puzzle){
                     riddles.put(room.getKey(),(Puzzle)room.getValue().getChallenge());
                 }
             }
            //placeholder for riddles from external files.
            return riddles.get(name);
    }
}