package de.hgykilian.scrabble;

public class Word {
	enum Direction {
		UP, RIGHT, DOWN, LEFT
	}
	String word;
	int startX;
	int startY;
	Direction direction;

	public Field[] getFields() {
		Field[] fields = new Field[word.length() - 1];
		int dX = 0;
		int dY = 0;
		switch (direction) {
		case UP:
			dY = 1;
			break;
		case DOWN:
			dY = -1;
			break;
		case LEFT:
			dX = -1;
			break;
		case RIGHT:
			dX = 1;
			break;
		}
		for (int i = 0; i < word.length(); i++) {
			Field f = new Field(startX + dX * i, startY + dY * i, null);
			f.currentChar = new Character(word.charAt(i));
			fields[i] = f;
		}
		return fields;
	}

	public int getScore() {
		Field[] fields = getFields();
		int score = 0;
		for (Field field : fields) {
			score += field.getLetterScore();
		}
		for (Field field : fields) {
			switch (field.getBonus()) {
			case DOUBLE_WORD:
				score *= 2;
			case TRIPLE_WORD:
				score *= 3;
			}
		}
		return score;
	}
}
