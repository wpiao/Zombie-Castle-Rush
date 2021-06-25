package com.zombiecastlerush.building;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.zombiecastlerush.entity.Role;
import com.zombiecastlerush.util.Parser;

import java.util.List;
import java.util.Random;

@JsonTypeName("combat")
public class Combat extends Challenge {
    static int playerDamageToEnemy = new Random().nextInt(50) + 1;
    static int enemyDamageToPlayer = new Random().nextInt(50) + 1;

    // needed for ObjectMapper
    public Combat() {}

    public Combat(String description) {
        super(description);
    }

    public static void combat(Role player, Role enemy) {
        List<Item> items = player.getInventory().getItems();
        for (Item item : items) {
            if (item.getName().equals("Sword")){
                playerDamageToEnemy += 20;
                System.out.println(Parser.BLUE+"You draw your " + item.getDescription() + Parser.ANSI_RESET);
            }
        }

        if (player.getHealth() > 0 && enemy.getHealth() > 0) {
            playerAttack(player, enemy);
        }
        if (enemy.getHealth() > 0 && player.getHealth() > 0) {
            enemyAttack(player, enemy);
        }
    }

    public static void playerAttack(Role player, Role enemy) {
            System.out.println(Parser.YELLOW+"You attack...... "+ Parser.ANSI_RESET);
            enemy.decreaseHealth(playerDamageToEnemy);
            if (enemy.getHealth() < 0) enemy.setHealth(0);
            System.out.println(Parser.YELLOW+ "Enemy sustained " + playerDamageToEnemy + " damage. " + Parser.ANSI_RESET);
            System.out.println(Parser.YELLOW+"Enemy health is now: " + enemy.getHealth()+ Parser.ANSI_RESET);
    }

    public static void enemyAttack(Role player, Role enemy) {
            System.out.println(Parser.RED+"Enemy attacks...... " + Parser.ANSI_RESET);
            player.decreaseHealth(enemyDamageToPlayer);
            if (player.getHealth() < 0) player.setHealth(0);
            System.out.printf(Parser.RED+"You sustained " + enemyDamageToPlayer + " damage. \n" + Parser.ANSI_RESET);
            System.out.printf(Parser.RED+"Your health is now: %s \n", player.getHealth() + Parser.ANSI_RESET);
    }
}