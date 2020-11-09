package ticTacToe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
* Lab 06 Code Exercise 4/5
* 
* @author Nathan Jack
* @version 1.0
* @since Nov 4, 2020
* 
* Console Client class. Handles connection between server Player using commandline interface.
*/
public class TicTacToeClient {
	private Socket socket;
	private Scanner in;
	private PrintWriter out;
	private String name;
	private BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

	/**
	 * Command line client for tic tac toe. Connects sockets and in/out streams to
	 * client. Signals to server that player is created by printing the name and player type (1 for human) to the out stream.
	 * 
	 * @param serverAddress
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public TicTacToeClient(String serverAddress) throws UnknownHostException, IOException {

		socket = new Socket(serverAddress, 9090);
		in = new Scanner(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);

		System.out.print("\nPlease enter the name of the player: ");
		String name = stdin.readLine();
		while (name == null) {
			System.out.print("Please try again: ");
			name = stdin.readLine();
		}

		this.name = name;
		out.println(name + "," + 1 + "\n");

	}

	/**
	 * Main play loop. Waits and responds to server commands using in and out
	 * sockets. Players are forced to wait for the server to respond before playing
	 * there next move.
	 * 
	 */
	public void communicate() {

		System.out.println("Waiting For Opponent");
		String response = "";
		char playerchar = ' ';
		playerchar = in.nextLine().charAt(0); // waits on buffered reader to receive value

		// game loop
		try {
			while (in.hasNextLine()) {
				var command = in.nextLine();

				if (command.startsWith("QUIT")) {
					String[] QUIT = new String[2];

					for (int i = 0; i < QUIT.length; i++) {
						QUIT[i] = in.nextLine();
					}
					for (String s : QUIT) {
						System.out.println(s);
					}
					break;
				} else if (command.startsWith("BOARD")) { // print current board to console
					String[] BOARD = new String[15];

					for (int i = 0; i < BOARD.length; i++) {
						BOARD[i] = in.nextLine();
					}
					for (String s : BOARD) {
						System.out.println(s);
					}
				} else if (command.startsWith("MOVE")) { // gets Move from player

					System.out.println("Your Move");
					out.println(this.getMove(this.name, playerchar));

				} else if (command.startsWith("BADMOVE")) {
					String[] BADMOVE = new String[1];

					for (int i = 0; i < BADMOVE.length; i++) {
						BADMOVE[i] = in.nextLine();
					}

					for (String s : BADMOVE) {
						System.out.println(s);
					}
					System.out.println("Your Move");
					out.println(this.getMove(this.name, playerchar));
				} else if (command.startsWith(" ")) {
					in.nextLine();
				}
			}

		} catch (IOException e) {
			System.out.println("Player get move command failed. Invalid input.");
			e.printStackTrace();
		}
		closeSocket();
	}

	/**
	 * Gets move from player. Throws IO and number exceptions if user input does not
	 * match expected results.
	 * 
	 * @param name
	 * @param playerchar
	 * @return returns built string representing player char and player move.
	 * @throws NumberFormatException throws if player inputs a char or string
	 *                               instead of a number
	 * @throws IOException           throws if buffered reader fails to get input or
	 *                               input is incorrect.
	 */
	private String getMove(String name, char playerchar) throws NumberFormatException, IOException {
		out.flush(); // clear current output socket
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		System.out.println(name + ", enter the row for your next " + playerchar);
		int row = Integer.parseInt(stdin.readLine());
		System.out.println(name + ", enter the column for your next " + playerchar);
		int col = Integer.parseInt(stdin.readLine());

		String res = "__" + row + col;

		if (0 > row || row > 2 || 0 > col || col > 2) {
			System.out.println("Please enter a valid cell.");
			res = this.getMove(name, playerchar); // recursive call if move played was invalid.
		} else {
			res = (name + "," + playerchar + "," + row + "," + col);
		}

		return res;
	}

	private void closeSocket() {
		try {
			socket.close();
			in.close();
			out.close();
			System.exit(0);
		} catch (IOException e) {
			System.out.println("Socket error, could not close connection. Force restart may be required.");
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws Exception {
		TicTacToeClient client = new TicTacToeClient("localhost");
		client.communicate();
	}
}
