
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;


public class StartScreen extends Application {

    private int sizeWindow;


    public StartScreen(){
        int sizeWindow = 300;

    }

    @Override
    public void start(Stage stage) throws Exception{

        // Stellt ein GridPane bereit, auf dem die Elemente verteilt werden können

        final GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(5, 5, 5, 5));
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        // Bereitet Alle Elemente für den Start Bildschirm vor

        final Button onePlayerButton = new Button("One Player Game");
        final Button twoPlayerButton = new Button("Two Player Game");
        final Button exitButton = new Button("Exit Game");

        // Zuordnung der Elemente zum Grid (Node oder Element, x-position, y-position)

        gridPane.add(onePlayerButton, 0, 0);
        gridPane.add(twoPlayerButton, 1, 0);
        gridPane.add(exitButton, 1, 2);

        onePlayerButton.setOnAction((event) -> {

            // Button was Clicked, Starts Game

            Game singlePlayerGame = new Game();
            Scene newScene = singlePlayerGame.buildScene();
            showWindow(stage, newScene);
        });

        twoPlayerButton.setOnAction((event) -> {

            // Button was Clicked, Starts Game

            Game singlePlayerGame = new Game();
            Scene newScene = singlePlayerGame.buildScene();
            showWindow(stage, newScene);
        });

        exitButton.setOnAction((event) -> {

            // Closes Window on Click

            Platform.exit();
        });

        Scene scene = new Scene(gridPane, sizeWindow, sizeWindow);
        showWindow(stage, scene);

    }

    public static void buildWindow() {
        launch();
    }

    public static void showWindow(Stage stage, Scene scene) {

        // Shows Scene on Stage

        stage.setTitle("Checkers");
        stage.setScene(scene);
        stage.show();

    }


}


