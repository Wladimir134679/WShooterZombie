package com.wdeath.wshooter.zombie.ecs.explosion.systs;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wdeath.wshooter.zombie.ecs.explosion.ExplosionBuilder;
import com.wdeath.wshooter.zombie.ecs.explosion.ExplosionShotBuilder;
import com.wdeath.wshooter.zombie.ecs.explosion.ExplosionSpawnZombieBuilder;
import com.wdeath.wshooter.zombie.ecs.explosion.coms.ExplosionSpawnComponent;

public class ExplosionSpawnSystem extends IteratingSystem {

    private Entity world;
    private ExplosionBuilder builderShot;
    private ExplosionSpawnZombieBuilder builderZombie;

    public ExplosionSpawnSystem(Entity world) {
        super(Family.all(ExplosionSpawnComponent.class).get());
        this.world = world;
        builderShot = new ExplosionShotBuilder();
        builderZombie = new ExplosionSpawnZombieBuilder();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        ExplosionSpawnComponent spawn = entity.getComponent(ExplosionSpawnComponent.class);
        Entity expl = null;
        switch (spawn.type){
            case SHOT: expl = createShot(spawn); break;
            case SPAWN_ZOMBIE: expl = createZombie(spawn); break;
        }
        if(expl != null)
            getEngine().addEntity(expl);
        getEngine().removeEntity(entity);
    }

    private Entity createShot(ExplosionSpawnComponent spawn){
        Entity expl = builderShot
                .create(world)
                .createData(spawn)
                .createLight(spawn)
                .attachToBody(spawn)
                .get();
        return expl;
    }

    private Entity createZombie(ExplosionSpawnComponent spawn){
        Entity expl = builderZombie
                .create(world)
                .createData(spawn)
                .createLight(spawn)
                .get();
        return expl;
    }
}
