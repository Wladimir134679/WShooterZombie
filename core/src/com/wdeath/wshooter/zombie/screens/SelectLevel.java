package com.wdeath.wshooter.zombie.screens;

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

import java.util.ArrayList;

public class SelectLevel implements Screen {

    private Stage stage;
    private VBox box;
    private ArrayList<TextButton> levels;
    private TextButton back;

    public SelectLevel() {
        stage = new Stage();
        box = new VBox();
        box.width = 350;
        box.height = 30;
        box.indent = 10;
        levels = new ArrayList<>();

        ArrayList<LevelData> list = LevelData.levels;
        for(LevelData level : list){
            TextButton levelButton = new TextButton(level.name, Assets.skinUI);
            levelButton.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Runnable run =() -> {
                        LevelData data = level;
                        LoadingGameScreen loader = new LoadingGameScreen(data);
                        MainGameClass.GAME.setScreen(loader);
                    };
                    box.close(run);
                }
            });
            box.add(levelButton);
            stage.addActor(levelButton);
        }

        back = new TextButton("Назад", Assets.skinUI);
        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Runnable run =() -> {
                    MainGameClass.GAME.setScreen(ScreensData.menu);
                };
                box.close(run);
            }
        });
        stage.addActor(back);
        box.add(back);
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
