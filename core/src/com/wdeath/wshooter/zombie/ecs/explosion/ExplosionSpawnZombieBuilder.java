package com.wdeath.wshooter.zombie.ecs.explosion;

import box2dLight.PointLight;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.Filter;
import com.wdeath.wshooter.zombie.ecs.explosion.coms.ExplosionDataComponent;
import com.wdeath.wshooter.zombie.ecs.explosion.coms.ExplosionLightComponent;
import com.wdeath.wshooter.zombie.ecs.explosion.coms.ExplosionSpawnComponent;
import com.wdeath.wshooter.zombie.ecs.world.BodyTypePhysics;
import com.wdeath.wshooter.zombie.ecs.world.component.WorldLightComponent;

import static com.wdeath.wshooter.zombie.ecs.explosion.ExplosionType.SPAWN_ZOMBIE;

public class ExplosionSpawnZombieBuilder extends ExplosionBuilder{
    @Override
    public ExplosionBuilder createData(ExplosionSpawnComponent spawn) {
        ExplosionDataComponent data = new ExplosionDataComponent();
        data.pos = spawn.pos;
        data.type = SPAWN_ZOMBIE;
        exp.add(data);
        return this;
    }

    @Override
    public ExplosionBuilder createLight(ExplosionSpawnComponent spawn) {
        ExplosionLightComponent lightComponent = new ExplosionLightComponent();
        PointLight light = new PointLight(world.getComponent(WorldLightComponent.class).rayHandler, 100);
        light.setDistance(20);
        light.setColor(Color.GREEN);
        light.setPosition(spawn.pos);
        light.setSoftnessLength(0.1f);
        lightComponent.light = light;
        Filter filter = new Filter();
        filter.categoryBits = BodyTypePhysics.CATEGORY_LIGHT;
        filter.maskBits = BodyTypePhysics.MASK_LIGHT_EXPLOSION;
        light.setContactFilter(filter);
        exp.add(lightComponent);
        return this;
    }
}
