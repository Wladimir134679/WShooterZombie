package com.wdeath.wshooter.zombie.ecs.explosion.coms;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.wdeath.wshooter.zombie.ecs.explosion.ExplosionType;

public class ExplosionSpawnComponent implements Component {

    public ExplosionType type;
    public Vector2 pos;

    public Body body;
    public float offsetX, offsetY;

}
