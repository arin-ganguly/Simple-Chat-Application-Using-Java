import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ChatServer extends JFrame {
    private JTextArea chatArea;
    private JTextField inputField;
    private PrintWriter out;

    public ChatServer() {
        setTitle("Server Chat");
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

        startServer();
    }

    private void startServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            chatArea.append("Waiting for client...\n");
            Socket socket = serverSocket.accept();
            chatArea.append("Client connected.\n");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            String line;
            while ((line = in.readLine()) != null) {
                chatArea.append("Client: " + line + "\n");
            }

            socket.close();
            serverSocket.close();
        } catch (IOException ex) {
            chatArea.append("Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new ChatServer();
    }
}