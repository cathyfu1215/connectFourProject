import connect.ConnectFourController;
import connect.ConnectFourModel;
import connect.ConnectFourModelImpl;
import connect.SwingConnectFourController;
import connect.SwingConnectFourView;
import java.io.IOException;

/**
 * Run a Connect 4 game interactively on the console. You can make the number of rows and columns
 * configurable by passing them as command-line arguments. It is also OK to hard-code the number of
 * rows and columns to 6 and 7, respectively.
 */
public class Main {
  /**
   * Run a Connect 4 game interactively on the console. Rows = 6, Columns = 7.
   *
   * @param args not used
   */
  public static void main(String[] args) throws IOException {
    ConnectFourModel model = new ConnectFourModelImpl(6, 7);
    SwingConnectFourView view = new SwingConnectFourView("Connect 4");
    ConnectFourController controller = new SwingConnectFourController(model, view);
    try {
      controller.playGame();
    } catch (IOException e) {
      System.out.println("IO Error");
    }
  }
}