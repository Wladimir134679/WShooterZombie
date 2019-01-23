package com.wdeath.wshooter.zombie.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.wdeath.wshooter.zombie.ecs.player.coms.PlayerComponent;
import com.wdeath.wshooter.zombie.ecs.player.coms.PlayerMoveComponent;

public class InputControllerPlayer implements InputProcessor {

    public String namePlayer;
    public GameScreen game;
    public Entity player = null;

    public InputControllerPlayer(String namePlayer, GameScreen game) {
        this.namePlayer = namePlayer;
        this.game = game;
        game.engine.addEntityListener(Family.all(PlayerComponent.class).get(), new EntityListener() {
            @Override
            public void entityAdded(Entity entity) {
                PlayerComponent data = entity.getComponent(PlayerComponent.class);
                if(!data.name.equals(namePlayer))
                    return;
                player = entity;
            }

            @Override
            public void entityRemoved(Entity entity) {

            }
        });
    }

    @Override
    public boolean keyDown(int keycode) {
        if(player == null)
            return false;
        PlayerMoveComponent move = player.getComponent(PlayerMoveComponent.class);
        switch (keycode){
            case Input.Keys.A: move.key[PlayerMoveComponent.LEFT] = true; break;
            case Input.Keys.W: move.key[PlayerMoveComponent.UP] = true; break;
            case Input.Keys.S: move.key[PlayerMoveComponent.DOWN] = true; break;
            case Input.Keys.D: move.key[PlayerMoveComponent.RIGHT] = true; break;
            case Input.Keys.SPACE: move.key[PlayerMoveComponent.SPACE] = true; break;
            case Input.Keys.CONTROL_LEFT: move.key[PlayerMoveComponent.CTRL] = true; break;
            case Input.Keys.SHIFT_LEFT: move.key[PlayerMoveComponent.SHIFT] = true; break;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(player == null)
            return false;
        PlayerMoveComponent move = player.getComponent(PlayerMoveComponent.class);
        switch (keycode){
            case Input.Keys.A: move.key[PlayerMoveComponent.LEFT] = false; break;
            case Input.Keys.W: move.key[PlayerMoveComponent.UP] = false; break;
            case Input.Keys.S: move.key[PlayerMoveComponent.DOWN] = false; break;
            case Input.Keys.D: move.key[PlayerMoveComponent.RIGHT] = false; break;
            case Input.Keys.SPACE: move.key[PlayerMoveComponent.SPACE] = false; break;
            case Input.Keys.CONTROL_LEFT: move.key[PlayerMoveComponent.CTRL] = false; break;
            case Input.Keys.SHIFT_LEFT: move.key[PlayerMoveComponent.SHIFT] = false; break;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
