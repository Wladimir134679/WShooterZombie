package com.wdeath.wshooter.zombie.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.wdeath.wshooter.zombie.Assets;
import com.wdeath.wshooter.zombie.MainGameClass;
import com.wdeath.wshooter.zombie.game.LoadingGameScreen;
import com.wdeath.wshooter.zombie.game.levels.LevelData;
import com.wdeath.wshooter.zombie.gui.AnimationScreen;
import com.wdeath.wshooter.zombie.gui.VBox;
import com.wdeath.wshooter.zombie.screens.ScreensData;
import com.wdeath.wshooter.zombie.utill.GUIActions;

import javax.xml.soap.Text;

public class MenuScreen extends AnimationScreen {

    private TextButton selectLevel, profile, shop, setting, author, exit;
    private Table table;

    @Override
    public void init() {
        table = new Table();
        table.setSize(Gdx.graphics.getWidth() - 20, Gdx.graphics.getHeight() - 20);
        table.setPosition(10, 10);
        table.center();


        selectLevel = new TextButton("Уровни", Assets.skinUI);
        selectLevel.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                close();
                Runnable r = () -> {
                    MainGameClass.GAME.setScreen(ScreensData.selectLevel);
                };
                timer(r, 0.2f);
            }
        });

        profile = new TextButton("Профиль", Assets.skinUI);
        profile.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                close();
                Runnable r = () -> {
                    MainGameClass.GAME.setScreen(ScreensData.profilePlayer);
                };
                timer(r, 0.2f);
            }
        });
        shop = new TextButton("Оруженый магазин", Assets.skinUI);
        setting = new TextButton("Настройки", Assets.skinUI);
        author = new TextButton("Автор", Assets.skinUI);
        exit = new TextButton("Выход", Assets.skinUI);
        exit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                close();
                Runnable r = () -> {
                    Gdx.app.exit();
                };
                timer(r, 0.3f);
            }
        });

        VBox buttons = new VBox(Assets.skinUI);
        buttons.addActor(selectLevel);
        buttons.addActor(profile);
        buttons.addActor(shop);
        buttons.addActor(setting);
        buttons.addActor(author);
        buttons.addActor(exit);

        Cell cell = null;
        cell = table.add(buttons);
        cell.size(300, Gdx.graphics.getHeight() - 20);

        getStage().addActor(table);
    }

    @Override
    public void open() {
        table.layout();
        table.getColor().a = 0;
        GUIActions.alpha(table, 1, 0.2f);
    }

    @Override
    public void close() {
        table.layout();
        GUIActions.alpha(table, 0, 0.2f);
    }
}
