package ticTacToeGui;

/**
 * GUI controller. Allows for gui and client thread to run concurrently.
 * @author nathan jack
 *
 */
public class GUIController {

	TTTGUI gui;
	TicTacToeGUIClient client;
	private boolean loopStarted = false;

	public GUIController(TicTacToeGUIClient client) {

		this.gui = TTTGUI.buildGUI(client);
		this.client = client;

	}

	public void startGui() {
		gui.setController(this);
		gui.run();
	}

	/**
	 * Creates new thread for client loop, runs once for each client.
	 */
	public void startClientLoop() {

		if (!loopStarted) {
			Thread clientThread = new Thread() {
				public void run() {
					client.startLoop();
				}
			};

			clientThread.start();
			loopStarted = true;
		}

	}

}
