package com.wdeath.wshooter.zombie.ecs.player.systs;

import com.badlogic.ashley.core.*;
import com.wdeath.wshooter.zombie.ecs.player.coms.PlayerComponent;

import java.util.HashMap;

public class PlayerControllerSystem extends EntitySystem {

    public HashMap<String, Entity> players;

    public PlayerControllerSystem(){
        players = new HashMap<>();
    }

    @Override
    public void addedToEngine(Engine engine) {
        engine.addEntityListener(Family.all(PlayerComponent.class).get(), new EntityListener() {
            @Override
            public void entityAdded(Entity entity) {
                PlayerComponent data = entity.getComponent(PlayerComponent.class);
                players.put(data.name, entity);
            }

            @Override
            public void entityRemoved(Entity entity) {
                PlayerComponent data = entity.getComponent(PlayerComponent.class);
                players.remove(data.name);
            }
        });
    }
}
