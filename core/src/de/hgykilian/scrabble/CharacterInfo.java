package de.hgykilian.scrabble;

public class CharacterInfo {

	public static int getLetterScore (Character currentChar) {
		int score = 1;
		if ("ENSIRTUAD".indexOf(currentChar.charValue()) != -1) {
			score = 1;
		} else if ("HGLO".indexOf(currentChar.charValue()) != -1) {
			score = 2;
		} else if ("MBWZ".indexOf(currentChar.charValue()) != -1) {
			score = 3;
		} else if ("CFKP".indexOf(currentChar.charValue()) != -1) {
			score = 4;
		} else if ("ÄJÜV".indexOf(currentChar.charValue()) != -1) {
			score = 6;
		} else if ("ÖX".indexOf(currentChar.charValue()) != -1) {
			score = 8;
		} else if ("QY".indexOf(currentChar.charValue()) != -1) {
			score = 10;
		}

		return score;
	}
}
