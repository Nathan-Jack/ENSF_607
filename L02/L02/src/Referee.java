

import java.io.IOException;

/**
 * Exercise 4 Code
 * 
 * @author Nathan Jack
 * @version 1.0
 * @since Sept 25, 2020
 * 
 *        Sources: Code base from D2L
 *        
 *        Description:
 *         Referee class handles the player objects and manages the interaction
 *         between players, their opponents, and the board.
 */
public class Referee {
	public Player oPlayer;
	public Player xPlayer;
	public Board board;

	/**
	 * assigns player and opponent and initiates play() loop.
	 * @throws NumberFormatException input stream uses parseInt to turn user input into useable index
	 * @throws IOException Play requires user input
	 */
	public void runTheGame() throws NumberFormatException, IOException {
		this.xPlayer.setOpponent(this.oPlayer);
		this.oPlayer.setOpponent(this.xPlayer);
		this.xPlayer.play();
	}

	public void setBoard(Board theBoard) {
		this.board = theBoard;
	}

	public void setoPlayer(Player oPlayer) {
		this.oPlayer = oPlayer;
	}

	public void setxPlayer(Player xPlayer) {
		this.xPlayer = xPlayer;

	}

}
