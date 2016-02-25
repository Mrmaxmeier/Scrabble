package de.hgykilian.scrabble;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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
	
	public void drawRand(SpriteBatch batch, BitmapFont font){
		for (int i = 1; i < 16; i++) {
			int a = 0;
			if (i > 9){
				a = 5;
			}
			font.setColor(0, 0, 0, 1);
			font.draw(batch, String.valueOf(i), 17 - a, 56+32*(15-i));
			font.draw(batch, String.valueOf(i), 525 - a, 56+32*(15-i));
			
			font.draw(batch, String.valueOf((char) (64 + i)), 46+32*(i-1), 550-17);
			font.draw(batch, String.valueOf((char) (64 + i)), 46+32*(i-1), 550-525);
		}
		
		
	}
	
}
