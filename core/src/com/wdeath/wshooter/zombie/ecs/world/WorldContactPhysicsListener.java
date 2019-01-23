package com.wdeath.wshooter.zombie.ecs.world;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.wdeath.wshooter.zombie.damage.Damage;
import com.wdeath.wshooter.zombie.damage.DamageType;
import com.wdeath.wshooter.zombie.ecs.bullet.coms.BulletDataComponent;
import com.wdeath.wshooter.zombie.ecs.bullet.coms.BulletDeleteComponent;
import com.wdeath.wshooter.zombie.ecs.player.coms.PlayerDamageComponent;
import com.wdeath.wshooter.zombie.ecs.zombie.coms.ZombieDamageComponent;

public class WorldContactPhysicsListener implements ContactListener {


    @Override
    public void beginContact(Contact contact) {

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        Pack pack = getA(BodyTypePhysics.BodyType.BULLET, BodyTypePhysics.BodyType.WORLD_WALL, contact);
        if(pack != null){
            Entity bulletEnt = ((Entity)((BodyTypePhysics)pack.a.getBody().getUserData()).data);
            bulletEnt.add(new BulletDeleteComponent());
        }
        bulletToZombie(contact);
        playerAndZombie(contact);
    }
    private void playerAndZombie(Contact contact) {
        Pack pack = getA(BodyTypePhysics.BodyType.ZOMBIE, BodyTypePhysics.BodyType.PLAYER, contact);
        if (pack == null) {
            return;
        }
        Entity player = ((Entity)((BodyTypePhysics)pack.b.getBody().getUserData()).data);
        Entity zombie = ((Entity)((BodyTypePhysics)pack.a.getBody().getUserData()).data);
        float angle = 0;
        Vector2 posP = pack.b.getBody().getPosition();
        Vector2 posZ = pack.a.getBody().getPosition();
        angle = (float)Math.atan2(posP.y - posZ.y, posP.x - posZ.x);
        float s = 10;
        Vector2 vec = new Vector2(s,s).rotate((float)Math.toDegrees(angle - 180));
        System.out.println(vec);
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
        Entity bulletEnt = ((Entity)((BodyTypePhysics)pack.a.getBody().getUserData()).data);
        BulletDataComponent data = bulletEnt.getComponent(BulletDataComponent.class);
        bulletEnt.add(new BulletDeleteComponent());

        Entity zombie = ((Entity)((BodyTypePhysics)pack.b.getBody().getUserData()).data);
        ZombieDamageComponent damageComponent = zombie.getComponent(ZombieDamageComponent.class);
        Damage damage = new Damage();
        damage.damage = data.damage;
        damage.name = data.nameShooter;
        damage.type = data.shooter;
        damageComponent.list.add(damage);
    }

    private Pack getA(BodyTypePhysics.BodyType typeA, BodyTypePhysics.BodyType typeB, Contact contact){
        Object objA = contact.getFixtureA().getBody().getUserData();
        Object objB = contact.getFixtureB().getBody().getUserData();
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
