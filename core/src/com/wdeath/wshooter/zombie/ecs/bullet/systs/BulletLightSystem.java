package com.wdeath.wshooter.zombie.ecs.bullet.systs;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wdeath.wshooter.zombie.ecs.bullet.coms.BulletLightComponent;

public class BulletLightSystem extends IteratingSystem {

    public BulletLightSystem() {
        super(Family.all(BulletLightComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BulletLightComponent light = entity.getComponent(BulletLightComponent.class);
        light.light.setDistance(light.light.getDistance() - 0.1f);
        light.light.update();
        if(light.light.getDistance() <= 0.01f) {
            light.light.setActive(false);
            light.light.remove();
            entity.remove(BulletLightComponent.class);
        }
    }
}
