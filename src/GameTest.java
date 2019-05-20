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
        System.out.println("testJumpChecker1");
        testJumpChecker1();
        /*
        System.out.println("testJumpChecker2");
        testJumpChecker2();
        System.out.println("testJumpChecker3");
        testJumpChecker3();
        */
        System.out.println("testJumpChecker4");
        testJumpChecker4();
    }

    public void testJumpChecker1() {
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

    public void testJumpChecker2() {
        //Set pieces in special order to test checkers jump-logic
        ArrayList<Integer> black = new ArrayList<>();
        black.add(11);
        black.add(53);
        black.add(99);

        ArrayList<Integer> white = new ArrayList<>();
        white.add(22);
        white.add(26);
        white.add(42);
        white.add(44);
        white.add(62);
        white.add(64);

        Game testGame = new Game(stage);
        testGame.createPlayers(black, white);

        //Testing black Player
        testGame.getBlackPlayer().checkOptions();
        //System.out.println(testGame.getBlackPlayer().getPieces().get(0).getOptions());
        //System.out.println(testGame.getBlackPlayer().getPieces().get(1).getOptions());
    }

    public void testJumpChecker3() {
        //Set pieces in special order to test checkers jump-logic
        ArrayList<Integer> black = new ArrayList<>();
        black.add(13);
        black.add(15);
        black.add(37);
        black.add(39);

        ArrayList<Integer> white = new ArrayList<>();
        white.add(48);

        Game testGame = new Game(stage);
        testGame.createPlayers(black, white);

        //Testing white Player
        testGame.getWhitePlayer().checkOptions();
        System.out.println(testGame.getWhitePlayer().getPieces().get(0).getOptions());
    }

    public void testJumpChecker4() {
        //Set pieces in special order to test checkers jump-logic
        ArrayList<Integer> black = new ArrayList<>();
        black.add(0);
        black.add(9);

        ArrayList<Integer> white = new ArrayList<>();
        white.add(11);
        white.add(13);
        white.add(16);
        white.add(18);
        white.add(31);
        white.add(38);

        Game testGame = new Game(stage);
        testGame.createPlayers(black, white);

        //Testing black Player
        testGame.getBlackPlayer().checkOptions();
    }

}
