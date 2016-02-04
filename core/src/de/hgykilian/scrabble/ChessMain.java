package de.hgykilian.scrabble;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ChessMain extends ApplicationAdapter {
	SpriteBatch batch;
	Texture chessboard;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		chessboard = new Texture("chessboard.jpg");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(chessboard, 0, 0);
		batch.end();
	}
}
