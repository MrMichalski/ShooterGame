package com.mygdx.shootergame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.shootergame.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Strzelanka 2D";
		config.height = 800;
		config.width = 480;
		config.backgroundFPS = 75;
		config.foregroundFPS = 75;
		config.resizable = false;
		new LwjglApplication(new Main(), config);
	}
}
