package com.wdeath.wshooter.zombie.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;

public abstract class AnimationScreen implements Screen {

    private Stage stage;
    private Actor timerActor;

    public AnimationScreen() {
        stage = new Stage();
        timerActor = new Actor();
        stage.addActor(timerActor);
        init();
    }

    public abstract void init();
    public abstract void open();
    public abstract void close();

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        open();
    }

    public Stage getStage(){
        return stage;
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    protected void timer(Runnable run, float time){
        TemporalAction temporal = new TemporalAction() {
            @Override
            protected void update(float percent) {
                if(percent != 1)
                    return;
                run.run();
                timerActor.removeAction(this);
            }
        };
        temporal.setDuration(time);
        timerActor.addAction(temporal);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
