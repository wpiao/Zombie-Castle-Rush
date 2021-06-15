package com.zombiecastlerush.building;

import com.zombiecastlerush.entity.Entity;
import com.zombiecastlerush.entity.Role;
import com.zombiecastlerush.util.Parser;
import com.zombiecastlerush.util.Prompter;

import java.util.List;
import java.util.Random;

public class Combat extends Challenge {
    public Combat(String description) {
        super(description);
    }

    public static void combat(Role player, Role enemy) {
        playerFight(player, enemy);
    }

    static void playerFight(Role player, Role enemy) {
        int playerDamageToEnemy = new Random().nextInt(50) + 1;
        List<Item> items = player.getInventory().getItems();
        for (Item item : items) {
            if (item.getName().equals("Sword")){
                playerDamageToEnemy += 20;
                System.out.println("You draw your " + item.getDescription());
            }
        }

        if (player.getHealth() > 0 && enemy.getHealth() > 0) {
            System.out.println("You attack...... ");
            enemy.decreaseHealth(playerDamageToEnemy);
            if (enemy.getHealth() < 0) enemy.setHealth(0);
            System.out.println("Enemy sustained " + playerDamageToEnemy + " damage. ");
            System.out.println("Enemy health is now: " + enemy.getHealth());
            enemyFight(player, enemy);
        }
    }

    public static void enemyFight(Role player, Role enemy) {
        int enemyDamageToPlayer = new Random().nextInt(50) + 1;

        if (enemy.getHealth() > 0 && player.getHealth() > 0) {
            System.out.println("Enemy attacks...... ");
            player.decreaseHealth(enemyDamageToPlayer);
            if (player.getHealth() < 0) player.setHealth(0);
            System.out.printf("You sustained %s damage \n", enemyDamageToPlayer);
            System.out.printf("Your health is now: %s \n", player.getHealth());
        }
    }
}