package com.zombiecastlerush.util;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ParserTest {

    @Test
    public void terminalLineBreakers() {
        String description = Game.castle.getCastleRooms().get("Combat-Hall").getDescription();
        String msg1 = description.substring(0,description.length()/3);
        String msg2 = description.substring(description.length()/3 + 1,description.length()/3 *2 );
        String msg3 = description.substring(description.length()/3 *2 + 1);

        System.out.println(msg1);
        System.out.println(msg2);
        System.out.println(msg3);
        }
    }
