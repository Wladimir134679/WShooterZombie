package com.wdeath.wshooter.zombie.ecs.game.systs;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.wdeath.wshooter.zombie.Assets;
import com.wdeath.wshooter.zombie.MainGameClass;
import com.wdeath.wshooter.zombie.ecs.game.GameType;
import com.wdeath.wshooter.zombie.ecs.game.coms.GameComponent;
import com.wdeath.wshooter.zombie.ecs.gui.coms.GuiComponent;
import com.wdeath.wshooter.zombie.ecs.level.coms.LevelComponent;
import com.wdeath.wshooter.zombie.ecs.player.systs.PlayerMoveSystem;
import com.wdeath.wshooter.zombie.ecs.player.systs.PlayerShotSystem;
import com.wdeath.wshooter.zombie.ecs.world.systems.WorldPhysicsSystem;
import com.wdeath.wshooter.zombie.ecs.zombie.systs.ZombieMoveSystem;
import com.wdeath.wshooter.zombie.ecs.zombie.systs.ZombieSpawnSystem;
import com.wdeath.wshooter.zombie.game.PlayerGameData;
import com.wdeath.wshooter.zombie.player.PlayerData;
import com.wdeath.wshooter.zombie.screens.ScreensData;

public class GameInfoViewSystem extends EntitySystem {

    Entity guiEntity;
    Entity game;
    Entity world;
    Entity level;

    private Label title, info;
    private Actor timerEnd;
    private boolean view;
    private boolean process;
    private GameType type;

    public GameInfoViewSystem(Entity guiEntity, Entity game, Entity world, Entity level) {
        this.guiEntity = guiEntity;
        this.game = game;
        this.world = world;
        this.level = level;
        timerEnd = new Actor();

        title = new Label("", Assets.skinUI, "title-end-game");
        title.setSize(300, 30);
        info = new Label("", Assets.skinUI);
        info.setSize(300, 30);


        view = false;
        process = false;
    }

    @Override
    public void update(float deltaTime) {
        GameComponent gameComponent = game.getComponent(GameComponent.class);
        if(gameComponent.type == GameType.VICTORY){
            type = gameComponent.type;
            victory();
        }
        if(gameComponent.type == GameType.DEFEAT){
            type = gameComponent.type;
            defeat();
        }
    }

    private void closeSystemForEnd(){
        Engine engine = getEngine();
        engine.getSystem(WorldPhysicsSystem.class).setProcessing(false);
        engine.getSystem(PlayerShotSystem.class).setProcessing(false);
        engine.getSystem(PlayerMoveSystem.class).setProcessing(false);
        engine.getSystem(ZombieSpawnSystem.class).setProcessing(false);
        engine.getSystem(ZombieMoveSystem.class).setProcessing(false);
    }

    private void defeat(){
        if(!process) {
            closeSystemForEnd();
            process = true;
        }

        if(!view){
            title.setText("Поражение!");
            String str = String.valueOf("Вас убили =(");
            info.setText(str);
            createView();
            view = true;
        }
    }

    private void victory(){
        if(!process) {
            closeSystemForEnd();
            process = true;
        }

        if(!view){
            LevelComponent levelComponent = level.getComponent(LevelComponent.class);
            title.setText("Победа!");
            String str = String.valueOf("Монет: " + levelComponent.data.numberMoney);
            PlayerData.money += levelComponent.data.numberMoney;
            info.setText(str);
            createView();
            view = true;
        }
    }

    private void createAnimation(){
        title.setPosition(
                Gdx.graphics.getWidth() / 2 - info.getWidth() / 2,
                Gdx.graphics.getHeight() + title.getHeight());
        MoveToAction moveInfo = new MoveToAction();
        moveInfo.setPosition(
                Gdx.graphics.getWidth() / 2 - title.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 + title.getHeight());
        moveInfo.setDuration(0.5f);
        moveInfo.setInterpolation(Interpolation.pow2Out);
        title.addAction(moveInfo);

        info.setPosition(
                Gdx.graphics.getWidth() / 2 - info.getWidth() / 2,
                0 - info.getHeight());
        MoveToAction moveMoney = new MoveToAction();
        moveMoney.setPosition(
                Gdx.graphics.getWidth() / 2 - info.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - title.getHeight());
        moveMoney.setDuration(0.5f);
        moveMoney.setInterpolation(Interpolation.pow2Out);
        info.addAction(moveMoney);
    }

    private void createEndClose(){
        Runnable close = () -> {
            MoveToAction moveInfo = new MoveToAction();
            moveInfo.setPosition(
                    0 - title.getWidth(),
                    title.getY());
            moveInfo.setDuration(0.5f);
            moveInfo.setInterpolation(Interpolation.pow2In);
            title.addAction(moveInfo);

            MoveToAction moveMoney = new MoveToAction();
            moveMoney.setPosition(
                    0 - info.getWidth(),
                    info.getY());
            moveMoney.setDuration(0.5f);
            moveMoney.setInterpolation(Interpolation.pow2In);
            info.addAction(moveMoney);
            Runnable passing =() -> {
                MainGameClass.GAME.setScreen(ScreensData.menu);
            };
            timer(passing, 0.6f);
        };
        timer(close, 3);
    }

    private void timer(Runnable run, float time){
        TemporalAction temporal = new TemporalAction() {
            @Override
            protected void update(float percent) {
                if(percent != 1)
                    return;
                run.run();
            }
        };
        temporal.setDuration(time);
        timerEnd.addAction(temporal);
    }

    private void createView(){
        createAnimation();
        GuiComponent guiComponent = guiEntity.getComponent(GuiComponent.class);
        guiComponent.stage.addActor(title);
        guiComponent.stage.addActor(info);
        guiComponent.stage.addActor(timerEnd);
        createEndClose();
    }
}
