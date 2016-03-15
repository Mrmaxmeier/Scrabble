package de.hgykilian.scrabble;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;

public class CharActor extends Actor {
    TextureRegion region;
    char c;
    ShapeRenderer renderer = new ShapeRenderer();
    Board board;

    public CharActor (char c, final Board board) {
        this.board = board;
        region = new TextureRegion();
        final Actor actor = this;
        this.addListener(new DragListener() {
            public void drag(InputEvent event, float x, float y, int pointer) {
            	actor.moveBy(x - actor.getWidth() / 2, y - actor.getHeight() / 2);
            	Vector2 newpos = new Vector2(getX(), getY());
            	if (board.getSnapPoint(newpos) != null) {
            		newpos = board.getSnapPoint(newpos);
            		actor.setPosition(newpos.x, newpos.y);
            	}
            }
            
            public void dragStart(InputEvent event, float x, float y, int pointer)  {
            	actor.debug();
            }
            
            public void dragStop(InputEvent event, float x, float y, int pointer)  {
            	actor.setPosition(0,  0);
            }
        });
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
    	if (getWidth() != board.fieldSize) {
            setWidth(board.fieldSize);
            setHeight(board.fieldSize);
            setBounds(0, 0, getWidth(), getHeight());
    	}
        batch.end();
        renderer.setProjectionMatrix(batch.getProjectionMatrix());
        renderer.setTransformMatrix(batch.getTransformMatrix());
        renderer.translate(getX(), getY(), 0);

        renderer.begin(ShapeType.Filled);
        renderer.setColor(Color.BLUE);
        renderer.rect(0, 0, getWidth(), getHeight());
        renderer.end();

        batch.begin();
    }
}
