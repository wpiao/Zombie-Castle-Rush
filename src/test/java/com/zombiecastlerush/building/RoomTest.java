package com.zombiecastlerush.building;

import com.zombiecastlerush.role.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RoomTest {
    Room room0 = new Room("room-0", "This is the first room.");
    Player player = new Player("Test Player");
    Item testItem0 = new Item("Test Item 0", "This is a test item.");
    Item testItem1 = new Item("Test Item 1", "This is a test item.");

    @Before
    public void setUp() {
        player.setCurrentPosition(room0);

    }

    @Test
    public void roomInventoryDropSingleItemDropsItem() {
        Inventory playerInventory = player.inventory;
        Inventory roomInventory = room0.inventory;
        // No items to begin with, both inventories should be empty
        assertEquals(0, playerInventory.getItems().size());
        assertEquals(0, roomInventory.getItems().size());

        playerInventory.addItems(testItem0);
        // playerInventory should now have 1 item, room should have zero
        assertEquals(1, playerInventory.getItems().size());
        assertEquals(0, roomInventory.getItems().size());

        player.drop(testItem0);
        //playerInventory should now have 0 items, room should have 1.
        assertEquals(0, playerInventory.getItems().size());
        assertEquals(1, roomInventory.getItems().size());
    }

    @Test
    public void roomInventoryDropAllDropsAllItems() {
        Inventory playerInventory = player.inventory;
        Inventory roomInventory = room0.inventory;
        playerInventory.addItems(testItem0);
        playerInventory.addItems(testItem1);
        // add two items, both should be in player inventory
        assertEquals(2, playerInventory.getItems().size());
        assertEquals(0, roomInventory.getItems().size());

        player.dropAll();
        // two items should be dropped into room by player after dropAll()
        assertEquals(0, playerInventory.getItems().size());
        assertEquals(2, roomInventory.getItems().size());
    }

    @Test
    public void playerPickUpGetsItemFromRoomInventory() {
        Inventory playerInventory = player.inventory;
        Inventory roomInventory = room0.inventory;
        roomInventory.addItems(testItem0);
        player.pickUp(testItem0);
        // after player picks up item, player should have the one item, room should now have 0
        assertEquals(1, playerInventory.getItems().size());
        assertEquals(0, roomInventory.getItems().size());
    }
}
