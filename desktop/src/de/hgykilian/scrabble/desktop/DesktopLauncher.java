package de.hgykilian.scrabble.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import de.hgykilian.scrabble.ScrabbleMain;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Scrabble";
		config.width= 800;
		config.height = 600;
		new LwjglApplication(new ScrabbleMain(), config);
	}
}
