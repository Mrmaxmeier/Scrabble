package de.hgykilian.scrabble.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import de.hgykilian.scrabble.ChessMain;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Scrabble";
		config.width= 550;
		config.height = 550;
		new LwjglApplication(new ChessMain(), config);
	}
}
