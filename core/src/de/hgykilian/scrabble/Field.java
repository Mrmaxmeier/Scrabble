package de.hgykilian.scrabble;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Field {
	/*
	 * 24 × doppelter Buchstabenwert (hellblaue Felder),
	 * 16 × doppelter Wortwert (rosafarbene Felder),
	 * 12 × dreifacher Buchstabenwert (dunkelblaue Felder),
	 * 8 × dreifacher Wortwert (rote Felder).
	 */

	public enum Bonus {
		NONE,
		DOUBLE_LETTER, DOUBLE_WORD,
		TRIPLE_LETTER, TRIPLE_WORD
	}

	Character currentChar;
	public Player player;
	Board board;
	int x;
	int y;
	
	public Field(int x, int y, Board board) {
		this.x = x;
		this.y = y;
		this.board = board;
	}
	
	Bonus getBonus() {
		int tmpX = Math.abs(x);
		int tmpY = Math.abs(y);
		if (tmpX == tmpY) {
			switch (tmpX) {
			case 1:
				return Bonus.DOUBLE_LETTER;
			case 2:
				return Bonus.TRIPLE_LETTER;
			case 3:
			case 4:
			case 5:
			case 6:
				return Bonus.DOUBLE_WORD;
			case 7:
				return Bonus.TRIPLE_WORD;
			}
		}
		
		if (tmpX == 0 && tmpY == 4 || tmpX == 4 && tmpY == 0) {
			return Bonus.DOUBLE_LETTER;
		}
		
		if (tmpX == 1 && tmpY == 5 || tmpX == 5 && tmpY == 1) {
			return Bonus.DOUBLE_LETTER;
		}
	
		if (tmpX == 2 && tmpY == 6 || tmpX == 6 && tmpY == 2) {
			return Bonus.TRIPLE_LETTER;
		}
		
		if (tmpX == 4 && tmpY == 7 || tmpX == 7 && tmpY == 4) {
			return Bonus.DOUBLE_LETTER;
		}
		
		if (tmpX == 0 && tmpY == 7 || tmpX == 7 && tmpY == 0) {
			return Bonus.TRIPLE_WORD;
		}
		
		return Bonus.NONE;
	}

	public boolean hasChar() {
		return this.currentChar != null;
	}

	public int getLetterScore(boolean bonus) {
		int score = CharacterInfo.getLetterScore(currentChar);
		if (!bonus) {
			return score;
		}

		switch (getBonus()) {
		case DOUBLE_LETTER:
			return score * 2;
		case TRIPLE_LETTER:
			return score * 2;
		default:
			return score;
		}	
	}

	public void draw(ShapeRenderer shapeRenderer) {
		switch (getBonus()) {
		case DOUBLE_WORD:
			shapeRenderer.setColor(227f/255, 102f/255, 179f/255, 1);
			break;
		case TRIPLE_WORD:
			shapeRenderer.setColor(Color.RED);
			break;
		case DOUBLE_LETTER:
			shapeRenderer.setColor(51f/255, 191f/255, 222f/255, 1);
			break;
		case TRIPLE_LETTER:
			shapeRenderer.setColor(51f/255, 105f/255, 222f/255, 1);
			break;
		default:
			if (x == 0 && y == 0) {
				shapeRenderer.setColor(Color.WHITE);
			} else {
				shapeRenderer.setColor(31f/255, 157f/255, 60f/255, 1);
			}
		}
		Vector2 pos = board.getFieldPos(x, y, Board.PositionType.BOTTOM_LEFT);
        shapeRenderer.rect(pos.x, pos.y, board.fieldSize, board.fieldSize);
	}
	
	public void drawText(SpriteBatch batch, BitmapFont font) {
        if (hasChar()) {
        	Vector2 pos = board.getFieldPos(x, y, Board.PositionType.FIELD_CHAR);
        	font.draw(batch, currentChar.toString(),pos.x, pos.y);
        	Vector2 scorePos = board.getFieldPos(x, y, Board.PositionType.FIELD_SCORE);
        	if (getLetterScore(false) != 10){
        		font.getData().setScale(0.75f);
        	} else {
        		font.getData().setScale(0.65f, 0.75f);
        	}
        	font.draw(batch, String.valueOf(getLetterScore(false)), scorePos.x, scorePos.y);
        	font.getData().setScale(1);
        	
        }
	}
}