import java.io.IOException;
import java.util.Arrays;

/**
 * Lab 4 Code
 * 
 * @author Nathan Jack
 * @version 1.0
 * @since Oct 19, 2020
 * 
 *        Sources: Code base from D2L
 *        
 *        Description:
 *        if opponent can be blocked, play that tile. Else plays a random tile.
 */
class BlockingPlayer extends RandomPlayer {

	public BlockingPlayer(String name, char letter) {
		super(name, letter);
		// TODO Auto-generated constructor stub
	}

	@Override
	/**
	 * Loops through entire board and tests moves to see if they will block
	 * opponent. Otherwise play a random move using super.
	 */
	protected void makeMove() throws NumberFormatException, IOException {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (this.board.getMark(i, j) == SPACE_CHAR) {
					if (testForBlocking(i, j, this.opponent.letter)) {
						System.out.println(this.getClass() + " " + this.name + " choose: " + i + ", " + j + "\n");
						this.board.addMark(i, j, this.letter);
						return;
					}
				}
			}
		}
		super.makeMove(); // if the above loop fails play a random move using parent class method.
		return;

	}

	/**
	 * Tests input cell against all win conditions. If an opponent has a valid win
	 * condition program returns true on the last empty cell in the win condition
	 * 
	 * @param row    input row to check
	 * @param col    input col to check
	 * @param target opponent char to block
	 * @return returns true if tested row col will block the opponent.
	 * @throws NumberFormatException
	 */
	protected boolean testForBlocking(int row, int col, char target) throws NumberFormatException {
		int pos = ((row) * 3) + col; // get linear position of index
		int[][] winCombo = { { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 }, { 0, 3, 6 }, { 1, 4, 7 }, { 2, 5, 8 }, { 0, 4, 8 },
				{ 2, 4, 6 } }; // all possible win conditions to check. favors horizontal<vertical<diag
		for (int[] condition : winCombo) {
			int count = 0;
			if (Arrays.stream(condition).anyMatch(i -> i == pos)) { // if current chosen position is within the win //
																	// condition cell
				for (int idx : condition) {
					int tempcol = ((idx) % 3);
					int temprow = (int) Math.ceil((idx) / 3);
					if (this.board.getMark(temprow, tempcol) == target) { // if a cell within the win condition contains a target char.
						count += 1;
					}
				}
				if (count > 1 && count < 3 && this.board.getMark(row, col) == SPACE_CHAR) { // if current position is
																							// the last empty cell
																							// within the win condition
					return true; // block the opponent
				}
			}

		}

		return false; // else call super random move.
	}
}
