/*
 * Author: Christopher Medlin
 * Email: cmedlin@cnm.edu
 * Date: 17 Jun 2020
 * Course: CSCI2251
 *
 * Has methods for starting and stopping the 4 matrix addition threads
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class ThreadOperation {

    private ExecutorService executor;
    private Matrix A;
    private Matrix B;
    private Matrix C;

    /**
     * @param file file which the 2 matrices are located
     * @throws IOException if the file does not exist
     */
    public ThreadOperation(Matrix A, Matrix B) {
        this.executor = Executors.newFixedThreadPool(4);
        this.A = A;
        this.B = B;
        // create destination matrix
        this.C = new Matrix(A.xlength(), A.ylength());
    }

    /**
     * Starts matrix addition.
     *
     * @param threading if true, uses 4 threads, if false, uses one
     */
    public void start(boolean threading) {
        // bottom right corner of matrix
        Point end = new Point(this.A.xlength()-1, this.A.ylength()-1);
        // midpoint of matrix
        Point mid = new Point((int) Math.floor(end.x/2), (int) Math.floor(end.y/2));
        
        // with threads
        if (threading) {
            // Quadrant II (upper left)
            this.executor.execute(new MatrixAddition(A, B, C,
                new Point(0, 0), mid
            ));
            // Quadrant I (upper right)
            this.executor.execute(new MatrixAddition(A, B, C,
                new Point(mid.x+1, 0), new Point(end.x, mid.y)
            ));
            // Quadrant III (bottom left)
            this.executor.execute(new MatrixAddition(A, B, C,
                new Point(0, mid.y-1), new Point(mid.x, end.y)
            ));
            // Quadrant IV (bottom right)
            this.executor.execute(new MatrixAddition(A, B, C,
                new Point(mid.x+1, mid.y-1), end
            ));
        } else {
            // without threads
            A.add(B, C);
        }
    }

    /**
     * Shuts down executor and waits for threads to complete
     */
    public void stop() throws InterruptedException {
        this.executor.shutdown();
        this.executor.awaitTermination(5, TimeUnit.MINUTES);
    }

    /**
     * Prints result of matrix addition
     */
    public Matrix result() {
        return this.C;
    }
}
