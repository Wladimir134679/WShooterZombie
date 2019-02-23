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

    private TextButton selectLevel;
    private Table table;

    @Override
    public void init() {
        table = new Table();
        table.setSize(Gdx.graphics.getWidth() - 20, Gdx.graphics.getHeight() - 20);
        table.setPosition(10, 10);
        table.center();


        selectLevel = new TextButton("Выбрать", Assets.skinUI);
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
        selectLevel.setSize(300, 30);
        VBox buttons = new VBox(Assets.skinUI);
        buttons.addActor(selectLevel);

        Cell cell = null;
        cell = table.add(buttons);
        cell.size(300, Gdx.graphics.getHeight() - 20);

        getStage().addActor(table);
//        getStage().setDebugAll(true);
    }

    @Override
    public void open() {
        table.layout();
        AlphaAction act= new AlphaAction();
        table.getColor().a = 0;
        act.setAlpha(1f);
        act.setDuration(0.1f);
        table.addAction(act);
//        Array<Cell> cells = table.getCells();
//        for(Cell cell : cells){
//            GUIActions.open(cell.getActor(), 3f);
//        }
    }

    @Override
    public void close() {
        table.layout();
        AlphaAction act= new AlphaAction();
        act.setAlpha(0f);
        act.setDuration(0.1f);
        table.addAction(act);
//        Array<Cell> cells = table.getCells();
//        for(Cell cell : cells){
//            GUIActions.close(cell.getActor(), 3f);
//        }
    }
}
