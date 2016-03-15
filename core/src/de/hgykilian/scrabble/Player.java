package de.hgykilian.scrabble;

import java.util.ArrayList;
import java.util.List;

public class Player {
	ArrayList<Word> words = new ArrayList<Word>();
	List<Character> chars = new ArrayList<Character>();
	int score;
	boolean pass;
	
	public Player(){
		score = 0;
		pass = false;
	}
	
	public void start(){
		
	}
	
	public void draw(){
		
	}
	
	public void pass(Scrabble sc) {
		pass = true;
	}
	
	
	public void end(Scrabble sc){
		if (pass = false) {
			sc.consecutivePasses = 0;
		}
		for(int i = chars.size()-1; i <= 7; i++){
			chars.add(sc.popChar());
		}
		pass = false;
	}

	public void replaceChar(Scrabble sc, char[] c) {
		for (char ch : c) {
			chars.remove(ch);
		}
		chars.add(sc.popChar());
		sc.addChar(c);
		
	}
}
