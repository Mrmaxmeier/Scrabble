package de.hgykilian.scrabble;

public class Board {
	int size = 15;
	Field[][] fields;
	Word[] words;
	
	public Board() {
		fields = new Field[size][size];
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				fields[x][y] = new Field(x - size/2, y - size/2);
				System.out.printf("%d %d %s\n", x, y, fields[x][y].getBonus());
			}
		}
	}
}