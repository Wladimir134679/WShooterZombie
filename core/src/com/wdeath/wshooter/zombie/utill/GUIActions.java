package com.wdeath.wshooter.zombie.utill;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;

public class GUIActions {


    public static void closeDelay(Runnable run, Actor ... actors){
        float delay = 0.1f;
        for(Actor actor : actors){
            TemporalAction temp = new TemporalAction() {
                @Override
                protected void update(float percent) {
                    if(percent == 1)
                        close(actor, 0);
                }
            };
            temp.setDuration(delay);
            actor.addAction(temp);
            delay += 0.25f;
        }
        TemporalAction temp = new TemporalAction() {
            @Override
            protected void update(float percent) {
                if(percent == 1)
                    run.run();
            }
        };
        temp.setDuration(delay);
        actors[0].addAction(temp);
    }

    public static void openDelay(Actor ... actors){
        float delay = 0.25f;
        for(Actor actor : actors){
            open(actor, delay);
            delay += 0.25f;
        }
    }

    public static void close(Actor actor, float delay){
        MoveToAction act = new MoveToAction();
        actor.addAction(act);
        act.setPosition(actor.getStage().getWidth() + actor.getWidth() - 20, actor.getY());
        act.setInterpolation(Interpolation.swingIn);
        act.setDuration(0.25f);
    }

    public static void open(Actor actor, float delay){
        MoveToAction act = new MoveToAction();
        actor.addAction(act);
        act.setPosition(actor.getX(), actor.getY());
        actor.setPosition(0 - actor.getWidth() - 20, actor.getY());
        act.setInterpolation(Interpolation.swingOut);
        act.setDuration(0.25f);
    }
}