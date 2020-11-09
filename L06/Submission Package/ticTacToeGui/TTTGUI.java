package ticTacToeGui;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.events.*;

public class TTTGUI extends Composite {

	private Text namePrompt;
	private Text nameInput;
	private Button buttons[] = new Button[9];
	private Button startButton;
	private Text serverDisplay;
	private Text serverDisplayTitle;
	private GUIController controller;
	private Display display;
	private Shell shell;
	private char playerChar;
	private boolean myTurn = false;
	public String board;
	public String lastMove = "";

	/**
	 * Static method to create gui object.
	 * 
	 * @param client client entity. Gui must have a client host in order to play
	 *               game.
	 * @return returns built gui.
	 */
	public static TTTGUI buildGUI(TicTacToeGUIClient client) {
		Display display = new Display(); // requried for swt applications.
		Shell shell = new Shell(display);
		shell.setLayout(new GridLayout(1, false));
		TTTGUI gui = new TTTGUI(shell, SWT.None);

		gui.shell = shell;
		gui.display = display;
		gui.setController(client.getController());
		return gui;
	}

	// main operation loop of the gui. Runs game loop inside main gui loop continuously.
	public void run() {

		this.shell.pack();
		this.shell.open();
		while (!this.shell.isDisposed()) {
			if (!this.display.readAndDispatch()) { // if there are no commands to respond to sleep.
				this.display.sleep();
			}

			controller.startClientLoop(); // starts a thread for the client game loop. Allows gui and client to run at the same time. only one client thread allowed.

		}
		this.display.dispose();
		controller.client.closeSocket(); // if Gui is closed, close all client sockets.

	}

	/**
	 * Create swt composite/gui window. All gui element locations are relative to other component locations.
	 * 
	 * @param parent
	 * @param style
	 */
	public TTTGUI(Composite parent, int style) {
		super(parent, SWT.BORDER);

		setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		setLayout(new FormLayout());

		this.initializebuttons(); // creates grid buttons

		namePrompt = new Text(this, SWT.BORDER | SWT.READ_ONLY | SWT.CENTER);
		FormData fd_namePrompt = new FormData();
		fd_namePrompt.top = new FormAttachment(buttons[8], 6, SWT.BOTTOM);
		fd_namePrompt.right = new FormAttachment(buttons[8], -1, SWT.RIGHT);
		fd_namePrompt.left = new FormAttachment(buttons[6], 1, SWT.LEFT);

		namePrompt.setLayoutData(fd_namePrompt);
		namePrompt.setText("Enter Your Name Below");
		namePrompt.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_BACKGROUND));
		namePrompt.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));

		nameInput = new Text(this, SWT.BORDER);
		nameInput.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_BACKGROUND));
		fd_namePrompt.bottom = new FormAttachment(100, -35);
		FormData fd_nameInput = new FormData();
		fd_nameInput.top = new FormAttachment(namePrompt, 6);
		fd_nameInput.right = new FormAttachment(namePrompt, 0, SWT.RIGHT);
		fd_nameInput.left = new FormAttachment(namePrompt, 0, SWT.LEFT);
		nameInput.setLayoutData(fd_nameInput);
		nameInput.setTouchEnabled(true);
		nameInput.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		nameInput.addTraverseListener(new enterListener());

		Button button_ConnectToServer = new Button(this, SWT.NONE);
		button_ConnectToServer.setEnabled(true);
		FormData fd_button_ConnectToServer = new FormData();
		fd_button_ConnectToServer.top = new FormAttachment(namePrompt, 0, SWT.TOP);
		fd_button_ConnectToServer.left = new FormAttachment(buttons[8], 30, SWT.RIGHT);
		button_ConnectToServer.setLayoutData(fd_button_ConnectToServer);
		button_ConnectToServer.setText("Connect To Server");
		button_ConnectToServer.addSelectionListener(new serverButtonListener()); // connects button to function

		serverDisplayTitle = new Text(this, SWT.BORDER | SWT.READ_ONLY | SWT.CENTER);
		serverDisplayTitle.setEnabled(false);
		serverDisplayTitle.setText("Server Messages");
		serverDisplayTitle.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		serverDisplayTitle.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_BACKGROUND));
		FormData fd_serverDisplayTitle = new FormData();
		fd_serverDisplayTitle.top = new FormAttachment(buttons[0], 0, SWT.TOP);
		fd_serverDisplayTitle.left = new FormAttachment(button_ConnectToServer, 0, SWT.LEFT);
		fd_serverDisplayTitle.right = new FormAttachment(button_ConnectToServer, 0, SWT.RIGHT);
		serverDisplayTitle.setLayoutData(fd_serverDisplayTitle);

		serverDisplay = new Text(this, SWT.BORDER | SWT.WRAP);
		serverDisplay.setEnabled(false);
		serverDisplay.setText("Tic-tac-toe client Launched! Click the button below to connect to game server.");
		serverDisplay.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		serverDisplay.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_BACKGROUND));
		FormData fd_serverDisplay = new FormData();
		fd_serverDisplay.top = new FormAttachment(serverDisplayTitle, 6, SWT.BOTTOM);
		fd_serverDisplay.bottom = new FormAttachment(namePrompt, 0, SWT.TOP);
		fd_serverDisplay.left = new FormAttachment(button_ConnectToServer, 0, SWT.LEFT);
		fd_serverDisplay.right = new FormAttachment(button_ConnectToServer, 0, SWT.RIGHT);
		serverDisplay.setLayoutData(fd_serverDisplay);

		startButton = new Button(this, SWT.NONE);
		startButton.setEnabled(false);
		FormData fd_startButton = new FormData();
		fd_startButton.top = new FormAttachment(button_ConnectToServer, -6, SWT.BOTTOM);
		fd_startButton.left = new FormAttachment(button_ConnectToServer, 0, SWT.LEFT);
		startButton.setLayoutData(fd_startButton);
		startButton.setText("Start");
		startButton.addSelectionListener(new startButtonListener()); // connects button to function

	}

	/**
	 * Creates button grid for game.
	 */
	public void initializebuttons() {

		int size = 60;
		int spacing = 6;
		int[] offsets = new int[4];

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {

				offsets[0] = 3 + (size * i) + spacing; // top
				offsets[1] = 63 + (size * i) + spacing; // bottom
				offsets[2] = 10 + (size * j) + spacing; // left
				offsets[3] = 70 + (size * j) + spacing; // right

				buttons[i * 3 + j] = new Button(this, SWT.NONE);
				buttons[i * 3 + j].setEnabled(false);
				buttons[i * 3 + j].setText(" "); // should use constants space char here.
				buttons[i * 3 + j].setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION_TEXT));
				FormData fd_button0 = new FormData();
				fd_button0.top = new FormAttachment(0, offsets[0]);
				fd_button0.bottom = new FormAttachment(0, offsets[1]);
				fd_button0.left = new FormAttachment(0, offsets[2]);
				fd_button0.right = new FormAttachment(0, offsets[3]);
				buttons[i * 3 + j].setLayoutData(fd_button0);

				buttons[i * 3 + j].addSelectionListener(new gridButtonListener()); // connect button to event handling.

			}
		}
	}

	/**
	 * grid button event handling. Button clicks change button text to player char and then toggle turn.
	 * @author nathanjack
	 *
	 */
	private class gridButtonListener implements SelectionListener {

		@Override
		public void widgetSelected(SelectionEvent e) {
			Button buttonClicked = (Button) e.getSource(); // get the particular button that was clicked
			buttonClicked.setText(playerChar + "");
			int idx = IntStream.range(0, buttons.length).filter(i -> buttonClicked.equals(buttons[i])).findFirst()
					.orElse(-1); // return -1 if target is not found
			int tempcol = ((idx) % 3);
			int temprow = (int) Math.ceil((idx) / 3);
			String move = temprow + "," + tempcol;

			setBoard();
			lastMove = move; // Last move havaing a value set triggers the client loop to stop sleeping and continue. lastmove is set back to "" after move is complete.
			toggleTurn();

		}

		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {
			// do nothing. required by interface but not used.
		}
	}

	/**
	 * Listens for enter key on name input. Sets name for client.
	 * @author nathanjack
	 *
	 */
	private class enterListener implements TraverseListener {

		@Override
		public void keyTraversed(TraverseEvent e) {
			if (e.detail == SWT.TRAVERSE_RETURN) {
				Text textInput = (Text) e.getSource(); //
				String name = textInput.getText();
				serverDisplay.append("\nName set to: " + name + "\n"); // update server display message
				if (controller.client.connected == true) {
					textInput.setEnabled(false); // accept no more input after name set
					controller.client.setName(name);
					controller.client.getOut().println(name + "," + 1 + "\n");
				}
			}
		}
	}

	/**
	 * Connect to server button listener. Requires name set before connection. Requires server be available to connect.
	 * @author nathanjack
	 *
	 */
	private class serverButtonListener implements SelectionListener {

		@Override
		public void widgetSelected(SelectionEvent e) {
			serverDisplay.append("\nAttempting Connection...");
			if (nameInput.getText().compareTo("") == 0) {
				serverDisplay.append("\nPlease set your Name before connecting to server");
				return;
			}

			if (controller.client.connected == true) {
				serverDisplay.append("Already connected to server.");
				return;
			} else {
				try {
					controller.client.connectToServer(controller.client.serverAddress);
					controller.client.setName(nameInput.getText());
					serverDisplay.append("\nConnected to server!");
					serverDisplay.append("\nWaiting for opponent...");
					controller.client.setChar();
					if (playerChar == 'X') {
						startButton.setEnabled(true); // Xplayer gets first turn.
					}
					return;
				} catch (UnknownHostException e1) { // server address incorrect
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) { // failed to connect
					serverDisplay.append("\nServer unavailable...");
					return;
				}

			}

		}

		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {
			// do nothing. required by interface but not used.
		}

	}

	/**
	 * Start button event handling. Starts player turn and disables self after press.
	 * @author nathan jack
	 *
	 */
	private class startButtonListener implements SelectionListener {

		@Override
		public void widgetSelected(SelectionEvent e) {

			if (controller.client.ready) {
				if (playerChar == 'X') {
					startButton.setEnabled(false);
					toggleTurn();
				}
				return;
			} else {
				serverDisplay.append("Waiting for opponent");
				return;
			}
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {
			// do nothing.
		}

	}

	/**
	 * Updates button text based on passed string from server. Sets current players lastmove to "" to signal that the other player has made there move.
	 * @param BOARD
	 */
	public void updateBoard(String BOARD) {
		int i = 0;
		for (char s : BOARD.toCharArray()) {
			this.buttons[i].setText(s + "");
			i++;
		}
		this.lastMove = "";
		this.setBoard();
	}

	/**
	 * Sets class board object to current button grid contents.
	 */
	public void setBoard() {
		char[][] tempboard = new char[3][3];
		for (int j = 0; j < 3; j++) {
			for (int i = 0; i < 3; i++) {
				tempboard[i][j] = buttons[(i * 3) + j].getText().charAt(0);
			}
		}

		this.board = String
				.valueOf(Arrays.stream(tempboard).map(String::new).collect(Collectors.joining()).toCharArray());

	}

	/**
	 * Turn the game grid on or off for player turn. Does not allow previously pressed buttons to be pressed again.
	 * @param t boolean for grid state.
	 */
	public void toggleGrid(boolean t) {
		for (Button b : this.buttons) {
			final String[] buttonText = new String[1];
			this.getDisplay().syncExec( // you should use sync exec here!
					new Runnable() {
						public void run() {
							buttonText[0] = b.getText();
						}
					});

			if (buttonText[0].equals(" ")) // should use constants space char here
				this.display.syncExec(() -> b.setEnabled(t));
			else {
				this.display.syncExec(() -> b.setEnabled(false)); // Buttons already played cannot be played again.
			}
		}
	}

	/**
	 * toggles turn boolean and allows or disallows grid.
	 */
	public void toggleTurn() {
		this.myTurn = !this.isMyTurn();
		toggleGrid(isMyTurn());
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public Text getServerDisplay() {
		return this.serverDisplay;
	}

	public void setPlayerChar(char playerChar) {
		this.playerChar = playerChar;
	}

	public void setController(GUIController controller) {
		this.controller = controller;
	}

	public boolean isMyTurn() {
		return myTurn;
	}

}
