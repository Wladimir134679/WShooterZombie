package com.wdeath.wshooter.zombie.ecs.zombie.coms;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;

public class ZombiePhysicsComponent implements Component {

    public Body body;
    public Fixture fixtureBody;
    public Fixture sign;

    public Fixture sensorPlayer;
}
