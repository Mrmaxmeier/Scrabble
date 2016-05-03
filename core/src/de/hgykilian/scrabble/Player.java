package de.hgykilian.scrabble;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Player {
	ArrayList<Word> words = new ArrayList<Word>();
	List<CharActor> chars = new ArrayList<>();
	int score;
	boolean pass;
	boolean firstPass;
	boolean secondPass;
	Board board;
	Stage stage;
	ArrayList<Field> currentWord = new ArrayList<>();
	private static BitmapFont font;
	
	public enum Position {
		LEFT, RIGHT
	}
	Position position;
	
	public Player(Position position, Board board, Stage stage){
		this.board = board;
		this.stage = stage;
		this.position = position;
		score = 0;
		pass = false;
//		if ()
	}
	
	public void start() {
		
	}
	
	public void draw() {
	}
	
	public void pass(Scrabble sc) {
		pass = true;
	}
	
	
	public void end(Scrabble sc){
		if (pass = false) {
			sc.consecutivePasses = 0;
		}
		fillChars(sc);
	}
	
	public void fillChars(Scrabble game) {
		if (chars.size() < 7) {
			for(int i = chars.size()-1; i <= 7; i++){
				CharActor ca = new CharActor(game.popChar(), this);
				chars.add(ca);
				this.stage.addActor(ca);
			}
		updateChars();
		}
		pass = false;
	}

	public void replaceChar(Scrabble sc, CharActor[] c) {
		for (CharActor ch : c) {
			chars.remove(ch);
		}
		fillChars(sc);
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
