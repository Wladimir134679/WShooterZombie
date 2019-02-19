package com.wdeath.wshooter.zombie.ecs.gui.systs;

import com.badlogic.ashley.core.*;
import com.badlogic.gdx.Gdx;
import com.wdeath.wshooter.zombie.ecs.level.coms.LevelComponent;
import com.wdeath.wshooter.zombie.ecs.player.coms.PlayerHealthComponent;
import com.wdeath.wshooter.zombie.game.DataStatic;
import com.wdeath.wshooter.zombie.ecs.gui.coms.GuiElementComponent;
import com.wdeath.wshooter.zombie.ecs.player.coms.PlayerComponent;
import com.wdeath.wshooter.zombie.ecs.player.coms.PlayerWeaponComponent;
import com.wdeath.wshooter.zombie.game.PlayerGameData;

public class GuiUpdateSystem extends EntitySystem {

    private Entity gui;
    private Entity player = null;
    private Entity level;

    public GuiUpdateSystem(Entity gui, Entity level) {
        this.gui = gui;
        this.level = level;
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        engine.addEntityListener(Family.all(PlayerComponent.class).get(), new EntityListener() {
            @Override
            public void entityAdded(Entity entity) {
                PlayerComponent playerData = entity.getComponent(PlayerComponent.class);
                if(playerData.name.equals(PlayerGameData.NAME_PLAYER)){
                    player = entity;
                }
            }

            @Override
            public void entityRemoved(Entity entity) {

            }
        });
    }

    @Override
    public void update(float deltaTime) {
        GuiElementComponent element = gui.getComponent(GuiElementComponent.class);
        updateFps(element);
        updateShot(element);
        updateLevelInfo(element);
        updateHealthPlayer(element);
    }

    private void updateHealthPlayer(GuiElementComponent element){
        if(player == null){
            element.healthPlayer.setText("Игрок не найден");
            return;
        }
        String text = "";
        PlayerHealthComponent health = player.getComponent(PlayerHealthComponent.class);
        text = "Health: " + health.current + "/" + health.max;
        element.healthPlayer.setText(text);
    }

    private void updateLevelInfo(GuiElementComponent element){
        if(level == null){
            element.infoLevel.setText("Уровень определяется...");
            return;
        }
        LevelComponent levelComponent = level.getComponent(LevelComponent.class);
        String text = "";
        if(levelComponent.isRun){
            text = "Новая волна через: " + String.valueOf((int)Math.round(levelComponent.data.sleep - levelComponent.timeSpawn)) + "s";
        }else{
            text = "Начало через... " + String.valueOf((int)Math.round(levelComponent.data.timeBegin - levelComponent.timeBegin)) + "s";
        }
        element.infoLevel.setText(text);
    }

    private void updateShot(GuiElementComponent element){
        String str = "Патроны: ";
        if(player == null){
            str = "Игрок не найден...";
        }else{
            PlayerWeaponComponent weapon = player.getComponent(PlayerWeaponComponent.class);
            if(weapon.weapon.timeRecharge > 0){
                str += "Перезарядка...";
            }else{
                str += String.valueOf(weapon.weapon.shotNum + "/" + weapon.weapon.data.ammunitionMax);
            }
        }
        element.shot.setText(str);
    }

    private void updateFps(GuiElementComponent element){
        String str = String.valueOf("FPS: " + Gdx.graphics.getFramesPerSecond());
        element.infoFPS.setText(str);
    }
}


