package de.dingermueller.chess.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import de.dingermueller.chess.ChessMain;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Chess";
		config.width= 800;
		config.height = 800;
		new LwjglApplication(new ChessMain(), config);
	}
}
