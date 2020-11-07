package ticTacToe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class TicTacToeClient {
	private Socket socket;
	private Scanner in;
	private PrintWriter out;
	private String name;
	private BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

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

	public void communicate() throws IOException {

		System.out.println("Waiting For Opponent");
		String response = "";
		char playerchar = ' ';
		playerchar = in.nextLine().charAt(0); // waits on buffered reader to recieve value

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
				} else if (command.startsWith("BOARD")) {
					String[] BOARD = new String[15];

					for (int i = 0; i < BOARD.length; i++) {
						BOARD[i] = in.nextLine();
					}
					for (String s : BOARD) {
						System.out.println(s);
					}
				} else if (command.startsWith("MOVE")) {

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		closeSocket();
	}

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
			res = this.getMove(name, playerchar);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	public static void main(String[] args) throws Exception {
		TicTacToeClient client = new TicTacToeClient("localhost");
		client.communicate();
	}
}
