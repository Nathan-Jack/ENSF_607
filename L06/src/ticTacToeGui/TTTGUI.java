package ticTacToeGui;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;
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
	private TicTacToeGUIClient client;
	private Display display;
	private Shell shell;
	private char playerChar;
	private boolean myTurn = false;

	public static TTTGUI buildGUI(TicTacToeGUIClient client) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new GridLayout(1, false));
		TTTGUI gui = new TTTGUI(shell, SWT.None);

		gui.shell = shell;
		gui.display = display;
		gui.client = client;
		return gui;
	}

	// main operation loop of the gui. throws IO exception if game loop in
	// communicate throws one.
	public static void run(TTTGUI gui) throws IOException {
		gui.shell.pack();
		gui.shell.open();
		while (!gui.shell.isDisposed()) {
			if (!gui.display.readAndDispatch()) { // if there are no commands to respond to

				gui.display.sleep();
			}

		}
		gui.display.dispose();
	}

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public TTTGUI(Composite parent, int style) {
		super(parent, SWT.BORDER);

		setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		setLayout(new FormLayout());

		this.initializebuttons();

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
				buttons[i * 3 + j].setText(" ");
				buttons[i * 3 + j].setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION_TEXT));
				FormData fd_button0 = new FormData();
				fd_button0.top = new FormAttachment(0, offsets[0]);
				fd_button0.bottom = new FormAttachment(0, offsets[1]);
				fd_button0.left = new FormAttachment(0, offsets[2]);
				fd_button0.right = new FormAttachment(0, offsets[3]);
				buttons[i * 3 + j].setLayoutData(fd_button0);

				buttons[i * 3 + j].addSelectionListener(new gridButtonListener());

			}
		}
	}

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
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {
			// do nothing.
		}
	}

	private class enterListener implements TraverseListener {

		@Override
		public void keyTraversed(TraverseEvent e) {
			if (e.detail == SWT.TRAVERSE_RETURN) {
				Text textInput = (Text) e.getSource(); //
				String name = textInput.getText();
				serverDisplay.append("\nName set to: " + name + "\n"); // update server display message
				if (client.connected == true) {
					textInput.setEnabled(false); // accept no more input after name.
					client.setName(name);
					client.getOut().println(name + "," + 1 + "\n");
				}
			}
		}
	}

	private class serverButtonListener implements SelectionListener {

		@Override
		public void widgetSelected(SelectionEvent e) {
			serverDisplay.append("\nAttempting Connection...");
			if (nameInput.getText().compareTo("") == 0) {
				serverDisplay.append("\nPlease set your Name before connecting to server");
				return;
			}

			if (client.connected == true) {
				serverDisplay.append("Already connected to server.");
				return;
			} else {
				try {
					client.connectToServer(client.serverAddress);
					client.setName(nameInput.getText());
					serverDisplay.append("\nConnected to server!");
					serverDisplay.append("\nWaiting for opponent...");
					client.setChar();
					if (playerChar == 'X') {
						startButton.setEnabled(true);
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
			// do nothing.
		}

	}

	private class startButtonListener implements SelectionListener {

		@Override
		public void widgetSelected(SelectionEvent e) {

			if (client.ready) {
				if (playerChar == 'X') {
					myTurn = true;
				}
				display.syncExec(new Runnable() {
					public void run() {
						client.startLoop();
					}
				});

				return;
			}

			else {
				serverDisplay.append("Waiting for opponent");
				return;
			}
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {
			// do nothing.
		}

	}

	public String getMove(int index) {
		
		String res = (playerChar + "," + "row,col");

		return res;
	}

	public void updateBoard(String[] BOARD) {

		for (String s : BOARD) {
			this.serverDisplay.append(s); // display to gui console because fuck it
		}
		this.toggleGrid(true);
	}

	public char[][] getBoard() {
		char[][] board = new char[3][];
		for (int j = 0; j <3; j++) {
			for(int i = 0; i <3; i++) {
				board[i][j] = buttons[i*3+j].getText().charAt(0);
			}
		}
		
		return board;
	}

	public void toggleGrid(boolean t) {
		for (Button b : this.buttons) {
			b.setEnabled(t);
		}
	}

	public void setTurn(boolean b) {
		this.myTurn = b;

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

}
