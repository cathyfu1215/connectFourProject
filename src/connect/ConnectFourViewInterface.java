package connect;

import java.io.IOException;

/**
 * Represents a View for Connect Four: display the game state and messages to the user.
 */
public interface ConnectFourViewInterface {
  /**
   * Displays the current state of the game board.
   *
   * @param gameState the current state of the game board
   */
  public void displayGameState(String gameState) throws IOException;

  /**
   * Displays the player whose turn it is to make a move.
   *
   * @param player the player whose turn it is
   */
  public void displayPlayerTurn(String player) throws IOException;

  /**
   * Displays an error message for an invalid move.
   *
   * @param invalidInput the invalid input that caused the error
   */
  public void displayInvalidNumber(String invalidInput) throws IOException;

  /**
   * Displays an error message when there is an illegal argument.
   *
   * @param message the error message
   */
  public void displayErrorMessage(String message) throws IOException;

  /**
   * Displays the game state when the player quits.
   *
   * @param gameState the game state when the player quits
   */
  public void displayGameQuit(String gameState) throws IOException;

  /**
   * Displays the game over message, including the winner (if there is one).
   *
   * @param winner the winner of the game, or {@code null} if there is no winner
   */
  public void displayGameOver(String winner) throws IOException;

  /**
   * Asks the player if they want to play again.
   */
  public void askPlayAgain() throws IOException;

  /**
   * Adds the features to the view.
   *
   * @param features the features to add
   */
  void addFeatures(Features features);

  /**
   * Updates the color of a button on the game board.
   *
   * @param i     the row of the button
   * @param j     the column of the button
   * @param color the color to update the button to
   */
  void updateButtonColor(int i, int j, String color);

  /**
   * Empties the game board when the restart button is clicked.
   */
  void emptyBoard();

  /**
   * starts the game.
   */
  void startGame();

  /**
   * Resets the labels when the game is restarted.
   */
  void resetLabel();
}