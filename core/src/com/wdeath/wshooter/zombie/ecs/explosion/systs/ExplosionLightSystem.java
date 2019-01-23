package com.wdeath.wshooter.zombie.ecs.explosion.systs;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wdeath.wshooter.zombie.ecs.explosion.coms.ExplosionLightComponent;

public class ExplosionLightSystem extends IteratingSystem {

    public ExplosionLightSystem(){
        super(Family.all(ExplosionLightComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        ExplosionLightComponent light = entity.getComponent(ExplosionLightComponent.class);
        light.light.setDistance(light.light.getDistance() - 1f);
        light.light.update();
        if(light.light.getDistance() <= 0.01f){
            light.light.remove();
            getEngine().removeEntity(entity);
        }
    }
}
