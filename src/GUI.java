/**
 * https://www.dame100-korbach.de/spielregeln/
 * https://www.tutorialspoint.com/javafx/javafx_application.htm
 * https://stackoverflow.com/questions/32892646/adding-borders-to-gridpane-javafx
 */

import javafx.application.Application;
import javafx.stage.Stage;

public class GUI extends Application {

    @Override
    public void start(Stage stage) {
        //Shows the main Window
        stage.setTitle("Checkers 100");
        stage.show();

        //wip: Create Start Menu --> Insert Code from Class StartScreen here
        Game game = new Game(stage);
        game.nextTurn();
    }

    public static void main(final String[] args) {
        launch();
    }

}
