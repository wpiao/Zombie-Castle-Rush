package com.zombiecastlerush.building;

import org.junit.Before;
import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RoomTest {
    private Room r1;

    @Before
    public void setUp() throws Exception {
        r1 = new Room("Kitchen","The kitchen is located on the east side");
    }

    @Test
    public void testTestSetName_getName() {
        String roomName = "Bedroom";
        r1.setName(roomName);
        assertEquals(roomName,r1.getName());
    }

    @Test
    public void testSetDescription_getDescription() {
        String description = "This room is on the west side";
        r1.setDescription(description);
        assertEquals(description,r1.getDescription());
    }

    @Test
    public void testAddConnectedRooms_GetConnectedRooms() {
        //Empty List when uninitialized
        assertEquals(Collections.emptyList(),r1.getConnectedRooms());

        Room r2 = new Room("R2","This is room R2");
        Room r3 = new Room("R3","This is room R3");
        Room r4 = new Room("R4","This is room R4");

        r1.addConnectedRooms(r3);
        r1.addConnectedRooms(r4);

        List<Room> expectedlist= new ArrayList<>(){{add(r3);add(r4);}};
        assertEquals(r1.getConnectedRooms(),expectedlist);
    }

    @Test
    public void testGetItems() {
    }
}
