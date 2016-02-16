package de.hgykilian.scrabble;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class ScrabbleMain extends ApplicationAdapter {
	SpriteBatch batch;
	ShapeRenderer shapeRenderer;
	Texture board_tex;
	Scrabble game = new Scrabble();
	BitmapFont font;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		font = new BitmapFont();
		font.setColor(Color.BLUE);
		board_tex = new Texture("scrabble_map.png");
		game.board.fields[0][0].currentChar = new Character('A');
		game.board.fields[2][3].currentChar = new Character('B');
		game.board.fields[7][7].currentChar = new Character('Q');
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
		batch.begin();
		batch.draw(board_tex, 0, 0);
		batch.end();

        shapeRenderer.begin(ShapeType.Filled);
		for (Field[] row : game.board.fields) {
			for (Field field : row) {
				field.draw(shapeRenderer);
			}
		}
        shapeRenderer.end();
        
        batch.begin();
        for (Field[] row : game.board.fields) {
			for (Field field : row) {
				field.drawText(batch, font);
			}
		}
		batch.end();
	}
}
