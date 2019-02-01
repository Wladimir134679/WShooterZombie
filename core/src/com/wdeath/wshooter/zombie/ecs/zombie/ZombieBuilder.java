package com.wdeath.wshooter.zombie.ecs.zombie;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.wdeath.wshooter.zombie.ecs.world.BodyTypePhysics;
import com.wdeath.wshooter.zombie.ecs.world.component.WorldPhysicsComponent;
import com.wdeath.wshooter.zombie.ecs.zombie.coms.*;

public class ZombieBuilder {

    public Entity zomb;
    ZombieSpawnComponent spawn;

    public ZombieBuilder create(){
        zomb = new Entity();
        return this;
    }

    public ZombieBuilder createData(ZombieSpawnComponent spawn){
        this.spawn = spawn;
        ZombieComponent data = new ZombieComponent();
        data.speed = spawn.speed;
        zomb.add(data);
        return this;
    }

    public ZombieBuilder createPhysics(WorldPhysicsComponent worldPhysics){
        ZombiePhysicsComponent physics = new ZombiePhysicsComponent();
        physics.body = createBody(worldPhysics);
        physics.fixtureBody = createFixBody(physics.body);
        physics.sign = createFixSign(physics.body);
        physics.body.setTransform(physics.body.getPosition(), spawn.angle);
        physics.body.setLinearDamping(6);

        zomb.add(physics);
        return this;
    }

    public ZombieBuilder createSensor(){
        CircleShape shape = new CircleShape();
        shape.setRadius(8);
        FixtureDef def = new FixtureDef();
        def.isSensor = true;
        def.shape = shape;
        def.filter.categoryBits = BodyTypePhysics.CATEGORY_SENSOR;
        def.filter.maskBits = BodyTypePhysics.MASK_SENSOR_ZOMBIE;

        ZombiePhysicsComponent physicsComponent = zomb.getComponent(ZombiePhysicsComponent.class);
        Fixture fix = physicsComponent.body.createFixture(def);
        physicsComponent.sensorPlayer = fix;
        BodyTypePhysics.add(fix, zomb, BodyTypePhysics.BodyType.SENSOR_ZOMBIE);
        return this;
    }

    public ZombieBuilder createDamage(){
        ZombieDamageComponent damage = new ZombieDamageComponent();
        zomb.add(damage);
        return this;
    }

    public ZombieBuilder createHealth(){
        ZombieHealthComponent health = new ZombieHealthComponent();
        health.current = spawn.maxHealth;
        health.max = spawn.maxHealth;
        zomb.add(health);
        return this;
    }

    public ZombieBuilder createMove(){
        ZombieMoveComponent move = new ZombieMoveComponent();
        zomb.add(move);
        return this;
    }

    public Entity get(){
        return zomb;
    }


    private Body createBody(WorldPhysicsComponent worldPhysics){
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(spawn.pos);
        def.fixedRotation = false;

        Body body = worldPhysics.world.createBody(def);
        return body;
    }

    private Fixture createFixBody(Body body){
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.2f, 0.4f);
        FixtureDef def = new FixtureDef();
        def.shape = shape;
        def.filter.groupIndex = BodyTypePhysics.GROUP_ZOMBIE;
        def.filter.categoryBits = BodyTypePhysics.CATEGORY_ZOMBIE;
        def.filter.maskBits = BodyTypePhysics.MASK_ZOMBIE;

        Fixture fix = body.createFixture(def);

        return addType(fix);
    }

    private Fixture createFixSign(Body body){
        PolygonShape shape = new PolygonShape();
        int id = 0;
        Vector2[] pos = new Vector2[4];
        pos[id++] = new Vector2(0, 0.1f);
        pos[id++] = new Vector2(0.4f, 0.1f);
        pos[id++] = new Vector2(0.4f, -0.1f);
        pos[id++] = new Vector2(0, - 0.1f);
        shape.set(pos);
        FixtureDef def = new FixtureDef();
        def.filter.groupIndex = BodyTypePhysics.GROUP_ZOMBIE;
        def.filter.categoryBits = BodyTypePhysics.CATEGORY_ZOMBIE;
        def.filter.maskBits = BodyTypePhysics.MASK_ZOMBIE;
        def.shape = shape;

        return addType(body.createFixture(def));
    }

    private Fixture addType(Fixture fix){
        return addType(fix, BodyTypePhysics.BodyType.ZOMBIE);
    }

    private Fixture addType(Fixture fix, BodyTypePhysics.BodyType typeB){
        BodyTypePhysics type = new BodyTypePhysics();
        type.type = typeB;
        type.data = zomb;
        fix.setUserData(type);
        return fix;
    }
}
