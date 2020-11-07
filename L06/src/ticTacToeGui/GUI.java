package ticTacToeGui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class GUI extends JPanel {

	JButton buttons[] = new JButton[9];

	private JPanel MasterPanel;

	public GUI() {
		// Building the gui using N/S/W/E content panes and multipanels.
		// North: N/A
		// South: Enter name text field and connect to server button
		// West: N.A
		// East: Server Messages display text
		// Center: Game board
		this.MasterPanel = new JPanel();
		
		
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new GridLayout(2, 0));

		JLabel prompt = new JLabel("Enter your name below:");
		JTextField nameField;
		nameField = new JTextField(5);
		nameField.setText("");
		nameField.addActionListener(new buttonListener());

		southPanel.add(prompt);
		southPanel.add(nameField);
		southPanel.setBounds(100,0,100,100);
		
		JPanel eastPanel = new JPanel();
		JLabel display_Title = new JLabel("Server Output");
		JTextArea serverMsgDisplay;
		serverMsgDisplay = new JTextArea(8, 30);
		serverMsgDisplay.append("Click below to start server connection" + "\n");
		eastPanel.add(display_Title);
		eastPanel.add(serverMsgDisplay);

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(3, 3));
		initializebuttons();

		JPanel masterPane = new JPanel();
		masterPane.setLayout(new BorderLayout());

		masterPane.add(southPanel,BorderLayout.SOUTH);
		masterPane.add(eastPanel,BorderLayout.EAST);
		masterPane.add(centerPanel,BorderLayout.CENTER);
		
		

		//3. Create components and put them in the frame.
		//...create emptyLabel...
		this.MasterPanel.add(masterPane);
		//frame.pack();
		//setTitle("TicTacToe Client");
		//pack();

	}

	public void initializebuttons() {
		for (int i = 0; i <= 8; i++) {
			buttons[i] = new JButton();
			buttons[i].setText("");
			buttons[i].addActionListener(new buttonListener());

			add(buttons[i]); // adds this button to JPanel (note: no need for JPanel.add(...)
								// because this whole class is a JPanel already
		}
	}

	public void resetButtons() {
		for (int i = 0; i <= 8; i++) {
			buttons[i].setText("");
		}
	}

// when a button is clicked, it generates an ActionEvent. Thus, each button needs an ActionListener. When it is clicked, it goes to this listener class that I have created and goes to the actionPerformed method. There (and in this class), we decide what we want to do.
	public class buttonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			int alternate = 0;
			JButton buttonClicked = (JButton) e.getSource(); // get the particular button that was clicked
			if (alternate % 2 == 0)
				buttonClicked.setText("X");
			else
				buttonClicked.setText("O");

			alternate++;

		}

	}

	public static void main(String[] args) {
		GUI game  = new GUI();
		JFrame window = new JFrame("Tic-Tac-Toe");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.getContentPane().add(game.MasterPanel);
		window.setBounds(300, 200, 300, 300);
		window.setVisible(true);
	}
}