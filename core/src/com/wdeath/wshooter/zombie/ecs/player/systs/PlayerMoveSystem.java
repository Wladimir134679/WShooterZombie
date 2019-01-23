package com.wdeath.wshooter.zombie.ecs.player.systs;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wdeath.wshooter.zombie.ecs.player.coms.PlayerMoveComponent;
import com.wdeath.wshooter.zombie.ecs.player.coms.PlayerPhysicsComponent;

public class PlayerMoveSystem extends IteratingSystem {

    private Entity world;

    public PlayerMoveSystem(Entity world) {
        super(Family.all(PlayerMoveComponent.class).get());
        this.world = world;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PlayerMoveComponent move = entity.getComponent(PlayerMoveComponent.class);
        PlayerPhysicsComponent physics = entity.getComponent(PlayerPhysicsComponent.class);
        float angle = physics.body.getAngle();
        float speed = 4;

        if(move.key[PlayerMoveComponent.CTRL])
            speed += 8;
        if(move.key[PlayerMoveComponent.SHIFT]){
            if (move.key[PlayerMoveComponent.UP]) {
                physics.body.setLinearVelocity(physics.body.getLinearVelocity().x, speed);
            }
            if (move.key[PlayerMoveComponent.DOWN]) {
                physics.body.setLinearVelocity(physics.body.getLinearVelocity().x, -speed);
            }
            if (move.key[PlayerMoveComponent.LEFT]) {
                physics.body.setLinearVelocity(-speed, physics.body.getLinearVelocity().y);
            }
            if (move.key[PlayerMoveComponent.RIGHT]) {
                physics.body.setLinearVelocity(speed, physics.body.getLinearVelocity().y);
            }
            if(!move.key[PlayerMoveComponent.UP] &&
                    !move.key[PlayerMoveComponent.DOWN] &&
                    !move.key[PlayerMoveComponent.LEFT] &&
                    !move.key[PlayerMoveComponent.RIGHT]){
                physics.body.setLinearVelocity(0, 0);
            }
        }else {
            if (move.key[PlayerMoveComponent.UP]) {
                physics.body.setLinearVelocity(speed * (float) Math.cos(angle), speed * (float) Math.sin(angle));
            } else if (move.key[PlayerMoveComponent.DOWN]) {
                physics.body.setLinearVelocity(-speed * (float) Math.cos(angle), -speed * (float) Math.sin(angle));
            } else {
                physics.body.setLinearVelocity(0, 0);
            }
            if (move.key[PlayerMoveComponent.LEFT]) {
                physics.body.setTransform(physics.body.getPosition(), physics.body.getAngle() + 0.05f);
            }
            if (move.key[PlayerMoveComponent.RIGHT]) {
                physics.body.setTransform(physics.body.getPosition(), physics.body.getAngle() - 0.05f);
            }
        }
    }
}
