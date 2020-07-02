/*
 * Author: Christopher Medlin
 * Email: cmedlin@cnm.edu
 * Date: 17 Jun 2020
 * Course: CSCI2251
 *
 * Runnable for adding together a section of two matrixes and storing it in a
 * third.
 */

public class MatrixAddition implements Runnable {

    Matrix A, B, C;
    Point start, end;
    
    public MatrixAddition(Matrix A, Matrix B, Matrix C, Point start, Point end) {
        this.A = A;
        this.B = B;
        this.C = C;
        this.start = start;
        this.end = end;   
    }
    
    @Override
    public void run() {
        System.out.println("thinking...");
        A.subMatrixAdd(B, C, start, end);
    }
}
