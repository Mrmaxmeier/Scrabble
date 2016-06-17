package de.hgykilian.scrabble;



public class Word {
	enum Direction {
		UP(0, 1), RIGHT(1, 0), DOWN(0, -1), LEFT(-1, 0);
		final int x;
		final int y;
		final Vector2X vec;

		Direction(int x, int y) {
			this.x = x;
			this.y = y;
			this.vec = new Vector2X(x, y);
		}

		static Direction fromXY(int x, int y) {
			Direction[] directions = { UP, RIGHT, DOWN, LEFT };
			for (Direction d : directions) {
				if (d.x == x && d.y == y) {
					return d;
				}
			}
			return null;
		}

		static Direction fromVec(Vector2X v) {
			return Direction.fromXY(v.x, v.y);
		}

		Vector2X movePosTimes(Vector2X pos, int times) {
			return new Vector2X(x*times, y*times).add(pos);
		}
	}

	String word;
	Vector2X start;
	Direction direction;
	private Board board;

	Word(Field field, Board board) {
		start = new Vector2X(field.x, field.y);
		word = field.currentChar.toString();
		this.board = board;
	}

	public Field[] getFields() {
		Field[] fields = new Field[word.length()];
		for (int i = 0; i < word.length(); i++) {
			Vector2X pos = start.add(direction.vec.mul(i));
			Field f = new Field(pos.x, pos.y, null);
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
			Vector2X next = start.add(direction.vec.mul(word.length()));
			Field f = board.getField(next.x, next.y);
			if (f == null || !f.hasChar()) {
				return;
			}
			word += (char) f.currentChar;
		}
	}

	public boolean add(Field field, char character) {
		if (direction == null) {
			direction = Direction.fromXY(field.x - start.x, field.y - start.y);
			if (direction == null) {
				return false;
			}
		} else {
			Vector2X next = start.add(direction.vec.mul(word.length()));
			if (field.x != next.x || field.y != next.y) {
				return false;
			}
		}

		word += character;
		checkNextOnBoard();
		return true;
	}
	
	public CharActor[] clearWord (Player player) {
//		Field snapField = board.getSnapField(new Vector2(getX(), getY()));

		char[] c = word.toCharArray();
		CharActor[] ca = new CharActor[c.length];
		for (int i = 0; i < c.length; i++) {
			ca[i] = new CharActor(c[i], player);
		}
		return ca;
	}
}
