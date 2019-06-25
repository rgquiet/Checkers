
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;


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

        Scene scene = new Scene(gridPane, 240, 100);
        this.scene = scene;
        showWindow();

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
        final Button btnnewgame = new Button("New Game");
        final Label lblwinner = new Label("Sieger: " + winner);


        borderPane.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5);");
        borderPane.setCenter(lblwinner);
        borderPane.setBottom(btnnewgame);




        btnnewgame.setOnAction((event) -> {

            // Closes Window on Click

            launch();

        });

        Scene scene = new Scene(borderPane, 240, 100);
        this.scene = scene;
        return scene;
    }

    public Scene vsPcScene(Stage stage) throws Exception {

        // Stellt ein BorderPane bereit, auf dem die Elemente verteilt werden können

        final BorderPane borderPane = new BorderPane();
        final HBox hboxLeft = new HBox();
        final HBox hboxTop = difficultyText();
        final VBox vboxCenter = difficultyRadios();
        final HBox hboxBot = new HBox();




        // Bereitet Alle Elemente für den Start Bildschirm vor

        final Button startButton = new Button("Start Game");
        final Button exitButton = new Button("Exit Game");


        // Zuordnung der Elemente zum Grid (Node oder Element)

        hboxBot.getChildren().addAll(startButton, exitButton);
        borderPane.setTop(hboxTop);
        borderPane.setCenter(vboxCenter);
        borderPane.setBottom(hboxBot);

        startButton.setOnAction((event) -> {

            // Closes Window on Click
            Platform.exit();
        });

        exitButton.setOnAction((event) -> {

            // Closes Window on Click
            Platform.exit();
        });

        Scene scene = new Scene(borderPane, 240, 100);
        return scene;

    }

    private HBox difficultyText() {
        HBox hbox = new HBox();

        final Label descriptionLabel = new Label("Choose your difficulty: ");

        descriptionLabel.setStyle("-fx-padding: 10 10 10 10;");

        hbox.getChildren().add(descriptionLabel);

        return hbox;

    }

    private VBox difficultyRadios() {

        VBox vbox = new VBox();

        final ToggleGroup group = new ToggleGroup();

        RadioButton rb1 = new RadioButton("Simple Algorithm");
        rb1.setToggleGroup(group);
        rb1.setSelected(true);
        RadioButton rb2 = new RadioButton("Genetic Algorithm");
        rb2.setToggleGroup(group);
        RadioButton rb3 = new RadioButton("QLearning");
        rb3.setToggleGroup(group);

        vbox.getChildren().addAll(rb1, rb2, rb3);

        return vbox;
    }


}


