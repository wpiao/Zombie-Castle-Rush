package com.zombiecastlerush.gui;

import org.junit.Test;

import static org.junit.Assert.*;

public class WorldBuilderTest {

    @Test
    public void testReadFileInto2DCharArray() {
        WorldBuilder worldBuilder = new WorldBuilder(100,51);
        worldBuilder.design();
    }
}