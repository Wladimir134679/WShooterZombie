package com.wdeath.wshooter.zombie.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.wdeath.wshooter.zombie.Assets;
import com.wdeath.wshooter.zombie.MainGameClass;
import com.wdeath.wshooter.zombie.game.LoadingGameScreen;
import com.wdeath.wshooter.zombie.game.levels.LevelData;
import com.wdeath.wshooter.zombie.gui.VBox;
import com.wdeath.wshooter.zombie.screens.ScreensData;
import com.wdeath.wshooter.zombie.utill.GUIActions;

public class MenuScreen implements Screen {

    private Stage stage;
    private VBox box;
    private TextButton selectLevel;

    public MenuScreen(){
        stage = new Stage();
        selectLevel = new TextButton("Выбрать уровень", Assets.skinUI);
        box = new VBox();

        box.width = 350;
        box.height = 30;
        box.indent = 10;

        selectLevel.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Runnable run = () -> {
                    MainGameClass.GAME.setScreen(ScreensData.selectLevel);
                };
                box.close(run);
            }
        });

        stage.addActor(selectLevel);
        box.add(selectLevel);
    }

    @Override
    public void show() {
        Gdx.gl.glClearColor(0.2f, 0, 0, 1);
        box.open();

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
