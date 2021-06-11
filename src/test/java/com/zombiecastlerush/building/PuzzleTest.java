package com.zombiecastlerush.building;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PuzzleTest {
    Puzzle p1;

    @Before
    public void setUp() throws Exception {
        p1 = new Puzzle("Puzzle#1", "What is 2+2?", "4");
    }

    @Test
    public void testSetQuestion_getQuestion() {
        String ques = "What is 2+2?";
        p1.setQuestion(ques);
        assertEquals(ques, p1.getQuestion());
    }

    @Test
    public void testSetSolution_getSolution() {
        String solution = "4";
        p1.setSolution(solution);
        assertEquals(solution, p1.getSolution());
    }

    @Test
    public void testgetAttempts() {
        //when initialized attempts should be zero
        assertEquals(0, p1.getAttempts());
    }

    @Test
    public void testAttemptPuzzle() {
        //Player answers incorrectly on first,2nd try
        assertEquals(1, p1.attemptPuzzle("2"));
        int actual = p1.attemptPuzzle("3");
        assertEquals(2, actual);

        //Player answers correctly on 3rd try
        assertEquals(3, p1.attemptPuzzle("4"));


    }
}
