package com.wdeath.wshooter.zombie.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.wdeath.wshooter.zombie.Assets;
import com.wdeath.wshooter.zombie.GameLoader;
import com.wdeath.wshooter.zombie.MainGameClass;
import com.wdeath.wshooter.zombie.game.LoadingGameScreen;
import com.wdeath.wshooter.zombie.game.levels.LevelData;
import com.wdeath.wshooter.zombie.gui.AnimationScreen;
import com.wdeath.wshooter.zombie.gui.HBox;
import com.wdeath.wshooter.zombie.gui.VBox;
import com.wdeath.wshooter.zombie.utill.GUIActions;

import java.util.ArrayList;
import java.util.HashMap;

public class SelectLevel extends AnimationScreen {

    private TextButton back, open;
    private Table table;
    private HashMap<String, LevelData> levelDatas;


    @Override
    public void init() {
        table = new Table();
        table.setSize(Gdx.graphics.getWidth() - 20, Gdx.graphics.getHeight() - 20);
        table.setPosition(10, 10);
        table.left();

        VBox paneInfo = new VBox(Assets.skinUI);
        Label title = new Label("Выберите уровень", Assets.skinUI);
        Label name = new Label("", Assets.skinUI, "small");
        Label money = new Label("", Assets.skinUI, "small");
        Label numKill = new Label("", Assets.skinUI, "small");
        Label timeWave = new Label("", Assets.skinUI, "small");
        paneInfo.addActor(title);
        paneInfo.addActor(name);
        paneInfo.addActor(money);
        paneInfo.addActor(numKill);
        paneInfo.addActor(timeWave);


        List<String> listLabel = new List<>(Assets.skinUI);
        ArrayList<LevelData> datas = LevelData.levels;
        Array<Label> l = new Array<>();
        levelDatas = new HashMap<>();
        for(LevelData level : datas){
            String str = String.valueOf("Уровень: " + level.id);
            listLabel.getItems().add(str);
            levelDatas.put(str, level);
        }
        listLabel.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                final String str = listLabel.getSelected();
                if(str == null)
                    return;
                GUIActions.alpha(paneInfo, 0, 0.1f);
                timer(() -> {
                    title.setText("Информация об уроне:");
                    name.setText(str);
                    LevelData data = levelDatas.get(str);
                    money.setText("Монеты: " + data.numberMoney);
                    numKill.setText("Нужно убийств: " + data.numberKill);
                    timeWave.setText("Время волны: " + data.timeWave + "c");
                    GUIActions.alpha(paneInfo, 1, 0.1f);
                }, 0.1f);
            }
        });

        ScrollPane selectLevel = new ScrollPane(listLabel, Assets.skinUI);
        selectLevel.setFadeScrollBars(false);

        back = new TextButton("Назад", Assets.skinUI);
        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                close();
                Runnable r = () -> {
                    MainGameClass.GAME.setScreen(ScreensData.menu);
                };
                timer(r, 0.2f);
            }
        });
        open = new TextButton("Играть", Assets.skinUI);
        open.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                close();
                Runnable r = () -> {
                    String str = listLabel.getSelected();
                    if(str == null){
                        GUIActions.alpha(paneInfo, 0, 0.1f);
                        timer(() -> {
                            GUIActions.alpha(paneInfo, 1, 0.1f);
                        }, 0.1f);
                        return;
                    }
                    LevelData data = levelDatas.get(str);
                    LoadingGameScreen load = new LoadingGameScreen(data);
                    MainGameClass.GAME.setScreen(load);
                };
                timer(r, 0.2f);
            }
        });
        open.setSize(300, 30);
        HBox buttons = new HBox(Assets.skinUI);
        buttons.addActor(back);
        buttons.addActor(open);

        Cell cell = null;
        float wPane = (table.getWidth() - 10);


        HBox panesAll = new HBox(Assets.skinUI);
        panesAll.addActor(selectLevel);
        panesAll.addActor(paneInfo);

        cell = table.add(panesAll);
        cell.size(wPane, Gdx.graphics.getHeight() - 20 - 50);
        cell.padBottom(5);
        table.row();
        cell = table.add(buttons);
        cell.size(table.getWidth(), 40);

        getStage().addActor(table);
//        getStage().setDebugAll(true);
    }

    @Override
    public void open() {
        table.layout();
        table.getColor().a = 0;
        GUIActions.alpha(table, 1, 0.1f);
//        Array<Cell> cells = table.getCells();
//        for(Cell cell : cells){
//            GUIActions.open(cell.getActor(), 3f);
//        }
    }

    @Override
    public void close() {
        table.layout();
        GUIActions.alpha(table, 0, 0.1f);
//        Array<Cell> cells = table.getCells();
//        for(Cell cell : cells){
//            GUIActions.close(cell.getActor(), 3f);
//        }
    }
}
