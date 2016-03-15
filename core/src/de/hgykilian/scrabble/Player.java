package de.hgykilian.scrabble;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Player {
	ArrayList<Word> words = new ArrayList<Word>();
	List<Character> chars = new ArrayList<Character>();
	int score;
	boolean firstPass;
	boolean secondPass;
	Board board;
	CharActor ca;
	Stage stage;
	public enum Position {
		LEFT, RIGHT
	}
	Position position;
	
	public Player(Position position, Board board, Stage stage){
		this.board = board;
		this.stage = stage;
		this.position = position;
		score = 0;
		firstPass = false;
		secondPass = false;
		ca = new CharActor('H', board);
		this.stage.addActor(ca);
	}
	
	public void start(){
		
	}
	
	public void draw(){
		for (int i = 0; i < chars.size(); i++) {
		}
	}
	
	public void end(Scrabble sc){
		for(int i = chars.size()-1; i <= 7; i++){
			chars.add(sc.popChar());
		}
	}
}
