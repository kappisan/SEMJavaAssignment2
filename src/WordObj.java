import java.awt.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;


public class WordObj {
	private String textFileLocation = "src/words.txt";
	
	private String guessWord; // this is the word to be guessed
	private char[] wordArray; // this is an array containing each char of the word to be guessed
	private char[] guessStringArray; // this is the array with correctly guessed letters and underscores
	private String guessTextString = ""; // this is the string showing correct guesses
	private int guessLeft = 10; // guesses left
	private String wrongGuessTextString = ""; // the string displayed containing wrong guesses
	private boolean gameRunning; // a variable that changes to false once the game has been won / lost
	ArrayList<String> wrongCharacterList;
		
	// constructor
	public WordObj() {
		try {
			guessWord = generateRandomWordfromFile();
			System.out.println("WORD IS FROM FILE");
		} catch (IOException e) {
			guessWord = generateRandomWord();
			System.out.println("WORD NOT FROM FILE");
			e.printStackTrace();
		}
		
		wrongCharacterList = new ArrayList<String>();
		wordArray = new char[guessWord.length()];
		guessStringArray = new char[guessWord.length()];

		for(int i = 0; i < guessWord.length(); i++) {
			wordArray[i] = guessWord.charAt(i);
			guessStringArray[i] = '_'; // populate guess array with underscores
		}
		
		updateGuessTextString(); // creates a fresh guess text string
		gameRunning = true;
	}
	
	private void updateGuessTextString() {
		guessTextString = "";
		for(int i = 0; i < guessWord.length(); i++) {
			guessTextString += guessStringArray[i] + " ";
		}
	}
	
	public String getGuessLeftString() {
		return new Integer(guessLeft).toString();
	}
	
	public String getTextString() {
		return guessTextString;
	}	
	
	public boolean checkGameState() {
		return gameRunning;
	}	

	public String getWrongTextString() {
		return wrongGuessTextString;
	}	
	
	public void checkGuess(String inputString) {
		if(gameRunning) {
			char inputChar = Character.toUpperCase(inputString.charAt(0));
			boolean hit = false; // check if guess is correct
			
			for(int i = 0; i < guessWord.length(); i++) {
				if(wordArray[i] == inputChar) {
					hit = true;
					guessStringArray[i] = inputChar;
				}
			}
			
			if(hit) { 
				updateGuessTextString();
			} else {
				if(!wrongCharacterList.contains(inputString)) {
					wrongCharacterList.add(inputString);
					guessLeft--;
					wrongGuessTextString += inputChar + " ";
					if(!checkVictory() && guessLeft == 0) {
						gameRunning = false;
				        JOptionPane.showMessageDialog(null, "YOU LOSE\nTHE WORD WAS\n\"" + guessWord + "\"", "KASPER HANGMAN", JOptionPane.INFORMATION_MESSAGE);				
					}
				} else {
					System.out.println("Character already guessed");
				}
			}
			
			if(checkVictory()) {
				gameRunning = false;
		        JOptionPane.showMessageDialog(null, "YOU WIN", "KASPER HANGMAN", JOptionPane.INFORMATION_MESSAGE);	
			}
		}
	}
	
	public boolean checkVictory() {
		boolean victory = true;
		
		for(int i = 0; i < guessWord.length(); i++) {
			if(guessStringArray[i] == '_') {
				victory = false;
			}
		}
		
		return victory;
	}	
	
	public void resetWord(int allowedGuesses) {
		gameRunning = true;
		guessLeft = allowedGuesses;
		wrongGuessTextString = "";
		wrongCharacterList = new ArrayList<String>();

		try {
			guessWord = generateRandomWordfromFile();
			System.out.println("WORD IS FROM FILE");
		} catch (IOException e) {
			guessWord = generateRandomWord();
			System.out.println("WORD NOT FROM FILE");
			e.printStackTrace();
		}
		
		wordArray = new char[guessWord.length()];
		guessStringArray = new char[guessWord.length()];

		for(int i = 0; i < guessWord.length(); i++) {
			wordArray[i] = guessWord.charAt(i);
			guessStringArray[i] = '_'; // populate guess array with underscores
		}
		
		updateGuessTextString(); // creates a fresh guess text string		
	}
	
	public String generateRandomWord() {
		String[] words = new String[3];
		Random r = new Random();
		
		words[0] = "ELEPHANT";
		words[1] = "SHOWER";
		words[2] = "PUMPKIN";
		
		return words[r.nextInt(3)];
	}
	
	public String generateRandomWordfromFile() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("src/words.txt"));
		String line = null;
		int arrayLength = 0;
		
		while ((line = reader.readLine()) != null) {
			arrayLength++;
		}		
		
		String[] wordArray = new String[arrayLength]; // make array based on the length just calculated
		
		int i = 0;
		reader = new BufferedReader(new FileReader(textFileLocation));
		line = null;
		while ((line = reader.readLine()) != null) {
		    wordArray[i] = line;
		    i++;
		}
		
		Random r = new Random();
		return wordArray[r.nextInt(arrayLength)];	
	}
	
}
