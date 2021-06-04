package com.zombiecastlerush.role;

import com.zombiecastlerush.building.Room;

import java.util.List;

/**
 * TODO: what does Player class provide?
 */
public class Player extends Role{

    public Player(String name){
        super(name);
    }

    public Player(String name, Room room) {
        super(name, room);
    }

    /**
     * TODO: move to a room by room name/description
     * @param roomName
     */
    public boolean moveTo(String roomName){
        Room targetRoom = this.canMoveToRoom(roomName);
        if(targetRoom != null){
            String previous = super.getCurrentPosition().getName();
            super.setCurrentPosition(targetRoom);
            System.out.printf("Player %s moved from room %s to room %s\n", super.getName(), previous, super.getCurrentPosition().getName());
            return true;
        } else{
            System.out.printf("Player %s's current room %s doesn't connect to other rooms.", super.getName(), super.getCurrentPosition().getName());
            return false;
        }
    }

    /**
     * decide next valid movement with target room name
     * @param roomName
     * @return Room reference if input room name is valid for my next movement
     */
    public Room canMoveToRoom(String roomName) {
        List<Room> validRooms = this.whichRoomNext();
        if (validRooms != null) {
            for (Room r : validRooms) {
                if (r.getName().equals(roomName))
                    return r; // TODO: is it possible to have rooms with same name? (Xander asking)
            }
        }
        return null;
    }

    /**
     * helper method that provide me a list which room can i go to
     * @return
     */
    private List<Room> whichRoomNext() {
        return (super.getCurrentPosition() == null) ? null : super.getCurrentPosition().getConnectedRooms();
    }
}
