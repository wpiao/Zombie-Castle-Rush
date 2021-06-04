package com.zombiecastlerush.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ParserTest {

    @Test
    public void parseAcceptsTwoWordStringReturnsArrayListOfTwoStrings() {
        List<String> expected = new ArrayList<>(Arrays.asList("go", "north"));
        List<String> actual = Parser.parse("go north");
        assertEquals(expected, actual);
    }
}