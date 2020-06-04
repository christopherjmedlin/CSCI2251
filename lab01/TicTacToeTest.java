/*
 * Author: Christopher Medlin
 * Email: cmedlin@cnm.edu
 * Date: 31 May 2020
 * Course: CSCI2251
 *
 * ASCII based game of Tic-Tac-Toe.
 */

public class TicTacToeTest {
    public static void main(String[] args) {
        TicTacToe game;

        // -r option results in player being selected randomly at start
        if (args.length > 0 && args[0].compareTo("-r") == 0) {
            game = new TicTacToe(true);
        } else {
            game = new TicTacToe();
        }

        // print intial empty board
        game.printBoard();
        // enter main loop
        game.play();
    }
}
