package connect;

import java.io.IOException;
import java.util.Arrays;


/**
 * Represents a controller for the Connect Four game.
 */

public class SwingConnectFourController implements Features, ConnectFourController {


  private final ConnectFourModel model;
  private final ConnectFourViewInterface view;


  /**
   * Constructor for the SwingConnectFourController class.
   *
   * @param model the Connect Four model
   * @param view  the Connect Four view
   */
  public SwingConnectFourController(ConnectFourModel model, ConnectFourViewInterface view) {

    this.model = model;
    this.view = view;
    this.view.addFeatures(this);
  }


  /**
   * Execute a single game of Connect Four given a Connect Four Model. When the game
   * is over, the playGame method ends.
   *
   * @throws IllegalArgumentException if the model is null
   */
  @Override
  public void playGame() throws IOException {
    this.view.startGame();

  }

  /**
   * A method to display the current game state.
   *
   * @param gameState the current game state.
   */
  @Override
  public void displayGameState(String gameState) {
    //parse the string to 6* 7 grid
    // Remove outer brackets and split by '], [' to get individual rows
    String[] rowStrings = gameState.substring(1, gameState.length() - 1).split("], \\[");

    // Initialize a 6x7 matrix
    String[][] matrix = new String[6][7];

    for (int i = 0; i < rowStrings.length; i++) {
      String[] elements = rowStrings[i].split(", ");
      for (int j = 0; j < 7; j++) {
        // Remove square brackets and trim whitespace
        matrix[i][j] = elements[j].replaceAll("[\\[\\]]", "").trim();
      }
    }

    for (int i = 0; i < 6; i++) {
      for (int j = 0; j < 7; j++) {
        //set the buttons to the right color
        this.view.updateButtonColor(i, j, matrix[i][j]);
      }

    }


  }

  /**
   * A method to display the player whose turn it is.
   *
   * @param player the player whose turn it is.
   */
  @Override
  public void displayPlayerTurn(String player) {
    //this method is not used here

  }

  /**
   * A method to display an error message.
   *
   * @param message the error message to display
   */
  @Override
  public void displayErrorMessage(String message) {
    //this method is not used here

  }


  /**
   * A method that exits the game.
   */
  @Override
  public void exitGame() {
    //close this window and exit
    System.exit(0);
  }

  /**
   * A method to make a move to a specified column.
   *
   * @param column the column to make a move to
   */
  @Override
  public void makeMove(int column) throws IOException {
    try {
      this.model.makeMove(column);
      displayGameState(Arrays.deepToString(this.model.getBoardState()));
      if (this.model.isGameOver() && this.model.getWinner() != null) {
        this.view.displayGameOver(this.model.getWinner().toString());
        this.view.askPlayAgain();
      }
      if (this.model.isGameOver() && this.model.getWinner() == null) {
        this.view.displayGameOver("It's a tie!");
        this.view.askPlayAgain();
      }
      if (this.model.getTurn() != null) {
        this.view.displayPlayerTurn(this.model.getTurn().toString());
      }
    } catch (IllegalArgumentException | IOException e) {
      this.view.displayErrorMessage(e.getMessage());
    }


  }

  /**
   * A method to restart the game.It will empty the board
   * and set the current player to player RED.
   */
  @Override
  public void restartGame() throws IOException {
    this.model.resetBoard();
    this.view.emptyBoard();
    this.view.resetLabel();
  }
}
