package com.zombiecastlerush.util;

import com.zombiecastlerush.building.Castle;
import com.zombiecastlerush.entity.Player;
import org.junit.Test;

public class SaveLoadTest {
    @Test
    public void convertOjbectToNodeTest() {
        Castle castle = new Castle();
        Player player = new Player("Test");
        player.setCurrentPosition(castle.getCastleRooms().get("Castle-Hall"));
        SaveAndLoad.save(castle, player);
    }
}