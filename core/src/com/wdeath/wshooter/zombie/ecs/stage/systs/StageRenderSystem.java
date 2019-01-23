package com.wdeath.wshooter.zombie.ecs.stage.systs;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wdeath.wshooter.zombie.ecs.stage.coms.StageComponent;

public class StageRenderSystem extends IteratingSystem {

    public StageRenderSystem(){
        super(Family.all(StageComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        StageComponent sceneGameComponent = entity.getComponent(StageComponent.class);
        sceneGameComponent.stage.act(1/60f);
        sceneGameComponent.stage.draw();
    }
}
