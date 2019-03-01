package com.wdeath.wshooter.zombie;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.wdeath.wshooter.zombie.game.PlayerGameData;
import com.wdeath.wshooter.zombie.game.levels.LevelData;
import com.wdeath.wshooter.zombie.menu.MenuScreen;
import com.wdeath.wshooter.zombie.screens.ScreensData;
import com.wdeath.wshooter.zombie.utill.LoadingEngine;
import com.wdeath.wshooter.zombie.weapon.WeaponData;

public class GameLoader implements Screen {

    public Stage stage;
    public LoadingEngine loading;

    @Override
    public void show() {
        Gdx.gl.glClearColor(0.2f, 0, 0, 1);
        stage = new Stage();
        Label infoLoad = new Label("Loading...", Assets.skinUI);
        infoLoad.setPosition(Gdx.graphics.getWidth() / 2 - infoLoad.getWidth() / 2, infoLoad.getHeight() + 20);
        stage.addActor(infoLoad);

        loading = new LoadingEngine();
        loading.add(() -> Assets.load());
        loading.add(() -> LevelData.init());
        loading.add(() -> WeaponData.load());
        loading.add(() -> PlayerGameData.load());
        loading.add(() -> ScreensData.init());
        loading.setEnd(() -> {
            MainGameClass.GAME.setScreen(ScreensData.menu);
        });
    }

    @Override
    public void render(float delta) {
        stage.act(1/60f);
        stage.draw();

        loading.run();
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
