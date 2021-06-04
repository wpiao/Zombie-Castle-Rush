package com.zombiecastlerush.role;

import com.zombiecastlerush.building.Room;

class Enemy extends Role implements Runnable{
    public Enemy(int id){
        super(id);
    }

    public Enemy(int id, Room room){
        super(id, room);
    }

    @Override
    public void run() {
        //TODO: add zombie/robot behaviors
        try {
            while(true) {
                System.out.printf("I am a %s moving to next room #%d\n", this, this.getCurrentPosition());
                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString(){
        return "Zombie #" + this.getId();
    }
}
