import java.util.ArrayList;
import javafx.application.Application;
import javafx.stage.Stage;

public class GUI extends Application {

    @Override
    public void start(Stage stage) {
        //Shows the main Window
        stage.setTitle("Checkers 100");
        stage.show();

        //wip: Insert Code from Class StartScreen here
        System.out.println("Refactor Pablo's Shit");

        //Create new Game
        Game game = new Game(stage);
        ArrayList<Integer> black = new ArrayList<>();
        ArrayList<Integer> white = new ArrayList<>();
        game.createPlayers(black, white);
        game.nextTurn();
    }

    public static void main(final String[] args) {
        launch();
    }

}
