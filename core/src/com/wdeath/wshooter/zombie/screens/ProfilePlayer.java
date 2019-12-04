package com.wdeath.wshooter.zombie.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.wdeath.wshooter.zombie.Assets;
import com.wdeath.wshooter.zombie.MainGameClass;
import com.wdeath.wshooter.zombie.game.PlayerGameData;
import com.wdeath.wshooter.zombie.gui.AnimationScreen;
import com.wdeath.wshooter.zombie.gui.HBox;
import com.wdeath.wshooter.zombie.gui.VBox;
import com.wdeath.wshooter.zombie.player.PlayerData;
import com.wdeath.wshooter.zombie.utill.GUIActions;
import com.wdeath.wshooter.zombie.weapon.Weapon;
import com.wdeath.wshooter.zombie.weapon.WeaponData;

import java.util.HashMap;
import java.util.Map;

public class ProfilePlayer extends AnimationScreen {

    private Table table;
    private ScrollPane scrollWeapon, scrollNavig;
    private TextButton back;
    private HashMap<String, Actor> boxs;
    private VBox infoList, statList;

    private List<String> listWeapons;
    private Label lMoney, lTypePlayer;
    private Label lKills;

    private Window winWeapon;

    private HashMap<String, Integer> weaponsData;

    @Override
    public void init() {
        table = new Table();
        table.setSize(Gdx.graphics.getWidth() - 10, Gdx.graphics.getHeight() - 10);
        table.setPosition(5 ,5);
        table.top();
        float w = table.getWidth() - 10;
        float h = table.getHeight();
        String[] buttonsListMain = {"Оружие", "Информация", "Статистика"};
        boxs = new HashMap<>();

        infoList = new VBox(Assets.skinUI);
        statList = new VBox(Assets.skinUI);


        //----------------------------

        listWeapons = new List<>(Assets.skinUI);

        lMoney = new Label("", Assets.skinUI);
        lTypePlayer = new Label("", Assets.skinUI);
        infoList.addActor(lMoney);
        infoList.addActor(lTypePlayer);

        lKills = new Label("", Assets.skinUI);
        statList.addActor(lKills);

        //----------------------------


        List<String> listNavig = new List<>(Assets.skinUI);

        scrollNavig = new ScrollPane(listNavig, Assets.skinUI);
        scrollNavig.setFadeScrollBars(false);
        scrollWeapon = new ScrollPane(listWeapons, Assets.skinUI);
        scrollWeapon.setFadeScrollBars(false);

        VBox boxScroll = new VBox(Assets.skinUI);
        boxScroll.addActor(scrollNavig);
        VBox boxInfo = new VBox(Assets.skinUI);

        boxs.put(buttonsListMain[0], scrollWeapon);
        boxs.put(buttonsListMain[1], infoList);
        boxs.put(buttonsListMain[2], statList);

        listNavig.setItems(buttonsListMain);
        weaponsData = new HashMap<>();
        listWeapons.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String select = listWeapons.getSelected();
                if(select == null)
                    return;
                WeaponData data = WeaponData.weapons.get(weaponsData.get(select));
                openWin(data);
            }
        });
        listNavig.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String select = listNavig.getSelected();
                if(select == null)
                    return;
                Actor box = boxs.get(select);
                GUIActions.alpha(boxInfo, 0, 0.1f);
                timer(() -> {
                    boxInfo.clear();
                    boxInfo.addActor(box);
                    GUIActions.alpha(boxInfo, 1, 0.1f);
                }, 0.15f);
            }
        });


        HBox boxPane = new HBox(Assets.skinUI);
        HBox boxButton = new HBox(Assets.skinUI);

        back = new TextButton("Назад", Assets.skinUI);
        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                close();
                Runnable run = () -> {
                    MainGameClass.GAME.setScreen(ScreensData.menu);
                };
                timer(run, 0.2f);
            }
        });

        boxPane.addActor(boxScroll);
        boxPane.addActor(boxInfo);
        boxButton.addActor(back);

        Cell cell;

        cell = table.add(boxPane);
        cell.size(w, h - 40);
        table.row();
        cell = table.add(boxButton);
        cell.size(w, 40);
        getStage().addActor(table);

        createWinWeapon();
        getStage().addActor(winWeapon);
    }

    private void update(){
        weaponsData = new HashMap<>();
        listWeapons.clearItems();
        for(Integer it : PlayerGameData.listBuyWeapon){
            WeaponData data = WeaponData.weapons.get(it);
            String str = data.name;
            if(PlayerGameData.weapon.id == it)
                str += " (снаряжено)";
            weaponsData.put(str, data.id);
            listWeapons.getItems().add(str);
        }

        lMoney.setText("Монеты: " + PlayerData.money);
        lKills.setText("Убито: " + PlayerData.killMonster);
        lTypePlayer.setText("Тип персонажа: " + PlayerData.typePlayer.name);

    }

    @Override
    public void open() {
        update();
        table.layout();
        table.getColor().a = 0;
        GUIActions.alpha(table, 1, 0.1f);
    }

    @Override
    public void close() {
        table.layout();
        GUIActions.alpha(table, 0, 0.1f);
    }

    private Label wName, wDamage, wStore, wRecharge, wShot;
    private WeaponData dataShow = null;

    private void createWinWeapon(){
        winWeapon = new Dialog("Оружие", Assets.skinUI);
        winWeapon.setResizable(false);
        winWeapon.top();
        winWeapon.clearChildren();

        VBox box = new VBox(Assets.skinUI);
        wName = new Label("Название: ", Assets.skinUI);
        wDamage = new Label("Урон: ", Assets.skinUI);
        wStore = new Label("Магазин: ", Assets.skinUI);
        wRecharge = new Label("Время перезарядки: ", Assets.skinUI);
        wShot = new Label("Время выстрела: ", Assets.skinUI);
        box.addActor(wName);
        box.addActor(wDamage);
        box.addActor(wStore);
        box.addActor(wRecharge);
        box.addActor(wShot);

        HBox buttons = new HBox(Assets.skinUI);

        TextButton fit = new TextButton("Снарядить", Assets.skinUI);
        fit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GUIActions.alpha(winWeapon, 0, 0.1f);
                timer(() -> {
                    winWeapon.setVisible(false);
                    PlayerGameData.weapon = dataShow;
                    update();
                }, 0.15f);
            }
        });

        TextButton close = new TextButton("Закрыть", Assets.skinUI);
        close.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GUIActions.alpha(winWeapon, 0, 0.1f);
                timer(() -> {
                    winWeapon.setVisible(false);
                }, 0.15f);
            }
        });
        buttons.addActor(fit);
        buttons.addActor(close);
        Cell cell = winWeapon.add(box);
        cell.size(400, 9 * 30);
        winWeapon.row();
        cell = winWeapon.add(buttons);
        cell.size(400, 40);
        winWeapon.pack();
        winWeapon.setVisible(false);
    }
    private void openWin(WeaponData data){
        this.dataShow = data;
        winWeapon.layout();
        winWeapon.setVisible(true);

        wName.setText("Название: " + data.name);
        wDamage.setText("Урон: " + data.damage);
        wStore.setText("Магазин: " + data.store);
        wRecharge.setText("Время перезарядки: " + data.timeRecharge);
        wShot.setText("Время выстрела: " + data.timeShot);

        winWeapon.setPosition(
                Gdx.graphics.getWidth() / 2 - winWeapon.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - winWeapon.getHeight() / 2);
        winWeapon.getColor().a = 0;
        GUIActions.alpha(winWeapon, 1, 0.1f);
    }
}
