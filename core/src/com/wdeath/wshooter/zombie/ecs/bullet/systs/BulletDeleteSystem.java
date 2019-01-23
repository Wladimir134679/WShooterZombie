package com.wdeath.wshooter.zombie.ecs.bullet.systs;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wdeath.wshooter.zombie.ecs.bullet.coms.BulletDeleteComponent;
import com.wdeath.wshooter.zombie.ecs.bullet.coms.BulletLightComponent;
import com.wdeath.wshooter.zombie.ecs.bullet.coms.BulletPhysicsComponent;
import com.wdeath.wshooter.zombie.ecs.world.component.WorldPhysicsComponent;

public class BulletDeleteSystem extends IteratingSystem {

    private Entity world;

    public BulletDeleteSystem(Entity world) {
        super(Family.all(BulletDeleteComponent.class).get());
        this.world = world;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        destoryBody(entity);
        destoryLight(entity);
        getEngine().removeEntity(entity);
    }

    private void destoryBody(Entity entity){
        BulletPhysicsComponent physics = entity.getComponent(BulletPhysicsComponent.class);
        WorldPhysicsComponent physicsWorld = world.getComponent(WorldPhysicsComponent.class);
        physicsWorld.world.destroyBody(physics.body);
    }

    private void destoryLight(Entity entity){
        BulletLightComponent light = entity.getComponent(BulletLightComponent.class);
        if(light == null)
            return;
//        WorldLightComponent lightComponent = world.getComponent(WorldLightComponent.class);
        light.light.remove();
    }
}
