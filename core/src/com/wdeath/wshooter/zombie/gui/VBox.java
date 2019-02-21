package com.wdeath.wshooter.zombie.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;

import java.util.ArrayList;

public class VBox{

    public ArrayList<Actor> actors;
    public float xUp, yUp, indent, width, height;

    public VBox(){
        actors = new ArrayList<>();
        xUp = Gdx.graphics.getWidth() / 2f;
        yUp = Gdx.graphics.getHeight();
        indent = 5;
        width = -1;
        height = -1;
    }

    public VBox add(Actor actor){
        if(width != -1)
            actor.setWidth(width);
        if(height != -1)
            actor.setHeight(height);
        actors.add(actor);
        return this;
    }

    public VBox close(Runnable run){
        for(int i = 0; i < actors.size(); i++){
            final int ip = i;
            final Actor act = actors.get(i);
            closeActor(act, ip);
        }

        TemporalAction temp = new TemporalAction() {
            @Override
            protected void update(float percent) {
                if(percent != 1)
                    return;
                if(run != null)
                    run.run();
            }
        };
        temp.setDuration(0.3f);
        actors.get(0).addAction(temp);
        return this;
    }

    private void closeActor(Actor actor, int i){
        MoveToAction move = new MoveToAction();
        move.setDuration(0.4f);
        move.setPosition(getXEnd(i), getYEnd(i));
        move.setInterpolation(Interpolation.pow4Out);
        actor.addAction(move);
    }

    public VBox open(){
        for(int i = 0; i < actors.size(); i++){
            final int ip = i;
            final Actor act = actors.get(i);
            act.setPosition(getXStart(i), getYStart(i));
            openActor(act, ip);
        }
        return this;
    }

    private void openActor(Actor actor, int i){
        MoveToAction move = new MoveToAction();
        move.setDuration(0.4f);
        move.setPosition(getXPos(i), getYPos(i));
        move.setInterpolation(Interpolation.pow4In);
        actor.addAction(move);
    }

    private float getXStart(int i){
        return (xUp - width / 2);
    }

    private float getYStart(int i){
        return (Gdx.graphics.getHeight() + height);
    }

    private float getXPos(int i){
        return (xUp - width / 2);
    }

    private float getYPos(int i){
        return (yUp - (indent + height) * (i + 1));
    }

    private float getXEnd(int i){
        return (xUp - width / 2);
    }

    private float getYEnd(int i){
        return (0 - height);
    }




}
