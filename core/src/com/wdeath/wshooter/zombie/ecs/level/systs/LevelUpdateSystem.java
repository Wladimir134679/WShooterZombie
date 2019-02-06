package com.wdeath.wshooter.zombie.ecs.level.systs;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.wdeath.wshooter.zombie.ecs.level.LevelData;
import com.wdeath.wshooter.zombie.ecs.level.coms.LevelComponent;
import com.wdeath.wshooter.zombie.ecs.world.component.WorldLevelComponent;
import com.wdeath.wshooter.zombie.ecs.world.component.WorldSpawnComponent;
import com.wdeath.wshooter.zombie.ecs.zombie.ZombieBuilder;
import com.wdeath.wshooter.zombie.ecs.zombie.coms.ZombieSpawnComponent;

public class LevelUpdateSystem extends IteratingSystem {

    private Entity world;
    private ZombieBuilder builder;

    public LevelUpdateSystem(Entity world){
        super(Family.one(LevelComponent.class).get());
        this.world = world;
        builder = new ZombieBuilder();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        LevelComponent level = entity.getComponent(LevelComponent.class);
        if(!level.isRun){
            level.timeBegin += deltaTime;
            if(level.timeBegin >= level.data.timeBegin) {
                level.isRun = true;
                createWave(level);
            }
            return;
        }
        level.timeSpawn += deltaTime;
        if(level.timeSpawn >= level.data.sleep){
            level.timeSpawn = 0;
            createWave(level);
        }
    }

    private void createWave(LevelComponent level){
        WorldSpawnComponent spawns = world.getComponent(WorldSpawnComponent.class);
        for(Vector2 pos : spawns.spawnZombies){
            spawn(pos);
        }
    }

    private void spawn(Vector2 pos){
        ZombieSpawnComponent spawn = new ZombieSpawnComponent();
        spawn.angle = MathUtils.random(0f, MathUtils.PI2);
        spawn.pos = pos;
        spawn.speed = 2;
        spawn.maxHealth = 10;

        Entity zombieSpawn = new Entity();
        zombieSpawn.add(spawn);
        getEngine().addEntity(zombieSpawn);
    }
}
