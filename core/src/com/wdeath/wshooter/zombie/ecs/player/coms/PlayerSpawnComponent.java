package com.wdeath.wshooter.zombie.ecs.player.coms;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class PlayerSpawnComponent implements Component {

    public Vector2 spawnPoint;
    public String name;

    public float maxHealth;
}
