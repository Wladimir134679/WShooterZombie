package com.wdeath.wshooter.zombie.ecs.world.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

public class WorldPhysicsComponent implements Component {

    public World world;
    public Box2DDebugRenderer debug;
    public Body wall;

    public float deltaPhysics = -1;
}
