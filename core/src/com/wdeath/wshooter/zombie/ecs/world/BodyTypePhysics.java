package com.wdeath.wshooter.zombie.ecs.world;

public class BodyTypePhysics {

//    public static final int PLAYER_COM_GROUP = -2, ZOMBIE_BODY = -1, ALL_OTHER = 1;

    public static final short CATEGORY_PLAYER = 1, CATEGORY_ZOMBIE = 2, CATEGORY_WALL = 3, CATEGORY_SENSOR = 4, CATEGORY_LIGHT = 5;

    public static final short MASK_PLAYER = CATEGORY_WALL | CATEGORY_ZOMBIE;
    public static final short MASK_ZOMBIE = CATEGORY_WALL | CATEGORY_WALL;
    public static final short MASK_LIGHT = CATEGORY_PLAYER | CATEGORY_ZOMBIE | CATEGORY_WALL;
    public static final short MASK_LIGHT_PLAYER = CATEGORY_WALL | CATEGORY_ZOMBIE;

    public BodyType type;
    public Object data;

    public enum BodyType{
        WORLD_WALL, BULLET, PLAYER, ZOMBIE
    }
}
