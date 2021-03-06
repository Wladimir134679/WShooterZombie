package com.wdeath.wshooter.zombie.ecs.zombie.systs;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.wdeath.wshooter.zombie.ecs.zombie.coms.ZombieComponent;
import com.wdeath.wshooter.zombie.ecs.zombie.coms.ZombieMoveComponent;
import com.wdeath.wshooter.zombie.ecs.zombie.coms.ZombiePhysicsComponent;
import com.wdeath.wshooter.zombie.utill.GameUtill;

public class ZombieMoveSystem extends IteratingSystem {


    public ZombieMoveSystem(){
        super(Family.all(ZombieMoveComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        ZombieMoveComponent move = entity.getComponent(ZombieMoveComponent.class);
        if(move.purse != null)
            purse(entity, move);
//        else
//            resetMove(entity, move);
    }

    private void resetMove(Entity entity, ZombieMoveComponent move){
        ZombiePhysicsComponent ph = entity.getComponent(ZombiePhysicsComponent.class);
        ph.body.setLinearVelocity(Vector2.Zero);
    }


    private void purse(Entity entity, ZombieMoveComponent move){
        ZombieComponent zc = entity.getComponent(ZombieComponent.class);
        ZombiePhysicsComponent ph = entity.getComponent(ZombiePhysicsComponent.class);
        float angle = (float)Math.toDegrees(ph.body.getAngle());
        Vector2 posP = move.purse.getPosition();
        Vector2 posZ = ph.body.getPosition();

        angle = GameUtill.deltaAngle(posP, posZ, angle, 5, 10);

        ph.body.setTransform(ph.body.getPosition(), (float)Math.toRadians(angle));
        Vector2 speed = new Vector2(zc.speed, 0).rotate(GameUtill.angleVector(posP, posZ));
        ph.body.setLinearVelocity(speed);

    }
}
