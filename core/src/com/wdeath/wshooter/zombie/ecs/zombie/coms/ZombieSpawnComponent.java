package com.wdeath.wshooter.zombie.ecs.zombie.coms;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class ZombieSpawnComponent implements Component {

    public Vector2 pos;
    public float angle;
    public float speed;

    public float maxHealth;

}
