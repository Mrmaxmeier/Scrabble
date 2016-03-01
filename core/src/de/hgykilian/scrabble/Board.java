package de.hgykilian.scrabble;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Board {
	int size = 15;
	Field[][] fields;
	Word[] words;
	

	int fieldSize = 30;
	int fieldGap = 2;
	
	public Board() {
		fields = new Field[size][size];
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				fields[x][y] = new Field(x - size/2, y - size/2, this);
				System.out.printf("%d %d %s\n", x, y, fields[x][y].getBonus());
			}
		}
	}
	
	
	public void drawRand(SpriteBatch batch, BitmapFont font){
		for (int i = 1; i < 16; i++) {
			int a = 0;
			if (i > 9){
				a = 5;
			}
			font.setColor(0, 0, 0, 1);
			Vector2 pos = getFieldPos(-8, -i+8, PositionType.FIELD_CHAR);
			//font.draw(batch, String.valueOf(i), 17 - a, 56+32*(15-i));
			font.draw(batch, String.valueOf(i), pos.x - a, pos.y);
			pos = getFieldPos(8, 8-i, PositionType.FIELD_CHAR);
			font.draw(batch, String.valueOf(i), pos.x - a, pos.y);

			pos = getFieldPos(i-8, 8, PositionType.FIELD_CHAR);
			font.draw(batch, String.valueOf((char) (64 + i)), pos.x, pos.y);
			pos = getFieldPos(i-8, -8, PositionType.FIELD_CHAR);
			font.draw(batch, String.valueOf((char) (64 + i)), pos.x, pos.y);
		}
		
		
	}
	
	public enum PositionType {
		TOP_LEFT, BOTTOM_LEFT,
		BOTTOM_RIGHT, MIDDLE,
		FIELD_CHAR, FIELD_SCORE
	}
	
	public Vector2 getFieldPos(int x, int y, PositionType type) {
		switch (type) {
		case TOP_LEFT:
			y++;
			break;
		case BOTTOM_LEFT:
			break;
		case BOTTOM_RIGHT:
			x++;
			break;
		default:
			break;
		}
		int xP = 120 + 36 + (x+7) * (fieldSize+fieldGap);
		int yP = 25 + 36 + (y+7) * (fieldSize+fieldGap);
		if (type == PositionType.MIDDLE) {
			xP += fieldSize/2;
			yP += fieldSize/2;
		} else if (type == PositionType.FIELD_SCORE) {
			xP += fieldSize*2/3;
			yP += fieldSize*0.4f;
		} else if (type == PositionType.FIELD_CHAR) {
			xP += fieldSize/3;
			yP += fieldSize*0.7f;
		}
		return new Vector2(xP, yP);
	}


	public void drawBackground(ShapeRenderer shapeRenderer) {
		Vector2 from = getFieldPos(-7, -7, PositionType.BOTTOM_LEFT);
		int width = (fieldSize + fieldGap) * 15 + fieldGap;
    	shapeRenderer.rect(from.x - fieldGap, from.y - fieldGap, width, width);
	}
}
