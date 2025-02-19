package com.zombiecastlerush.gui.component;

import com.zombiecastlerush.gui.layout.Path;
import com.zombiecastlerush.gui.layout.Point;

import java.io.Serializable;
import java.util.List;

public class AggZombieAi extends CreatureAi implements Serializable {
    private final Creature player;

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


    public void hunt(Creature target){
        List<Point> points = new Path(creature, target.x, target.y).points();
        if (points != null && points.size()!=0) {
            int mx = points.get(0).x - creature.x;
            int my = points.get(0).y - creature.y;
            creature.moveBy(mx, my);
        }

    }
}
