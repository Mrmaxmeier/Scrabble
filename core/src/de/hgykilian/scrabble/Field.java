package de.hgykilian.scrabble;

public class Field {
	Character currentChar;
	public Player player;
	int x;
	int y;
	
	public Field(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	FieldBonus getFieldBonus() {
		int tmpX = Math.abs(x);
		int tmpY = Math.abs(y);
		if (tmpX == tmpY) {
			switch (tmpX) {
			case 1:
				return FieldBonus.DOUBLE_LETTER;
			case 2:
				return FieldBonus.TRIPPLE_LETTER;
			case 3:
			case 4:
			case 5:
			case 6:
				return FieldBonus.DOUBLE_WORD;
			case 7:
				return FieldBonus.TRIPPLE_WORD;
			}
		}
		
		if (tmpX == 0 && tmpY == 4 || tmpX == 4 && tmpY == 0) {
			return FieldBonus.DOUBLE_LETTER;
		}
		
		if (tmpX == 1 && tmpY == 5 || tmpX == 5 && tmpY == 1) {
			return FieldBonus.DOUBLE_LETTER;
		}
	
		if (tmpX == 2 && tmpY == 6 || tmpX == 6 && tmpY == 2) {
			return FieldBonus.TRIPPLE_LETTER;
		}
		
		if (tmpX == 4 && tmpY == 7 || tmpX == 7 && tmpY == 4) {
			return FieldBonus.DOUBLE_LETTER;
		}
		
		if (tmpX == 0 && tmpY == 7 || tmpX == 7 && tmpY == 0) {
			return FieldBonus.TRIPPLE_WORD;
		}
		
		return FieldBonus.NONE;
	}

	public boolean hasChar() {
		return this.currentChar != null;
	}
}
