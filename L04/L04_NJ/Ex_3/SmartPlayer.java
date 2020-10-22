import java.io.IOException;

/**
 * Lab 4 Code
 * 
 * @author Nathan Jack
 * @version 1.0
 * @since Oct 19, 2020
 * 
 * 
 *        Description: If game can be won, plays that tile, else if opponent can
 *        be blocked, play that tile. Else plays a random tile.
 */
public class SmartPlayer extends BlockingPlayer {

	public SmartPlayer(String name, char letter) {
		super(name, letter);
	}

	@Override
	/**
	 * Loops through each cell, checks if win condition can be met using the
	 * superclass's blocking algorithm to search for win conditions using the player
	 * char as the target. If game cannot be one super is called and a blocking move
	 * is played. if that fails a random move is played (using super from blocking player)
	 */
	protected void makeMove() throws NumberFormatException, IOException {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (this.board.getMark(i, j) == SPACE_CHAR) { // check all available tiles
					if (testForWinning(i, j, this.letter)) {
						System.out.println(this.getClass() + " " + this.name + " choose: " + i + ", " + j + "\n");
						this.board.addMark(i, j, this.letter);
						return;
					}
				}
			}
		}
		super.makeMove(); // call blocking player move.
		return;

	}

	/**
	 * Tests for winning move by looking using super classes block move, but instead
	 * looks for own chars.
	 * 
	 * @param row    input row to check
	 * @param col    input col to check
	 * @param target char target. For check winning this will be the current turns
	 *               player char. checks any potential win conditions (ie two player chars + a space in a row)
	 * @return returns positive if the move will win.
	 * @throws NumberFormatException
	 */
	protected boolean testForWinning(int row, int col, char target) throws NumberFormatException {
		return super.testForBlocking(row, col, target);
	}
}