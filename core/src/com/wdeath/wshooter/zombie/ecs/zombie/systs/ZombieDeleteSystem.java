package com.wdeath.wshooter.zombie.ecs.zombie.systs;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wdeath.wshooter.zombie.ecs.world.component.WorldPhysicsComponent;
import com.wdeath.wshooter.zombie.ecs.zombie.coms.ZombieDeleteComponent;
import com.wdeath.wshooter.zombie.ecs.zombie.coms.ZombiePhysicsComponent;

public class ZombieDeleteSystem extends IteratingSystem {

    private Entity world;

    public ZombieDeleteSystem(Entity world){
        super(Family.all(ZombieDeleteComponent.class).get());
        this.world = world;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        ZombiePhysicsComponent physicsComponent = entity.getComponent(ZombiePhysicsComponent.class);
        WorldPhysicsComponent com = world.getComponent(WorldPhysicsComponent.class);
        com.world.destroyBody(physicsComponent.body);
        entity.remove(ZombieDeleteComponent.class);
        getEngine().removeEntity(entity);
    }
}
