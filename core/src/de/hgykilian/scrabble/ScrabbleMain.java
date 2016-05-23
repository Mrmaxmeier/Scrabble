package de.hgykilian.scrabble;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class ScrabbleMain extends ApplicationAdapter {
	SpriteBatch batch;
	ShapeRenderer shapeRenderer;
	Scrabble game = new Scrabble();
	BitmapFont font;
	ScreenViewport viewport;
	Stage stage;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		font = new BitmapFont();
		viewport = new ScreenViewport();
	    stage = new Stage(viewport, batch);
		game.board.viewport = viewport;
	
	    Gdx.input.setInputProcessor(stage);
	    
		game.addPlayer(new Player(Player.Position.LEFT, game.board, stage));
		game.addPlayer(new Player(Player.Position.RIGHT, game.board, stage));

		game.update();
	}

	@Override
	public void render () {
		viewport.apply();
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());


        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, 1);
        game.board.drawBackground(shapeRenderer);
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
        game.draw(font, batch);
		batch.end();
		stage.act();
        stage.draw();
	}
	
	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
	}
}
