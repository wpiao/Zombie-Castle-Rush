package com.zombiecastlerush.role;

import com.zombiecastlerush.building.Room;
import com.zombiecastlerush.util.Directions;

/**
 * TODO: what does Player class provide?
 */
class Player extends Role implements Runnable{
    public Player(int id) {
        super(id);
    }

    public Player(int id, Room room) {
        super(id, room);
    }

    @Override
    public void run() {
        try {
            while (true) {
                System.out.printf("I am a Player #%d and sitting in room #%d.\n In the real game, the user will have a fully control.\n", this.getId(), this.getCurrentPosition());
                Thread.sleep(3000);
            }
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * TODO: move to a room by direction
     * @param direction
     */
    public void moveTo(Directions direction){

    }

    /**
     * TODO: move to a room by room name/description
     * @param roomName
     */
    public void moveTo(String roomName){

    }
}
