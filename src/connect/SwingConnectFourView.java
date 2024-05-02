package connect;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.Objects;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Represents the view for the Connect Four game.
 */
public class SwingConnectFourView extends JFrame implements ConnectFourViewInterface {


  private final JButton restartButton;
  private final JButton exitButton;
  private final JButton[][] buttons;
  private final JLabel hintLabel;

  private final JLabel instructionLabel;


  /**
   * Constructor for the SwingConnectFourView class.
   *
   * @param title the title of the game
   */
  public SwingConnectFourView(String title) throws IOException {
    super(title);


    setSize(600, 600);
    setLocation(100, 100);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

    instructionLabel = new JLabel("Click a row when it is your turn.");
    hintLabel = new JLabel("Player RED's turn");

    // Create two JPanels with FlowLayout for the labels
    JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));

    panel1.add(instructionLabel);
    panel2.add(hintLabel);

    panel.add(panel1);
    panel.add(panel2);

    //create 6*7 gird for the game board, each cell is a button
    JPanel gameBoard = new JPanel();
    gameBoard.setLayout(new GridLayout(6, 7));

    // Create an array of JButtons
    buttons = new JButton[6][7];

    for (int row = 0; row < 6; row++) {
      for (int col = 0; col < 7; col++) {
        buttons[row][col] = new JButton("◉");
        buttons[row][col].putClientProperty("col", col); // Set column data
        buttons[row][col].putClientProperty("row", row); // Set row data
        buttons[row][col].setActionCommand("Connect Four Button");
        gameBoard.add(buttons[row][col]);
      }
    }


    panel.add(gameBoard);

    // add the restart button and exit button
    restartButton = new JButton("Restart"); // NOTE: No action listener
    restartButton.setActionCommand("Restart Button");
    JPanel twoButtonPanel = new JPanel();
    twoButtonPanel.add(restartButton);

    exitButton = new JButton("Exit");
    exitButton.setActionCommand("Exit Button");
    twoButtonPanel.add(exitButton);

    panel.add(twoButtonPanel);
    this.add(panel);
    pack();
    setVisible(true);

  }


  /**
   * Adds features to the view.
   *
   * @param features the features to add
   */
  public void addFeatures(Features features) {
    for (int row = 0; row < 6; row++) {
      for (int col = 0; col < 7; col++) {
        buttons[row][col].addActionListener(e -> {
          JButton button = (JButton) e.getSource();
          int column = (int) button.getClientProperty("col");
          try {
            features.makeMove(column);
          } catch (IOException ex) {
            throw new RuntimeException(ex);
          }
        });
      }
    }

    exitButton.addActionListener(e -> {
      features.exitGame();
    });
    restartButton.addActionListener(e -> {
      try {
        features.restartGame();
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
    });
  }

  /**
   * Updates the color of a button on the game board.
   *
   * @param i     the row of the button
   * @param j     the column of the button
   * @param color the color to update the button to
   */
  @Override
  public void updateButtonColor(int i, int j, String color) {
    if ("RED".equals(color)) {
      buttons[i][j].setForeground(java.awt.Color.RED);
      revalidate();
      repaint();
    } else {
      if ("YELLOW".equals(color)) {
        buttons[i][j].setForeground(java.awt.Color.YELLOW);
        revalidate();
        repaint();
      } else {
        buttons[i][j].setForeground(java.awt.Color.WHITE);
        revalidate();
        repaint();
      }
    }
  }

  /**
   * Empties the game board when the restart button is clicked.
   */
  @Override
  public void emptyBoard() {
    for (int i = 0; i < 6; i++) {
      for (int j = 0; j < 7; j++) {
        buttons[i][j].setForeground(java.awt.Color.WHITE);
        revalidate();
        repaint();
      }
    }
  }

  /**
   * starts the game.
   */
  @Override
  public void startGame() {
    for (int i = 0; i < 6; i++) {
      for (int j = 0; j < 7; j++) {
        buttons[i][j].setForeground(java.awt.Color.WHITE);
        revalidate();
        repaint();
      }
    }

  }

  /**
   * Resets the labels when the game is restarted.
   */
  @Override
  public void resetLabel() {
    hintLabel.setText("Player RED's turn");
    instructionLabel.setText("Click a row when it is your turn.");
    revalidate();
    repaint();
  }

  /**
   * Displays the current state of the game board.
   *
   * @param gameState the current state of the game board
   */
  @Override
  public void displayGameState(String gameState) throws IOException {
    //this is not used here

  }

  /**
   * Displays the player whose turn it is to make a move.
   *
   * @param player the player whose turn it is
   */
  @Override
  public void displayPlayerTurn(String player) throws IOException {
    this.hintLabel.setText("Player " + player + "'s turn");
    revalidate();
    repaint();

  }

  /**
   * Displays an error message for an invalid move.
   *
   * @param invalidInput the invalid input that caused the error
   */
  @Override
  public void displayInvalidNumber(String invalidInput) throws IOException {
    //this function will never be called
  }

  /**
   * Displays an error message when there is an illegal argument.
   *
   * @param message the error message
   */
  @Override
  public void displayErrorMessage(String message) throws IOException {
    hintLabel.setText(message);
    revalidate();
    repaint();

  }

  /**
   * Displays the game state when the player quits.
   *
   * @param gameState the game state when the player quits
   */
  @Override
  public void displayGameQuit(String gameState) throws IOException {
    hintLabel.setText("Game Quit! ");
    revalidate();
    repaint();

  }

  /**
   * Displays the game over message, including the winner (if there is one).
   *
   * @param winner the winner of the game, or {@code null} if there is no winner
   */
  @Override
  public void displayGameOver(String winner) throws IOException {
    if (Objects.equals(winner, "It's a tie!")) {
      hintLabel.setText("Game Over! It's a tie!");
      revalidate();
      repaint();

    } else {
      hintLabel.setText("Game Over! Winner is " + winner);
      revalidate();
      repaint();
    }
  }

  /**
   * Asks the player if they want to play again.
   */
  @Override
  public void askPlayAgain() throws IOException {
    instructionLabel.setText("Do you want to play again? Click Restart to play again.");
    revalidate();
    repaint();
  }
}
