package de.hgykilian.scrabble;

import java.util.ArrayList;
import java.util.List;

public class Player {
	ArrayList<Word> words = new ArrayList<Word>();
	List<Character> chars = new ArrayList<Character>();
	int score;
	boolean firstPass;
	boolean secondPass;
	
	public Player(){
		score = 0;
		firstPass = false;
		secondPass = false;
	}
	
	public void start(){
		
	}
	
	public void draw(){
		
	}
	
	public void end(Scrabble sc){
		for(int i = chars.size()-1; i <= 7; i++){
			chars.add(sc.popChar());
		}
	}
}
