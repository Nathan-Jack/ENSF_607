
import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Exercise 4 Code
 * 
 * @author Nathan Jack
 * @version 1.1
 * @since Oct 19, 2020
 * 
 *        Sources: Code base from D2L Description: Player object handles
 *        translating user input into board manipulation. Implements Constants to
 *        ensure consistent char usage.
 */
public abstract class Player implements Constants {
	/**
	 * Player Name
	 */
	protected String name;

	/**
	 * Player char from Constants
	 */
	protected char letter;
	protected Board board;

	/**
	 * Player object for opponent
	 */
	protected Player opponent;

	/**
	 * Creates player with name and sets char
	 * 
	 * @param name   User input name String
	 * @param letter Game defined X or O
	 */
	protected Player(String name, char letter) {
		this.name = name;
		this.letter = letter;
	}

	/**
	 * Connects players to board object
	 * 
	 * @param theBoard Board object
	 */
	protected void setBoard(Board theBoard) {
		this.board = theBoard;
	}

	/**
	 * Assigns opponent to this Player object
	 * 
	 * @param player Opponent Player object
	 */
	protected void setOpponent(Player player) {
		this.opponent = player;
	}

	/**
	 * Main Play loop. While the win conditions haven't been met the following steps
	 * occur 1. Board displayed 2. Player the play method was called on takes a turn
	 * 3. Win conditions are checked for that player 4. Board is updated 5. Opponent
	 * takes a turn 6. Check all win states and restart step 1 7. End State found,
	 * display winner or Tie message based on result
	 * 
	 * @throws NumberFormatException see makeMove() method
	 * @throws IOException           see makeMove() method
	 */
	protected abstract void play() throws NumberFormatException, IOException ;

	/**
	 * Prompts user for input. Checks input for range validity. not for type. Add
	 * char if space is empty, otherwise recursivly calls self and restarts turn for
	 * player without changing board
	 * 
	 * @throws NumberFormatException for parseInt
	 * @throws IOException           for IO input with bufferedreader for human players only.
	 */
	protected abstract void makeMove() throws NumberFormatException, IOException ;

}
