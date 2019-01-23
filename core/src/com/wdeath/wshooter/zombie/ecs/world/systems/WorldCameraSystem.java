package com.wdeath.wshooter.zombie.ecs.world.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.wdeath.wshooter.zombie.ecs.player.coms.PlayerComponent;
import com.wdeath.wshooter.zombie.ecs.player.coms.PlayerPhysicsComponent;
import com.wdeath.wshooter.zombie.ecs.world.component.WorldDrawComponent;

public class WorldCameraSystem extends EntitySystem {

    private Entity world, player = null;
    private String namePlayer;

    public WorldCameraSystem(Entity world, String namePlayer) {
        this.world = world;
        this.namePlayer = namePlayer;
    }

    @Override
    public void addedToEngine(Engine engine) {
        engine.addEntityListener(Family.all(PlayerComponent.class).get(), new EntityListener() {
            @Override
            public void entityAdded(Entity entity) {
                PlayerComponent data = entity.getComponent(PlayerComponent.class);
                if(!data.name.equals(namePlayer))
                    return;
                player = entity;
            }

            @Override
            public void entityRemoved(Entity entity) {

            }
        });
        super.addedToEngine(engine);
    }

    @Override
    public void update(float deltaTime) {
        WorldDrawComponent drawComponent = world.getComponent(WorldDrawComponent.class);
        drawComponent.camera.update();

        if(player == null)
            return;
        OrthographicCamera camera = drawComponent.camera;
        PlayerPhysicsComponent physicsComponent = player.getComponent(PlayerPhysicsComponent.class);

        Vector2 posPlayer = physicsComponent.body.getPosition();
        float angle = physicsComponent.body.getAngle();
        posPlayer.x += 5 * Math.cos(angle);
        posPlayer.y += 5 * Math.sin(angle);

        camera.position.set(posPlayer, 0);

        float width = camera.viewportWidth * camera.zoom;
        float height = camera.viewportHeight * camera.zoom;

        if(camera.position.x < 0 + width / 2)
            camera.position.x = width / 2;
        if(camera.position.y < 0 + height / 2)
            camera.position.y = height / 2;

        if(camera.position.x + width / 2 > (int)drawComponent.mapRenderer.getMap().getProperties().get("width")){
            camera.position.x = ((int)drawComponent.mapRenderer.getMap().getProperties().get("width")) - width / 2;
        }
        if(camera.position.y + height / 2 > (int)drawComponent.mapRenderer.getMap().getProperties().get("height")){
            camera.position.y = ((int)drawComponent.mapRenderer.getMap().getProperties().get("height")) - height / 2;
        }
    }
}
