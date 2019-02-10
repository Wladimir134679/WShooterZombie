package com.wdeath.wshooter.zombie.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.wdeath.wshooter.zombie.Assets;
import com.wdeath.wshooter.zombie.MainGameClass;
import com.wdeath.wshooter.zombie.game.GameData;
import com.wdeath.wshooter.zombie.game.GameScreen;
import com.wdeath.wshooter.zombie.game.LoadingGameScreen;

public class MenuScreen implements Screen {

    Stage stage;

    @Override
    public void show() {
        Gdx.gl.glClearColor(0.1f, 0, 0, 1);
        stage = new Stage();

        TextButton newGame = new TextButton("Новая игра", Assets.skinUI);
        newGame.setSize(200, 30);
        newGame.setPosition(Gdx.graphics.getWidth() / 2 - newGame.getWidth() / 2,
                Gdx.graphics.getHeight() - newGame.getHeight() - 10);
        newGame.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameData data = new GameData();
                data.name = "Level 2";
                data.fileLevel = "levels/level2.tmx";
                LoadingGameScreen loader = new LoadingGameScreen(data);
                MainGameClass.GAME.setScreen(loader);
            }
        });
        stage.addActor(newGame);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
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
