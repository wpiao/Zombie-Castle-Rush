package com.zombiecastlerush.util;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ParserTest {

    @Test
    public void parseAcceptsTwoWordStringReturnsArrayListOfTwoStrings() {
        List<String> expected = Arrays.asList("go", "north");
        List<String> actual = Parser.parse("go north");
        assertEquals(expected, actual);
    }

    @Test
    public void parseAcceptsAnyCapitalizationVariantOfInput() {
        List<String> expected = Arrays.asList("go", "north");
        List<String> actual = Parser.parse("Go North");
        assertEquals(expected, actual);
    }
}