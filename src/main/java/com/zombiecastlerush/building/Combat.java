package com.zombiecastlerush.building;

import com.zombiecastlerush.role.Enemy;
import com.zombiecastlerush.role.Player;
import com.zombiecastlerush.util.Prompter;

public class Combat extends Challenge{
    public Combat(String description) {
        super(description);
    }

    public static void combat(Player player, Enemy enemy) {
        String msg = "Welcome to Combat mode, what would you like to do?";
        System.out.println(" 1 - fight" +
                "run-away");
        String combatDecision = Prompter.getUserInput(msg);
        while (player.getHealth() > 0 && enemy.getHealth() > 0) {
        }
    }
}