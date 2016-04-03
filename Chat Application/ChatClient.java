import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Simple chat application using swings for the user interface.
 * @author Gagandeep Malhotra
 */
public class ChatClient {

    BufferedReader in;
    PrintWriter out;
    OutputStream os;
    Socket socket;
    JButton clearButton= new JButton("clear");
    JLabel lblEnterText ;
    JLabel lblChat;
    private JTextArea messageArea;
    JFrame frame = new JFrame("Chatter");
    JTextField textField = new JTextField(40);
    JButton quitButton = new JButton("Quit Chat");
    JButton attachButton = new JButton("Attach");
    
    /**
     * This method is used to request messages that are delivered to the server
     * and it remains inactive until client receives NAMEACCEPTED message from server.
     */
    public ChatClient() {
    	// creating the GUI for chat
    	frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		clearButton= new JButton("Clear");
		clearButton.setBounds(327, 229, 117, 29);
		frame.getContentPane().add(clearButton);
		
		quitButton= new JButton("Quit Chat");
		quitButton.setBounds(6, 229, 117, 29);
		frame.getContentPane().add(quitButton);
		
		textField = new JTextField();
		textField.setBounds(103, 172, 341, 28);
		frame.getContentPane().add(textField);
		textField.setEditable(false);
		textField.setColumns(10);
		
        messageArea = new JTextArea();
		messageArea.setSize(341, 151);
		messageArea.setLocation(103, 6);
        frame.getContentPane().add(messageArea, "Center");
        messageArea.setEditable(false);
        
        lblEnterText = new JLabel("Enter Message");
        lblEnterText.setBounds(6, 175, 99, 22);
        frame.getContentPane().add(lblEnterText);
        
        lblChat = new JLabel("Chat");
        lblChat.setBounds(6, 6, 61, 16);
        frame.getContentPane().add(lblChat);
	
        // Adding Listeners
        textField.addActionListener(new ActionListener() {
            /**
             * Here after the text is entered into the send message field the textField is set to null again for new messages
             */
            public void actionPerformed(ActionEvent e) {
                out.println(textField.getText());
                textField.setText("");
            }
        });
        
        quitButton.addActionListener(new ActionListener() {
			/**
			 * This listener responds to the button click for quitting the chat session.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				out.close();
				frame.dispose();
			}
		});
        
        clearButton.addActionListener(new ActionListener() {
			/**
			 * This button clears the textArea field.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				messageArea.setText("");
			}
		});
    }
    
    
    /**
     * Prompt for and return the desired screen name.
     * @throws InterruptedException 
     * @throws HeadlessException 
     */
    private String getName1() throws HeadlessException, InterruptedException {
        return JOptionPane.showInputDialog(
            frame,
            "Choose a name for chatting",
            "Pick on screen name",
            JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Connects to the server then enters the processing loop.
     * @throws InterruptedException 
     */
    private void run() throws IOException, InterruptedException {

    	// Initializing the connection
        String serverAddress = "localhost";
        socket = new Socket(serverAddress, 9001);
        in = new BufferedReader(new InputStreamReader(
            socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        // constantly keeps on checking the text messages entered by the client
        while (true) {
            String line = in.readLine();
            if (line.startsWith("SUBMITNAME")) {
            	String abc= getName1();
            	out.println(abc);
                frame.setTitle(abc);
            } else if (line.startsWith("NAMEACCEPTED")) {
                textField.setEditable(true);
            } else if (line.startsWith("MESSAGE")) {
                messageArea.append(line.substring(8) + "\n");
            }
        }
    }

    /**
     * Runs the client as an application with a closeable frame.
     */
    public static void main(String[] args) throws Exception {
        ChatClient client = new ChatClient();
        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.frame.setVisible(true);
        client.run();
    }
}