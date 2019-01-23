package com.wdeath.wshooter.zombie.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.wdeath.wshooter.zombie.utill.EngineThread;

public class GameScreen implements Screen {

    public Engine engine;
    public GameData data;
    private InputMultiplexer inputMultiplexer;

    public GameScreen(Engine engine, GameData data) {
        this.engine = engine;
        this.data = data;
        inputMultiplexer = new InputMultiplexer();
    }

    @Override
    public void show() {
        Gdx.gl.glClearColor(0, 0, 0.1f, 1);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    public GameScreen addInput(InputProcessor processor){
        inputMultiplexer.addProcessor(processor);
        return this;
    }

    @Override
    public void render(float delta) {
        engine.update(delta);
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
