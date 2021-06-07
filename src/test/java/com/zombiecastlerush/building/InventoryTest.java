package com.zombiecastlerush.building;

import com.zombiecastlerush.role.Player;
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
}