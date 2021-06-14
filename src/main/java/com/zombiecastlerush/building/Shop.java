package com.zombiecastlerush.building;

import com.zombiecastlerush.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class Shop extends Room {

    public Shop(String name, String description) {
        super(name, description);
    }

    public void sellItemToPlayer(Player player, Item item) {
        //Do we allow buying if player already has the item??--No, prompter handles
        if (player.getAcctBalance() >= item.getPrice()) {
            player.getInventory().transferItem(this.getInventory(), player.getInventory(), item);
            player.setAcctBalance(player.getAcctBalance() - item.getPrice());
            System.out.println("You've bought yourself a " + item.getName());
        } else
            System.out.println("You do not have enough money to buy the " + item.getName() + ".");
    }

    public void buyItemFromPlayer(Player player, Item item) {
        player.getInventory().transferItem(player.getInventory(), this.getInventory(), item);
        player.setAcctBalance(player.getAcctBalance() + 0.75 * item.getPrice());
        System.out.println("You've sold your " + item.getName() + " for " + 0.75 * item.getPrice() + ".");
    }

    public String toStringShopInventory() {
        String listString = getInventory().getItems().stream().map(Item -> Item.getName() + ", Price= $" + Item.getPrice())
                .collect(Collectors.joining(";  "));
        return listString;
    }
}