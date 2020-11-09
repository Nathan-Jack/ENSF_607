package ticTacToeGui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class TicTacToeGUIClient {
	private GUIController gControl;
	private Socket socket;
	private Scanner in;
	private PrintWriter out;
	private String name;
	private BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
	public String serverAddress;
	public boolean connected = false;
	public boolean ready = true;
	private char playerChar;

	public TicTacToeGUIClient(String serverAddress) throws UnknownHostException, IOException {
		this.serverAddress = serverAddress;
		this.gControl = new GUIController(this);
		this.gControl.startGui();
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

		temp[0] = in.nextLine().charAt(0);
		gControl.gui.setPlayerChar(temp[0]);
		gControl.gui.getServerDisplay().append("\nYour char is: " + temp[0]);
		this.ready = true;
		this.playerChar = temp[0];
		return;
	}

	public void startLoop() {
		
		while(in == null) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		while (in.hasNextLine()) {
			var command = in.next();
			
			if (command.startsWith("QUIT")) { // do nothing
				String[] QUIT = new String[2];

				for (int i = 0; i < QUIT.length; i++) {
					QUIT[i] = in.nextLine();
				}
				gControl.gui.getDisplay().syncExec(() -> gControl.gui.getServerDisplay().append("\nThe Game has Ended!\n"));
				for (String s : QUIT) {
					gControl.gui.getDisplay().syncExec(() -> gControl.gui.getServerDisplay().append(s));
				}
				break;
			} else if (command.startsWith("BOARD")) { // from server
				String[] BOARD = new String[15];

				for (int i = 0; i < BOARD.length; i++) {
					BOARD[i] = in.nextLine();
				}
				String board = this.parseBoard(BOARD);

				gControl.gui.getDisplay().syncExec(() -> gControl.gui.updateBoard(board));
				gControl.gui.getDisplay().syncExec(() -> gControl.gui.toggleGrid(gControl.gui.isMyTurn()));
				// this.ready = false;
				//break;
			} else if (command.startsWith("MOVE")) { // from server
				while (gControl.gui.lastMove.equals("")) { // waits for move to be made before continuing
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				gControl.gui.getDisplay().syncExec(() -> gControl.gui.setBoard());
				out.println(name + "," + playerChar + "," + gControl.gui.lastMove); // send turn to game
		
			} else if (command.startsWith("SWAP")) {
				gControl.gui.toggleTurn();
			}

		}

	}

	private String parseBoard(String[] BOARD) {
		String res = "";
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				// System.out.println();
				res += BOARD[(4 + (i * 4))].charAt((13 + (j * 6)));
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

	public GUIController getController() {
		return this.gControl;
	}

}
