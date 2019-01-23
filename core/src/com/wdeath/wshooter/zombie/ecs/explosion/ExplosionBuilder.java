package com.wdeath.wshooter.zombie.ecs.explosion;

import com.badlogic.ashley.core.Entity;
import com.wdeath.wshooter.zombie.ecs.explosion.coms.ExplosionSpawnComponent;

public abstract class ExplosionBuilder {

    protected Entity exp;
    protected Entity world;

    public ExplosionBuilder create(Entity world){
        exp = new Entity();
        this.world = world;
        return this;
    }

    public abstract ExplosionBuilder createData(ExplosionSpawnComponent spawn);
    public abstract ExplosionBuilder createLight(ExplosionSpawnComponent spawn);

    public ExplosionBuilder attachToBody(ExplosionSpawnComponent spawn){
        return this;
    }

    public Entity get(){
        return exp;
    }
}
