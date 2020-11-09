package ticTacToeGui;

import java.io.IOException;

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
		try {
			gui.run();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void startClientLoop() {

		if (!loopStarted) {
		Thread clientThread = new Thread() {
			public void run() {
				client.startLoop();
			}
		};
		
			clientThread.start();
			loopStarted = true;
			//try {
				//clientThread.join();
			//} catch (InterruptedException e) {
				//System.out.println("it fucked upt here");
				//e.printStackTrace();
			//}
		}
		
	}

}
