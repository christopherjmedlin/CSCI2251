/*
 * Author: Christopher Medlin
 * Email: cmedlin@cnm.edu
 * Date: 17 Jun 2020
 * Course: CSCI2251
 *
 * Driver class for Lab03.
 */
import java.io.File;
import java.io.IOException;

public class MatrixThread {
    public static void main(String[] args) {
        File file;
        Args parsedArgs = new Args(args);
        file = new File(parsedArgs.filename);

        try {
            ThreadOperation threadop = new ThreadOperation(file);
            
            
            if (parsedArgs.benchmarking) {
                System.out.printf("Available cores: %d%n", Runtime.getRuntime().availableProcessors());
                // measure time time taken to compute
                long start = System.nanoTime();
                threadop.start(parsedArgs.threading);
                threadop.stop();
                long time = System.nanoTime() - start;
                threadop.print();
                System.out.printf("Computation time: %d ns%n", time);
            } else {
                threadop.start(parsedArgs.threading);
                threadop.stop();
                threadop.print();
            }
        } catch (IOException e) {
            System.err.printf("File %s does not exist.%n", parsedArgs.filename);
        } catch (Matrix.FormatException e) {
            System.err.printf("Incorrect matrix format: %s%n", e.getMessage());
        } catch (InterruptedException e) {
            System.err.printf("Error: One or more threads have been unexpectedly interrupted.");
        } catch (Exception e) {
            System.err.println("An unexpected error has occurred. How unlucky.\n");
            e.printStackTrace();
        }
    }
}

class Args {
    public boolean threading;
    public boolean benchmarking;
    public String filename;

    public Args(String[] args) {
        this.filename = "matrix.txt";
        // enable multithreading by default
        this.threading = true;

        for (int i = 0; i < args.length; i++) {
            // s for single-threaded
            if (args[i].equals("-s") || args[i].equals("--single")) {
                this.threading = false;
            } else if (args[i].equals("-b") || args[i].equals("--benchmark")) {
                this.benchmarking = true;
            } else {
                // assume any excess arg is the filename
                this.filename = args[i];
            }
        }
    }
}
