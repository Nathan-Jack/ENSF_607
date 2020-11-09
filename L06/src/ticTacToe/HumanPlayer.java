package ticTacToe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Lab 4 Code
 * 
 * @author Nathan Jack
 * @version 1.1
 * @since Oct 19, 2020
 * 
 *        Sources: Code base from D2L
 * 
 *        Description: Human player. allows for input from keyboard.
 */
class HumanPlayer extends Player {

	protected HumanPlayer(String name, char letter, Socket socket) {
		super(name, letter, socket);
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

			// display board to client
			this.socketOut.println(this.board.toString());
			this.makeMove();
			
			if (this.board.checkWinner(this.letter) == 1 || this.board.isFull() == true) {
				this.socketOut.println(this.board.toString()); // show the winning board to both clients
				this.opponent.socketOut.println(this.board.toString());
				StringBuilder res = new StringBuilder();
				if (this.board.oWins() == true) {
					res.append("QUIT\n");
					res.append("THE GAME IS OVER: " + this.opponent.name + " is the winner!\n");
					res.append("Game Ended...");
					System.out.println("THE GAME IS OVER: " + this.opponent.name +" and "+ this.name+ " Disconnecting");
					
					this.socketOut.println(res);
					this.opponent.socketOut.println(res);
				} else if (this.board.xWins() == true) {
					res.append("QUIT\n");
					res.append("THE GAME IS OVER: " + this.name + " is the winner!\n");
					res.append("Game Ended...");
					System.out.println("THE GAME IS OVER: " + this.name +" and "+ this.opponent.name+ " Disconnecting");
					
					this.socketOut.println(res);
					this.opponent.socketOut.println(res);
				} else if (this.board.isFull() == true) {
					res.append("QUIT\n");
					res.append("THE GAME IS OVER NO ONE WINS\n");
					res.append("Game Ended...");
					System.out.println("EVERYONE IS DISCONNECTING");
	
					this.socketOut.println(res);
					this.opponent.socketOut.println(res);
				}
			}

			this.socketOut.println(this.board.toString());
			this.opponent.socketOut.println("SWAP");
			this.opponent.socketOut.println(this.board.toString());
			
			this.opponent.play(); // needs to print to server that its the other players turn.

		}
		this.socketOut.println(this.board.toString()); // show board after game is over
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
		this.socketOut.flush();
		this.socketOut.println("MOVE\n"); // prompt client for action
		// breaking here?
		
		String move = this.socketIn.readLine(); // read in move // waits for client action
		
		String[] values = move.split(",");
		
		int row = Integer.parseInt(values[2]);
		int col = Integer.parseInt(values[3]);
		
		char current = this.board.getMark(row, col);

		if (current == SPACE_CHAR) {
			this.board.addMark(row, col, this.letter);
		} else {
			StringBuilder res = new StringBuilder();
			this.socketOut.flush();
			res.append("BADMOVE\n");
			res.append("Please enter a valid cell.");
			this.socketOut.println(res);
			this.makeMove();
		}
	}

}
