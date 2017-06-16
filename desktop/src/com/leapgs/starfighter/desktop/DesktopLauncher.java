package com.leapgs.starfighter.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.leapgs.starfighter.MainGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Starfighter Repair";
		config.width = 400;
		config.height = 400;
		new LwjglApplication(new MainGame(), config);
	}
}
