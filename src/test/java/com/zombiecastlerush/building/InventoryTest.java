package com.zombiecastlerush.building;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class InventoryTest {

    @Before
    public void setUp() throws Exception {
        Inventory inventory = new Inventory();
        inventory.addItems(new Item("item0","item0"));
        inventory.addItems(new Item("item1","item1"));
        inventory.addItems(new Item("item2","item2"));
        inventory.addItems(new Item("item3","item3"));
    }

    @Test
    public void getItems() {
    }

    @Test
    public void addItems() {
    }

    @Test
    public void deleteItems() {
    }
}