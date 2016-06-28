package de.hgykilian.scrabble;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.badlogic.gdx.Gdx;

public class WordChecker {
	static BufferedReader file;
	public static boolean check (String word) {
		boolean found = false;
		try {
			file = new BufferedReader(new FileReader("words.txt"));
			String line;
			
			while((line = file.readLine()) != null)
			{
			   if (word.equalsIgnoreCase(line)) {
				   found = true;
				   break;
			   }
			}
			file.close();
			
			if (found) {
				Gdx.app.log("Word", "Word found");
			} else {
				Gdx.app.log("Word", "Word not found");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return true; // FIXME
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return found;
		
		
	}
	
}
