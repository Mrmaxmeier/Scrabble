package de.hgykilian.scrabble;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import jdk.internal.util.xml.impl.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

	public Field getField(int x, int y) {
		if (x < -size/2 || y < -size/2 || x > size/2 || y > size/2) {
			return null;
		}
		return fields[x+size/2][y+size/2];
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
	
	public Vector2 getPlayerScorePos(Player.Position playerPos) {
		int xP = 2;
		if (playerPos == Player.Position.RIGHT) {
			xP = viewport.getScreenWidth() - 75;
		}
		return new Vector2(xP, viewport.getScreenHeight()-2);
	}
	
	public boolean hasCharNeighbour(Field field) {
		int x = field.x + size/2;
		int y = field.y + size/2;
		if ((x > 0 && fields[x-1][y].hasChar()) || (x < size-1 && fields[x+1][y].hasChar())) {
			return true;
		}
		if ((y > 0 && fields[x][y-1].hasChar()) || (y < size-1 && fields[x][y+1].hasChar())) {
			return true;
		}
		return false;
	}

	public Stream<Field> fields() {
		Field[] fields = new Field[size * size];
		int index = 0;
		for ( Field[] rows : this.fields) {
			for (Field field : rows) {
				fields[index++] = field;
			}
		}
		return Arrays.stream(fields);
	}

	public Field getSnapField(Vector2 pos) {

		class FieldDistPair {
			public Field field;
			public float dist;
			public FieldDistPair() {}
			public FieldDistPair(Field f, Vector2 point) {
				field = f;
				dist = getFieldPos(field.x, field.y, Board.PositionType.BOTTOM_LEFT).sub(pos).len2();
			}
		}

		List<FieldDistPair> f = fields()
				.filter(field -> !field.hasChar())
				.filter(field -> hasCharNeighbour(field) || (field.x == 0 && field.y == 0))
				.map(field -> new FieldDistPair(field, pos))
				.filter(pair -> pair.dist < fieldSize * fieldSize / 2f)
				.collect(Collectors.toCollection(ArrayList::new));

		if (f.size() < 1) {
			return null;
		}

		float dist = f.stream()
				.map(pair -> pair.dist)
				.min(Float::compare).get();

		return f.stream()
				.filter(pair -> pair.dist == dist).findFirst().orElse(new FieldDistPair()).field;
	}
	
	public Vector2 getSnapPoint(Vector2 pos) {
		Field f = getSnapField(pos);
		if (f != null) {
			return getFieldPos(f.x, f.y, Board.PositionType.BOTTOM_LEFT);
		}
		return null;
	}
	
	public Vector2 getWordSnap(Vector2 pos, Word word) {
		if (word == null) {
			return null;
		}
		Field f = getSnapField(pos);
		if (f == null) {
			return null;
		}
		if (word.direction == null) {
			int manhattanDist = Math.abs(word.start.x - f.x) + Math.abs(word.start.y - f.y);
			if (manhattanDist != 1) {
				return null;
			}
		} else {
			if (word.direction.x == 0 && word.start.x != f.x) {
				return null;
			}
			if (word.direction.y == 0 && word.start.y != f.y) {
				return null;
			}

			de.hgykilian.scrabble.Vector2 p = word.direction.movePosTimes(word.start, word.word.length());
			if (word.start.x != p.x || word.start.y != p.y) {
				return null;
			}

		}
		return getFieldPos(f.x, f.y, Board.PositionType.BOTTOM_LEFT);
	}


	public void drawBackground(ShapeRenderer shapeRenderer) {
		Vector2 from = getFieldPos(-size/2, -size/2, PositionType.BOTTOM_LEFT);
		int width = (fieldSize + fieldGap) * size + fieldGap;
    	shapeRenderer.rect(from.x - fieldGap, from.y - fieldGap, width, width);
	}
}
