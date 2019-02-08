package com.wdeath.wshooter.zombie.ecs.player.systs;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.wdeath.wshooter.zombie.ecs.player.coms.PlayerMoveComponent;
import com.wdeath.wshooter.zombie.ecs.player.coms.PlayerPhysicsComponent;
import com.wdeath.wshooter.zombie.ecs.world.component.WorldDrawComponent;
import com.wdeath.wshooter.zombie.utill.GameUtill;

public class PlayerMoveSystem extends IteratingSystem {

    private Entity world;

    public PlayerMoveSystem(Entity world) {
        super(Family.all(PlayerMoveComponent.class).get());
        this.world = world;
    }

    private float clamp(float a){
        if(a < 0) a += 360;
        if(a > 360) a -= 360;
        return Math.max(Math.min(a, 360), 0);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PlayerMoveComponent move = entity.getComponent(PlayerMoveComponent.class);
        PlayerPhysicsComponent physics = entity.getComponent(PlayerPhysicsComponent.class);
        WorldDrawComponent drawWorld = world.getComponent(WorldDrawComponent.class);

        Vector2 posMouse = new Vector2();
        Vector3 mouser = new Vector3();
        mouser.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        mouser = drawWorld.camera.unproject(mouser);
        posMouse.set(mouser.x, mouser.y);

        float angle = (float)Math.toDegrees(physics.body.getAngle());

        angle = GameUtill.deltaAngle(posMouse, physics.body.getPosition(), angle, 6, 5);

        physics.body.setTransform(physics.body.getPosition(), (float)Math.toRadians(angle));

        boolean forwards = move.keys.get(PlayerMoveComponent.UP);
        boolean left = move.keys.get(PlayerMoveComponent.LEFT);
        boolean right = move.keys.get(PlayerMoveComponent.RIGHT);
        boolean backwards = move.keys.get(PlayerMoveComponent.DOWN);
        float angleMove = (float) Math.toRadians(angle);
        physics.body.setLinearVelocity(0, 0);
        if(forwards){
            physics.body.setLinearVelocity(
                    physics.body.getLinearVelocity().x + move.speed * (float) Math.cos(angleMove),
                    physics.body.getLinearVelocity().y + move.speed * (float) Math.sin(angleMove));
        }
        if(backwards){
            physics.body.setLinearVelocity(
                    physics.body.getLinearVelocity().x + -move.speed * (float) Math.cos(angleMove),
                    physics.body.getLinearVelocity().y + -move.speed * (float) Math.sin(angleMove));
        }
        if(left) {
            physics.body.setLinearVelocity(
                    physics.body.getLinearVelocity().x + move.speed * (float) Math.cos(angleMove + MathUtils.PI / 2),
                    physics.body.getLinearVelocity().y + move.speed * (float) Math.sin(angleMove + MathUtils.PI / 2));
        }
        if(right) {
            physics.body.setLinearVelocity(
                    physics.body.getLinearVelocity().x + move.speed * (float) Math.cos(angleMove - MathUtils.PI / 2),
                    physics.body.getLinearVelocity().y + move.speed * (float) Math.sin(angleMove - MathUtils.PI / 2));
        }
        if(!forwards && !backwards && ! left && !right){
            physics.body.setLinearVelocity(0, 0);
        }

//        ph.body.setTransform(ph.body.getPosition(), (float)Math.toRadians(angle));

//        if(move.key[PlayerMoveComponent.CTRL])
//            speed += 8;
//        if(move.key[PlayerMoveComponent.SHIFT]){
//            if (move.key[PlayerMoveComponent.UP]) {
//                physics.body.setLinearVelocity(physics.body.getLinearVelocity().x, speed);
//            }
//            if (move.key[PlayerMoveComponent.DOWN]) {
//                physics.body.setLinearVelocity(physics.body.getLinearVelocity().x, -speed);
//            }
//            if (move.key[PlayerMoveComponent.LEFT]) {
//                physics.body.setLinearVelocity(-speed, physics.body.getLinearVelocity().y);
//            }
//            if (move.key[PlayerMoveComponent.RIGHT]) {
//                physics.body.setLinearVelocity(speed, physics.body.getLinearVelocity().y);
//            }
//            if(!move.key[PlayerMoveComponent.UP] &&
//                    !move.key[PlayerMoveComponent.DOWN] &&
//                    !move.key[PlayerMoveComponent.LEFT] &&
//                    !move.key[PlayerMoveComponent.RIGHT]){
//                physics.body.setLinearVelocity(0, 0);
//            }
//        }else {
//            if (move.key[PlayerMoveComponent.UP]) {
//                physics.body.setLinearVelocity(speed * (float) Math.cos(angle), speed * (float) Math.sin(angle));
//            } else if (move.key[PlayerMoveComponent.DOWN]) {
//                physics.body.setLinearVelocity(-speed * (float) Math.cos(angle), -speed * (float) Math.sin(angle));
//            } else {
//                physics.body.setLinearVelocity(0, 0);
//            }
//            if (move.key[PlayerMoveComponent.LEFT]) {
//                physics.body.setTransform(physics.body.getPosition(), physics.body.getAngle() + 0.05f);
//            }
//            if (move.key[PlayerMoveComponent.RIGHT]) {
//                physics.body.setTransform(physics.body.getPosition(), physics.body.getAngle() - 0.05f);
//            }
//        }
    }
}
