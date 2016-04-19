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
    Player player;
    int index;

    public CharActor (final char c, final Player player) {
        this.board = player.board;
        this.player = player;
        region = new TextureRegion();
        final CharActor actor = this;
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
            	//actor.debug();
            }
            
            public void dragStop(InputEvent event, float x, float y, int pointer)  {
            	Field snapField = board.getSnapField(new Vector2(getX(), getY()));
            	if (snapField != null) {
            		snapField.currentChar = c;
            		board.fields[snapField.x+board.size/2][snapField.y+board.size/2] = snapField;
                	player.delChar(actor);
            	}
            	actor.resetPos();
            }
        });
        this.resetPos();
    }
    
    public void resetPos() {
    	setPosition(0, index * (board.fieldSize + board.fieldGap));
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
    	if (getWidth() != board.fieldSize) {
            setWidth(board.fieldSize);
            setHeight(board.fieldSize);
            setBounds(0, 0, getWidth(), getHeight());
            resetPos();
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