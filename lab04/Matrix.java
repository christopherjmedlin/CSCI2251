/*
 * Author: Christopher Medlin
 * Email: cmedlin@cnm.edu
 * Date: 17 Jun 2020
 * Course: CSCI2251
 *
 * A matrix with the ability to perform addition using a subset of itself with
 * another matrix.
 */

import java.io.File;
import java.util.Scanner;
import java.util.NoSuchElementException;

public class Matrix {

    private int[][] data;
    
    /**
     * Thrown when reading a matrix with a Scanner fails.
     */
    public class FormatException extends RuntimeException {
        public FormatException(String msg) {
            super(msg);
        }
    }

    public Matrix(int xlength, int ylength) {
        this.data = new int[ylength][xlength];
    }
    
    /**
     * Attempts to load a matrix from the scanner given.
     *
     * @throws FormatException if the matrix scanned by the scanner is
     * improperly formatted
     */
    public Matrix(Scanner in, int xlength, int ylength) throws FormatException {
        this(xlength, ylength);
        
        try {
            for (int i = 0; i < ylength; i++) {
                String[] row = in.nextLine().split(" ");
                for (int j = 0; j < xlength; j++) {
                    this.data[i][j] = Integer.parseInt(row[j]);
                }
            }
        } catch (ArrayIndexOutOfBoundsException | NoSuchElementException e) {
            // no such element exception means the scanner tried to read more
            // lines than there actually were, so the metadata was wrong.
            throw new FormatException(
               "One or more of the size indicators are incorrect."
            );
        } catch (NumberFormatException e) {
            throw new FormatException(
                "Invalid numbers found."
            );
        }
    }

    /**
     * Just for testing without multithreading.
     */
    public void add(Matrix right, Matrix dest) {
        for (int i = 0; i < this.data.length; i++)  {
            for (int j = 0; j < this.data[i].length; j++) {
                dest.data[i][j] = this.data[i][j] + right.data[i][j];
            }
        }
    }
    
    /**
     * Adds a subsection of the matrix with that of another.
     *
     * @param right the matrix to be added to
     * @param dest the matrix where the result will be stored
     * @param start upper left corner of the subsection
     * @param end lower right corner of the subsection
     */
    public void subMatrixAdd(Matrix right, Matrix dest, Point start, Point end) {
        for (int i = start.y; i <= end.y; i++)  {
            for (int j = start.x; j <= end.x; j++) {
                dest.data[i][j] = this.data[i][j] + right.data[i][j];
            }
        }
    }

    public int ylength() {
        return this.data.length;
    }
    
    public int xlength() {
        return this.data[0].length;
    }

    public String toString() {
        // start with intial capacity for efficiency
        StringBuilder str = new StringBuilder(data.length*data.length*2);

        for (int i = 0; i < data.length; i++) {
            // add first number of row without space
            str.append(Integer.toString(data[i][0]));
            for (int j = 1; j < data[i].length; j++) {
                str.append(" " + Integer.toString(data[i][j]));
            }
            // next row
            str.append('\n');
        }

        return str.toString();
    }
}
