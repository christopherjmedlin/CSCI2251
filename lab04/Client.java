/*
 * Author: Christopher Medlin
 * Email: cmedlin@cnm.edu
 * Date: 13 Jul 2020
 * Course: CSCI2251
 *
 * JFrame for the client application.
 */
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.util.Scanner;
import java.io.File;
import java.io.IOException;

import java.net.Socket;
import java.net.InetAddress;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Client extends JFrame {
    JTextField fileField;
    JTextArea result;
    JButton send;
    JButton browse;

    Socket sock;
    String hostname;
    ObjectInputStream in;
    ObjectOutputStream out;
    
    Matrix A, B, C;

    public Client() {
        // create a border for the text area to make it more distinct
        Border blackLine = BorderFactory.createLineBorder(Color.black);

        fileField = new JTextField("Path to File");
        result = new JTextArea("Addition result will be printed here.");
        result.setBorder(blackLine);
        result.setEditable(false);

        send = new JButton("Add Matrices");
        // triggered when button is pressed
        send.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    loadMatrices();
                    connect();
                    sendMatrices();
                    receiveResult();
                    close();
                }
            }
        );

        browse = new JButton("Browse Files");
        browse.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fileChooser();
                }
            }
        );
        
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(0,2));
        buttons.add(send);
        buttons.add(browse);
        this.add(fileField, BorderLayout.NORTH);
        this.add(result, BorderLayout.CENTER);
        this.add(buttons, BorderLayout.SOUTH);
        
        setSize(400, 400);
        setVisible(true);

        this.hostname = askForHostname();
    }
    
    // brings up a file chooser and sets the text of the file field according to selection
    private void fileChooser() {
        JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            this.fileField.setText(fc.getSelectedFile().getPath());
        }
    }
    
    // shows an error dialog box
    private void error(String message) {
        JOptionPane.showMessageDialog(this, message, "Error",
                JOptionPane.ERROR_MESSAGE);
        this.result.setText(message);
    }
    
    // prompts user with dialog for hostname
    private String askForHostname() {
        return (String) JOptionPane.showInputDialog(this, "Enter server hostname:",
                "Customized Dialog", JOptionPane.PLAIN_MESSAGE, null, null, "localhost");
    }

    // loads the matrices from the file
    private void loadMatrices() {
        String filename = this.fileField.getText();
        try (Scanner in = new Scanner(new File(filename))) {
            // read 2 length indicators at start
            String[] metadata = in.nextLine().split(" ");
            int ylength = Integer.parseInt(metadata[0]);
            int xlength = Integer.parseInt(metadata[1]);
            // read first matrix
            this.A = new Matrix(in, xlength, ylength);
            // read second matrix
            this.B = new Matrix(in, xlength, ylength);
            // create destination matrix
            this.C = new Matrix(xlength, ylength);
        } catch (IOException e) {
            error("File not found");
        }
    }
    
    // writes both matrices to the object output stream
    private void sendMatrices() {
        try {
            this.out.writeObject(this.A);
            this.out.writeObject(this.B);
            this.out.flush();
        } catch (IOException e) {
            error("Error sending matrices.");
        }
    }
    
    // connects to the server and sets in and out streams
    private void connect() {
        try {
            this.sock = new Socket(InetAddress.getByName(this.hostname), 1234);
            this.out = new ObjectOutputStream(sock.getOutputStream());
            this.in = new ObjectInputStream(sock.getInputStream());
        } catch (IOException e) {
            error("Could not connect to server.");
        }
    }
    
    // reads success indicator and result matrix from object input stream
    private void receiveResult() {
        try {
            // if success, we can expect a result matrix from the server
            if (this.in.readBoolean()) {
                this.C = (Matrix) this.in.readObject();
                this.result.setText(C.toString());
            } else {
                error("Unexpected server error. Check output from server.");
            }
        } catch (Exception e) {
            error("Error receiving response from server.");
        }
    }

    // closes the currently active connection
    private void close() {
        try {
            in.close();
            out.close();
            sock.close();
        } catch (IOException e) {
            error("Error closing sockets.");
        }
    }
}
