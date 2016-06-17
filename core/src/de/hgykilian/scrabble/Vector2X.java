package de.hgykilian.scrabble;

public class Vector2X {
	int x;
	int y;

	Vector2X (int x, int y) {
		this.x = x;
		this.y = y;
	}

	Vector2X add(Vector2X other) {
		return new Vector2X(x + other.x, y + other.y);
	}

	Vector2X mul(int n) {
		return new Vector2X(x*n, y*n);
	}
}
