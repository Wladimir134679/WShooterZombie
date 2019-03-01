package com.wdeath.wshooter.zombie.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.wdeath.wshooter.zombie.MainGameClass;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
//		config.fullscreen = true;
		config.width = 800;
		config.height = 600;
		config.title = "WShooter Zombie";
		new LwjglApplication(new MainGameClass(), config);
	}
}
