package de.hgykilian.scrabble;

import java.util.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Scrabble {
	Board board;
	List<Player> players = new ArrayList<Player>();
	Queue<Character> charPool = new LinkedList<Character>();
	int currentPlayer;
	int consecutivePasses = 0;

	public Scrabble() {
		board = new Board();
		currentPlayer = 0;
		
		fcp('E', 15);
		fcp('N', 9);
		fcp('S', 7);
		fcp('I', 6);
		fcp('T', 6);
		fcp('U', 6);
		fcp('A', 5);
		fcp('D', 4);
		
		fcp('H', 4);
		fcp('G', 3);
		fcp('L', 3);
		fcp('O', 3);
		
		fcp('M', 4);
		fcp('B', 2);
		fcp('W', 1);
		fcp('Z', 1);
		
		fcp('C', 2);
		fcp('F', 2);
		fcp('K', 2);
		fcp('P', 1);
		
		fcp('Ü', 1);
		fcp('J', 1);
		fcp('Ö', 1);
		fcp('V', 1);
		
		fcp('Ä', 1);
		fcp('X', 1);
		fcp('Q', 1);
		fcp('Y', 1);
		shuffle();
		
		// 2 Joker/Blankos
	}
	
	public void fcp(char c, int amount) {
		for (int i = 0; i < amount; i++) {
			charPool.add(new Character(c));
		}
	}

	@SuppressWarnings("unchecked")
	public void shuffle() {
		Collections.shuffle((List<Character>) charPool);
	}
	
	public void addChar(char[] c) {
		for (char ch : c) {
			charPool.add(ch);
		}
		shuffle();
	}
	
	public Character popChar() {
		if (charPool.size() < 20) {
			System.out.println("Chars left: " + (charPool.size()-1));
		}
		return charPool.isEmpty() ? null : charPool.remove();
	}
	
	public void addPlayer(Player p) {
		p.fillChars(this);
		players.add(p);
	}
	
	public void nextPlayer(){
		boolean noChars = charPool.isEmpty() && players.get(currentPlayer).chars.size() == 0;
		boolean passed = consecutivePasses >= players.size()*2;
		
		if (noChars || passed) {
			endGame();
			return;
		}
		players.get(currentPlayer).end(this);
		if (currentPlayer < players.size()-1) {
			currentPlayer++;
		} else {
			currentPlayer = 0;
		}
		update();
	}
	
	public void draw(BitmapFont font, SpriteBatch batch) {
		for (int i = 0; i < players.size(); i++) {
			//draw score von den playern
			players.get(i).draw(batch, board);
		}
	}
	
	public void pass() {
		consecutivePasses++;
		players.get(currentPlayer).pass(this);
	}
	
	public void replaceChar(CharActor[] c) {
		if (charPool.size() >= 7) {
			players.get(currentPlayer).replaceChar(this, c);
		} else {
			Gdx.app.log("Scrabble", "Remaining letters < 7 | Replacing not allowed");
		}
		
		nextPlayer();
	}
	
	public void endGame(){
		
	}

	public void update() {
		for (int i = 0; i < players.size(); i++) {
			players.get(i).setActive(i == currentPlayer);
		}
	}
}
