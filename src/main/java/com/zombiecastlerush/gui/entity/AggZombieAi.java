package com.zombiecastlerush.gui.entity;

import com.zombiecastlerush.gui.layout.Path;
import com.zombiecastlerush.gui.layout.Point;
import com.zombiecastlerush.gui.layout.Tile;

import java.util.List;

public class AggZombieAi extends CreatureAi {
    private Creature player;


    public AggZombieAi(Creature creature, Creature player) {
        super(creature);
        this.player = player;
    }

    public void onUpdate(){
        if (Math.random() < 0.2)
            return;

        if (creature.canSee(player.x, player.y))
            hunt(player);
        else
            wander();
    }

    public void onEnter(int x, int y, Tile tile) {
        if (tile.isGround() || tile.isDoor() || tile.isBox()) {
            creature.x = x;
            creature.y = y;
        }

    }

    public void hunt(Creature target){
        List<Point> points = new Path(creature, target.x, target.y).points();
        if (points != null && points.size()!=0) {
            int mx = points.get(0).x - creature.x;
            int my = points.get(0).y - creature.y;
            creature.moveBy(mx, my);
        }

    }
}
