package com.zombiecastlerush.util;

import com.zombiecastlerush.building.Puzzle;
import com.zombiecastlerush.building.Room;
import com.zombiecastlerush.role.Player;
import org.junit.Before;
import org.junit.Test;

class PrompterTest {
    private Game game = Game.getInstance();
    private Player player = new Player("Player 1");
    Room r1 = new Room("room1", "ENTRY room");

    @Before
    public void setUp() throws Exception {
        Room r1 = new Room("room1", "This is room number1");
        r1.setChallenge(new Puzzle("r1 Puzzle","What is (2+2) X (2-2)?","0"));
    }

    /*
    * Puzzle Test Scenarios
    * 1) If room has no puzzle and user enters-attempt puzzle
    * */

    public void testattemptPuzzle() {


    }
}
