package com.wdeath.wshooter.zombie.ecs.game.systs;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.wdeath.wshooter.zombie.ecs.game.GameType;
import com.wdeath.wshooter.zombie.ecs.game.coms.GameComponent;
import com.wdeath.wshooter.zombie.ecs.level.coms.LevelComponent;
import com.wdeath.wshooter.zombie.ecs.player.coms.PlayerComponent;
import com.wdeath.wshooter.zombie.ecs.player.coms.PlayerHealthComponent;

public class GameListenerSystem extends EntitySystem {

    private Entity game;
    private Entity world;
    private Entity level;
    private Entity player;

    public GameListenerSystem(Entity game, Entity world, Entity level) {
        this.game = game;
        this.world = world;
        this.level = level;
        this.player = null;
    }


    @Override
    public void update(float deltaTime) {
        GameComponent gameComponent = game.getComponent(GameComponent.class);
        if(gameComponent.type == GameType.RUN){
            LevelComponent levelComponent = level.getComponent(LevelComponent.class);
            if(levelComponent.numKill >= levelComponent.data.numberKill){
                gameComponent.type = GameType.VICTORY;
            }
            updatePlayer();
            PlayerHealthComponent playerHealthComponent = player.getComponent(PlayerHealthComponent.class);
            if(playerHealthComponent.current <= 0){
                gameComponent.type = GameType.DEFEAT;
            }
        }
    }

    private void updatePlayer(){
        if(player != null)
            return;
        player = getEngine().getEntitiesFor(Family.one(PlayerComponent.class).get()).get(0);
    }
}
