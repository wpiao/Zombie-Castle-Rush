package com.zombiecastlerush.gui.component;

import com.zombiecastlerush.building.Puzzle;
import com.zombiecastlerush.building.Room;
import com.zombiecastlerush.util.Game;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class RiddleFactory implements Serializable {
    public static String answer = "";

    public static Puzzle generateRiddle(String name) {
        Map<String, Puzzle> riddles = new HashMap<>();
             Map<String, Room> rooms = Game.castle.getCastleRooms();

             for(Map.Entry<String,Room> room : rooms.entrySet()){
                 if (room.getValue().getChallenge() instanceof Puzzle){
                     riddles.put(room.getKey(),(Puzzle)room.getValue().getChallenge());
                 }
             }

            return riddles.get(name);
    }
}