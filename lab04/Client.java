/*
 * Author: Christopher Medlin
 * Email: cmedlin@cnm.edu
 * Date: 13 Jul 2020
 * Course: CSCI2251
 *
 * JFrame for the client application.
 */
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;

public class Client extends JFrame {
    JTextField fileField;
    JTextArea result;
    JButton send;

    public Client() {
        Border blackLine = BorderFactory.createLineBorder(Color.black);
        fileField = new JTextField("Path to File");
        result = new JTextArea("Addition result will be printed here.");
        result.setBorder(blackLine);

        result.setEditable(false);
        send = new JButton("Add Matrices");
        
        add(fileField, BorderLayout.NORTH);
        add(result, BorderLayout.CENTER);
        add(send, BorderLayout.SOUTH);

        setSize(400, 400);
        setVisible(true);
    }
    
}
