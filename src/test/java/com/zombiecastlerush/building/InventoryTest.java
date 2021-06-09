package com.zombiecastlerush.building;

import com.zombiecastlerush.role.Player;
import org.junit.Before;
import org.junit.Test;

import javax.management.relation.Role;
import java.util.*;
import static org.junit.Assert.*;

public class InventoryTest {
    Inventory inventory = new Inventory();
    Item item0;

    @Before
    public void setUp() throws Exception {
        item0 = new Item("item 0", "item 0");
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
        Item item1 = new Item("item 1", "item 1");
        inventory.addItems(item1);
        List<Item> itemList = new ArrayList<>(inventory.getItems());
        var actual = itemList.contains(item1);
        assertTrue(actual);
    }

    @Test
    public void addItems_acceptsVarArgs_addTwoItemsToInventory() {
        Item item2 = new Item("item 2", "item 2");
        Item item3 = new Item("item 3", "item 3");
        inventory.addItems(item2, item3);
        List<Item> itemList = new ArrayList<>(inventory.getItems());
        var actual = itemList.contains(item2);
        var actual1 = itemList.contains(item3);
        assertTrue(actual);
        assertTrue(actual1);
    }

    @Test
    public void deleteItems_removesItemFromInventory() {
        Item item3 = new Item("item 3", "item 3");
        inventory.addItems(item3);
        inventory.deleteItems(item3);
        var actual = !inventory.getItems().contains(item3);
        assertTrue(actual);
    }

    @Test
    public void deleteItems_removesTwoItemsFromInventory() {
        Item item3 = new Item("item 3", "item 3");
        Item item4 = new Item("item 4", "item 4");
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
}
