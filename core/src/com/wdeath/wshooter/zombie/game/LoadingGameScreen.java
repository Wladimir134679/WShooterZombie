package com.wdeath.wshooter.zombie.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.wdeath.wshooter.zombie.Assets;
import com.wdeath.wshooter.zombie.MainGameClass;
import com.wdeath.wshooter.zombie.ecs.bullet.systs.BulletDeleteSystem;
import com.wdeath.wshooter.zombie.ecs.bullet.systs.BulletLightSystem;
import com.wdeath.wshooter.zombie.ecs.explosion.systs.ExplosionLightSystem;
import com.wdeath.wshooter.zombie.ecs.explosion.systs.ExplosionSpawnSystem;
import com.wdeath.wshooter.zombie.ecs.game.GameBuilder;
import com.wdeath.wshooter.zombie.ecs.gui.GuiBuilder;
import com.wdeath.wshooter.zombie.ecs.gui.coms.GuiComponent;
import com.wdeath.wshooter.zombie.ecs.gui.systs.GuiRenderSystem;
import com.wdeath.wshooter.zombie.ecs.gui.systs.GuiUpdateSystem;
import com.wdeath.wshooter.zombie.ecs.level.LevelBuilder;
import com.wdeath.wshooter.zombie.ecs.level.systs.LevelUpdateSystem;
import com.wdeath.wshooter.zombie.ecs.player.systs.*;
import com.wdeath.wshooter.zombie.ecs.stage.systs.StageRenderSystem;
import com.wdeath.wshooter.zombie.ecs.player.coms.PlayerSpawnComponent;
import com.wdeath.wshooter.zombie.ecs.zombie.systs.ZombieDamageSystem;
import com.wdeath.wshooter.zombie.ecs.zombie.systs.ZombieDeleteSystem;
import com.wdeath.wshooter.zombie.ecs.zombie.systs.ZombieMoveSystem;
import com.wdeath.wshooter.zombie.ecs.zombie.systs.ZombieSpawnSystem;
import com.wdeath.wshooter.zombie.game.levels.LevelData;
import com.wdeath.wshooter.zombie.utill.LoadingEngine;
import com.wdeath.wshooter.zombie.ecs.world.WorldBuilder;
import com.wdeath.wshooter.zombie.ecs.world.component.WorldSpawnComponent;
import com.wdeath.wshooter.zombie.ecs.world.systems.*;

public class LoadingGameScreen implements Screen {

    private Stage stage;
    private LoadingEngine loadingEngine;
    private LevelData levelData;
    private Engine engine;
    GameScreen gameScreen;

    public LoadingGameScreen(LevelData levelData) {
        this.levelData = levelData;
    }

    @Override
    public void show() {
        stage = new Stage();
        Label infoLoad = new Label("Loading...", Assets.skinUI);
        infoLoad.setPosition(Gdx.graphics.getWidth() / 2 - infoLoad.getWidth() / 2, infoLoad.getHeight() + 20);
        stage.addActor(infoLoad);

        loadingEngine = new LoadingEngine();
        loadingEngine.add(() -> {
            engine = new Engine();
        }).add(() -> {
            gameScreen = new GameScreen(engine, levelData);
            levelData.timeBegin = 2;
            levelData.sleep = 10;
        }).add(() -> {
            WorldBuilder builder = new WorldBuilder();
            Entity world = builder
                    .create()
                    .createMap(levelData.file)
                    .createPhysics()
                    .createDraw()
                    .createLight()
                    .get();
            engine.addEntity(world);

            LevelBuilder levelBuilder = new LevelBuilder();
            Entity level = levelBuilder
                    .create()
                    .createData(levelData)
                    .get();
            engine.addEntity(level);

            Entity playerSpawnEntity = new Entity();
            PlayerSpawnComponent spawnPlayer = new PlayerSpawnComponent();
            spawnPlayer.name = PlayerGameData.NAME_PLAYER;
            spawnPlayer.maxHealth = 100;
            spawnPlayer.spawnPoint = new Vector2(world.getComponent(WorldSpawnComponent.class).spawns.get("player"));
            playerSpawnEntity.add(spawnPlayer);
            engine.addEntity(playerSpawnEntity);

            InputControllerPlayer input = new InputControllerPlayer(PlayerGameData.NAME_PLAYER, gameScreen);
            gameScreen.addInput(input);

            GuiBuilder guiBuilder = new GuiBuilder();
            Entity guiEntity = guiBuilder.create();
            gameScreen.addInput(guiEntity.getComponent(GuiComponent.class).stage);

            GameBuilder gameBuilder = new GameBuilder();
            Entity gameEntity = gameBuilder
                    .createData()
                    .get();
            engine.addEntity(gameEntity);

            WorldPhysicsSystem physicsSystem = new WorldPhysicsSystem(world);
            WorldPhysicsDebugSystem debug = new WorldPhysicsDebugSystem(world);
            WorldDrawTiledMapSystem draw = new WorldDrawTiledMapSystem(world);
            PlayerSpawnSystem playerSpawn = new PlayerSpawnSystem(world);
            WorldCameraSystem cameraSystem = new WorldCameraSystem(world, PlayerGameData.NAME_PLAYER);
            PlayerMoveSystem moveSystem = new PlayerMoveSystem(world);
            PlayerDamageSystem playerDamageSystem = new PlayerDamageSystem(gameEntity);
            WorldUpdateLightSystem lightSystem = new WorldUpdateLightSystem(world);
            BulletLightSystem bulletLightSystem = new BulletLightSystem();
            BulletDeleteSystem bulletDeleteSystem = new BulletDeleteSystem(world);
            ExplosionLightSystem explosionLightSystem = new ExplosionLightSystem();
            ExplosionSpawnSystem explosionSpawnSystem = new ExplosionSpawnSystem(world);
            PlayerShotSystem playerShotSystem = new PlayerShotSystem(world);
            StageRenderSystem stageRenderSystem = new StageRenderSystem();
            GuiRenderSystem guiRenderSystem = new GuiRenderSystem(guiEntity);
            GuiUpdateSystem guiUpdateSystem = new GuiUpdateSystem(guiEntity, level);
            LevelUpdateSystem levelUpdateSystem = new LevelUpdateSystem(world);
            ZombieSpawnSystem zombieSpawnSystem = new ZombieSpawnSystem(world);
            ZombieDeleteSystem zombieDeleteSystem = new ZombieDeleteSystem(world);
            ZombieDamageSystem zombieDamageSystem = new ZombieDamageSystem(world);
            PlayerControllerSystem playerControllerSystem = new PlayerControllerSystem();
            ZombieMoveSystem zombieMoveSystem = new ZombieMoveSystem();
            engine.addSystem(playerControllerSystem);
            engine.addSystem(levelUpdateSystem);
            engine.addSystem(zombieSpawnSystem);
            engine.addSystem(physicsSystem);
            engine.addSystem(playerSpawn);
            engine.addSystem(moveSystem);
            engine.addSystem(playerShotSystem);
            engine.addSystem(zombieDamageSystem);
            engine.addSystem(zombieDeleteSystem);
            engine.addSystem(zombieMoveSystem);
            engine.addSystem(bulletDeleteSystem);
            engine.addSystem(explosionLightSystem);
            engine.addSystem(explosionSpawnSystem);
            engine.addSystem(playerDamageSystem);
            engine.addSystem(cameraSystem);
            engine.addSystem(draw);
            engine.addSystem(debug);
            engine.addSystem(bulletLightSystem);
            engine.addSystem(lightSystem);
            engine.addSystem(stageRenderSystem);
            engine.addSystem(guiUpdateSystem);
            engine.addSystem(guiRenderSystem);
        });
        loadingEngine.setEnd(() -> {
            MainGameClass.GAME.setScreen(gameScreen);
        });

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
        loadingEngine.run();
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
