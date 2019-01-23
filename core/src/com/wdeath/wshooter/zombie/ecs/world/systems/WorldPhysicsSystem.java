package com.wdeath.wshooter.zombie.ecs.world.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.wdeath.wshooter.zombie.ecs.world.component.WorldPhysicsComponent;

public class WorldPhysicsSystem extends EntitySystem {

    private Entity entity;

    public WorldPhysicsSystem(Entity entity) {
        this.entity = entity;
    }

    @Override
    public void update(float deltaTime) {
        WorldPhysicsComponent physicsComponent = entity.getComponent(WorldPhysicsComponent.class);
        physicsComponent.world.step(deltaTime, 5,5);
    }
}
