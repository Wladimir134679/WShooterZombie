package com.wdeath.wshooter.zombie;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wdeath.wshooter.zombie.game.GameScreen;
import com.wdeath.wshooter.zombie.game.levels.LevelData;
import com.wdeath.wshooter.zombie.menu.MenuScreen;
import com.wdeath.wshooter.zombie.weapon.WeaponData;

public class MainGameClass extends Game {

	public static MainGameClass GAME;

	@Override
	public void create() {
//		System.out.println(Gdx.graphics.getWidth() + "x" + Gdx.graphics.getHeight()); // 640x480 in Window
		GAME = this;
		Assets.loadSkin();
		this.setScreen(new GameLoader());
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();
	}
}
