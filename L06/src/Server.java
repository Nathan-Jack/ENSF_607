import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
	private BufferedReader socketInput;
	private PrintWriter socketOutput;
	private ServerSocket serverSocket;
	private Socket aSocket;

	/**
	 * Construct a Server with Port 8099
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
	 * Get input from Client.
	 * 
	 * @throws IOException
	 */
	public void getUserInput() throws IOException {
		StringBuffer line = null;
		while (true) {
			line = new StringBuffer(socketInput.readLine());
			if (line != null) {
		        StringBuilder reverseLine = new StringBuilder();
		        reverseLine.append(line);
		        reverseLine = reverseLine.reverse();
				
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
