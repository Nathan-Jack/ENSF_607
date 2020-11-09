package ticTacToeGui;

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
* GUI Client class. Handles connection between server and gui controller.
*/
public class TicTacToeGUIClient {
	private GUIController gControl;
	private Socket socket;
	private Scanner in;
	private PrintWriter out;
	private String name;
	public String serverAddress;
	public boolean connected = false;
	public boolean ready = true;
	private char playerChar;

	/**
	 * Creates client object using the specified server address and links it to the gui controller. Starts Gui.
	 * 
	 * @param serverAddress Required for server connection
	 */
	public TicTacToeGUIClient(String serverAddress) {
		this.serverAddress = serverAddress;
		this.gControl = new GUIController(this);
		this.gControl.startGui();
	}

	/**
	 * Initiated by player using connect to server button. Hard coded to port 9090 for no particular reason.
	 * 
	 * @param serverAddress
	 * @throws UnknownHostException Throws if socket cannot find host server
	 * @throws IOException          thres if inputstream reader fails for any
	 *                              reason.
	 */
	public void connectToServer(String serverAddress) throws UnknownHostException, IOException {
		if (!this.connected) { // if client has not already connected to server.
			socket = new Socket(serverAddress, 9090);
			in = new Scanner(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			this.connected = true;
		}
	}

	/**
	 * Sets player char and indicates that player is ready to play.
	 */
	public void setChar() {
		// listen for server return
		final char[] temp = new char[1];

		temp[0] = in.nextLine().charAt(0);
		gControl.gui.setPlayerChar(temp[0]);
		gControl.gui.getServerDisplay().append("\nYour char is: " + temp[0]);
		this.ready = true;
		this.playerChar = temp[0];
		return;
	}

	/**
	 * Main game loop. Waits for socket in to be available before running. Pauses on
	 * MOVE command until player has chosen a move on the gui.
	 */
	public void startLoop() {

		while (in == null) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				System.out.println("Client input socket connected");
				e.printStackTrace();
			}
		}

		while (in.hasNextLine()) {
			var command = in.next(); // get next command from game.

			if (command.startsWith("QUIT")) {
				String[] QUIT = new String[2];

				for (int i = 0; i < QUIT.length; i++) {
					QUIT[i] = in.nextLine();
				}
				gControl.gui.getDisplay()
						.syncExec(() -> gControl.gui.getServerDisplay().append("\nThe Game has Ended!\n"));
				for (String s : QUIT) {
					gControl.gui.getDisplay().syncExec(() -> gControl.gui.getServerDisplay().append(s)); // send to gui
																											// server
																											// output.
				}
				break;
			} else if (command.startsWith("BOARD")) { // update and display game board to player
				String[] BOARD = new String[15];

				for (int i = 0; i < BOARD.length; i++) {
					BOARD[i] = in.nextLine();
				}
				String board = this.parseBoard(BOARD); // returns just the X/Os in a string.

				gControl.gui.getDisplay().syncExec(() -> gControl.gui.updateBoard(board));
				gControl.gui.getDisplay().syncExec(() -> gControl.gui.toggleGrid(gControl.gui.isMyTurn()));

			} else if (command.startsWith("MOVE")) { // Get move from gui
				while (gControl.gui.lastMove.equals("")) { // waits for move to be made before continuing
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// No handling. This sleep exists only to force the client to wait for the gui.
					}
				}
				gControl.gui.getDisplay().syncExec(() -> gControl.gui.setBoard());
				out.println(name + "," + playerChar + "," + gControl.gui.lastMove); // send turn to game

			} else if (command.startsWith("SWAP")) { // triggers sleeping gui to wake up when turn is passed.
				gControl.gui.toggleTurn();
			}

		}

	}

	private String parseBoard(String[] BOARD) {
		String res = "";
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				// System.out.println();
				res += BOARD[(4 + (i * 4))].charAt((13 + (j * 6))); // location of each player char in the text version
																	// of the board.
			}
		}
		System.out.println(res);
		return res;
	}

	void closeSocket() {
		try {
			socket.close();
			in.close();
			out.close();
			System.exit(0);
		} catch (IOException e) {
			System.out.println("Socket could not be closed. Force restart may be required.");
			e.printStackTrace();
		}

	}

	// Allows gui controller to access output socket
	public PrintWriter getOut() {
		return out;
	}

	public Scanner getIn() {
		return in;
	}

	public void setName(String name) {
		this.name = name;
		out.println(name + "," + 1 + "\n"); // returns name to server
	}

	public GUIController getController() {
		return this.gControl;
	}

	public static void main(String[] args) throws Exception {
		TicTacToeGUIClient client = new TicTacToeGUIClient("localhost");
	}

}
