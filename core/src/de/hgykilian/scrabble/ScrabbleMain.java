package de.hgykilian.scrabble;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class ScrabbleMain extends ApplicationAdapter implements InputProcessor {
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
		
		InputMultiplexer inputM = new InputMultiplexer();
		inputM.addProcessor(stage);
		inputM.addProcessor(this);
	
	    Gdx.input.setInputProcessor(inputM);
	    
		game.addPlayer(new Player(Player.Position.LEFT, game.board, stage));
		game.addPlayer(new Player(Player.Position.RIGHT, game.board, stage));

		game.update();
	}

	@Override
	public void render () {
		viewport.apply();
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);;
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
        
        if (game.gameFinished) {
//        	game.drawOverlay(shapeRenderer);
        	shapeRenderer.begin(ShapeType.Filled);
        	shapeRenderer.setColor(0, 0, 0, 0);
        	shapeRenderer.rect(0, 0, viewport.getScreenWidth(), viewport.getScreenHeight());
        	shapeRenderer.end();
        	
        }
	}
	
	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
	}

	@Override
	public boolean keyDown(int arg0) {
		if (Input.Keys.ENTER == arg0 && !game.gameFinished) {
			if (game.players.get(game.currentPlayer).currentTrashChars.isEmpty()) {
				if (game.players.get(game.currentPlayer).currentWord == null) {
					game.pass(); // current player passes
				} else {
					game.checkWord(); // current player has a word
				}
			} else {
				game.replaceChar(game.players.get(game.currentPlayer).currentTrashChars.toArray(new CharActor[0])); //// curret player change chars
			}
			game.nextPlayer();
			return true;
		}
		return false;
	}

	@Override
	public boolean keyTyped(char arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}
}
