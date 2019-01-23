package com.wdeath.wshooter.zombie.ecs.world.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.wdeath.wshooter.zombie.ecs.world.component.WorldDrawComponent;
import com.wdeath.wshooter.zombie.ecs.world.component.WorldLightComponent;

public class WorldUpdateLightSystem extends EntitySystem {

    private Entity world;

    public WorldUpdateLightSystem(Entity world) {
        this.world = world;
    }

    @Override
    public void update(float deltaTime) {
        WorldLightComponent lightComponent = world.getComponent(WorldLightComponent.class);
        WorldDrawComponent draw = world.getComponent(WorldDrawComponent.class);

        lightComponent.rayHandler.update();
        lightComponent.rayHandler.setCombinedMatrix(draw.camera);
        lightComponent.rayHandler.render();
    }
}
