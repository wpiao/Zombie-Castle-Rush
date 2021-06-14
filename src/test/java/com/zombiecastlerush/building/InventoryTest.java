package com.zombiecastlerush.building;

import com.zombiecastlerush.entity.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class InventoryTest {
    Inventory inventory = new Inventory();
    Item item0;

    @Before
    public void setUp() throws Exception {
        item0 = new Item("item 0", "item 0", 100.0);
        inventory.addItems(item0);
    }

    @Test
    public void getItems_ReturnsListOfCurrentItems() {
        List<Item> expected = new ArrayList<>(inventory.getItems());
        List<Item> actual = new ArrayList<>(Arrays.asList(item0));
        assertTrue(expected.equals(actual));
    }

    @Test
    public void addItems_addOneItemToInventory() {
        Item item1 = new Item("item 1", "item 1", 25.0);
        inventory.addItems(item1);
        List<Item> itemList = new ArrayList<>(inventory.getItems());
        var actual = itemList.contains(item1);
        assertTrue(actual);
    }

    @Test
    public void addItems_acceptsVarArgs_addTwoItemsToInventory() {
        Item item2 = new Item("item 2", "item 2", 50.0);
        Item item3 = new Item("item 3", "item 3", 75.0);
        inventory.addItems(item2, item3);
        List<Item> itemList = new ArrayList<>(inventory.getItems());
        var actual = itemList.contains(item2);
        var actual1 = itemList.contains(item3);
        assertTrue(actual);
        assertTrue(actual1);
    }

    @Test
    public void deleteItems_removesItemFromInventory() {
        Item item3 = new Item("item 3", "item 3", 75.0);
        inventory.addItems(item3);
        inventory.deleteItems(item3);
        var actual = !inventory.getItems().contains(item3);
        assertTrue(actual);
    }

    @Test
    public void deleteItems_removesTwoItemsFromInventory() {
        Item item3 = new Item("item 3", "item 3", 75.0);
        Item item4 = new Item("item 4", "item 4", 100.0);
        inventory.addItems(item3, item4);
        inventory.deleteItems(item3, item4);
        List<Item> itemList = new ArrayList<>(inventory.getItems());
        var actual = !itemList.contains(item3);
        var actual1 = !itemList.contains(item4);
        assertTrue(actual);
        assertTrue(actual1);
    }

    @Test
    public void transferItem() {
        Inventory invTo = new Inventory();
        var item = inventory.getItems().get(0);
        inventory.transferItem(this.inventory, invTo, item);
        assertTrue(invTo.getItems().contains(item));
    }

    @Test
    public void testMoveItem_targetItemInDestinationInventoryAfterMovement() {
        Player player = new Player("Player");
        Room room = new Room("Room", "just a room");
        //TODO: need to test Puzzle inventory once updated version available
        Item item = new Item("target item", "I will be moved between objects", 100.0);
        player.getInventory().addItems(item);
        Assert.assertEquals(room.getInventory().getItems().size(), 0); //empty inventory
        Assert.assertEquals(player.getInventory().getItems().get(0), item); //one item in inventory
        // move item from player to room
        player.getInventory().moveItem(item, room);
        Assert.assertEquals(player.getInventory().getItems().size(), 0); //empty
        Assert.assertEquals(room.getInventory().getItems().get(0), item); //one item
    }

    @Test
    public void testMoveItem_moveItemBackAndForce() {
        Player player = new Player("Player");
        Room room = new Room("Room", "just a room");
        //TODO: need to test Puzzle inventory once updated version available
        Item item = new Item("target item", "I will be moved between objects", 100.0);
        player.getInventory().addItems(item);
        assertEquals(room.getInventory().getItems().size(), 0); //empty inventory
        assertEquals(player.getInventory().getItems().get(0), item); //one item in inventory
        // move item from player to room
        player.getInventory().moveItem(item, room);
        assertEquals(player.getInventory().getItems().size(), 0); //empty
        Assert.assertEquals(room.getInventory().getItems().get(0), item); //one item
        // now i want to move that item back from the room to the player
        room.getInventory().moveItem(item, player);
        Assert.assertEquals(room.getInventory().getItems().size(), 0); //empty inventory
        assertEquals(player.getInventory().getItems().get(0), item); //one item in inventory
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMoveItem_throwExceptionTargetItemNull() {
        Player player = new Player("Player");
        Room room = new Room("Room", "just a room");
        Item item = null;
        // move item null from player to room
        player.getInventory().moveItem(item, room);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMoveItem_throwExceptionDestinationObjectNull() {
        Player player = new Player("Player");
        Room room = null;
        Item item = new Item("target item", "I will be moved between objects", 100.0);
        player.getInventory().getItems().add(item);
        Assert.assertEquals(player.getInventory().getItems().get(0), item);
        //can not move item to null
        player.getInventory().moveItem(item, room);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMoveItem_throwExceptionMovingNonexistentItem() {
        Player player = new Player("Player");
        Room room = new Room("Room", "just a room");
        Item item = new Item("target item", "I will be moved between objects", 100.0);
        Item nonExistentItem = new Item("nonexistent item", "i don't belong to you and you can not move me", 0.0);
        player.getInventory().getItems().add(item);
        Assert.assertEquals(player.getInventory().getItems().get(0), item);
        // item doesn't belong to player, so cannot be moved
        player.getInventory().moveItem(nonExistentItem, room);
    }
}