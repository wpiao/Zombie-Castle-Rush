package com.zombiecastlerush.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.zombiecastlerush.building.Room;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RoleTest {
    private Role role = new Role("Role#1");

    @Before
    public void setUp() throws Exception {
        role.setCurrentPosition(new Room("1", "i am room 1"));
    }

    @Test
    public void testIncreaseHealth_returnMaxHealthValueIfTotalHealthBiggerThanMax() {
        Assert.assertEquals(role.getHealth(), 100); // current health
        role.increaseHealth(1); // change it to 101
        Assert.assertEquals(role.getHealth(), 100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIncreaseHealth_throwExceptionIfIncreasedHealthNegative() {
        role.increaseHealth(-1); // cannot increase negative points
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDecreaseHealth_throwExceptionIfDecreasedHealthNegative() {
        role.decreaseHealth(-1); // cannot decrease negative points
    }

    @Test
    public void testDecreaseHealth_returnMinHealthIfTotalHealthLessThanMin() {
        Assert.assertEquals(role.getHealth(), 100); // current health
        role.decreaseHealth(101); // change it to -1
        Assert.assertEquals(role.getHealth(), 0);
    }

    @Test
    public void testGetCurrentPosition_returnCurrentRoomNumber() {
        Assert.assertEquals(role.getCurrentPosition().getName(), "1");
    }

    @Test
    public void testSetCurrentPosition_returnChangedRoomNumber() {
        role.getCurrentPosition();
        Assert.assertEquals(role.getCurrentPosition().getName(), "1");
        Room roomTest = new Room("test2", "I am Room test2");
        role.setCurrentPosition(roomTest);
        Assert.assertEquals(role.getCurrentPosition().getName(), "test2");
    }

    @Test
    public void testGetHealth_returnPredefinedValue() {
        role.setHealth(100);
        Assert.assertEquals(role.getHealth(), 100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetHealth_throwExceptionNegativeHealth() {
        role.setHealth(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetHealth_throwExceptionOverMaxHealth() {
        role.setHealth(101);
    }

    @Test
    public void testSetHealth_allowInputValidHealthValue() {
        role.setHealth(0);
        role.setHealth(100);
        role.setHealth(50);
        Assert.assertEquals(role.getHealth(), 50);
    }

    @Test
    public void testDisplayStatus_outputInJsonFormat() throws JsonProcessingException {
        Player player = new Player("playerXander");
        Room room = new Room("roomTest", "just a room");
        player.setCurrentPosition(room);
        String jsonStringPlayer = player.displayStatus();
        System.out.println(jsonStringPlayer);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jNodePlayer = mapper.readTree(jsonStringPlayer);
        Assert.assertEquals(jNodePlayer.at("/name").asText(), "playerXander");
        Assert.assertEquals(jNodePlayer.at("/currentRoom/name").asText(), "roomTest");
        Assert.assertEquals(jNodePlayer.at("/currentRoom/connectedRooms").size(), 0);
        Assert.assertEquals(jNodePlayer.at("/inventory/items").size(), 0);
    }
}
