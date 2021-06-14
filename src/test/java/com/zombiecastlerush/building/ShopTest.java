package com.zombiecastlerush.building;

import com.zombiecastlerush.entity.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ShopTest {
    Player p1;
    Shop s1;
    Item i1 = new Item("Magic-Lamp", "Genie's houe", 500.0);
    Item i2 = new Item("Flying-Carpet", "Let's tour the sky", 200.0);


    @Before
    public void setUp() throws Exception {
        p1 = new Player("testPlaye01");
        s1 = new Shop("S1", "Shop1");
        p1.setCurrentPosition(s1);
        s1.getInventory().addItems(i1);
        p1.getInventory().addItems(i2);
    }

    @Test
    public void testBuyWhenPlayerDoesNotHaveEnoughMoney() {
        //can't buy when player does not have enough money
        double preBuy_balance = p1.getAcctBalance();
        s1.sellItemToPlayer(p1, i1);
        assertFalse(p1.getInventory().getItems().contains(i1));
        assertTrue(s1.getInventory().getItems().contains(i1));
        assertEquals(preBuy_balance, p1.getAcctBalance(), 0.001);
    }

    @Test
    public void testBuyWhenPlayerHasEnoughMoney() {
        //can buy when player has enough money
        p1.setAcctBalance(550.0);
        double preBuy_balance = p1.getAcctBalance();
        s1.sellItemToPlayer(p1, i1);
        assertTrue(p1.getInventory().getItems().contains(i1));
        assertFalse(s1.getInventory().getItems().contains(i1));
        assertEquals(p1.getAcctBalance(), preBuy_balance - i1.getPrice(), 0.01);
    }

    @Test
    public void testSell() {
        //player's balance should increase by 75% of item's price
        double preSell_balance = p1.getAcctBalance();
        s1.buyItemFromPlayer(p1, i2);
        assertEquals((preSell_balance + 0.75 * i2.getPrice()), p1.getAcctBalance(), 0.01);

        //player's inventory should not contain, shops's inventory should contain
        assertFalse(p1.getInventory().getItems().contains(i2));
        assertTrue(s1.getInventory().getItems().contains(i2));

    }
}