package com.wdeath.wshooter.zombie.ecs.player.systs;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wdeath.wshooter.zombie.ecs.player.PlayerBuilder;
import com.wdeath.wshooter.zombie.ecs.player.coms.PlayerSpawnComponent;
import com.wdeath.wshooter.zombie.ecs.world.component.WorldLightComponent;
import com.wdeath.wshooter.zombie.ecs.world.component.WorldPhysicsComponent;
import com.wdeath.wshooter.zombie.game.PlayerGameData;

public class PlayerSpawnSystem extends IteratingSystem {

    private PlayerBuilder builder;
    private Entity world;

    public PlayerSpawnSystem(Entity world) {
        super(Family.all(PlayerSpawnComponent.class).get());
        builder = new PlayerBuilder();
        this.world = world;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        WorldLightComponent lightComponent = world.getComponent(WorldLightComponent.class);
        PlayerSpawnComponent spawn = entity.getComponent(PlayerSpawnComponent.class);
        entity.remove(PlayerSpawnComponent.class);
        Entity player = builder
                .create()
                .createData(spawn.name)
                .createPhysics(spawn, world.getComponent(WorldPhysicsComponent.class))
                .createMove()
                .createLight(lightComponent)
                .createWeapon(PlayerGameData.weapon)
                .createHealth(spawn)
                .createDamage()
                .get();


        getEngine().addEntity(player);
    }
}
