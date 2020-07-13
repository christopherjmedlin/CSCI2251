/*
 * Author: Christopher Medlin
 * Email: cmedlin@cnm.edu
 * Date: 13 Jul 2020
 * Course: CSCI2251
 *
 * Driver class for the server
 */
import java.io.IOException;

public class ServerTest {
    public static void main(String[] args) {
        int port = 1234;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (Exception e) {
                System.err.println("Invalid port number.");
            }
        }
        
        try {
            Server s = new Server(port);
            s.run();
        } catch (IOException e) {
            System.err.printf("Could not establish server on port %d.", port);
        }
    }
}
