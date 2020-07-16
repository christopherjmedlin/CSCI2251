/*
 * Author: Christopher Medlin
 * Email: cmedlin@cnm.edu
 * Date: 13 Jul 2020
 * Course: CSCI2251
 *
 * Contains code for starting the server.
 */

import java.net.ServerSocket;
import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

public class Server {
    private ServerSocket server;
    private Socket sock;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public Server(int port) throws IOException {
        // maximum queue of 100
        this.server = new ServerSocket(port, 100);
    }   
    
    /**
     * Begins listening for connections.
     */
    public void run() {
        while (true) {
            try {
                this.sock = this.server.accept();
                setStreams();
                processConnection();   
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    // sets in and out to the input and output streams of the socket
    private void setStreams() throws IOException {
        this.out = new ObjectOutputStream(this.sock.getOutputStream());
        this.in = new ObjectInputStream(this.sock.getInputStream());
    }
    
    // after connection, receives matrices and performs addition, sending the
    // result to client
    private void processConnection() throws IOException {
        try {
            Matrix A, B;
            A = (Matrix) this.in.readObject();
            B = (Matrix) this.in.readObject();

            // perform addition
            ThreadOperation op = new ThreadOperation(A, B);
            op.start(true);
            op.stop();
            
            // indicate success
            this.out.writeBoolean(true);
            // write result
            this.out.writeObject(op.result());
            this.out.flush();
        } catch (ClassNotFoundException | InterruptedException e) {
            //indicate failure
            this.out.writeBoolean(false);
            this.out.flush();
        }
    }
}
