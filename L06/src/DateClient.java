import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Lab 06 Code Exercise 2
 * 
 * @author Nathan Jack
 * @version 1.0
 * @since Nov 4, 2020
 * 
 */
public class DateClient {
	private PrintWriter socketOut;
	private Socket dateinSocket;
	private BufferedReader stdIn;
	private BufferedReader socketIn;

	/**
	 * Creates a client built to send and receive from a date server.
	 * Source code base pulled from Exercise 1 client code.
	 * @param serverName
	 * @param portNumber
	 */
	public DateClient(String serverName, int portNumber) {
		try {
			dateinSocket = new Socket(serverName, portNumber);
			stdIn = new BufferedReader(new InputStreamReader(System.in));
			socketIn = new BufferedReader(new InputStreamReader(
					dateinSocket.getInputStream()));
			socketOut = new PrintWriter((dateinSocket.getOutputStream()), true);
		} catch (IOException e) {
			System.err.println(e.getStackTrace());
		}
	}

	public void communicate()  {

		String line = "";
		String response = "";
		boolean running = true;
		while (running) {
			try {
				System.out.println("Please Select and option (DATE/TIME)");
				line = stdIn.readLine();
				if (!line.equals("QUIT")){
					System.out.println(line);
					socketOut.println(line);
					response = socketIn.readLine();
					System.out.println(response);	
				}else{
					running = false;
				}
				
			} catch (IOException e) {
				System.out.println("Sending error: " + e.getMessage());
			}
		}
		try {
			stdIn.close();
			socketIn.close();
			socketOut.close();
		} catch (IOException e) {
			System.out.println("Closing error: " + e.getMessage());
		}

	}

	public static void main(String[] args) throws IOException  {
		DateClient aClient = new DateClient("localhost", 9090);
		aClient.communicate();
	}
}