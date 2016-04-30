package de.hgykilian.scrabble;

public class Word {
	enum Direction {
		UP(0, 1), RIGHT(1, 0), DOWN(0, -1), LEFT(-1, 0);
		final int x;
	    final int y;
	    Direction(int mass, int radius) {
	        this.x = mass;
	        this.y = radius;
	    }
	    
	    static Direction fromXY(int x, int y) {
	    	Direction[] directions = {UP, RIGHT, DOWN, LEFT};
	    	for (Direction d : directions) {
				if (d.x == x && d.y == y) {
					return d;
				}
			}
			return null;
	    }
	}
	String word;
	int startX;
	int startY;
	Direction direction;
	private Board board;
	
	Word(Field field, Board board) {
		startX = field.x;
		startY = field.y;
		word = field.currentChar.toString();
		this.board = board;
	}

	public Field[] getFields() {
		Field[] fields = new Field[word.length() - 1];
		for (int i = 0; i < word.length(); i++) {
			Field f = new Field(startX + direction.x * i, startY + direction.y * i, null);
			f.currentChar = new Character(word.charAt(i));
			fields[i] = f;
		}
		return fields;
	}

	@SuppressWarnings("incomplete-switch")
	public int getScore() {
		Field[] fields = getFields();
		int score = 0;
		for (Field field : fields) {
			score += field.getLetterScore(true);
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
	
	public void checkNextOnBoard() {
		while (true) {
			int xIndex = startX + (word.length() + 1) * direction.x + board.size/2;
			int yIndex = startY + (word.length() + 1) * direction.y + board.size/2;
			if (xIndex < 0 || yIndex < 0 || xIndex > board.size || yIndex > board.size) {
				return;
			}
			Field f = board.fields[xIndex][yIndex];
			if (f.hasChar()) {
				word += f.currentChar;
			} else {
				return;
			}
		}
	}

	public boolean add(Field field) {
		if (direction == null) {
			direction = Direction.fromXY(startX - field.x, startY - field.y);
			if (direction == null) {
				return false;
			}
		} else {
			int nextX = startX + (word.length() + 1) * direction.x;
			int nextY = startY + (word.length() + 1) * direction.y;
			if (field.x != nextX || field.y != nextY) {
				return false;
			}	
		}

		word += field.currentChar;
		checkNextOnBoard();
		return true;
	}
}
