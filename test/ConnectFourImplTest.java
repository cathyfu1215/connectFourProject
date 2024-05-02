import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import connect.ConnectFourImpl;
import connect.Player;
import org.junit.Before;
import org.junit.Test;

/**
 * A JUnit test class for the ConnectFourImpl class.
 */
public class ConnectFourImplTest {

  private ConnectFourImpl fiveByFive;
  private ConnectFourImpl tenByTen;
  private ConnectFourImpl eightByNine;


  /**
   * Set up the test fixture with three different ConnectFourImpl objects.
   */
  @Before
  public void setUp() {
    this.fiveByFive = new ConnectFourImpl(5, 5); // a small square board
    this.tenByTen = new ConnectFourImpl(10, 10); // a big square board
    this.eightByNine = new ConnectFourImpl(8, 9); // a rectangular board
  }

  /**
   * Test that an exception is thrown when the number of rows is less than 4.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorRowsLessThanFour() {
    ConnectFourImpl invalid = new ConnectFourImpl(3, 5);
  }

  /**
   * Test that an exception is thrown when the number of columns is less than 4.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorColumnsLessThanFour() {
    ConnectFourImpl invalid = new ConnectFourImpl(13, 3);
  }

  /**
   * Check that the game board is initialized with the correct number of rows and columns,
   * with all cells empty.
   */
  @Test
  public void initializeBoard() {
    this.fiveByFive.initializeBoard();
    this.tenByTen.initializeBoard();
    this.eightByNine.initializeBoard();
    // check that the board is initialized with the correct number of rows and columns
    assertEquals(5, this.fiveByFive.getBoardState().length);
    assertEquals(5, this.fiveByFive.getBoardState()[0].length);
    assertEquals(10, this.tenByTen.getBoardState().length);
    assertEquals(10, this.tenByTen.getBoardState()[0].length);
    assertEquals(8, this.eightByNine.getBoardState().length);
    assertEquals(9, this.eightByNine.getBoardState()[0].length);
    // check that the board is initialized with all cells empty, with a null inside
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        assertNull(this.fiveByFive.getBoardState()[i][j]);
      }
    }
    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 10; j++) {
        assertNull(this.tenByTen.getBoardState()[i][j]);
      }
    }
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 9; j++) {
        assertNull(this.eightByNine.getBoardState()[i][j]);
      }
    }
  }

  /**
   * Test that a move is made to valid columns.
   */
  @Test
  public void makeMove() {
    //since the game will keep track of the correct player to make the move,
    // there is no need to test if the player is correct

    this.fiveByFive.initializeBoard();
    this.fiveByFive.makeMove(1); //red

    //check if the turn is switched after a move is made
    assertEquals(Player.YELLOW, this.fiveByFive.getTurn());
    this.fiveByFive.makeMove(1); // yellow

    //check if the turn is switched after a move is made
    assertEquals(Player.RED, this.fiveByFive.getTurn());

    //check if the board is updated correctly after the moves
    Player[][] board = new Player[5][5];
    board[4][1] = Player.RED;
    board[3][1] = Player.YELLOW;
    assertEquals(board, this.fiveByFive.getBoardState());


    this.eightByNine.initializeBoard();
    this.eightByNine.makeMove(0); //red
    this.eightByNine.makeMove(0); //yellow
    this.eightByNine.makeMove(1); //red
    this.eightByNine.makeMove(1); //yellow
    for (int i = 0; i < 8; i++) {
      this.eightByNine.makeMove(8);
    }
    //check if the board is updated correctly after the moves
    Player[][] board2 = new Player[8][9];
    board2[7][0] = Player.RED;
    board2[6][0] = Player.YELLOW;
    board2[7][1] = Player.RED;
    board2[6][1] = Player.YELLOW;
    for (int i = 0; i < 8; i = i + 2) {
      board2[7 - i][8] = Player.RED;
    }
    for (int i = 1; i < 8; i = i + 2) {
      board2[7 - i][8] = Player.YELLOW;
    }
    assertEquals(board2, this.eightByNine.getBoardState());
  }

  /**
   * Test that an exception is thrown when a move is made to an invalid column(out of bound).
   */
  @Test(expected = IllegalArgumentException.class)
  public void makeMoveInvalidColumn() {
    this.fiveByFive.initializeBoard();
    this.fiveByFive.makeMove(15);
  }

  /**
   * Test that an exception is thrown when a move is made to an invalid column(negative value).
   */
  @Test(expected = IllegalArgumentException.class)
  public void makeMoveNegativeColumn() {
    this.fiveByFive.initializeBoard();
    this.fiveByFive.makeMove(-3);
  }

  /**
   * Test that an exception is thrown when a move is made to a full column.
   */
  @Test(expected = IllegalArgumentException.class)
  public void makeMoveFullColumn() {
    this.fiveByFive.initializeBoard();
    for (int i = 0; i < 5; i++) {
      this.fiveByFive.makeMove(1);
    }
    //so far this column is full
    this.fiveByFive.makeMove(1);
  }

  /**
   * Test that the turn is switched after a move is made and returned by calling getTurn.
   */
  @Test
  public void getTurn() {
    this.fiveByFive.initializeBoard();
    assertEquals(Player.RED, this.fiveByFive.getTurn());
    this.fiveByFive.makeMove(3);
    assertEquals(Player.YELLOW, this.fiveByFive.getTurn());
    this.fiveByFive.makeMove(3);
    assertEquals(Player.RED, this.fiveByFive.getTurn());
    this.fiveByFive.makeMove(2);
    assertEquals(Player.YELLOW, this.fiveByFive.getTurn());

    //reset board, check if the turn is reset to red
    this.fiveByFive.resetBoard();
    assertEquals(Player.RED, this.fiveByFive.getTurn());


  }

  /**
   * Test that the getTurn method returns null when the game is over.
   */
  @Test
  public void getTurnWhenGameOver() {
    this.fiveByFive.initializeBoard();
    //if game is over, getTurn should return null

    //create a not full board with a winner (red, horizontal win)

    this.fiveByFive.makeMove(0); //red
    this.fiveByFive.makeMove(0); //yellow
    this.fiveByFive.makeMove(1); //red
    this.fiveByFive.makeMove(1); //yellow
    this.fiveByFive.makeMove(2); //red
    this.fiveByFive.makeMove(2); //yellow
    this.fiveByFive.makeMove(3); //red

    assertEquals(Player.RED, this.fiveByFive.getWinner());
    assertNull(this.fiveByFive.getTurn());


  }

  /**
   * Test that the game is not over when the board is initialized.
   */
  @Test
  public void isGameOverWhenBoardIsInitialized() {
    this.fiveByFive.initializeBoard();
    assertFalse(this.fiveByFive.isGameOver());
  }

  /**
   * Test that the game is over when the board is full.
   */
  @Test
  public void isGameOverWhenBoardIsFull() {

    //create a new board and check it is not game over
    this.eightByNine.initializeBoard();
    assertFalse(this.eightByNine.isGameOver());

    //create a full board with no winner (no more moves can be made)
    this.fiveByFive.initializeBoard();
    this.fiveByFive.makeMove(2); //red 1
    this.fiveByFive.makeMove(1); //yellow 1

    this.fiveByFive.makeMove(3); //red 2
    this.fiveByFive.makeMove(2); //yellow 2

    this.fiveByFive.makeMove(1); //red 3
    this.fiveByFive.makeMove(3); //yellow 3

    this.fiveByFive.makeMove(3); //red 4
    this.fiveByFive.makeMove(0); //yellow 4

    this.fiveByFive.makeMove(4); //red 5
    this.fiveByFive.makeMove(0); //yellow 5

    this.fiveByFive.makeMove(0); //red 6
    this.fiveByFive.makeMove(4); //yellow 6

    this.fiveByFive.makeMove(1); //red 7
    this.fiveByFive.makeMove(2); //yellow 7

    this.fiveByFive.makeMove(2); //red 8
    this.fiveByFive.makeMove(1); //yellow 8

    this.fiveByFive.makeMove(4); //red 9
    this.fiveByFive.makeMove(0); //yellow 9

    this.fiveByFive.makeMove(0); //red 10
    this.fiveByFive.makeMove(3); //yellow 10

    this.fiveByFive.makeMove(4); // red 11
    this.fiveByFive.makeMove(1); // yellow 11


    this.fiveByFive.makeMove(2); // red 12
    this.fiveByFive.makeMove(3); // yellow 12

    this.fiveByFive.makeMove(4); // red 13

    //check if there is no winner
    assertNull(this.fiveByFive.getWinner());

    //check if the board is full, hence the game is over
    assertTrue(this.fiveByFive.isGameOver());


  }

  /**
   * Test that the game is over when red wins horizontally.
   */
  @Test
  public void isGameOverWhenRedWinsHorizontally() {
    //create a not full board with a winner (red, horizontal win)
    this.fiveByFive.initializeBoard();
    Player[][] emptyBoard = new Player[5][5];
    assertEquals(emptyBoard, this.fiveByFive.getBoardState());

    this.fiveByFive.makeMove(0); //red
    this.fiveByFive.makeMove(0); //yellow
    this.fiveByFive.makeMove(1); //red
    this.fiveByFive.makeMove(1); //yellow
    this.fiveByFive.makeMove(2); //red
    this.fiveByFive.makeMove(2); //yellow
    this.fiveByFive.makeMove(3); //red

    //since isFull is not a public method, we cannot check if the board is full
    //print the current board, so we can see it is not full
    Player[][] board = new Player[5][5];
    board[4][0] = Player.RED;
    board[4][1] = Player.RED;
    board[4][2] = Player.RED;
    board[4][3] = Player.RED;
    board[3][0] = Player.YELLOW;
    board[3][1] = Player.YELLOW;
    board[3][2] = Player.YELLOW;
    assertEquals(board, this.fiveByFive.getBoardState());


    //check that the game is over (red wins the game)
    assertTrue(this.fiveByFive.isGameOver());
    assertEquals(Player.RED, this.fiveByFive.getWinner());

  }

  /**
   * Test that the game is over when yellow wins horizontally.
   */

  @Test
  public void isGameOverWhenYellowWinsHorizontally() {
    //create a not full board with a winner (yellow, horizontal win)
    this.fiveByFive.initializeBoard();
    Player[][] emptyBoard2 = new Player[5][5];
    assertEquals(emptyBoard2, this.fiveByFive.getBoardState());

    this.fiveByFive.makeMove(0); //red
    this.fiveByFive.makeMove(0); //yellow
    this.fiveByFive.makeMove(1); //red
    this.fiveByFive.makeMove(1); //yellow
    this.fiveByFive.makeMove(2); //red
    this.fiveByFive.makeMove(2); //yellow
    this.fiveByFive.makeMove(4); //red
    this.fiveByFive.makeMove(3); //yellow
    this.fiveByFive.makeMove(4); //red
    this.fiveByFive.makeMove(3); //yellow


    //check that the game is over (yellow wins the game)
    assertTrue(this.fiveByFive.isGameOver());
    assertEquals(Player.YELLOW, this.fiveByFive.getWinner());

  }

  /**
   * Test that the game is over when red wins vertically.
   */
  @Test
  public void isGameOverWhenRedWinsVertically() {
    //create a not full board with a winner (red, vertical win)
    this.eightByNine.initializeBoard();
    Player[][] emptyBoard = new Player[8][9];
    assertEquals(emptyBoard, this.eightByNine.getBoardState());

    this.eightByNine.makeMove(0); //red 1
    this.eightByNine.makeMove(1); //yellow 1
    this.eightByNine.makeMove(0); //red 2
    this.eightByNine.makeMove(1); //yellow 2
    this.eightByNine.makeMove(0); //red 3
    this.eightByNine.makeMove(1); //yellow 3
    this.eightByNine.makeMove(0); //red 4

    //check that the game is over (red wins the game)
    assertTrue(this.eightByNine.isGameOver());
    assertEquals(Player.RED, this.eightByNine.getWinner());

  }

  /**
   * Test that the game is over when yellow wins vertically.
   */
  @Test
  public void isGameOverWhenYellowWinsVertically() {
    //create a not full board with a winner (yellow, vertical win)
    this.eightByNine.initializeBoard();
    Player[][] emptyBoard = new Player[8][9];
    assertEquals(emptyBoard, this.eightByNine.getBoardState());

    this.eightByNine.makeMove(0); //red 1
    this.eightByNine.makeMove(1); //yellow 1
    this.eightByNine.makeMove(0); //red 2
    this.eightByNine.makeMove(1); //yellow 2
    this.eightByNine.makeMove(0); //red 3
    this.eightByNine.makeMove(1); //yellow 3
    this.eightByNine.makeMove(2); //red 4
    this.eightByNine.makeMove(1); //yellow 4

    //check that the game is over (yellow wins the game)
    assertTrue(this.eightByNine.isGameOver());
    assertEquals(Player.YELLOW, this.eightByNine.getWinner());
  }


  /**
   * Test game is over when yellow wins diagonally, from top left to bottom right.
   */
  @Test
  public void isGameOverWhenYellowWinsDiagonallyFromTopLeft() {
    //create a not full board with a winner (yellow, diagonal win)
    this.tenByTen.initializeBoard();
    Player[][] emptyBoard = new Player[10][10];
    assertEquals(emptyBoard, this.tenByTen.getBoardState());

    this.tenByTen.makeMove(5); //red 1
    this.tenByTen.makeMove(4); //yellow 1
    this.tenByTen.makeMove(4); //red 2
    this.tenByTen.makeMove(5); //yellow 2
    this.tenByTen.makeMove(3); //red 3
    this.tenByTen.makeMove(6); //yellow 3
    this.tenByTen.makeMove(5); //red 4
    this.tenByTen.makeMove(4); //yellow 4
    this.tenByTen.makeMove(6); //red 5
    this.tenByTen.makeMove(7); //yellow 5
    this.tenByTen.makeMove(3); //red 6
    this.tenByTen.makeMove(3); //yellow 6
    this.tenByTen.makeMove(6); //red 7
    this.tenByTen.makeMove(3); //yellow 7

    //check that the game is over (yellow wins the game)
    assertTrue(this.tenByTen.isGameOver());
    assertEquals(Player.YELLOW, this.tenByTen.getWinner());

  }

  /**
   * Test game is over when red wins diagonally, from top left to bottom right.
   */
  @Test
  public void isGameOverWhenRedWinsDiagonallyFromTopLeft() {
    //create a not full board with a winner (red, diagonal win)
    this.tenByTen.initializeBoard();
    Player[][] emptyBoard = new Player[10][10];
    assertEquals(emptyBoard, this.tenByTen.getBoardState());

    this.tenByTen.makeMove(9); //red 1
    this.tenByTen.makeMove(8); //yellow 1
    this.tenByTen.makeMove(8); //red 2
    this.tenByTen.makeMove(7); //yellow 2
    this.tenByTen.makeMove(6); //red 3
    this.tenByTen.makeMove(7); //yellow 3
    this.tenByTen.makeMove(6); //red 4
    this.tenByTen.makeMove(6); //yellow 4
    this.tenByTen.makeMove(6); //red 5
    this.tenByTen.makeMove(5); //yellow 5
    this.tenByTen.makeMove(7); //red 6

    assertTrue(this.tenByTen.isGameOver());
    assertEquals(Player.RED, this.tenByTen.getWinner());
  }

  /**
   * Test game is over when yellow wins diagonally, from top right to bottom left.
   */
  @Test
  public void isGameOverWhenYellowWinsDiagonallyFromTopRight() {
    //create a not full board with a winner (yellow, diagonal win)
    this.tenByTen.initializeBoard();
    Player[][] emptyBoard = new Player[10][10];
    assertEquals(emptyBoard, this.tenByTen.getBoardState());

    this.tenByTen.makeMove(1); //red 1
    this.tenByTen.makeMove(0); //yellow 1
    this.tenByTen.makeMove(2); //red 2
    this.tenByTen.makeMove(1); //yellow 2
    this.tenByTen.makeMove(2); //red 3
    this.tenByTen.makeMove(2); //yellow 3
    this.tenByTen.makeMove(3); //red 4
    this.tenByTen.makeMove(4); //yellow 4
    this.tenByTen.makeMove(3); //red 5
    this.tenByTen.makeMove(3); //yellow 5
    this.tenByTen.makeMove(4); //red 6
    this.tenByTen.makeMove(3); //yellow 6

    //check that the game is over (yellow wins the game)
    assertTrue(this.tenByTen.isGameOver());
    assertEquals(Player.YELLOW, this.tenByTen.getWinner());
  }

  /**
   * Test game is over when red wins diagonally, from top right to bottom left.
   */
  @Test
  public void isGameOverWhenRedWinsDiagonallyFromTopRight() {
    //create a not full board with a winner (red, diagonal win)
    this.tenByTen.initializeBoard();
    Player[][] emptyBoard = new Player[10][10];
    assertEquals(emptyBoard, this.tenByTen.getBoardState());

    this.tenByTen.makeMove(6); //red 1
    this.tenByTen.makeMove(7); //yellow 1
    this.tenByTen.makeMove(7); //red 2
    this.tenByTen.makeMove(8); //yellow 2
    this.tenByTen.makeMove(9); //red 3
    this.tenByTen.makeMove(8); //yellow 3
    this.tenByTen.makeMove(8); //red 4
    this.tenByTen.makeMove(9); //yellow 4
    this.tenByTen.makeMove(9); //red 5
    this.tenByTen.makeMove(5); //yellow 5
    this.tenByTen.makeMove(9); //red 6

    //check that the game is over (red wins the game)
    assertTrue(this.tenByTen.isGameOver());
    assertEquals(Player.RED, this.tenByTen.getWinner());
  }


  /**
   * Test that the winner is returned correctly.
   */
  @Test
  public void getWinner() {

    this.fiveByFive.initializeBoard();
    //test at the beginning of the game, there is no winner
    assertNull(this.fiveByFive.getWinner());


    //create a small and not full board with a winner (red, horizontal win)
    this.fiveByFive.makeMove(0); //red
    this.fiveByFive.makeMove(0); //yellow
    this.fiveByFive.makeMove(1); //red
    this.fiveByFive.makeMove(1); //yellow
    this.fiveByFive.makeMove(2); //red
    this.fiveByFive.makeMove(2); //yellow
    this.fiveByFive.makeMove(3); //red

    assertEquals(Player.RED, this.fiveByFive.getWinner());

    //create a larger board with a red winner (vertical win)

    this.eightByNine.initializeBoard();
    this.eightByNine.makeMove(0); //red 1
    this.eightByNine.makeMove(1); //yellow 1
    this.eightByNine.makeMove(0); //red 2
    this.eightByNine.makeMove(1); //yellow 2
    this.eightByNine.makeMove(0); //red 3
    this.eightByNine.makeMove(1); //yellow 3
    this.eightByNine.makeMove(0); //red 4
    assertEquals(Player.RED, this.eightByNine.getWinner());


    //create a larger board with a yellow winner(diagonal win)
    this.tenByTen.initializeBoard();
    this.tenByTen.makeMove(5); //red 1
    this.tenByTen.makeMove(4); //yellow 1
    this.tenByTen.makeMove(4); //red 2
    this.tenByTen.makeMove(5); //yellow 2
    this.tenByTen.makeMove(3); //red 3
    this.tenByTen.makeMove(6); //yellow 3

    this.tenByTen.makeMove(5); //red 4
    this.tenByTen.makeMove(4); //yellow 4
    this.tenByTen.makeMove(6); //red 5
    this.tenByTen.makeMove(7); //yellow 5
    this.tenByTen.makeMove(3); //red 6
    this.tenByTen.makeMove(3); //yellow 6
    this.tenByTen.makeMove(6); //red 7
    this.tenByTen.makeMove(3); //yellow 7
    assertEquals(Player.YELLOW, this.tenByTen.getWinner());


    //create a full board with no winner
    this.fiveByFive.resetBoard();
    this.fiveByFive.makeMove(2); //red 1
    this.fiveByFive.makeMove(1); //yellow 1

    this.fiveByFive.makeMove(3); //red 2
    this.fiveByFive.makeMove(2); //yellow 2

    this.fiveByFive.makeMove(1); //red 3
    this.fiveByFive.makeMove(3); //yellow 3

    this.fiveByFive.makeMove(3); //red 4
    this.fiveByFive.makeMove(0); //yellow 4

    this.fiveByFive.makeMove(4); //red 5
    this.fiveByFive.makeMove(0); //yellow 5

    this.fiveByFive.makeMove(0); //red 6
    this.fiveByFive.makeMove(4); //yellow 6

    this.fiveByFive.makeMove(1); //red 7
    this.fiveByFive.makeMove(2); //yellow 7

    this.fiveByFive.makeMove(2); //red 8
    this.fiveByFive.makeMove(1); //yellow 8

    this.fiveByFive.makeMove(4); //red 9
    this.fiveByFive.makeMove(0); //yellow 9

    this.fiveByFive.makeMove(0); //red 10
    this.fiveByFive.makeMove(3); //yellow 10

    this.fiveByFive.makeMove(4); // red 11
    this.fiveByFive.makeMove(1); // yellow 11


    this.fiveByFive.makeMove(2); // red 12
    this.fiveByFive.makeMove(3); // yellow 12

    this.fiveByFive.makeMove(4); // red 13

    //check if there is no winner
    assertNull(this.fiveByFive.getWinner());
  }

  /**
   * Test the resetBoard method.
   */
  @Test
  public void resetBoard() {
    this.fiveByFive.initializeBoard();
    this.tenByTen.initializeBoard();
    this.eightByNine.initializeBoard();
    //check all cells are empty after reset
    this.fiveByFive.resetBoard();
    this.tenByTen.resetBoard();
    this.eightByNine.resetBoard();
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        assertNull(this.fiveByFive.getBoardState()[i][j]);
      }
    }

    assertNull(this.fiveByFive.getWinner());
    assertEquals(Player.RED, this.fiveByFive.getTurn());

    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 10; j++) {
        assertNull(this.tenByTen.getBoardState()[i][j]);
      }
    }
    assertNull(this.tenByTen.getWinner());
    assertEquals(Player.RED, this.tenByTen.getTurn());


    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 9; j++) {
        assertNull(this.eightByNine.getBoardState()[i][j]);
      }
    }
    assertNull(this.eightByNine.getWinner());
    assertEquals(Player.RED, this.eightByNine.getTurn());
  }

  /**
   * Test the getBoardState method.
   */
  @Test
  public void getBoardState() {
    //test the state of the empty board that is just initialized
    this.fiveByFive.initializeBoard();
    Player[][] emptyBoard = new Player[5][5];
    assertEquals(emptyBoard, this.fiveByFive.getBoardState());

    this.fiveByFive.makeMove(0); //red
    this.fiveByFive.makeMove(0); //yellow
    this.fiveByFive.makeMove(1); //red
    this.fiveByFive.makeMove(1); //yellow
    this.fiveByFive.makeMove(2); //red
    this.fiveByFive.makeMove(2); //yellow
    this.fiveByFive.makeMove(3); //red

    //red should win
    //check the state of the board, red has one row of 4 in the bottom,
    //yellow has one row of 3 on top of red
    Player[][] board = new Player[5][5];
    board[4][0] = Player.RED;
    board[4][1] = Player.RED;
    board[4][2] = Player.RED;
    board[4][3] = Player.RED;
    board[3][0] = Player.YELLOW;
    board[3][1] = Player.YELLOW;
    board[3][2] = Player.YELLOW;
    assertEquals(board, this.fiveByFive.getBoardState());
  }

  /**
   * Test the getRows method.
   */
  @Test
  public void testToString() {
    this.fiveByFive.initializeBoard();

    // test the empty board representation
    String expected = "null, null, null, null, null\n"
        + "null, null, null, null, null\n"
        + "null, null, null, null, null\n"
        + "null, null, null, null, null\n"
        + "null, null, null, null, null\n";
    assertEquals(expected, this.fiveByFive.toString());

    // test the board representation after some moves
    this.fiveByFive.makeMove(0); //red
    this.fiveByFive.makeMove(0); //yellow
    String expected2 = "null, null, null, null, null\n"
        + "null, null, null, null, null\n"
        + "null, null, null, null, null\n"
        + "YELLOW, null, null, null, null\n"
        + "RED, null, null, null, null\n";

    assertEquals(expected2, this.fiveByFive.toString());
    this.fiveByFive.resetBoard();

    // test when there is a winner
    this.fiveByFive.makeMove(0); //red
    this.fiveByFive.makeMove(0); //yellow
    this.fiveByFive.makeMove(1); //red
    this.fiveByFive.makeMove(1); //yellow
    this.fiveByFive.makeMove(2); //red
    this.fiveByFive.makeMove(2); //yellow
    this.fiveByFive.makeMove(3); //red

    String expected3 = "null, null, null, null, null\n"
        + "null, null, null, null, null\n"
        + "null, null, null, null, null\n"
        + "YELLOW, YELLOW, YELLOW, null, null\n"
        + "RED, RED, RED, RED, null\n";
    assertEquals(expected3, this.fiveByFive.toString());

    //test when there is no winner and the board is full
    this.fiveByFive.resetBoard();
    this.fiveByFive.makeMove(2); //red 1
    this.fiveByFive.makeMove(1); //yellow 1

    this.fiveByFive.makeMove(3); //red 2
    this.fiveByFive.makeMove(2); //yellow 2

    this.fiveByFive.makeMove(1); //red 3
    this.fiveByFive.makeMove(3); //yellow 3

    this.fiveByFive.makeMove(3); //red 4
    this.fiveByFive.makeMove(0); //yellow 4

    this.fiveByFive.makeMove(4); //red 5
    this.fiveByFive.makeMove(0); //yellow 5

    this.fiveByFive.makeMove(0); //red 6
    this.fiveByFive.makeMove(4); //yellow 6

    this.fiveByFive.makeMove(1); //red 7
    this.fiveByFive.makeMove(2); //yellow 7

    this.fiveByFive.makeMove(2); //red 8
    this.fiveByFive.makeMove(1); //yellow 8

    this.fiveByFive.makeMove(4); //red 9
    this.fiveByFive.makeMove(0); //yellow 9

    this.fiveByFive.makeMove(0); //red 10
    this.fiveByFive.makeMove(3); //yellow 10

    this.fiveByFive.makeMove(4); // red 11
    this.fiveByFive.makeMove(1); // yellow 11


    this.fiveByFive.makeMove(2); // red 12
    this.fiveByFive.makeMove(3); // yellow 12

    this.fiveByFive.makeMove(4); // red 13

    String expected4 = "RED, YELLOW, RED, YELLOW, RED\n"
        + "YELLOW, YELLOW, RED, YELLOW, RED\n"
        + "RED, RED, YELLOW, RED, RED\n"
        + "YELLOW, RED, YELLOW, YELLOW, YELLOW\n"
        + "YELLOW, YELLOW, RED, RED, RED\n";
    assertEquals(expected4, this.fiveByFive.toString());

  }
}