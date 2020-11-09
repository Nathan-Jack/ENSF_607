package ticTacToe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
* Lab 06 Code Exercise 4/5
* 
* @author Nathan Jack
* @version 1.0
* @since Nov 4, 2020
* 
* Creates a game server for every two clients that join. Allows GUI clients and console clients. Creates a new thread for each game.
*/
public class TicTacToeServer implements Constants {

	private final Player[] players; // array of Players
	private ServerSocket server; // server socket to connect with clients
	private Socket aSocket;
	private BufferedReader socketIn;// input from client
	private PrintWriter socketOut; // output to client
	private final static char[] MARKS = { LETTER_X, LETTER_O }; // array of marks
	private final ExecutorService runGame; // will run players

	/**
	 * Build server on localhost server address. Outputs to screen that server is
	 * running
	 */
	public TicTacToeServer() {

		players = new Player[2]; // create array of players
		// create ExecutorService with a thread for each player
		runGame = Executors.newCachedThreadPool();

		try {
			server = new ServerSocket(9090, 0, InetAddress.getByName("localhost")); // set up ServerSocket
			System.out.println("Tic Tac Toe Server is Running...");
		} catch (IOException ioException) {
			System.out.println(ioException.toString());
			System.exit(1);
		}
	}

	// waits for two connections so game can be played. Allows any number of games to be played at one time.
	public void execute() {

		// wait for each client to connect
		for (int i = 0; i < players.length; i++) {
			// wait for connection, create Player, start runnable
			try {
				/**
				 * When a client connects, a new Player object is created to manage the
				 * connection as a separate thread, and the thread is executed in the runGame
				 * thread pool.
				 *
				 * The Player constructor receives the Socket object representing the connection
				 * to the client and gets the associated input and output streams.
				 */
				aSocket = server.accept();
				System.out.println("Connecting to player...");
				socketIn = new BufferedReader(new InputStreamReader(aSocket.getInputStream()));
				socketOut = new PrintWriter(aSocket.getOutputStream(), true);

				String line = socketIn.readLine(); // gets player name and player char from client connection.
				String[] values = line.split(",");

				players[i] = this.create_player(values[0], MARKS[i], Integer.parseInt(values[1]), aSocket);
				System.out.println("Player " + players[i].name + " connected\n");
				players[i].socketOut.println(players[i].letter); // signal to players that match can begin. starts
																	// client internal game loops.
			}

			catch (IOException ioException) {
				System.out.println(ioException.toString());
				System.exit(1);
			}
		}

		// create a new game on the server with players
		Game game = new Game(players, socketIn, socketOut);

		runGame.execute(game);

		while (game.GAMEOVER) {
			try {
				runGame.awaitTermination(200, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Game Ended, Looking for new Players\n");
		}

		this.execute(); // sever continuously looks for players after game completes.
	}

	/**
	 * 
	 * @param name        Player name
	 * @param mark        Player mark
	 * @param player_type Always human for this use case. Functionality to play
	 *                    against non human player not yet implemented.
	 * @param socket      Socket for player to connect to. Unique to each player and
	 *                    thread.
	 * @return 			  Returns created player linked to correct socket.
	 */
	private Player create_player(String name, char mark, int player_type, Socket socket) {

		Player result = null;
		switch (player_type) {
		case 1:
			result = new HumanPlayer(name, mark, socket);
			break;
		case 2:
			result = new RandomPlayer(name, mark, socket);
			break;
		case 3:
			result = new BlockingPlayer(name, mark, socket);
			break;
		case 4:
			result = new SmartPlayer(name, mark, socket);
			break;
		default:
			System.out.print("\nDefault case in switch should not be reached.\n" + "  Program terminated.\n");
			System.exit(0);
		}
		return result;
	}

	public static void main(String[] args) throws Exception {
		TicTacToeServer application = new TicTacToeServer();
		application.execute();

	}
}
