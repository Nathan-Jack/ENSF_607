import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Lab 06 Code Exercise 1
 * 
 * @author Nathan Jack
 * @version 1.0
 * @since Nov 4, 2020
 * 
 */
public class Server {
	private BufferedReader socketInput;
	private PrintWriter socketOutput;
	private ServerSocket serverSocket;
	private Socket aSocket;

	/**
	 * Construct a Server with Port 8099, display that server is running to user.
	 * Source code pulled from notes and dateServer exercise 2 code.
	 */
	public Server() {
		try {
			serverSocket = new ServerSocket(8099);
			System.out.println("Server is now running.");
			aSocket = serverSocket.accept();
			socketInput = new BufferedReader(new InputStreamReader(aSocket.getInputStream()));
			socketOutput = new PrintWriter(aSocket.getOutputStream(), true);
		} catch (IOException e) {
		}
	}

	/**
	 * Get input from Client socket input and validate if string is a palindrome or not.
	 * 
	 * @throws IOException if read-in is interrupted or invalid in anyway
	 */
	public void getUserInput() throws IOException {
		StringBuffer line = null;
		while (true) {
			line = new StringBuffer(socketInput.readLine());
			if (line != null) {
		        StringBuilder reverseLine = new StringBuilder();
		        reverseLine.append(line);
		        reverseLine = reverseLine.reverse(); // using built in stringbuilder reverse function to compare strings quickly.
				
				if (line.toString().equals("QUIT")) {
					socketOutput.println("Server shutdown.");
					break;
				}
				if (line.toString().equals(reverseLine.toString())) {
					socketOutput.println( line + " is a Palindrome");
				}else {
					socketOutput.println(line + " is not a Palindrome");
				}
			}
		}
		
		socketInput.close();
		socketOutput.close();
		serverSocket.close();
		
	}
	
	public static void main(String[] args) throws IOException {
		Server palserv = new Server();
		palserv.getUserInput();
	}
}
