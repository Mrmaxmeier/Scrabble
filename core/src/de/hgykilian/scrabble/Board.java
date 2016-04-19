package de.hgykilian.scrabble;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Board {
	int size = 15;
	Field[][] fields;
	Word[] words;
	Viewport viewport;
	

	int fieldSize = 30;
	int fieldGap = 2;
	
	public Board() {
		fields = new Field[size][size];
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				fields[x][y] = new Field(x - size/2, y - size/2, this);
				//System.out.printf("%d %d %s\n", x, y, fields[x][y].getBonus());
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
	
	// Mit x und y von -size/2 bis size/2 (-7 und 7)
	public Vector2 getFieldPos(int x, int y, PositionType type) {
		int width = viewport.getScreenWidth();
		int height = viewport.getScreenHeight();
		int boardSize = (int) (Math.min(width, height) * 0.8);
		fieldSize = (boardSize / size)-fieldGap;
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
		int xP = (width - boardSize) / 2 + (x+size/2) * (fieldSize+fieldGap);
		int yP = (height - boardSize) / 2 + (y+size/2) * (fieldSize+fieldGap);
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
	
	public Vector2 getSidebarTextPos(Vector2 pos, PositionType type) {
		int xP = (int) pos.x;
		int yP = (int) pos.y;
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
	
	public Vector2 getSidebarPos(int index, int total, Player.Position playerPos) {
		int yP = viewport.getScreenHeight()/2 + (total/2 - index) * (fieldSize + fieldGap);
		int xP = 0;
		if (playerPos == Player.Position.RIGHT) {
			xP = viewport.getScreenWidth() - fieldSize;
		}
		return new Vector2(xP, yP);
	}
	
	public boolean hasCharNeighbour(int x, int y) {
		if ((x > 0 && fields[x-1][y].hasChar()) || (x < size-1 && fields[x+1][y].hasChar())) {
			return true;
		}
		if ((y > 0 && fields[x][y-1].hasChar()) || (y < size-1 && fields[x][y+1].hasChar())) {
			return true;
		}
		return false;
	}
	
	public Field getSnapField(Vector2 pos) {
		float nearestDist = 0;
		Field nearestField = null;
		float nearestUsableDist = 0;
		Field nearestUsableField = null;
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				// Vector2 gibt vec2 zurück, modifiziert aber auch inplace .__.
				Vector2 fpos = getFieldPos(x-size/2, y-size/2, Board.PositionType.BOTTOM_LEFT);
				if (nearestDist > fpos.cpy().sub(pos).len2() || nearestField == null) {
					nearestDist = fpos.sub(pos).len2();
					nearestField = fields[x][y];
				}
				if (!hasCharNeighbour(x, y) || fields[x][y].hasChar()) {
					continue;
				}
				
				if (nearestUsableDist > fpos.cpy().sub(pos).len2() || nearestUsableField == null) {
					nearestUsableDist = fpos.sub(pos).len2();
					nearestUsableField = fields[x][y];
				}
			}
		}
		
		
		if (nearestField.currentChar == null) {
			int index_x = nearestField.x + size/2;
			int index_y = nearestField.y + size/2;
			for (int i = 0; i < (size - index_x); i++) {
				if (fields[index_x + i][index_y].hasChar()) {
					return nearestField;
				}
			}
		
			for (int i = 0; i < index_y; i++) {
				if (fields[index_x][index_y - i].hasChar()) {
					return nearestField;
				}
			}
		}
		
		if (nearestUsableField == null || nearestUsableDist > fieldSize*fieldSize*2) {
			return null;
		}
		
		return nearestUsableField;
	}
	
	public Vector2 getSnapPoint(Vector2 pos) {
		Field f = getSnapField(pos);
		if (f != null) {
			return getFieldPos(f.x, f.y, Board.PositionType.BOTTOM_LEFT);
		}
		return null;
	}


	public void drawBackground(ShapeRenderer shapeRenderer) {
		Vector2 from = getFieldPos(-size/2, -size/2, PositionType.BOTTOM_LEFT);
		int width = (fieldSize + fieldGap) * size + fieldGap;
    	shapeRenderer.rect(from.x - fieldGap, from.y - fieldGap, width, width);
	}
}
