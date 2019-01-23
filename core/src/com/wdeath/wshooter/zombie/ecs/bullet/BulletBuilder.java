package com.wdeath.wshooter.zombie.ecs.bullet;

import box2dLight.PointLight;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.wdeath.wshooter.zombie.damage.DamageType;
import com.wdeath.wshooter.zombie.ecs.bullet.coms.BulletDataComponent;
import com.wdeath.wshooter.zombie.ecs.bullet.coms.BulletLightComponent;
import com.wdeath.wshooter.zombie.ecs.bullet.coms.BulletPhysicsComponent;
import com.wdeath.wshooter.zombie.ecs.world.BodyTypePhysics;
import com.wdeath.wshooter.zombie.ecs.world.component.WorldLightComponent;
import com.wdeath.wshooter.zombie.ecs.world.component.WorldPhysicsComponent;

public class BulletBuilder {

    private Entity bullet;

    public BulletBuilder create(){
        bullet = new Entity();
        return this;
    }

    public BulletBuilder createData(DamageType shooter, String name, float damage){
        BulletDataComponent data = new BulletDataComponent();
        data.shooter = shooter;
        data.nameShooter = name;
        data.damage = damage;
        bullet.add(data);
        return this;
    }

    public BulletBuilder createPhysics(WorldPhysicsComponent world, Vector2 point, float angle){
        BulletDataComponent data = bullet.getComponent(BulletDataComponent.class);
        data.angle = angle;
        data.pointStart = point;

        BulletPhysicsComponent physics = new BulletPhysicsComponent();
        physics.body = createBody(world, point);
        physics.fixture = createFixture(physics);
        bullet.add(physics);
        float speed = 100;
        physics.body.setLinearVelocity(speed * (float)Math.cos(angle), speed * (float)Math.sin(angle));

        return this;
    }

    public BulletBuilder createLight(WorldLightComponent lightComponent){
        BulletLightComponent light = new BulletLightComponent();
        BulletPhysicsComponent physics = bullet.getComponent(BulletPhysicsComponent.class);

        PointLight pl = new PointLight(lightComponent.rayHandler, 50);
        pl.setDistance(6);
        Color color = Color.RED;
        pl.setColor(color);
        pl.setSoftnessLength(5);
        pl.attachToBody(physics.body);
        light.light = pl;
        bullet.add(light);

        return this;
    }

    private Body createBody(WorldPhysicsComponent world, Vector2 point){
        BodyDef def = new BodyDef();
        def.position.set(point);
        def.type = BodyDef.BodyType.DynamicBody;
        def.bullet = true;
        Body body = world.world.createBody(def);
        BodyTypePhysics type = new BodyTypePhysics();
        type.data = bullet;
        type.type = BodyTypePhysics.BodyType.BULLET;
        body.setUserData(type);
        return body;
    }

    private Fixture createFixture(BulletPhysicsComponent physics){
        CircleShape shape = new CircleShape();
        shape.setRadius(0.1f);

        FixtureDef def = new FixtureDef();
        def.density = 2;
        def.shape = shape;

        Fixture fix = physics.body.createFixture(def);
        return fix;
    }

    public Entity get(){
        return bullet;
    }
}
