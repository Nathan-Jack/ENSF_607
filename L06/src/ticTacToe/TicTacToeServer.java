package ticTacToe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TicTacToeServer implements Constants {

	private final Player[] players; // array of Players
	private ServerSocket server; // server socket to connect with clients
	private Socket aSocket;
	private BufferedReader socketIn;// input from client
	private PrintWriter socketOut; // output to client
	private final static char[] MARKS = { LETTER_X, LETTER_O }; // array of marks
	private int currentPlayer; // keeps track of player with current move
	private final static int PLAYER_X = 0; // constant for first player
	private final static int PLAYER_O = 1; // constant for second player
	private final ExecutorService runGame; // will run players

	public TicTacToeServer() {

		players = new Player[2]; // create array of players
		currentPlayer = PLAYER_X; // set current player to first player

		// create ExecutorService with a thread for each player
		runGame = Executors.newFixedThreadPool(2);

		try {
			server = new ServerSocket(9090, 0, InetAddress.getByName("localhost")); // set up ServerSocket
			System.out.println("Tic Tac Toe Server is Running...");
		} catch (IOException ioException) {
			System.out.println(ioException.toString());
			System.exit(1);
		}
	}

	// wait for two connections so game can be played
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
				
				String line = socketIn.readLine();
				String[] values = line.split(",");
				
				players[i] = this.create_player(values[0], MARKS[i], Integer.parseInt(values[1]),aSocket);
				System.out.println("Player " + players[i].name + " connected\n");
			}

			catch (IOException ioException) {
				System.out.println(ioException.toString());
				System.exit(1);
			}
		}
		
		players[0].socketOut.println(players[0].letter); // signal to players that match can begin
		players[1].socketOut.println(players[1].letter); // signal to players that match can begin
		// create a new game on the server with players
		Game game = new Game(players, socketIn, socketOut);
		
		runGame.execute(game);
		
		while(game.GAMEOVER) {
		try {
			runGame.awaitTermination(200, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Game Ended, Looking for new Players\n");}
		
		this.execute();
	}

	private Player create_player(String name, char mark, int player_type, Socket socket) throws IOException {

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
		// result.setBoard(board);
		return result;
	}

	public static void main(String[] args) throws Exception {
		TicTacToeServer application = new TicTacToeServer();
		application.execute();

	}
}
