package de.hgykilian.scrabble;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Player {
	ArrayList<Word> words = new ArrayList<Word>();
	List<CharActor> chars = new ArrayList<>();
	int score;
	boolean firstPass;
	boolean secondPass;
	Board board;
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
	}
	
	public void start() {
		
	}
	
	public void draw() {
	}
	
	public void end(Scrabble sc){
		fillChars(sc);
	}
	
	public void fillChars(Scrabble game) {
		for(int i = chars.size()-1; i <= 7; i++){
			CharActor ca = new CharActor(game.popChar(), this);
			chars.add(ca);
			this.stage.addActor(ca);
		}
		updateChars();
	}
	
	public void updateChars() {
		for (int i = 0; i < chars.size(); i++) {
			chars.get(i).index = i;	
			chars.get(i).resetPos();
		}
	}

	public void delChar(CharActor actor) {
		chars.remove(actor);
		actor.remove();
		updateChars();
	}
}
