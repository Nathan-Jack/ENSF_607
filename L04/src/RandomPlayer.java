import java.io.IOException;

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
 *        Plays a mark on a random empty tile
 */
class RandomPlayer extends Player {

	protected RandomPlayer(String name, char letter) {
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

	@Override
	/**
	 * Chooses a random board tile until an empty cell is found.
	 * 
	 * @throws NumberFormatException for parseInt
	 * @throws IOException           for IO input with bufferedreader for human
	 *                               players only.
	 */
	protected void makeMove() throws NumberFormatException, IOException {
		RandomGenerator randy = new RandomGenerator();
		int row = randy.discrete(0, 2);
		int col = randy.discrete(0, 2);
		char current = this.board.getMark(row, col);

		while (current != SPACE_CHAR) {
			row = randy.discrete(0, 2);
			col = randy.discrete(0, 2);
			current = this.board.getMark(row, col);
		}
		System.out.println(this.getClass() + " " + this.name + " choose: " + row + ", " + col + "\n");
		this.board.addMark(row, col, this.letter);
	}
}
