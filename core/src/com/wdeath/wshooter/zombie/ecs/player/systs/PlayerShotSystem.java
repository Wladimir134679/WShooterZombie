package com.wdeath.wshooter.zombie.ecs.player.systs;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.wdeath.wshooter.zombie.damage.DamageType;
import com.wdeath.wshooter.zombie.ecs.bullet.BulletBuilder;
import com.wdeath.wshooter.zombie.ecs.explosion.ExplosionType;
import com.wdeath.wshooter.zombie.ecs.explosion.coms.ExplosionSpawnComponent;
import com.wdeath.wshooter.zombie.ecs.player.coms.PlayerMoveComponent;
import com.wdeath.wshooter.zombie.ecs.player.coms.PlayerPhysicsComponent;
import com.wdeath.wshooter.zombie.ecs.player.coms.PlayerWeaponComponent;
import com.wdeath.wshooter.zombie.game.PlayerGameData;
import com.wdeath.wshooter.zombie.weapon.Weapon;
import com.wdeath.wshooter.zombie.ecs.world.component.WorldLightComponent;
import com.wdeath.wshooter.zombie.ecs.world.component.WorldPhysicsComponent;

public class PlayerShotSystem extends IteratingSystem {

    private Entity world;
    private BulletBuilder bulletBuilder;

    public PlayerShotSystem(Entity world){
        super(Family.all(PlayerMoveComponent.class, PlayerWeaponComponent.class).get());
        this.world = world;
        bulletBuilder = new BulletBuilder();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PlayerMoveComponent move = entity.getComponent(PlayerMoveComponent.class);
        PlayerPhysicsComponent physics = entity.getComponent(PlayerPhysicsComponent.class);
        PlayerWeaponComponent weaponComponent = entity.getComponent(PlayerWeaponComponent.class);
        Weapon weapon = weaponComponent.weapon;

        weapon.timeShot += deltaTime;
        if(weapon.isRecharge){
            weapon.timeRecharge += deltaTime;
            if(weapon.timeRecharge >= weapon.data.timeRecharge) {
                weapon.isRecharge = false;
                weapon.timeRecharge = 0;
                weapon.shotNum = weapon.data.store;
            }
            return;
        }
        if(move.shot){
            if(weapon.shotNum <= 0){
                weapon.isRecharge = true;
                return;
            }
            if(weapon.timeShot >= weapon.data.timeShot) {
                weapon.shotNum--;
                weapon.timeShot = 0;
                float angle = physics.body.getAngle();
                Vector2 posSpawn = physics.body.getPosition();
                posSpawn.x += 0.7f * (float)Math.cos(angle);
                posSpawn.y += 0.7f * (float)Math.sin(angle);
                Entity bullet = bulletBuilder
                        .create()
                        .createData(DamageType.PLAYER, PlayerGameData.NAME_PLAYER, weapon.data.damage)
                        .createPhysics(
                                world.getComponent(WorldPhysicsComponent.class),
                                posSpawn,
                                angle)
                        .createLight(world.getComponent(WorldLightComponent.class))
                        .get();
                getEngine().addEntity(bullet);

                Entity expl = new Entity();
                ExplosionSpawnComponent spawn = new ExplosionSpawnComponent();
                spawn.type = ExplosionType.SHOT;
                spawn.pos = posSpawn;
                spawn.body = physics.body;
                spawn.offsetX = 0.6f;
                expl.add(spawn);
                getEngine().addEntity(expl);
            }
        }

    }
}
