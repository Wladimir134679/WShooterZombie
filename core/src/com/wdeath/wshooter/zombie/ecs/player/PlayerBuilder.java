package com.wdeath.wshooter.zombie.ecs.player;

import box2dLight.ConeLight;
import box2dLight.PointLight;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.wdeath.wshooter.zombie.ecs.player.coms.*;
import com.wdeath.wshooter.zombie.weapon.Weapon;
import com.wdeath.wshooter.zombie.weapon.WeaponData;
import com.wdeath.wshooter.zombie.ecs.world.BodyTypePhysics;
import com.wdeath.wshooter.zombie.ecs.world.component.WorldLightComponent;
import com.wdeath.wshooter.zombie.ecs.world.component.WorldPhysicsComponent;

public class PlayerBuilder {

    private Entity entity;

    public PlayerBuilder create(){
        entity = new Entity();
        return this;
    }

    public PlayerBuilder createData(String name){
        PlayerComponent data = new PlayerComponent();
        data.name = name;
        entity.add(data);
        return this;
    }

    public PlayerBuilder createMove(){
        PlayerMoveComponent move = new PlayerMoveComponent();
        entity.add(move);
        return this;
    }

    public PlayerBuilder createPhysics(PlayerSpawnComponent spawn, WorldPhysicsComponent worldPhysics){
        PlayerPhysicsComponent physics = new PlayerPhysicsComponent();
        physics.body = createBody(spawn, worldPhysics);
        physics.body.setLinearDamping(1f);
        physics.fixture = createFixture(physics, spawn, worldPhysics);
        entity.add(physics);
        return this;
    }

    public PlayerBuilder createHealth(PlayerSpawnComponent spawn){
        PlayerHealthComponent health = new PlayerHealthComponent();
        health.max = spawn.maxHealth;
        health.current = health.max;
        entity.add(health);
        return this;
    }

    public PlayerBuilder createDamage(){
        PlayerDamageComponent damageComponent = new PlayerDamageComponent();
        entity.add(damageComponent);
        return this;
    }

    private Body createBody(PlayerSpawnComponent spawn, WorldPhysicsComponent worldPhysics){
        BodyDef def = new BodyDef();
        def.fixedRotation = true;
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(spawn.spawnPoint.x, spawn.spawnPoint.y);
        Body body = worldPhysics.world.createBody(def);

        BodyTypePhysics type = new BodyTypePhysics();
        type.type = BodyTypePhysics.BodyType.PLAYER;
        type.data = entity;
        body.setUserData(type);

        return body;
    }

    private Fixture createFixture(PlayerPhysicsComponent physics, PlayerSpawnComponent spawn, WorldPhysicsComponent worldPhysics){
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.20f, 0.5f);
        FixtureDef def = new FixtureDef();
        def.friction = 1f;
        def.shape = shape;
        def.filter.groupIndex = BodyTypePhysics.PLAYER_COM_GROUP;
        def.filter.categoryBits = BodyTypePhysics.CATEGORY_PLAYER;
        def.filter.maskBits = BodyTypePhysics.MASK_PLAYER;

        Fixture fix = physics.body.createFixture(def);
        return fix;
    }

    public PlayerBuilder createLight(WorldLightComponent lightComponent){
        PlayerLightComponent light = new PlayerLightComponent();

        Filter filter = new Filter();
        filter.groupIndex = BodyTypePhysics.PLAYER_COM_GROUP;

        float angle = 40;
        ConeLight lantern = new ConeLight(lightComponent.rayHandler, 50, Color.BLACK, 0, 0, 0, -angle/2, angle/2);
        lantern.setDistance(10f);
        lantern.setSoftnessLength(0.5f);
        lantern.setContactFilter(filter);
        lantern.attachToBody(entity.getComponent(PlayerPhysicsComponent.class).body, 0.4f, 0);
        light.lanter = lantern;

        PointLight autra = new PointLight(lightComponent.rayHandler, 100);
        autra.setColor(Color.BLACK);
        autra.setDistance(2f);
        autra.setSoftnessLength(0.5f);
        autra.setContactFilter(filter);
        autra.attachToBody(entity.getComponent(PlayerPhysicsComponent.class).body);
        light.aura = autra;

        entity.add(light);
        return this;
    }

    public PlayerBuilder createWeapon(){
        PlayerWeaponComponent weaponComponent = new PlayerWeaponComponent();
        Weapon weapon = new Weapon();
        weapon.data = WeaponData.weapons.get("Gun");
        weapon.shotNum = weapon.data.ammunitionMax;
        weapon.timeShot = 0;
        weaponComponent.weapon = weapon;
        entity.add(weaponComponent);
        createWeaponBody();
        return this;
    }

    private void createWeaponBody(){
        PolygonShape shape = new PolygonShape();
        Vector2[] posArr = new Vector2[4];
        int id = 0;
        posArr[id++] = new Vector2(0, -0.1f);
        posArr[id++] = new Vector2(0.5f, -0.1f);
        posArr[id++] = new Vector2(0.5f, 0.1f);
        posArr[id++] = new Vector2(0,  0.1f);
        shape.set(posArr);
        FixtureDef def = new FixtureDef();
        def.shape = shape;
        def.filter.groupIndex = BodyTypePhysics.PLAYER_COM_GROUP;
        def.filter.categoryBits = BodyTypePhysics.CATEGORY_PLAYER;
        def.filter.maskBits = BodyTypePhysics.MASK_PLAYER;
        PlayerPhysicsComponent physicsComponent = entity.getComponent(PlayerPhysicsComponent.class);
        Fixture fix = physicsComponent.body.createFixture(def);
        physicsComponent.weapon = fix;
    }

    public Entity get(){
        return entity;
    }
}
