package com.wdeath.wshooter.zombie.ecs.world.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.wdeath.wshooter.zombie.ecs.world.component.WorldDrawComponent;
import com.wdeath.wshooter.zombie.ecs.world.component.WorldPhysicsComponent;

public class WorldPhysicsDebugSystem extends EntitySystem {

    private Entity entity;

    public WorldPhysicsDebugSystem(Entity entity) {
        this.entity = entity;
    }

    @Override
    public void update(float deltaTime) {
        WorldPhysicsComponent physicsComponent = entity.getComponent(WorldPhysicsComponent.class);
        WorldDrawComponent draw = entity.getComponent(WorldDrawComponent.class);
        physicsComponent.debug.render(physicsComponent.world, draw.camera.combined);
    }
}

