package com.zombiecastlerush.gui.creature;

import com.zombiecastlerush.gui.layout.Line;
import com.zombiecastlerush.gui.layout.Point;
import com.zombiecastlerush.gui.layout.Tile;

public abstract class CreatureAi {
    protected Creature creature;

    public CreatureAi(Creature creature){
        this.creature = creature;
        this.creature.setCreatureAi(this);
    }

    public void onUpdate(){
    }

    public void onEnter(int x, int y, Tile tile){
        if (tile.isGround()){
            creature.x = x;
            creature.y = y;
        }
    }

    public boolean canSee(int wx, int wy) {

        if ((creature.x-wx)*(creature.x-wx) + (creature.y-wy)*(creature.y-wy) > creature.visionRadius()*creature.visionRadius())
            return false;

        for (Point p : new Line(creature.x, creature.y, wx, wy)){
            if (creature.tile(p.x, p.y).isGround() || p.x == wx && p.y == wy)
                continue;

            return false;
        }

        return true;
    }

    public void wander(){
        int mx = (int)(Math.random() * 3) - 1;
        int my = (int)(Math.random() * 3) - 1;

        creature.moveBy(mx, my);
    }
}