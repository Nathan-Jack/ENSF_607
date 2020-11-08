package ticTacToeGui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TicTacToeGUIClient {
	private TTTGUI gui;
	private Socket socket;
	private Scanner in;
	private PrintWriter out;
	private String name;
	private BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
	public String serverAddress;
	public boolean connected = false;
	public boolean ready = false;

	public TicTacToeGUIClient(String serverAddress) throws UnknownHostException, IOException {
		this.serverAddress = serverAddress;

		this.gui = TTTGUI.buildGUI(this);
		TTTGUI.run(gui); // transfer control to gui
	}

	public void connectToServer(String serverAddress) throws UnknownHostException, IOException {
		if (!this.connected) { // if client has not already connected to server.
			socket = new Socket(serverAddress, 9090);
			in = new Scanner(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			this.connected = true;
		}
	}

	public void setChar() {
		// listen for server return
		final char[] temp = new char[1];
		gui.getDisplay().syncExec(new Runnable() {
			public void run() {
				temp[0] = in.nextLine().charAt(0); // waits on buffered reader to receive value
			}
		});
		gui.setPlayerChar(temp[0]);
		gui.getServerDisplay().append("\nYour char is: " + temp[0]);
		this.ready = true;
		return;
	}

	public void startLoop() {
		String[] move = new String[1];

		gui.getDisplay().syncExec(new Runnable() {
			public void run() {
				while (in.hasNextLine()) {
					var command = in.nextLine();

					if (command.startsWith("QUIT")) { // do nothing
						break;
					} else if (command.startsWith("BOARD")) { // from server
						String[] BOARD = new String[15];

						for (int i = 0; i < BOARD.length; i++) {
							BOARD[i] = in.nextLine();
						}
						gui.getDisplay().syncExec(() -> gui.setTurn(true));
						gui.getDisplay().syncExec(() -> gui.updateBoard(BOARD));

					} else if (command.startsWith("MOVE")) { // from server
						try {
							final char[][] oldBoard = new char[1][];

							//gui.getDisplay().syncExec(() -> {
								char[][] temp = gui.getBoard(); // fails here. // try the old craete new thread optiob>
								oldBoard[0] = Arrays.stream(temp).map(String::new).collect(Collectors.joining())
										.toCharArray();
							//});

							char[] newBoard = oldBoard[0];
							while (oldBoard.toString().compareTo(newBoard.toString()) == 0) { // while board looks the
																								// same as the previers
								final char[][] tempBoard = new char[1][];
								gui.getDisplay().syncExec(() -> {
									char[][] temp1 = gui.getBoard();
									tempBoard[0] = Arrays.stream(temp1).map(String::new).collect(Collectors.joining())
											.toCharArray();
								});
								newBoard = tempBoard[0];
								Thread.sleep(100);
							}
							System.out.println("fuck you it workded");
							out.println(name + "," + "row" + "," + "col");
							// gui.getDisplay().syncExec(() -> out.println(gui.getBoard()));
						} catch (final InterruptedException e) {

							e.printStackTrace();
						}
						break; // do nothing for now
					} else if (command.startsWith(" ")) {
						in.nextLine(); // skip line
					}

				}
			}

		});
		/**
		 * command[0] = in.nextLine(); System.out.println(command); // DEBUG
		 * 
		 * if (command[0].startsWith("QUIT")) { // from server String[] QUIT = new
		 * String[2];
		 * 
		 * for (int i = 0; i < QUIT.length; i++) { QUIT[i] = in.nextLine(); } for
		 * (String s : QUIT) { gui.getServerDisplay().append(s); } break; }
		 */

	}

	public void communicate() throws IOException {

		while (in.hasNextLine()) {
			var command = this.getServerCommand();
			System.out.println(command); // DEBUG

			if (command.startsWith("QUIT")) { // from server
				String[] QUIT = new String[2];

				for (int i = 0; i < QUIT.length; i++) {
					QUIT[i] = in.nextLine();
				}
				for (String s : QUIT) {
					gui.getServerDisplay().append(s);
				}
				break;

			} else if (command.startsWith("BOARD")) { // from server
				String[] BOARD = new String[15];

				for (int i = 0; i < BOARD.length; i++) {
					BOARD[i] = in.nextLine();
				}
				for (String s : BOARD) {
					System.out.println(s); // parse board?? X/Os only on certain lines, could count num and update
											// accordingly
				}
			} else if (command.startsWith("MOVE")) { // from server

				gui.getServerDisplay().append("Your Move");
				out.println(name + "," + this.getMove()); // output reader now waits
				break;
				// for move. other client
				// cant do anything till this runs.

			} else if (command.startsWith(" ")) {
				in.nextLine(); // skip line
			}
		}

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

	// Allows other classes to access output socket
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

	public static void main(String[] args) throws Exception {
		TicTacToeGUIClient client = new TicTacToeGUIClient("localhost");
	}

}
