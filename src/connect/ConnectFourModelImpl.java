package connect;

import java.util.Arrays;

/**
 * This class represents the model of the Connect Four game.
 */
public class ConnectFourModelImpl implements ConnectFourModel {

  private final int rows;
  private final int columns;
  private Player[][] board;
  private Player turn; // decides who is the next to play
  private Player lastTurn = null;
  // will be used to decide if one player has won right after the last move
  private Player winner = null;

  /**
   * This is a constructor of the class ConnectFourImpl, which initializes
   * the game board with a specified number of rows and columns.
   *
   * @param rows    the number of rows in the game board
   *                (must be at least 4)
   * @param columns the number of columns in the game board
   *                (must be at least 4)
   * @throws IllegalArgumentException if the number of rows or columns is less than 4
   */

  public ConnectFourModelImpl(int rows, int columns) throws IllegalArgumentException {
    if (rows < 4 || columns < 4) {
      throw new IllegalArgumentException("The number of rows and columns must be at least 4");
    }
    // Initialize the game board with the specified number of rows and columns
    this.rows = rows;
    this.columns = columns;
    this.turn = Player.RED; // we assume red goes first,
    // according to the canvas instructions
    this.board = new Player[rows][columns];
    //we have to call initializeBoard() to fill the board with empty cells
    // for the GUI view
    initializeBoard();
  }


  /**
   * Initializes the game board with a specified number of rows and columns.
   * Each cell in the board is set to a default state (e.g., empty).
   */
  @Override
  public void initializeBoard() {
    //create a 2D array to represent the game board, filling each cell with the empty state (null)
    // We assume the upper left corner is (0,0) and the bottom right corner is (rows-1, columns-1)
    this.board = new Player[rows][columns];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        this.board[i][j] = null;
      }
    }
  }

  /**
   * Switches the turn to the other player while keep track who made the last move.
   */

  private void switchTurn() {
    if (this.turn == Player.RED) {
      this.turn = Player.YELLOW;
      this.lastTurn = Player.RED;
    } else {
      this.turn = Player.RED;
      this.lastTurn = Player.YELLOW;
    }
  }


  /**
   * Attempts to place a disc in the specified column.
   * The disc will occupy the lowest available row within the column.
   * If the column is full, the move will be rejected.
   *
   * @param column the column in which to place the disc
   * @throws IllegalArgumentException if the column is out of bounds, or the column is full
   */
  @Override
  public void makeMove(int column) throws IllegalArgumentException {
    if (column < 0 || column >= this.columns) {
      throw new IllegalArgumentException("The column is out of bounds");
    }
    if (this.board[0][column] != null) {
      throw new IllegalArgumentException("The column is full");
    }
    for (int i = this.rows - 1; i >= 0; i--) {
      if (this.board[i][column] == null) {
        this.board[i][column] = getTurn();
        break;
      }
    }
    switchTurn(); //after placing the piece, switch the turn to the other player

  }

  /**
   * Retrieves the player whose turn it is to make a move.
   *
   * @return the player whose turn it is
   */
  @Override
  public Player getTurn() {
    //if game is over, return null
    if (isGameOver()) {
      return null;
    }
    return this.turn;
  }


  /**
   * Checks if the board is full.
   *
   * @return true if the board is full, false otherwise
   */
  private boolean isFull() {
    for (int i = 0; i < this.columns; i++) {
      if (this.board[0][i] == null) {
        return false;
      }
    }
    return true;
  }


  /**
   * Checks if the game is over. The game is over when either the board is full, or
   * one player has won.
   *
   * @return true if the game is over, false otherwise
   */
  @Override
  public boolean isGameOver() {
    return getWinner() != null || isFull();
  }

  /**
   * Retrieves the winner of the game, or {@code null} if there is no winner. If the game is not
   * over, returns {@code null}.
   *
   * @return the winner, or null if there is no winner
   */
  @Override
  public Player getWinner() {
    //check for horizontal win for the current player who just made a move
    // (placed a piece on the board )
    for (int i = 0; i < this.rows; i++) {
      for (int j = 0; j < this.columns - 3; j++) {
        if (this.board[i][j] == this.lastTurn
            && this.board[i][j + 1] == this.lastTurn
            && this.board[i][j + 2] == this.lastTurn
            && this.board[i][j + 3] == this.lastTurn) {
          this.winner = this.lastTurn;
          return this.winner;
        }
      }
    }
    // check for vertical win for the current player
    for (int i = 0; i < this.rows - 3; i++) {
      for (int j = 0; j < this.columns; j++) {
        if (this.board[i][j] == this.lastTurn
            && this.board[i + 1][j] == this.lastTurn
            && this.board[i + 2][j] == this.lastTurn
            && this.board[i + 3][j] == this.lastTurn) {
          this.winner = this.lastTurn;
          return this.winner;
        }
      }
    }

    // check for diagonal win for the current player (from top left to bottom right)
    for (int i = 0; i < this.rows - 3; i++) {
      for (int j = 0; j < this.columns - 3; j++) {
        if (this.board[i][j] == this.lastTurn
            && this.board[i + 1][j + 1] == this.lastTurn
            && this.board[i + 2][j + 2] == this.lastTurn
            && this.board[i + 3][j + 3] == this.lastTurn) {
          this.winner = this.lastTurn;
          return this.winner;
        }
      }
    }

    // check for diagonal win for the current player (from top right to bottom left)
    for (int i = 0; i < this.rows - 3; i++) {
      for (int j = 3; j < this.columns; j++) {
        if (this.board[i][j] == this.lastTurn
            && this.board[i + 1][j - 1] == this.lastTurn
            && this.board[i + 2][j - 2] == this.lastTurn
            && this.board[i + 3][j - 3] == this.lastTurn) {
          this.winner = this.lastTurn;
          return this.winner;
        }
      }
    }
    return this.winner;
  }

  /**
   * Resets the game board to its initial state, clearing all cells.
   */
  @Override
  public void resetBoard() {
    // keep the number of rows and columns
    //clear the board by setting every cell to null
    //keep the first player as red

    for (int i = 0; i < this.rows; i++) {
      for (int j = 0; j < this.columns; j++) {
        this.board[i][j] = null;
      }


    }
    this.turn = Player.RED; // reset the first player to red
    this.lastTurn = null; // reset the last turn to null
    this.winner = null; // reset the winner to null

  }

  /**
   * Retrieves the current state of the game board. This method is useful
   * for the View component to display the current game status.
   *
   * @return a 2D array representing the current state of the board
   */
  @Override
  public Player[][] getBoardState() {
    //return a copy of the 2D array representing the current state of the board
    //to avoid modifying the original array
    Player[][] copy = new Player[this.rows][this.columns];
    for (int i = 0; i < this.rows; i++) {
      System.arraycopy(this.board[i], 0, copy[i], 0, this.columns);
    }
    return copy;
  }

  /**
   * Returns a string representation of the game board. This method is useful
   * for debugging purposes.
   *
   * @return a string representation of the game board
   */

  public String toString() {
    // print the board
    StringBuilder string = new StringBuilder();
    //print every item in getBoardState, add a new line after each row
    Player[][] array = getBoardState();
    for (Player[] players : array) {
      for (int j = 0; j < players.length; j++) {
        string.append(players[j]);
        if (j < players.length - 1) {
          //separate every element with a comma
          string.append(", ");
        }
      }
      string.append("\n");
    }
    return string.toString();
  }
}