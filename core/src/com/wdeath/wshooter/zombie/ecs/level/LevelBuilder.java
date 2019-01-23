package com.wdeath.wshooter.zombie.ecs.level;

import com.badlogic.ashley.core.Entity;
import com.wdeath.wshooter.zombie.ecs.level.coms.LevelComponent;

public class LevelBuilder {

    private Entity level;

    public LevelBuilder create(){
        level = new Entity();
        return this;
    }

    public LevelBuilder createData(LevelData data){
        LevelComponent levelComponent = new LevelComponent();
        levelComponent.data = data;
        level.add(levelComponent);
        return this;
    }

    public Entity get(){
        return level;
    }
}
