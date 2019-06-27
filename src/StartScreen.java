
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Scene;


public class StartScreen extends Application {

    static Scene scene;
    static Stage stage;

    public static void buildWindow() {
        launch();
    }

    public void setScene(Scene scene){
        this.scene = scene;
    }

    @Override
    public void start(Stage stage) throws Exception {

        this.stage = stage;


        Scene scene = mainScreen();
        this.scene = scene;
        showWindow();

    }

    public Scene mainScreen(){

        // Stellt ein GridPane bereit, auf dem die Elemente verteilt werden können

        final BorderPane borderPane = new BorderPane();
        final VBox menuBox = new VBox();
        final VBox backgroundBox = new VBox();

        // Bereitet Alle Elemente für den Start Bildschirm vor

        final Button onePlayerButton = new Button("One Player Game");
        final Button twoPlayerButton = new Button("Two Player Game");
        final Button exitButton = new Button("Exit Game");

        menuBox.getChildren().addAll(onePlayerButton,twoPlayerButton,exitButton);
        menuBox.setSpacing(30);
        menuBox.setPadding(new Insets(20,20,20,20));
        menuBox.setStyle("-fx-background-color: #12197a");
        backgroundBox.setStyle("-fx-background-image: url(/background.png)");
        borderPane.setLeft(menuBox);
        borderPane.setCenter(backgroundBox);

        onePlayerButton.setOnAction((event) -> {

            // Button was Clicked, Starts Game

            try {
                this.scene = winScreen(stage, "Black Player");
            } catch (Exception e) {
                e.printStackTrace();
            }
            showWindow();
        });

        twoPlayerButton.setOnAction((event) -> {

            // Button was Clicked, Starts Game

            Game singlePlayerGame = new Game();
            this.scene = singlePlayerGame.buildScene();
            showWindow();
        });

        exitButton.setOnAction((event) -> {

            // Closes Window on Click

            Platform.exit();
        });

        Scene scene = new Scene(borderPane, 500, 500);
        return scene;

    }

    public static void showWindow() {

        // Shows Scene on Stage

        stage.setTitle("Checkers");
        stage.setScene(scene);
        stage.show();

    }

    public Scene winScreen(Stage stage, String winner) throws Exception {

        // Stellt ein GridPane bereit, auf dem die Elemente verteilt werden können

        final BorderPane borderPane = new BorderPane();
        final HBox winBox = new HBox();
        final HBox menuBox = new HBox();
        final Button btnnewgame = new Button("New Game");
        final Button btnexitgame = new Button("Exit Game");
        final Label lblwinner = new Label("Sieger: " + winner);

        winBox.getChildren().add(lblwinner);
        menuBox.getChildren().addAll(btnnewgame, btnexitgame);
        winBox.setPadding(new Insets(50,5,5,5));
        menuBox.setPadding(new Insets(5,5,50,5));
        winBox.setAlignment(Pos.CENTER);
        menuBox.setAlignment(Pos.CENTER);
        borderPane.setStyle("-fx-background-color: rgba(255, 255, 255);");
        borderPane.setCenter(winBox);
        borderPane.setBottom(menuBox);




        btnnewgame.setOnAction((event) -> {

            // Closes Window on Click
            Platform.runLater(() -> {
                scene = mainScreen();
                showWindow();
            });
        });

        btnexitgame.setOnAction((event) -> {

            // Closes Window on Click

            Platform.exit();
        });

        Scene scene = new Scene(borderPane, 500, 500);
        this.scene = scene;
        return scene;
    }


}


