package com.zombiecastlerush.util;

import org.junit.Test;

import java.awt.*;
import java.util.List;

import static org.junit.Assert.*;

public class ParserTest {

    @Test
    public void terminalLineBreakers() {
        String description = Game.castle.getCastleRooms().get("Shop").getDescription();
        String msg1 = description.substring(0,75);
        String msg2 = description.substring(75);


        System.out.println(msg1);
        System.out.println(msg2);

        }
    }
