import java.util.Arrays;
import java.util.Scanner;
import java.security.SecureRandom;

public class TicTacToe {
    
    // represents the status of a tic tac toe game
    public enum Status {
        WIN, DRAW, CONTINUE;
    }
    
    // represents whether a cell has an X, an O, or nothing
    private enum CellValue {
        X, O, EMPTY;

        public String toString() {
            if (this.name().compareTo("EMPTY") == 0) {
                return " ";
            }
            return this.name();
        }
    }

    private CellValue[][] board;
    private Status status;
    // either 'X' or 'O'
    private char currentPlayer;
    
    /* 
     * this represents the net number of tiles marked by a player in a row,
     * column or diagonal.
     * 
     * index 0-2 are rows 1-3, 3-5 are cols 1-3, 6 and 7 are diagonals
     *
     * so, if player 1 marks the tile in the center, indexes 1, 4, 6, and 7 are incremented,
     * while if player 2 does the same they are decremented.
     *
     * If any value in this array is 3, player 1 wins, and if any is -3, player 2 wins.
     */ 
    private int[] score;

    // for checking for draw
    private int totalMoves;
    
    /**
     * @param randomPlayer if true, starts with a random player
     */
    public TicTacToe(boolean randomPlayer) {
        this.board = new CellValue[][] {
            { CellValue.EMPTY, CellValue.EMPTY, CellValue.EMPTY },
            { CellValue.EMPTY, CellValue.EMPTY, CellValue.EMPTY },
            { CellValue.EMPTY, CellValue.EMPTY, CellValue.EMPTY },
        };
        this.score = new int[] {0, 0, 0, 0, 0, 0, 0, 0};
        this.status = Status.CONTINUE;
        this.totalMoves = 0;
        
        if (randomPlayer) {
            SecureRandom rand = new SecureRandom();
            this.currentPlayer = rand.nextInt(2) == 1 ? 'X' : 'O';
        } else {
            this.currentPlayer = 'X';
        }
    }

    public TicTacToe() {
        this(false);
    }
    
    /**
     * Prints the current state of the board as ASCII art.
     */
    public void printBoard() {
        System.out.println(" _________________ ");
        for (int i = 0; i < 3; i++) {
            System.out.println("|     |     |     |");
            for (int j = 0; j < 3; j++) {
                System.out.printf("|  %s  ", board[i][j].toString());
            }
            System.out.println("|");
            System.out.println("|_____|_____|_____|");
        }
    }

    /**
     * Loops until the game is over.
     */
    public void play() {
        do {
            checkForWin();
            printStatus();
            if (gameStatus() == Status.CONTINUE)
                printBoard();
        } while (this.gameStatus() == Status.CONTINUE);
    }
    
    /**
     * @return the current status of the game (WIN, DRAW, or CONTINUE)
     */
    public Status gameStatus() {
        return this.status;
    }
    
    // iterates through the score array to check for a win, and sets the status
    // and player accordingly
    private void checkForWin() {
        for (int i = 0; i < this.score.length; i++) {
            if (this.score[i] == 3) {
                this.status = Status.WIN;
                // set current player to the winner, which will be printed in
                // printStatus
                this.currentPlayer = 'X';
                return;
            }
            else if (this.score[i] == -3) {
                this.status = Status.WIN;
                this.currentPlayer = 'O';
                return;
            }
        }
        
        // if there have been 9 moves, the board is full, and it is a draw
        if (this.totalMoves == 9)
            this.status = Status.DRAW;
    }

    /**
     * @return true if the move is within the board's range and on an empty
     * cell, false if not
     */
    private boolean validMove(String row, String col) {
        int x = 0, y = 0;
        try {
            x = Integer.parseInt(col);
            y = Integer.parseInt(row);
        } catch (NumberFormatException e) {
            return false;
        }
        return (x <= 2 && x >= 0 && y <= 2 && y >= 0) &&
               this.board[y][x] == CellValue.EMPTY;
    }

    /**
     * Marks a tile on the board, updating the score array
     */
    private void markTile(int x, int y) { 
        this.board[y][x] = this.currentPlayer == 'X' ? CellValue.X : CellValue.O;

        // +1 if player is 1, -1 if player is 2
        int scoreAddition = this.currentPlayer == 'X' ? 1 : -1;

        // row score
        this.score[y] += scoreAddition;
            
        // col score
        this.score[3+x] += scoreAddition;

        // first diagonal coordinates follow the pattern of 0,0 1,1, 2,2, so if
        // x == y then the coordinate is on the first diagonal
        this.score[6] += x == y ? scoreAddition : 0;
        // second diagonal coordinates follow a pattern of 2,0 1,1 0,2, which
        // each add up to 2
        this.score[7] += (x + y == 2) ? scoreAddition : 0; 

        this.totalMoves++;
    }
    
    /**
     * Prompts for player's turn if status is CONTINUE, prints winner if it is
     * WIN, or informs the player of a draw if it is DRAW.
     */
    private void printStatus() {
        if (this.gameStatus() == gameStatus().CONTINUE) {
            Scanner in = new Scanner(System.in);
            String row = "", col = "";

            System.out.printf("Player %c's turn%n%n", this.currentPlayer);      
            while (!this.validMove(row, col)) {
                System.out.printf("Player %c: Enter row (0, 1, or 2): ", this.currentPlayer);
                row = in.nextLine();
                System.out.printf("Player %c: Enter column (0, 1, or 2): ", this.currentPlayer);
                col = in.nextLine();
            }

            this.markTile(Integer.parseInt(col), Integer.parseInt(row));

            // toggle player
            this.currentPlayer = this.currentPlayer == 'X' ? 'O' : 'X';
        } else if (this.gameStatus() == gameStatus().WIN) {
            System.out.printf("Player %c wins!%n", this.currentPlayer);
        } else {
            System.out.printf("Draw!%n");
        }
    }
}
