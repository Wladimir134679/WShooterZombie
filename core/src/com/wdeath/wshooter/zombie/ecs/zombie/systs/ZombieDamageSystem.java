package com.wdeath.wshooter.zombie.ecs.zombie.systs;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wdeath.wshooter.zombie.damage.Damage;
import com.wdeath.wshooter.zombie.ecs.level.coms.LevelComponent;
import com.wdeath.wshooter.zombie.ecs.zombie.coms.ZombieDeleteComponent;
import com.wdeath.wshooter.zombie.ecs.zombie.coms.ZombieDamageComponent;
import com.wdeath.wshooter.zombie.ecs.zombie.coms.ZombieHealthComponent;

public class ZombieDamageSystem extends IteratingSystem {
    
    public Entity world;
    
    public ZombieDamageSystem(Entity world){
        super(Family.all(ZombieDamageComponent.class, ZombieHealthComponent.class).get());
        this.world = world;
    }
    
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        ZombieDamageComponent damageComponent = entity.getComponent(ZombieDamageComponent.class);
        ZombieHealthComponent healthComponent = entity.getComponent(ZombieHealthComponent.class);

        int size = damageComponent.list.size();
        for(int i = 0; i < size; i++){
            Damage damage = damageComponent.list.get(0);
            damageComponent.list.remove(0);

            healthComponent.current -= damage.damage;

            boolean death = death(damage, healthComponent, entity);
            if(death)
                break;
        }
    }
    
    private boolean death(Damage damage, ZombieHealthComponent healthComponent, Entity entity){
        if(healthComponent.current <= 0){
            Entity levelEntity = getEngine().getEntitiesFor(Family.one(LevelComponent.class).get()).get(0);
            LevelComponent levelComponent = levelEntity.getComponent(LevelComponent.class);
            levelComponent.numKill += 1;

            ZombieDeleteComponent delete = new ZombieDeleteComponent();
            entity.add(delete);
            return true;
        }else{
            return false;
        }
    }
}
