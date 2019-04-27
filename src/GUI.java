import java.util.ArrayList;
import javafx.application.Application;
import javafx.stage.Stage;

//Merged Rob Branch to Master

public class GUI extends Application {

    @Override
    public void start(Stage stage) {
        //Shows the main Window
        stage.setTitle("Checkers 100");
        stage.show();

        //wip: Create Start Menu --> Insert Code from Class StartScreen here

        Game game = new Game(stage);
        ArrayList<Integer> black = new ArrayList<>();
        ArrayList<Integer> white = new ArrayList<>();
        game.createPlayers(black, white);
    }

    public static void main(final String[] args) {
        launch();
    }

}
