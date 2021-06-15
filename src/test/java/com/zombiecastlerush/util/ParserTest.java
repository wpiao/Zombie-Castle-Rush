package com.zombiecastlerush.util;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ParserTest {
    List<String> CAPITALIZATION_VARIANTS = Arrays.asList(
            "go north",
            "GO NORTH",
            "Go NoRtH",
            "gO nOrth"
    );

    @Test
    public void parseAcceptsTwoWordStringReturnsArrayListOfTwoStrings() {
        List<String> expected = Arrays.asList("go", "west");
        List<String> actual = Parser.parse("go west");

        assertEquals(expected, actual);
    }

    @Test
    public void parseAcceptsAnyCapitalizationVariantOfInput() {
        List<String> expected = Arrays.asList("go", "north");
        for (String variant : CAPITALIZATION_VARIANTS) {
            List<String> actual = Parser.parse(variant);
            assertEquals(expected, actual);
        }
    }

    @Test
    public void parseInvalidFirstInputWordEqualsNull() {
        assertNull(Parser.parse("incorrect_action north"));
    }

    @Test
    public void parseInvalidInputLengthCausesNull() {
        assertNull(Parser.parse("incorrect_action blah blah blah north"));
        assertNull(Parser.parse("incorrect"));
    }
}