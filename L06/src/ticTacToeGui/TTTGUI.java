package ticTacToeGui;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Text;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;

import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.TouchEvent;
import org.eclipse.swt.events.TouchListener;

public class TTTGUI extends Composite {
	private Text namePrompt;
	private Text nameInput;

	private Button buttons[] = new Button[9];

	private Text serverDisplay;
	private Text serverDisplayTitle;


	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new GridLayout(1, false));

		TTTGUI gui = new TTTGUI(shell, SWT.None);

		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
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

		Button button_ConnectToServer = new Button(this, SWT.NONE);
		button_ConnectToServer.setEnabled(true);
		FormData fd_button_ConnectToServer = new FormData();
		fd_button_ConnectToServer.top = new FormAttachment(namePrompt, 0, SWT.TOP);
		fd_button_ConnectToServer.left = new FormAttachment(buttons[8], 30, SWT.RIGHT);
		button_ConnectToServer.setLayoutData(fd_button_ConnectToServer);
		button_ConnectToServer.setText("Connect To Server");
		
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
				buttons[i * 3 + j].setTouchEnabled(true);
				buttons[i * 3 + j].setText("TEST");
				buttons[i * 3 + j].setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION_TEXT));
				FormData fd_button0 = new FormData();
				fd_button0.top = new FormAttachment(0, offsets[0]);
				fd_button0.bottom = new FormAttachment(0, offsets[1]);
				fd_button0.left = new FormAttachment(0, offsets[2]);
				fd_button0.right = new FormAttachment(0, offsets[3]);
				buttons[i * 3 + j].setLayoutData(fd_button0);

				buttons[i * 3 + j].addSelectionListener(new buttonListener());

			}
		}
	}


	private class buttonListener implements SelectionListener {

		@Override
		public void widgetSelected(SelectionEvent e) {
			int alternate = 0;
			Button buttonClicked = (Button) e.getSource(); // get the particular button that was clicked
			if (alternate % 2 == 0)
				buttonClicked.setText("X");
			else
				buttonClicked.setText("O");

			alternate++;

		}

		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {
			
		}



	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
