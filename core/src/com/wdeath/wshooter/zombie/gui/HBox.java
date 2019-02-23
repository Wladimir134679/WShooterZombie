package com.wdeath.wshooter.zombie.gui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;

public class HBox extends WidgetGroup {

    private Drawable background;

    public HBox(Skin skin){
        this(skin, "border-dark-blue");
    }

    public HBox(Skin skin, String name){
        background = skin.getDrawable(name);
    }

    @Override
    public void layout() {
        Array<Actor> list = this.getChildren();
        float h = getHeight() - 10;
        int size = list.size;
        float p = 5;
        float w = (getWidth() - (p * size) - p) / size;
        for(int i = 0; i < list.size; i++){
            Actor actor = list.get(i);
            actor.setWidth(w);
            actor.setHeight(h);
            actor.setX(p + (w + 5) * i);
            actor.setY(5);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        background.draw(batch,getX(), getY(), getWidth(), getHeight());
        super.draw(batch, parentAlpha);
    }
}
