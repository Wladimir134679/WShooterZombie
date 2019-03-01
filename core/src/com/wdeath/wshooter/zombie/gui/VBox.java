package com.wdeath.wshooter.zombie.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

public class VBox extends WidgetGroup {

    private Drawable background;

    public VBox(Skin skin){
        this(skin, "border-dark-blue");
    }

    public VBox(Skin skin, String name){
        background = skin.getDrawable(name);
    }

    @Override
    public void layout() {
        Array<Actor> list = this.getChildren();
        float w = getWidth() - 10;
        float h = (getHeight() - (list.size * 5) - 5) / (list.size);
        for(int i = 0; i < list.size; i++){
            Actor actor = list.get(i);
            actor.setHeight(h);
            if(actor instanceof Label){
                ((Label)actor).pack();
            }
            actor.setWidth(w);
            actor.setX(getWidth() / 2 - w / 2);
            actor.setY(getHeight() - (actor.getHeight() + 5) * (i + 1));
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(1, 1, 1, parentAlpha);
        background.draw(batch,getX(), getY(), getWidth(), getHeight());
        batch.flush();
        super.draw(batch, parentAlpha);
    }
}
