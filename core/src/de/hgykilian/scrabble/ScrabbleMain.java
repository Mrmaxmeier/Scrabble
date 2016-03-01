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
	Scrabble game = new Scrabble();
	BitmapFont font;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		font = new BitmapFont();
		font.setColor(Color.BLUE);
		
		
		game.addPlayer(new Player());
		
		int i = 0;
		for (Field[] row : game.board.fields) {
			for (Field field : row) {
				if (i % 4 == 0) {
					field.currentChar = game.popChar();
				}
				i++;
			}
		}
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());


        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, 1);
    	shapeRenderer.rect(34, 34, 482, 482);
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
        game.board.drawRand(batch, font);
        
        game.players.get(game.currentPlayer).draw();
		batch.end();
	}
}
