package connect;

import java.io.IOException;

/**
 * Interface for the Features component in the MVC architecture of a Connect Four game.
 * The controller will implement this interface to interact with the view.
 */


// TODO: modify this later
public interface Features {

  /**
   * A method to display the current game state.
   * @param gameState the current game state.
   */
  void displayGameState(String gameState);

  /**
   * A method to display the player whose turn it is.
   * @param player the player whose turn it is.
   */

  void displayPlayerTurn(String player);

  /**
   * A method to display an error message.
   * @param message the error message to display
   */
  void displayErrorMessage(String message);


  /**
   * A method that exits the game.
   */
  void exitGame();

  /**
   * A method to make a move to a specified column.
   * @param column the column to make a move to
   */
  void makeMove(int column) throws IOException;


  /**
   * A method to restart the game.It will empty the board
   * and set the current player to player RED.
   */
  void restartGame() throws IOException;
}
