package de.hgykilian.scrabble;

import java.util.*;

public class Scrabble {
	Board board;
	List<Player> players = new ArrayList<Player>();
	Queue<Character> charPool = new LinkedList<Character>();
	
	public Scrabble() {
		board = new Board();
		
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
		
		fcp('Ä', 1);
		fcp('J', 1);
		fcp('Ü', 1);
		fcp('V', 1);
		
		fcp('Ö', 1);
		fcp('X', 1);
		fcp('Q', 1);
		fcp('Y', 1);
		Collections.shuffle((List<Character>) charPool);
		
		// 2 Joker/Blankos
	}
	
	public void fcp(char c, int amount) {
		for (int i = 0; i < amount; i++) {
			charPool.add(new Character(c));
		}
	}
	
	public Character popChar() {
		System.out.println("Chars left: " + (charPool.size()-1));
		return charPool.isEmpty() ? null : charPool.remove();
	}
	
	public void addPlayer(Player p) {
		for (int i = 0; i < 7; i++) {
			p.chars.add(popChar());
		}
		players.add(p);
	}
}
