package com.wdeath.wshooter.zombie.ecs.explosion.coms;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.wdeath.wshooter.zombie.ecs.explosion.ExplosionType;

public class ExplosionDataComponent implements Component {

    public Vector2 pos;
    public ExplosionType type;
}
