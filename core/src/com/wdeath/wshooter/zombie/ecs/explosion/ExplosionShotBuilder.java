package com.wdeath.wshooter.zombie.ecs.explosion;

import box2dLight.PointLight;
import com.badlogic.gdx.graphics.Color;
import com.wdeath.wshooter.zombie.ecs.explosion.coms.ExplosionDataComponent;
import com.wdeath.wshooter.zombie.ecs.explosion.coms.ExplosionLightComponent;
import com.wdeath.wshooter.zombie.ecs.explosion.coms.ExplosionSpawnComponent;
import com.wdeath.wshooter.zombie.ecs.world.component.WorldLightComponent;

public class ExplosionShotBuilder extends ExplosionBuilder {

    @Override
    public ExplosionBuilder createData(ExplosionSpawnComponent spawn) {
        ExplosionDataComponent data = new ExplosionDataComponent();
        data.pos = spawn.pos;
        data.type = spawn.type;
        exp.add(data);
        return this;
    }

    @Override
    public ExplosionBuilder createLight(ExplosionSpawnComponent spawn) {
        ExplosionLightComponent light = new ExplosionLightComponent();
        PointLight point = new PointLight(world.getComponent(WorldLightComponent.class).rayHandler, 100);
        point.setDistance(20);
        Color color = new Color();
        color.r = 0.6f;
        color.a = 0.5f;
        point.setColor(color);
        point.setSoftnessLength(5f);
        point.setPosition(spawn.pos);
        light.light = point;
        exp.add(light);
        return this;
    }

    @Override
    public ExplosionBuilder attachToBody(ExplosionSpawnComponent spawn) {
        ExplosionLightComponent light = exp.getComponent(ExplosionLightComponent.class);
        light.light.attachToBody(spawn.body, spawn.offsetX, spawn.offsetY);
        return this;
    }
}
