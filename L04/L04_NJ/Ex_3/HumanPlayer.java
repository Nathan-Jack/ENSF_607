import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Lab 4 Code
 * 
 * @author Nathan Jack
 * @version 1.1
 * @since Oct 19, 2020
 * 
 *        Sources: Code base from D2L
 *        
 *        Description:
 *         Human player. allows for input from keyboard.
 */
class HumanPlayer extends Player {

	protected HumanPlayer(String name, char letter) {
		super(name, letter);
		// TODO Auto-generated constructor stub
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
	protected void play() throws NumberFormatException, IOException {
		while (this.board.isFull() == false && this.board.oWins() == false && this.board.xWins() == false) {
			this.makeMove();
			this.board.display();

			if (this.board.checkWinner(this.letter) == 1 || this.board.isFull() == true) {
				this.board.display();
				break;
			}
			this.opponent.makeMove();
			this.board.display();

		}
		if (this.board.oWins() == true) {
			System.out.println("THE GAME IS OVER: " + this.opponent.name + " is the winner!");
			System.out.println("Game Ended...");
		} else if (this.board.xWins() == true) {
			System.out.println("THE GAME IS OVER: " + this.name + " is the winner!");
			System.out.println("Game Ended...");
		} else if (this.board.isFull() == true) {
			System.out.println("Cats Game");
		}

	}

	/**
	 * Prompts user for input. Checks input for range validity. not for type. Add
	 * char if space is empty, otherwise recursively calls self and restarts turn
	 * for player without changing board
	 * 
	 * @throws NumberFormatException for parseInt
	 * @throws IOException           for IO input with bufferedreader for human
	 *                               players only.
	 */
	protected void makeMove() throws NumberFormatException, IOException {
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		System.out.println(this.name + ", enter the row for your next " + this.letter);
		int row = Integer.parseInt(stdin.readLine());

		System.out.println(this.name + ", enter the column for your next " + this.letter);
		int col = Integer.parseInt(stdin.readLine());

		if (0 > row || row > 2 || 0 > col || col > 2) {
			System.out.println("Please enter a valid cell.");
			this.makeMove();
		} else {

			char current = this.board.getMark(row, col);

			if (current == SPACE_CHAR) {
				this.board.addMark(row, col, this.letter);
			} else {
				System.out.println("Please enter a valid cell.");
				this.makeMove();
			}
		}
	}

}
