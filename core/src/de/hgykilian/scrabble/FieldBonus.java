package de.hgykilian.scrabble;

/* 
 * 24 × doppelter Buchstabenwert (hellblaue Felder),
 * 16 × doppelter Wortwert (rosafarbene Felder),
 * 12 × dreifacher Buchstabenwert (dunkelblaue Felder),
 * 8 × dreifacher Wortwert (rote Felder).
 */

public enum FieldBonus {
	NONE, DOUBLE_LETTER, DOUBLE_WORD,
	TRIPPLE_LETTER, TRIPPLE_WORD
}