package com.zombiecastlerush.building;

import com.zombiecastlerush.role.Enemy;
import com.zombiecastlerush.role.Player;

public class Combat extends Challenge{
    public Combat(String description) {
        super(description);
    }

    public static void combat(Player player, Enemy enemy) {
        while (player.getHealth() > 0 && enemy.getHealth() > 0) {
            System.out.println("You have entered combat mode, what would you like to do?");
            System.out.println(" 1 - fight" +
                    "run-away");

        }
    }



}