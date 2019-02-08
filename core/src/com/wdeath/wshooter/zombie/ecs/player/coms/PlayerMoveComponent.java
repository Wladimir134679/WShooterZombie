package com.wdeath.wshooter.zombie.ecs.player.coms;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.Input;

import java.util.HashMap;

public class PlayerMoveComponent implements Component {

    public static int
            UP = Input.Keys.W,
            DOWN = Input.Keys.S,
            LEFT = Input.Keys.A,
            RIGHT = Input.Keys.D,
            SHIFT = Input.Keys.SHIFT_LEFT,
            CTRL = Input.Keys.CONTROL_LEFT,
            SPACE = Input.Keys.SPACE;

    public HashMap<Integer, Boolean> keys;
    public int scrollMouse;

    public float speed = 5;

    public boolean shot;

    public PlayerMoveComponent() {
        keys = new HashMap<>();
        keys.put(Input.Keys.W, false);
        keys.put(Input.Keys.S, false);
        keys.put(Input.Keys.A, false);
        keys.put(Input.Keys.D, false);

        keys.put(Input.Keys.SHIFT_LEFT, false);
        keys.put(Input.Keys.SPACE, false);
        keys.put(Input.Keys.CONTROL_LEFT, false);

        shot = false;
    }
}
