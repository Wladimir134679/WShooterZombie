package com.wdeath.wshooter.zombie.ecs.world.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.wdeath.wshooter.zombie.ecs.world.component.WorldDrawComponent;

public class WorldDrawTiledMapSystem extends EntitySystem {

    private Entity world;

    public WorldDrawTiledMapSystem(Entity world) {
        this.world = world;
    }

    @Override
    public void update(float deltaTime) {
        WorldDrawComponent draw = world.getComponent(WorldDrawComponent.class);
        float S = 0.005f;
        draw.camera.position.x = Math.round(draw.camera.position.x/S)*S;
        draw.camera.position.y = Math.round(draw.camera.position.y/S)*S;
        draw.mapRenderer.setView(draw.camera);
        draw.mapRenderer.render();
    }
}
