package de.hgykilian.scrabble;

public class Vector2 {
	int x;
	int y;

	Vector2 (int x, int y) {
		this.x = x;
		this.y = y;
	}

	Vector2 add(Vector2 other) {
		return new Vector2(x + other.x, y + other.y);
	}

	Vector2 mul(int n) {
		return new Vector2(x*n, y*n);
	}
}
