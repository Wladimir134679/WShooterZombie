package com.wdeath.wshooter.zombie.ecs.world;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.Fixture;

public class BodyTypePhysics {

    public static final int GROUP_ZOMBIE = 1, GROUP_PLAYER = 2;

    public static final short CATEGORY_PLAYER = 1, CATEGORY_ZOMBIE = 2, CATEGORY_WALL = 4, CATEGORY_SENSOR = 8
            , CATEGORY_LIGHT = 16, CATEGORY_BULLET = 32;

    public static final short MASK_PLAYER = CATEGORY_WALL | CATEGORY_ZOMBIE | CATEGORY_SENSOR;
    public static final short MASK_ZOMBIE = CATEGORY_WALL | CATEGORY_PLAYER | CATEGORY_BULLET;
    public static final short MASK_BULLET = CATEGORY_WALL | CATEGORY_ZOMBIE;
    public static final short MASK_LIGHT_WORLD = CATEGORY_PLAYER | CATEGORY_ZOMBIE | CATEGORY_WALL;
    public static final short MASK_LIGHT_PLAYER = CATEGORY_WALL | CATEGORY_ZOMBIE;
    public static final short MASK_LIGHT_EXPLOSION = CATEGORY_WALL | CATEGORY_PLAYER;
    public static final short MASK_SENSOR_ZOMBIE = CATEGORY_PLAYER;

    public BodyType type;
    public Object data;

    public static Fixture add(Fixture fix, Object ent, BodyType type){
        BodyTypePhysics t = new BodyTypePhysics();
        t.type = type;
        t.data = ent;
        fix.setUserData(t);
        return fix;
    }

    public enum BodyType{
        WORLD_WALL, BULLET, PLAYER, ZOMBIE, SENSOR_ZOMBIE
    }
}
