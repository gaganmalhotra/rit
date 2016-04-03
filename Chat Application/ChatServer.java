import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.LinkedHashSet;

/**
 * Server that accepts the chat messages from client and accepts the value of
 * clients and adds them to out print writers list
 * @author Gagandeep Malhotra
 */
public class ChatServer {

	/**
	 * The port that the server listens on.
	 */
	private static final int PORT_NUMBER = 9001;

	/**
	 * All the clients who have joined chat room are mainted in the list below.
	 */
	private static LinkedHashSet<String> clientsAddedList = new LinkedHashSet<String>();

	/**
	 * All the print writers each per client are saved in the list below
	 */
	private static HashSet<PrintWriter> clientOutPrintWritersList = new HashSet<PrintWriter>();

	public static void main(String[] args) throws Exception {
		System.out.println("The chat server is running.");
		ServerSocket listener = new ServerSocket(PORT_NUMBER);
		try {
			while (true) {
				new ServerHandler(listener.accept()).start();
			}
		} finally {
			listener.close();
		}
	}

	/**
	 * A server handler class that accepts each clients messages and is
	 * responsible for broadcasting it.
	 * 
	 */
	private static class ServerHandler extends Thread {
		private String name;
		private Socket socket;
		private BufferedReader inputS;
		private PrintWriter outputS;

		/**
		 * Constructs a handler thread, squirreling away the socket. All the
		 * interesting work is done in the run method.
		 */
		public ServerHandler(Socket socket) {
			this.socket = socket;
		}

		/**
		 * 
		 * This method is used to check whether a new client has connected if
		 * yes then it adds it to the list of out print writers and broadcasts
		 * its message to the common stream.
		 */
		public void run() {
			try {

				// Create character streams for the socket.
				inputS = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
				outputS = new PrintWriter(socket.getOutputStream(), true);

				while (true) {
					outputS.println("SUBMITNAME");
					name = inputS.readLine();
					if (name == null) {
						return;
					}
					synchronized (clientsAddedList) {
						if (!clientsAddedList.contains(name)) {
							clientsAddedList.add(name);
							break;
						}
					}
				}

				// After the input onscreen name all the broadcast messages are
				// output through PrintWriters
				outputS.println("NAMEACCEPTED");
				clientOutPrintWritersList.add(outputS);

				// Accept messages from this client and broadcast them.
				while (true) {
					String input = inputS.readLine();
					System.out.println("input val" + input);
					if (input == null) {
						return;
					}

					for (PrintWriter writer : clientOutPrintWritersList) {
						writer.println("MESSAGE " + name + ": " + input);
					}
				}

			} catch (IOException e) {
				System.out.println(e);
			} finally {
				// Remove clients name and close its socket and out printwriter
				if (name != null) {
					clientsAddedList.remove(name);
				}
				if (outputS != null) {
					clientOutPrintWritersList.remove(outputS);
				}
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}