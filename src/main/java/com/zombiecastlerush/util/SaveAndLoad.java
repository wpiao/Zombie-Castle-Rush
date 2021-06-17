package com.zombiecastlerush.util;

// import Jackson

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zombiecastlerush.building.Castle;
import com.zombiecastlerush.entity.Player;

/**
 * Writes the current state of a Castle and Player object to a save.json file.
 * Also reads that file to recreate the objects.
 */
class SaveAndLoad {
    private static final String saveLocation = "/save.json";

    static void save(Castle castle, Player player) {

    }
}