package com.zombiecastlerush.building;

import com.zombiecastlerush.role.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class InventoryTest {
    private Inventory inventory = new Inventory();
    @Before
    public void setUp() throws Exception {
        inventory.addItems(new Item("item0","item0"));
        inventory.addItems(new Item("item1","item1"));
        inventory.addItems(new Item("item2","item2"));
        inventory.addItems(new Item("item3","item3"));
    }

    @Test
    public void getItemsReturnsListOfCurrentItemsForRequestingRole() {
        List<Item> itemsList = inventory.getItems();
        System.out.println(itemsList);;
    }

    @Test
    public void addItems() {
    }

    @Test
    public void deleteItems() {
    }

    @Test
    public void testMoveItem_targetItemInDestinationInventoryAfterMovement(){
        Player player = new Player("Player");
        Room room = new Room("Room", "just a room");
        //TODO: need to test Puzzle inventory once updated version available
        Item item = new Item("target item", "I will be moved between objects");
        player.getInventory().addItems(item);
        Assert.assertEquals(room.getInventory().getItems().size(), 0); //empty inventory
        Assert.assertEquals(player.getInventory().getItems().get(0).getName(),item.getName()); //one item in inventory
        // move item from player to room
        player.getInventory().moveItem(item,room);
        Assert.assertEquals(player.getInventory().getItems().size(), 0); //empty
        Assert.assertEquals(room.getInventory().getItems().get(0).getName(), item.getName()); //one item
    }

    @Test
    public void testMoveItem_moveItemBackAndForce(){
        Player player = new Player("Player");
        Room room = new Room("Room", "just a room");
        //TODO: need to test Puzzle inventory once updated version available
        Item item = new Item("target item", "I will be moved between objects");
        player.getInventory().addItems(item);
        Assert.assertEquals(room.getInventory().getItems().size(), 0); //empty inventory
        Assert.assertEquals(player.getInventory().getItems().get(0).getName(),item.getName()); //one item in inventory
        // move item from player to room
        player.getInventory().moveItem(item,room);
        Assert.assertEquals(player.getInventory().getItems().size(), 0); //empty
        Assert.assertEquals(room.getInventory().getItems().get(0).getName(), item.getName()); //one item
        // now i want to move that item back from the room to the player
        room.getInventory().moveItem(item,player);
        Assert.assertEquals(room.getInventory().getItems().size(), 0); //empty inventory
        Assert.assertEquals(player.getInventory().getItems().get(0).getName(),item.getName()); //one item in inventory
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMoveItem_throwExceptionTargetItemNull(){
        Player player = new Player("Player");
        Room room = new Room("Room", "just a room");
        Item item = null;
        // move item null from player to room
        player.getInventory().moveItem(item,room);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMoveItem_throwExceptionDestinationObjectNull(){
        Player player = new Player("Player");
        Room room = null;
        Item item = new Item("target item", "I will be moved between objects");
        player.getInventory().getItems().add(item);
        Assert.assertEquals(player.getInventory().getItems().get(0).getName(),item.getName());
        //can not move item to null
        player.getInventory().moveItem(item,room);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMoveItem_throwExceptionMovingNonexistentItem(){
        Player player = new Player("Player");
        Room room = new Room("Room", "just a room");
        Item item = new Item("target item", "I will be moved between objects");
        Item nonExistentItem = new Item("nonexistent item", "i don't belong to you and you can not move me");
        player.getInventory().getItems().add(item);
        Assert.assertEquals(player.getInventory().getItems().get(0).getName(),item.getName());
        // item doesn't belong to player, so cannot be moved
        player.getInventory().moveItem(nonExistentItem,room);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMoveItem_throwExceptionUnrecognizedDestination(){
        Player player = new Player("Player");
        Castle castle = new Castle();
        Item item = new Item("target item", "I will be moved between objects");
        player.getInventory().getItems().add(item);
        Assert.assertEquals(player.getInventory().getItems().get(0).getName(),item.getName()); // i have the item
        // move to a destination that can not be recognized
        player.getInventory().moveItem(item, castle);
    }
}