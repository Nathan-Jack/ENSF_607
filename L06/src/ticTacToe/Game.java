package ticTacToe;

//Game.java
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Game implements Constants, Runnable {

	private Board theBoard;
	private Referee theRef;
	private Player xPlayer, oPlayer;
	private BufferedReader socketIn;// input from client
	private PrintWriter socketOut; // output to client
	public boolean GAMEOVER = false;

	/**
	 * creates a board for the game
	 * 
	 * @param players
	 */
	public Game(Player[] players,BufferedReader in, PrintWriter out ) {
		theBoard = new Board();
		xPlayer = players[0];
		oPlayer = players[1];
		xPlayer.board = theBoard;
		oPlayer.board = theBoard;
		socketIn = in;
		socketOut = out;
	}

	/**
	 * Calls the referee method runTheGame
	 * 
	 * @param r refers to the appointed referee for the game
	 * @throws IOException
	 */
	public void appointReferee(Referee r) throws IOException {
		theRef = r;
		theRef.runTheGame();
	}

	@Override
	public void run() {

		Referee theRef;

		// pass client players into game here.
		theRef = new Referee();
		theRef.setBoard(this.theBoard);
		theRef.setoPlayer(oPlayer);
		theRef.setxPlayer(xPlayer);
		
		theRef.xPlayer.setBoard(this.theBoard);
		theRef.oPlayer.setBoard(this.theBoard);
		
		theRef.xPlayer.setOpponent(this.oPlayer);
		theRef.oPlayer.setOpponent(this.xPlayer);
		
		try {
			theRef.xPlayer.play();
		} catch (NumberFormatException | IOException e1) {
			
			e1.printStackTrace();
		}

		GAMEOVER = true;
	}
}
