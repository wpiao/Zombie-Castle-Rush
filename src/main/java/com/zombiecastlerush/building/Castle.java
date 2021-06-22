package com.zombiecastlerush.building;

import java.util.*;

public class Castle {
    //create a map of rooms in castle
    private Map<String, Room> castleRooms = new HashMap<>();

    //Ctor
    public Castle() {
        // create rooms
        Room eastWing = new Room("East-Wing", "Another box is here. Sounds like the moans of a man in agony grow louder as you venture deeper into this room.");
        Room westWing = new Room("West-Wing", "Eerily quiet, only a box awaits you in this chamber.");
        Room castleHall = new Room("Castle-Hall", "It is cold, dark, and empty, save for a dimly lit, white box. ");
        Room drawBridge = new Room("Draw-Bridge", "The bridge is up, and there is no way to the other side. Nothing about the giant, open castle doors looks inviting, but alas, it is the only way forward. A box lays on the ground right before the doorway.");
        Room combatHall = new Room("Combat-Hall", "Festooned with the arms and armor of warriors past, this room is better lit than the others. In the middle of the room, a single coffin has been left slightly open, its lid closed just enough to obscure the contents from view");
        Shop shop = new Shop("Shop", "A strangely silent shopkeeper seems to preside over a collection of wares, oblivious or indifferent to your presence.");

        //add connected rooms to room
        eastWing.addConnectedRooms(castleHall, combatHall);
        castleHall.addConnectedRooms(drawBridge, eastWing, westWing, shop);
        drawBridge.addConnectedRooms(westWing, castleHall);
        westWing.addConnectedRooms(castleHall, drawBridge);
        combatHall.addConnectedRooms(eastWing);
        shop.addConnectedRooms(castleHall);

        //add Challenge to room
        eastWing.setChallenge(new Puzzle("East-Wing-Puzzle", "What is (2+2) X (2-2)?", "0"));
        eastWing.getChallenge().getInventory().addItems(new Item("Knife", "This is a knife", 25.0),new Item("Sword", "The Sword of Light", 100));
        westWing.setChallenge(new Puzzle("West-Wing-Puzzle", "What is (2+2) X (2-2)?", "0"));
        westWing.getChallenge().getInventory().addItems(new Item("Spoon", "This is a spoon", 25.0));
        castleHall.setChallenge(new Puzzle("Castle-Hall-Puzzle", "What is (2+2) X (2-2)?", "0"));
        castleHall.getChallenge().getInventory().addItems(new Item("Fork", "This is a fork", 5.0));
        drawBridge.setChallenge(new Puzzle("Draw-Bridge-Puzzle", "What is (2+2) X (2-2)?", "0"));
        drawBridge.getChallenge().getInventory().addItems(new Item("Vase", "This is a vase", 5.0));
        combatHall.setChallenge(new Combat("Life or Death Battle"));
        combatHall.setExit(true);


        //add items to Rooms inventory
        shop.getInventory().addItems(
                new Item("Sword", "This is the sword of Destiny, made up of the Valyrian Steel", 100.0),
                new Item("Helmet", "This is the ultimate shield which will be carried by Captain America in the distant future", 50.0),
                new Item("Potion", "Drinking this potion will restore your health", 100.0)
        );

        //Add rooms to castleRooms
        castleRooms.put(eastWing.getName(), eastWing);
        castleRooms.put(westWing.getName(), westWing);
        castleRooms.put(castleHall.getName(), castleHall);
        castleRooms.put(drawBridge.getName(), drawBridge);
        castleRooms.put(combatHall.getName(), combatHall);
        castleRooms.put(shop.getName(), shop);
        //Add room names to Room.connectedRoomNames
        for (Room room : castleRooms.values()) {
            room.addConnectedRoomNames();
        }
    }

    //getter
    public Map<String, Room> getCastleRooms() {
        return castleRooms;
    }

    @Override
    public String toString() {
        return castleRooms.keySet().toString();
    }
}