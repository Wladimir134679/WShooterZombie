package com.wdeath.wshooter.zombie.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.wdeath.wshooter.zombie.Assets;
import com.wdeath.wshooter.zombie.MainGameClass;
import com.wdeath.wshooter.zombie.game.GameScreen;
import com.wdeath.wshooter.zombie.game.LoadingGameScreen;
import com.wdeath.wshooter.zombie.game.levels.LevelData;
import com.wdeath.wshooter.zombie.gui.AnimationScreen;
import com.wdeath.wshooter.zombie.gui.HBox;
import com.wdeath.wshooter.zombie.gui.VBox;
import com.wdeath.wshooter.zombie.player.PlayerData;
import com.wdeath.wshooter.zombie.player.TypePlayer;
import com.wdeath.wshooter.zombie.utill.GUIActions;

import java.util.HashMap;
import java.util.Set;

public class SetTypePlayer extends AnimationScreen {


    private TextButton back, select;
    private Table table;

    @Override
    public void init() {
        table = new Table();
        table.setSize(Gdx.graphics.getWidth() - 10, Gdx.graphics.getHeight() - 10);
        table.setPosition(5, 5);
        table.top();

        VBox paneInfo = new VBox(Assets.skinUI);
        Label title = new Label("Выберите персонажа", Assets.skinUI);
        Label name = new Label("", Assets.skinUI, "small");
        Label description = new Label("", Assets.skinUI, "small");
        paneInfo.addActor(title);
        paneInfo.addActor(name);
        paneInfo.addActor(description);


        List<String> listLabel = new List<>(Assets.skinUI);
        Set<String> names = TypePlayer.getNames();
        for(String str : names){
            listLabel.getItems().add(str);
        }

        listLabel.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                final String str = listLabel.getSelected();
                if(str == null)
                    return;
                GUIActions.alpha(paneInfo, 0, 0.1f);
                timer(() -> {
                    TypePlayer typePlayer = TypePlayer.typesPlayers.get(str);
                    title.setText("Информация об персонаже:");
                    name.setText("Имя: " + typePlayer.name);
                    description.setText("Описание: " + typePlayer.description);

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
        select = new TextButton("Выбрать", Assets.skinUI);
        select.addListener(new ClickListener(){
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
                    PlayerData.typePlayer = TypePlayer.typesPlayers.get(str);
                    MainGameClass.GAME.setScreen(ScreensData.menu);
                };
                timer(r, 0.2f);
            }
        });

        select.setSize(300, 30);
        HBox buttons = new HBox(Assets.skinUI);
        buttons.addActor(back);
        buttons.addActor(select);

        Cell cell = null;
        float wPane = (table.getWidth() - 10);


        HBox panesAll = new HBox(Assets.skinUI);
        panesAll.addActor(selectLevel);
        panesAll.addActor(paneInfo);

        cell = table.add(panesAll);
        cell.size(wPane, table.getHeight() - 40);
//        cell.padBottom(5);
        table.row();
        cell = table.add(buttons);
        cell.size(wPane, 40);

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
