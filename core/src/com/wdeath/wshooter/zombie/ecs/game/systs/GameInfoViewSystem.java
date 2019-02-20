package com.wdeath.wshooter.zombie.ecs.game.systs;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.wdeath.wshooter.zombie.Assets;
import com.wdeath.wshooter.zombie.ecs.game.GameType;
import com.wdeath.wshooter.zombie.ecs.game.coms.GameComponent;
import com.wdeath.wshooter.zombie.ecs.gui.coms.GuiComponent;
import com.wdeath.wshooter.zombie.ecs.level.coms.LevelComponent;
import com.wdeath.wshooter.zombie.ecs.player.systs.PlayerMoveSystem;
import com.wdeath.wshooter.zombie.ecs.player.systs.PlayerShotSystem;
import com.wdeath.wshooter.zombie.ecs.world.component.WorldPhysicsComponent;
import com.wdeath.wshooter.zombie.ecs.world.systems.WorldPhysicsSystem;
import com.wdeath.wshooter.zombie.ecs.zombie.systs.ZombieMoveSystem;
import com.wdeath.wshooter.zombie.ecs.zombie.systs.ZombieSpawnSystem;

public class GameInfoViewSystem extends EntitySystem {

    Entity guiEntity;
    Entity game;
    Entity world;
    Entity level;

    private Label info, numMoney;
    private boolean view;
    private boolean process;

    public GameInfoViewSystem(Entity guiEntity, Entity game, Entity world, Entity level) {
        this.guiEntity = guiEntity;
        this.game = game;
        this.world = world;
        this.level = level;

        info = new Label("", Assets.skinUI);
        info.setSize(300, 30);
        numMoney = new Label("", Assets.skinUI);
        numMoney.setSize(300, 30);

        info.setPosition(
                Gdx.graphics.getWidth() / 2 - numMoney.getWidth() / 2,
                Gdx.graphics.getHeight() + info.getHeight());
        MoveToAction moveInfo = new MoveToAction();
        moveInfo.setPosition(
                Gdx.graphics.getWidth() / 2 - info.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 + info.getHeight());
        moveInfo.setDuration(0.5f);
        moveInfo.setInterpolation(Interpolation.pow2Out);
        info.addAction(moveInfo);
//        Gdx.graphics.getWidth() / 2 - info.getWidth() / 2,
//        Gdx.graphics.getHeight() / 2 - info.getHeight() / 2

        numMoney.setPosition(
                Gdx.graphics.getWidth() / 2 - numMoney.getWidth() / 2,
                0 - numMoney.getHeight());
        MoveToAction moveMoney = new MoveToAction();
        moveMoney.setPosition(
                Gdx.graphics.getWidth() / 2 - numMoney.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - info.getHeight());
        moveMoney.setDuration(0.5f);
        moveMoney.setInterpolation(Interpolation.pow2Out);
        numMoney.addAction(moveMoney);


        view = false;
        process = false;
    }

    @Override
    public void update(float deltaTime) {
        GameComponent gameComponent = game.getComponent(GameComponent.class);
        if(gameComponent.type == GameType.VICTORY){
            victory();
        }
    }

    private void victory(){
        if(!process) {
            Engine engine = getEngine();
            engine.getSystem(WorldPhysicsSystem.class).setProcessing(false);
            engine.getSystem(PlayerShotSystem.class).setProcessing(false);
            engine.getSystem(PlayerMoveSystem.class).setProcessing(false);
            engine.getSystem(ZombieSpawnSystem.class).setProcessing(false);
            engine.getSystem(ZombieMoveSystem.class).setProcessing(false);
            process = true;
        }

        if(!view){
            LevelComponent levelComponent = level.getComponent(LevelComponent.class);
            info.setText("Победа!");
            String str = String.valueOf("Монет: " + levelComponent.data.numberMoney);
            numMoney.setText(str);
            createView();
            view = true;
        }
    }

    private void createView(){
        GuiComponent guiComponent = guiEntity.getComponent(GuiComponent.class);
        guiComponent.stage.addActor(info);
        guiComponent.stage.addActor(numMoney);
    }
}
