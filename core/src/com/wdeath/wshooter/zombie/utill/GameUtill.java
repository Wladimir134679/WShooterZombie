package com.wdeath.wshooter.zombie.utill;

import com.badlogic.gdx.math.Vector2;

public class GameUtill {

    private static float clamp(float a){
        if(a < 0) a += 360;
        if(a > 360) a -= 360;
        return Math.max(Math.min(a, 360), 0);
    }

    public static float deltaAngle(Vector2 target, Vector2 cur, float angle, float max, float delta){
        Vector2 pos = new Vector2();
        pos.set(target.cpy().sub(cur));
        float dang = clamp(180 + pos.angle() - angle) - 180;
        dang /= delta;
        angle += Math.max(Math.min(dang, max), -max);
        angle = clamp(angle);
        return angle;
    }

    public static float angleVector(Vector2 target, Vector2 cur){
        Vector2 pos = new Vector2();
        pos.set(target.cpy().sub(cur));
        return pos.angle();
    }
}
