import java.util.ArrayList;
import javafx.application.Application;
import javafx.stage.Stage;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class GameTest extends Application {

    private Stage stage;

    @Test
    public void initGUI() {
        launch();
    }

    @Override
    public void start(Stage stage) {
        //Shows the main Window
        stage.setTitle("Checkers 100 - Testing");
        stage.show();
        this.stage = stage;

        //Execute all Tests
        /*
        System.out.println("testCheckOptions");
        testCheckOptions();

        System.out.println("testJumpChecker");
        testJumpChecker();
        */

        System.out.println("testJumpKing");
        testJumpKing();
    }

    public void testCheckOptions() {
        //Set pieces in special order to test checkers jump-logic
        ArrayList<Integer> black = new ArrayList<>();
        black.add(22);

        ArrayList<Integer> white = new ArrayList<>();
        white.add(31);

        Game testGame = new Game(stage);
        testGame.createPlayers(black, white);

        //Testing black Player
        testGame.getBlackPlayer().checkOptions();
        System.out.println("Black1: " + testGame.getBlackPlayer().getPieces().get(0).getOptions());
        assertEquals(testGame.getBlackPlayer().getPieces().get(0).getOptions().size(), 1);
        assertEquals(testGame.getBlackPlayer().getPieces().get(0).getOptions().get(0).size(), 2);
        assertEquals(testGame.getBlackPlayer().getPieces().get(0).getOptions().get(0).get(1), 40);

        //Testing white Player
        testGame.getWhitePlayer().checkOptions();
        System.out.println("White1: " + testGame.getWhitePlayer().getPieces().get(0).getOptions());
        assertEquals(testGame.getWhitePlayer().getPieces().get(0).getOptions().size(), 1);
        assertEquals(testGame.getWhitePlayer().getPieces().get(0).getOptions().get(0).size(), 2);
        assertEquals(testGame.getWhitePlayer().getPieces().get(0).getOptions().get(0).get(1), 13);
    }

    public void testJumpChecker() {
        //Set pieces in special order to test checkers jump-logic
        ArrayList<Integer> black = new ArrayList<>();
        black.add(10);
        black.add(19);

        ArrayList<Integer> white = new ArrayList<>();
        white.add(20);
        white.add(23);
        white.add(26);
        white.add(28);
        white.add(41);
        white.add(48);

        Game testGame = new Game(stage);
        testGame.createPlayers(black, white);

        //Testing black Player
        testGame.getBlackPlayer().checkOptions();
    }

    public void testJumpKing() {
        //Set pieces in special order to test kings jump-logic
        ArrayList<Integer> black = new ArrayList<>();
        black.add(44);
        black.add(86);
        black.add(73);

        ArrayList<Integer> white = new ArrayList<>();
        white.add(11);

        Game testGame = new Game(stage);
        testGame.createPlayers(black, white);

        //Testing black Player
        testGame.getBlackPlayer().checkOptions();
    }

}
