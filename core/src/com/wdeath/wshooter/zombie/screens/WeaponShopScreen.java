package com.wdeath.wshooter.zombie.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.wdeath.wshooter.zombie.Assets;
import com.wdeath.wshooter.zombie.MainGameClass;
import com.wdeath.wshooter.zombie.game.PlayerGameData;
import com.wdeath.wshooter.zombie.gui.AnimationScreen;
import com.wdeath.wshooter.zombie.gui.DialogWindow;
import com.wdeath.wshooter.zombie.gui.HBox;
import com.wdeath.wshooter.zombie.gui.VBox;
import com.wdeath.wshooter.zombie.player.PlayerData;
import com.wdeath.wshooter.zombie.utill.GUIActions;
import com.wdeath.wshooter.zombie.weapon.Weapon;
import com.wdeath.wshooter.zombie.weapon.WeaponData;

import java.util.HashMap;
import java.util.Map;

public class WeaponShopScreen extends AnimationScreen {

    Table table;

    public HBox infoBox;
    public VBox mainBox;
    private List<String> listWeapons;
    public HBox buttonsBox;

    private Label infoMoney;

    private WindowWeapon windowWeapon;
    private HashMap<String, Integer> weaponsDataID;

    @Override
    public void init() {
        table = new Table();
        table.setSize(Gdx.graphics.getWidth() - 10, Gdx.graphics.getHeight() - 10);
        table.setPosition(5, 5);
        table.top();
        createGUI();
        getStage().addActor(table);
        createWinBuy();
    }

    private  void createGUI(){
        infoBox = new HBox(Assets.skinUI);
        mainBox = new VBox(Assets.skinUI);
        buttonsBox = new HBox(Assets.skinUI);

        infoMoney = new Label("", Assets.skinUI);
        infoBox.addActor(infoMoney);

        listWeapons = new List<String>(Assets.skinUI);
        ScrollPane scrollPane = new ScrollPane(listWeapons, Assets.skinUI);
        mainBox.addActor(scrollPane);

        TextButton showWeapon = new TextButton("Показать", Assets.skinUI);
        showWeapon.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String select = listWeapons.getSelected();
                if(select == null)
                    return;
                WeaponData data = WeaponData.weapons.get(weaponsDataID.get(select));
                if(data == null)
                    return;
                windowWeapon.setClose(() -> {update();});
                windowWeapon.open(data);
            }
        });
        buttonsBox.addActor(showWeapon);

        TextButton back = new TextButton("Назад", Assets.skinUI);
        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                close();
                timer(() -> {
                    MainGameClass.GAME.setScreen(ScreensData.menu);
                }, 0.15f);
            }
        });
        buttonsBox.addActor(back);

        Cell cell;

        cell = table.add(infoBox);
        cell.size(table.getWidth(), 40);
        table.row();
        cell = table.add(mainBox);
        cell.size(table.getWidth(), table.getHeight() - 40 - 40);
        table.row();
        cell = table.add(buttonsBox);
        cell.size(table.getWidth(), 40);
    }

    private void createWinBuy(){
        windowWeapon = new WindowWeapon(Assets.skinUI);
        windowWeapon.create();
        this.getStage().addActor(windowWeapon);
    }

    private void update(){
        infoMoney.setText("Монеты: " + PlayerData.money);
        HashMap<Integer, WeaponData> weaponsData = WeaponData.weapons;
        weaponsDataID = new HashMap<>();
        listWeapons.clearItems();
        for(Map.Entry<Integer, WeaponData> weapon : weaponsData.entrySet()){
            String str = weapon.getValue().name;
            if(PlayerGameData.isBuyWeapon(weapon.getValue().id)){
                str += " (куплено)";
            }
            weaponsDataID.put(str, weapon.getValue().id);
            listWeapons.getItems().add(str);
        }
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
        table.getColor().a = 1;
        GUIActions.alpha(table, 0, 0.1f);
    }

    ////////===============================================

    public static class WindowWeapon extends Window{

        public WeaponData data;
        private Label wName, wDamage, wStore, wRecharge, wShot, price;
        private Runnable rClose = null;

        public WindowWeapon(Skin skin) {
            super("", skin);
        }

        public void create(){
            setVisible(false);
            this.clearChildren();

            VBox box = new VBox(Assets.skinUI);
            wName = new Label("Название: ", Assets.skinUI);
            wDamage = new Label("Урон: ", Assets.skinUI);
            wStore = new Label("Магазин: ", Assets.skinUI);
            wRecharge = new Label("Время перезарядки: ", Assets.skinUI);
            wShot = new Label("Время выстрела: ", Assets.skinUI);
            price = new Label("", Assets.skinUI);
            box.addActor(wName);
            box.addActor(wDamage);
            box.addActor(wStore);
            box.addActor(wRecharge);
            box.addActor(wShot);
            box.addActor(price);

            HBox buttons = new HBox(Assets.skinUI);
            TextButton ok = new TextButton("Купить", Assets.skinUI);
            ok.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    String infa = "Купить " + data.name + " за " + data.price + " монет?";
                    DialogWindow dialogWindow = new DialogWindow(Assets.skinUI);
                    Runnable rOk = () -> {
                        if(PlayerGameData.isNewBuyWeapon(data)){
                            PlayerData.money -= data.price;
                            PlayerGameData.listBuyWeapon.add(data.id);
                            WindowWeapon.this.close();
                        }else{
                            WindowWeapon.this.close();
                        }
                    };
                    Runnable rBack = () -> {};
                    dialogWindow.openConfirmation(getStage(), infa, rOk, rBack);
                }
            });
            TextButton close = new TextButton("Закрыть", Assets.skinUI);
            close.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    close();
                }
            });
            buttons.addActor(ok);
            buttons.addActor(close);

            Cell cell;
            cell = this.add(box);
            cell.size(400, 250);
            row();
            cell = this.add(buttons);
            cell.size(400, 40);

            this.pack();
        }

        public void open(WeaponData data){
            this.data = data;

            wName.setText("Название: " + data.name);
            wDamage.setText("Урон: " + data.damage);
            wStore.setText("Магазин: " + data.store);
            wRecharge.setText("Время перезарядки: " + data.timeRecharge);
            wShot.setText("Время выстрела: " + data.timeShot);
            price.setText("Цена: " + data.price);

            setVisible(true);
            layout();
            this.getTitleLabel().setText(data.name);
            getColor().a = 0;
            GUIActions.alpha(WindowWeapon.this, 1, 0.1f);
            setPosition(Gdx.graphics.getWidth() / 2 - this.getWidth() / 2,
                    Gdx.graphics.getHeight() / 2 - this.getHeight() / 2);
        }

        public void close(){
            getColor().a = 1;
            GUIActions.alpha(WindowWeapon.this, 0, 0.1f);
            GUIActions.timer(WindowWeapon.this, () -> {
                closeWin();
            }, 0.15f);
        }

        private void closeWin(){
            this.setVisible(false);
            if(rClose != null)
                rClose.run();
        }

        private WindowWeapon setClose(Runnable r){
            rClose = r;
            return this;
        }
    }
}
