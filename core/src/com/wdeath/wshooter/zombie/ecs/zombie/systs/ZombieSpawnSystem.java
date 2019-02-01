package com.wdeath.wshooter.zombie.ecs.zombie.systs;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.wdeath.wshooter.zombie.ecs.explosion.ExplosionSpawnZombieBuilder;
import com.wdeath.wshooter.zombie.ecs.explosion.ExplosionType;
import com.wdeath.wshooter.zombie.ecs.explosion.coms.ExplosionSpawnComponent;
import com.wdeath.wshooter.zombie.ecs.world.component.WorldPhysicsComponent;
import com.wdeath.wshooter.zombie.ecs.zombie.ZombieBuilder;
import com.wdeath.wshooter.zombie.ecs.zombie.coms.ZombieSpawnComponent;

public class ZombieSpawnSystem extends IteratingSystem {

    private Entity world;
    private ZombieBuilder builder;

    public ZombieSpawnSystem(Entity world){
        super(Family.all(ZombieSpawnComponent.class).get());
        this.world = world;
        builder = new ZombieBuilder();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        ZombieSpawnComponent spawn = entity.getComponent(ZombieSpawnComponent.class);
        createZombie(spawn);
        createLight(spawn.pos);
        getEngine().removeEntity(entity);
    }

    private void createZombie(ZombieSpawnComponent spawn){
        Entity zomb = builder.create()
                .createData(spawn)
                .createPhysics(world.getComponent(WorldPhysicsComponent.class))
                .createHealth()
                .createDamage()
                .createSensor()
                .createMove()
                .get();
        getEngine().addEntity(zomb);
    }

    private void createLight(Vector2 pos){
        ExplosionSpawnComponent spawn = new ExplosionSpawnComponent();
        spawn.pos = pos;
        spawn.type = ExplosionType.SPAWN_ZOMBIE;
        Entity entity = new Entity();
        entity.add(spawn);
        getEngine().addEntity(entity);
    }
}
