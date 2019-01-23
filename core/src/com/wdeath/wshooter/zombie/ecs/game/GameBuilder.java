package com.wdeath.wshooter.zombie.ecs.game;

import com.badlogic.ashley.core.Entity;
import com.wdeath.wshooter.zombie.ecs.game.coms.GameComponent;

public class GameBuilder {

    Entity game;

    public GameBuilder createData(){
        GameComponent gameComponent = new GameComponent();
        gameComponent.type = GameType.RUN;
        game = new Entity();
        game.add(gameComponent);
        return this;
    }

    public Entity get(){
        return game;
    }
}
