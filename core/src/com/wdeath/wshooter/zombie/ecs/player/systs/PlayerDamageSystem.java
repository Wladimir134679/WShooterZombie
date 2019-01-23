package com.wdeath.wshooter.zombie.ecs.player.systs;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wdeath.wshooter.zombie.damage.Damage;
import com.wdeath.wshooter.zombie.ecs.game.GameType;
import com.wdeath.wshooter.zombie.ecs.game.coms.GameComponent;
import com.wdeath.wshooter.zombie.ecs.player.coms.PlayerDamageComponent;
import com.wdeath.wshooter.zombie.ecs.player.coms.PlayerHealthComponent;

public class PlayerDamageSystem extends IteratingSystem {

    private Entity game;

    public PlayerDamageSystem(Entity game){
        super(Family.all(PlayerDamageComponent.class).get());
        this.game = game;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PlayerDamageComponent damageComponent = entity.getComponent(PlayerDamageComponent.class);
        PlayerHealthComponent healthComponent = entity.getComponent(PlayerHealthComponent.class);

        int size = damageComponent.list.size();
        for(int i = 0; i < size; i++){
            Damage damage = damageComponent.list.get(0);
            healthComponent.current -= damage.damage;
            damageComponent.list.remove(0);
        }

        if(healthComponent.current <= 0){
            GameComponent gameComponent = game.getComponent(GameComponent.class);
            gameComponent.type = GameType.DEFEAT;
        }
    }
}
