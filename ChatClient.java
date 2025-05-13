import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ChatClient extends JFrame {
    private JTextArea chatArea;
    private JTextField inputField;
    private PrintWriter out;

    public ChatClient() {
        setTitle("Client Chat");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        inputField = new JTextField();

        inputField.addActionListener(e -> {
            String message = inputField.getText();
            chatArea.append("You: " + message + "\n");
            out.println(message);
            inputField.setText("");
        });

        add(new JScrollPane(chatArea), BorderLayout.CENTER);
        add(inputField, BorderLayout.SOUTH);
        setVisible(true);

        connectToServer();
    }

    private void connectToServer() {
        try {
            Socket socket = new Socket("localhost", 1234);
            chatArea.append("Connected to server.\n");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            String line;
            while ((line = in.readLine()) != null) {
                chatArea.append("Server: " + line + "\n");
            }

            socket.close();
        } catch (IOException ex) {
            chatArea.append("Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new ChatClient();
    }
}