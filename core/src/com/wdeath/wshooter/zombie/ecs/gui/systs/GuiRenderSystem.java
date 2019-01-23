package com.wdeath.wshooter.zombie.ecs.gui.systs;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.wdeath.wshooter.zombie.ecs.gui.coms.GuiComponent;

public class GuiRenderSystem extends EntitySystem {

    private Entity guiEntity;

    public GuiRenderSystem(Entity gui) {
        this.guiEntity = gui;
    }

    @Override
    public void update(float deltaTime) {
        GuiComponent gui = guiEntity.getComponent(GuiComponent.class);
        gui.stage.act(1/60f);
        gui.stage.draw();
    }
}
