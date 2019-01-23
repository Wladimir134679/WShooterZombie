package com.wdeath.wshooter.zombie.ecs.player.coms;

import com.badlogic.ashley.core.Component;

public class PlayerMoveComponent implements Component {

    public static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3, SHIFT = 4, CTRL = 5, SPACE = 6;

    public boolean[] key;

    public PlayerMoveComponent() {
        key = new boolean[7];
        key[UP] = false;
        key[DOWN] = false;
        key[LEFT] = false;
        key[RIGHT] = false;
        key[SHIFT] = false;
        key[CTRL] = false;
        key[SPACE] = false;
    }
}
