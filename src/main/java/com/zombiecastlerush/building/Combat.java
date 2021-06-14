package com.zombiecastlerush.building;


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
        String msg = "Welcome to Combat mode, what would you like to do?";
        String combatChoice = Prompter.getUserInput(msg);
        while (player.getHealth() > 0 && enemy.getHealth() > 0) {
            System.out.println("fight" +
                    "run-away");
            if (combatChoice.isEmpty()) {
                continue;
            }
            combatChoiceParser(combatChoice, player, enemy);
        }
    }

    public static void combatChoiceParser(String choice, Role player, Role enemy) {
        List<String> combatCommandList = Parser.parse(choice);
        if (combatCommandList == null) {
            System.out.println("That's not a valid command. Please try again ....");
        } else if (combatCommandList.get(0).contains("fight")) {
            playerFight(player, enemy);
        }
    }

    static void playerFight(Role player, Role enemy) {
        int playerHitPoints = new Random().nextInt(50) + 1;

        if (player.getHealth() > 0 && enemy.getHealth() > 0) {
            System.out.println("You attack......");
            enemy.decreaseHealth(playerHitPoints);
            if (enemy.getHealth() < 0) enemy.setHealth(0);
            System.out.println("Enemy sustained " + playerHitPoints + " damage.");
            System.out.println("Enemy health is now: " + enemy.getHealth());
        }
        enemyFight(player, enemy);
    }

    static void enemyFight(Role player, Role enemy) {
        int enemyHitPoints = new Random().nextInt(50) + 1;

        if (player.getHealth() > 0 && enemy.getHealth() > 0) {
            System.out.println("Enemy attacks......");
            player.decreaseHealth(enemyHitPoints);
            if (player.getHealth() < 0) player.setHealth(0);
            System.out.printf("You sustained %s damage", enemyHitPoints);
            System.out.printf("Your health is now: %s", player.getHealth());
        }
    }
}