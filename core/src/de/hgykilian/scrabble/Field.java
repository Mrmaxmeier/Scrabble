package de.hgykilian.scrabble;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

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
	int x;
	int y;
	
	public Field(int x, int y) {
		this.x = x;
		this.y = y;
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

	public int getLetterScore() {
		int score = 1;
		if ("ENSIRTUAD".indexOf(currentChar.charValue()) != -1) {
			score = 1;
		} else if ("HGLO".indexOf(currentChar.charValue()) != -1) {
			score = 2;
		} else if ("MBWZ".indexOf(currentChar.charValue()) != -1) {
			score = 3;
		} else if ("CFKP".indexOf(currentChar.charValue()) != -1) {
			score = 4;
		} else if ("ÄJÜV".indexOf(currentChar.charValue()) != -1) {
			score = 6;
		} else if ("ÖX".indexOf(currentChar.charValue()) != -1) {
			score = 8;
		} else if ("QY".indexOf(currentChar.charValue()) != -1) {
			score = 10;
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
		int fieldSize = 30;
		int gap = 2;
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(36 + (x+7)*(fieldSize+gap), 36 + (y+7)*(fieldSize+gap), fieldSize, fieldSize);
	}
}
