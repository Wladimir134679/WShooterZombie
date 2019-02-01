package com.wdeath.wshooter.zombie.ecs.world;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.wdeath.wshooter.zombie.damage.Damage;
import com.wdeath.wshooter.zombie.damage.DamageType;
import com.wdeath.wshooter.zombie.ecs.bullet.coms.BulletDataComponent;
import com.wdeath.wshooter.zombie.ecs.bullet.coms.BulletDeleteComponent;
import com.wdeath.wshooter.zombie.ecs.player.coms.PlayerDamageComponent;
import com.wdeath.wshooter.zombie.ecs.player.coms.PlayerPhysicsComponent;
import com.wdeath.wshooter.zombie.ecs.zombie.coms.ZombieDamageComponent;
import com.wdeath.wshooter.zombie.ecs.zombie.coms.ZombieMoveComponent;

public class WorldContactPhysicsListener implements ContactListener {


    @Override
    public void beginContact(Contact contact) {
        bulletAndWorldWall(contact);
        bulletToZombie(contact);
        playerAndZombie(contact);

        playerAndSensorZombieBegin(contact);
    }

    @Override
    public void endContact(Contact contact) {
        playerAndSensorZombieEnd(contact);
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }

    private void bulletAndWorldWall(Contact contact){
        Pack pack = getA(BodyTypePhysics.BodyType.BULLET, BodyTypePhysics.BodyType.WORLD_WALL, contact);
        if(pack == null)
            return;
        Entity bulletEnt = ((Entity)((BodyTypePhysics)pack.a.getUserData()).data);
        bulletEnt.add(new BulletDeleteComponent());
    }

    private void playerAndSensorZombieBegin(Contact contact){
        Pack pack = getA(BodyTypePhysics.BodyType.SENSOR_ZOMBIE, BodyTypePhysics.BodyType.PLAYER, contact);
        if (pack == null) {
            return;
        }
        Entity zombie = ((Entity)((BodyTypePhysics)pack.a.getUserData()).data);
        Entity player = ((Entity)((BodyTypePhysics)pack.b.getUserData()).data);

        PlayerPhysicsComponent phP = player.getComponent(PlayerPhysicsComponent.class);

        ZombieMoveComponent moveZ = zombie.getComponent(ZombieMoveComponent.class);
        moveZ.purse = phP.body;
    }

    private void playerAndSensorZombieEnd(Contact contact){
        Pack pack = getA(BodyTypePhysics.BodyType.SENSOR_ZOMBIE, BodyTypePhysics.BodyType.PLAYER, contact);
        if (pack == null) {
            return;
        }
        Entity zombie = ((Entity)((BodyTypePhysics)pack.a.getUserData()).data);
        ZombieMoveComponent moveZ = zombie.getComponent(ZombieMoveComponent.class);
        moveZ.purse = null;
    }

    private void playerAndZombie(Contact contact) {
        Pack pack = getA(BodyTypePhysics.BodyType.ZOMBIE, BodyTypePhysics.BodyType.PLAYER, contact);
        if (pack == null) {
            return;
        }
        Entity player = ((Entity)((BodyTypePhysics)pack.b.getUserData()).data);
        Entity zombie = ((Entity)((BodyTypePhysics)pack.a.getUserData()).data);
        float angle = 0;
        Vector2 posP = pack.b.getBody().getPosition();
        Vector2 posZ = pack.a.getBody().getPosition();
        angle = (float)Math.atan2(posZ.y - posP.y, posZ.x - posP.x);
        float s = 10;
        Vector2 vec = new Vector2(s,s).rotate((float)Math.toDegrees(angle));
        pack.a.getBody().setLinearVelocity(vec);
        PlayerDamageComponent damageComponent = player.getComponent(PlayerDamageComponent.class);
        Damage damage = new Damage();
        damage.type = DamageType.ZOMBIE;
        damage.damage = 1;
        damageComponent.list.add(damage);
    }

    private void bulletToZombie(Contact contact){
        Pack pack = getA(BodyTypePhysics.BodyType.BULLET, BodyTypePhysics.BodyType.ZOMBIE, contact);
        if(pack == null) {
            return;
        }
        Entity bulletEnt = ((Entity)((BodyTypePhysics)pack.a.getUserData()).data);
        BulletDataComponent data = bulletEnt.getComponent(BulletDataComponent.class);
        bulletEnt.add(new BulletDeleteComponent());

        Entity zombie = ((Entity)((BodyTypePhysics)pack.b.getUserData()).data);
        ZombieDamageComponent damageComponent = zombie.getComponent(ZombieDamageComponent.class);
        Damage damage = new Damage();
        damage.damage = data.damage;
        damage.name = data.nameShooter;
        damage.type = data.shooter;
        damageComponent.list.add(damage);
    }

    private Pack getA(BodyTypePhysics.BodyType typeA, BodyTypePhysics.BodyType typeB, Contact contact){
        Object objA = contact.getFixtureA().getUserData();
        Object objB = contact.getFixtureB().getUserData();
        boolean aNotNull = (objA instanceof BodyTypePhysics);
        boolean bNotNull = (objB instanceof BodyTypePhysics);
        if(!aNotNull || !bNotNull)
            return null;
        BodyTypePhysics data1 = ((BodyTypePhysics)objA);
        BodyTypePhysics data2 = ((BodyTypePhysics)objB);
        boolean a;
        boolean b;
        a = aNotNull && data1.type == typeA;
        b = bNotNull && data2.type == typeB;
        Pack pack = new Pack();
        if(a && b){
            pack.a = contact.getFixtureA();
            pack.b = contact.getFixtureB();
            return pack;
        }
        a = aNotNull && data1.type == typeB;
        b = bNotNull && data2.type == typeA;
        if(a && b){
            pack.a = contact.getFixtureB();
            pack.b = contact.getFixtureA();
            return pack;
        }
        return null;
    }

    public static class Pack{
        Fixture a;
        Fixture b;
    }
}
