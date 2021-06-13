package com.zombiecastlerush.building;

import com.zombiecastlerush.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class Shop extends Room {

    public Shop(String name, String description) {
        super(name, description);
    }

    public void buy(Player player, Item item) {

        //check action buy -- in prompter--only allow if the room is shop
        //check if the item is in the shops' inventory--do on prompter
        //check if player has more money than the item's price
        // , check item's price only if the room is shop--not really, all items will have
        // price so that they can be sold to the shop-
        // if yes, move the item from shop's inventory to player's
        //deduct money= item's price from player's

        //Do we allow buying if player already has the item??--No, prompter handles
        if (player.getAcctBalance() >= item.getPrice()) {
            player.getInventory().transferItem(this.getInventory(), player.getInventory(), item);
            player.setAcctBalance(player.getAcctBalance() - item.getPrice());
            System.out.println("You've bought yourself a " + item.getName());
        } else
            System.out.println("You do not have enough money to buy the " + item.getName() + ".");
    }

    public void sell(Player player, Item item) {
        //check if the player has the item in his inventory -- in prompter
        //offer 75% of the item's price to the player--in prompter
        //move item from player's inventory to shop's
        //add item's 75% of the price to player's
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