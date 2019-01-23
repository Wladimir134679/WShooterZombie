package com.wdeath.wshooter.zombie.ecs.bullet.coms;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;

public class BulletPhysicsComponent implements Component {

    public Body body;
    public Fixture fixture;
}
