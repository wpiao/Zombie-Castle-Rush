package com.zombiecastlerush.building;

import com.zombiecastlerush.entity.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class RoomTest {
    Room room0 = new Room("room-0", "This is the first room.");
    private Room r1;
    Player player = new Player("Test Player");
    Item testItem0 = new Item("Test Item 0", "This is a test item.", 50.0);
    Item testItem1 = new Item("Test Item 1", "This is a test item.", 100.0);

    @Before
    public void setUp() {
        player.setCurrentPosition(room0);
        r1 = new Room("Kitchen", "The kitchen is located on the east side");

    }

    @Test
    public void roomInventoryDropSingleItemDropsItem() {
        Inventory playerInventory = player.getInventory();
        Inventory roomInventory = room0.getInventory();
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
        Inventory playerInventory = player.getInventory();
        Inventory roomInventory = room0.getInventory();
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
        Inventory playerInventory = player.getInventory();
        Inventory roomInventory = room0.getInventory();
        roomInventory.addItems(testItem0);
        player.pickUp(testItem0);
        // after player picks up item, player should have the one item, room should now have 0
        assertEquals(1, playerInventory.getItems().size());
        assertEquals(0, roomInventory.getItems().size());
    }

    @Test
    public void testTestSetName_getName() {
        String roomName = "Bedroom";
        r1.setName(roomName);
        assertEquals(roomName, r1.getName());
    }

    @Test
    public void testSetDescription_getDescription() {
        String description = "This room is on the west side";
        r1.setDescription(description);
        assertEquals(description, r1.getDescription());
    }

    @Test
    public void testAddConnectedRooms_GetConnectedRooms() {
        //Empty List when uninitialized
        assertEquals(Collections.emptyList(), r1.getConnectedRooms());

        Room r2 = new Room("R2", "This is room R2");
        Room r3 = new Room("R3", "This is room R3");
        Room r4 = new Room("R4", "This is room R4");

        r1.addConnectedRooms(r3);
        r1.addConnectedRooms(r4);

        List<Room> expectedlist = new ArrayList<>() {{
            add(r3);
            add(r4);
        }};
        assertEquals(r1.getConnectedRooms(), expectedlist);
    }
}
