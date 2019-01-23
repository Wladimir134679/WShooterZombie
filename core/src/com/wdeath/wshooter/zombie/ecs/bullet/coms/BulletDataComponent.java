package com.wdeath.wshooter.zombie.ecs.bullet.coms;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.wdeath.wshooter.zombie.damage.DamageType;

public class BulletDataComponent implements Component {

    public DamageType shooter;
    public String nameShooter = "default-name";

    public Vector2 pointStart;
    public float angle;
    public float damage;
}
