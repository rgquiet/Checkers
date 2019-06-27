package ftoop_checkers_guedel;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUI extends Application {

    static Scene scene;
    static Stage stage;
    static Game activegame;
    static private boolean tomenu;

    @Override
    public void start(Stage stage) {
        //Shows the main Window
        stage.setTitle("Checkers 100");
        stage.show();

        this.stage = stage;
        stage.setResizable(false);

        Scene scene = mainScreen();
        this.scene = scene;
        showWindow();



    }

    public static void main(final String[] args) {
        launch();
    }

    public static Scene getScene(){
        return scene;
    }

    public static void goToMenu(){
        scene = mainScreen();
        showWindow();
    }

    public static void showWindow() {

        // Shows Scene on Stage
        if(activegame != null && !tomenu){
            scene = activegame.getScene();
        }
        stage.setTitle("Checkers 100");
        stage.setScene(scene);
        stage.show();

    }

    public static void startGame(){
        //Create new Game
        tomenu = false;
        Game game = new Game(stage);
        activegame = game;
        ArrayList<Integer> black = new ArrayList<>();
        ArrayList<Integer> white = new ArrayList<>();
        game.createPlayers(black, white);
        game.nextTurn();
        scene = game.getScene();
        showWindow();
    }

    public static Scene mainScreen(){


        // Stellt ein GridPane bereit, auf dem die Elemente verteilt werden können

        final BorderPane borderPane = new BorderPane();

        // Bereitet Alle Elemente für den Start Bildschirm vor

        final VBox menuBox = new VBox();
        final VBox backgroundBox = new VBox();
        final Button onePlayerButton = new Button("One Player Game");
        final Button twoPlayerButton = new Button("Two Player Game");
        final Button exitButton = new Button("Exit Game");

        // Platziert die Nodes in den entsprechenden Parents

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
                scene = winScreen("Black Player");
            } catch (Exception e) {
                e.printStackTrace();
            }
            showWindow();
        });

        twoPlayerButton.setOnAction((event) -> {

            // Button was Clicked, Starts Game

            startGame();
            showWindow();
        });

        exitButton.setOnAction((event) -> {

            // Closes Window on Click

            Platform.exit();
        });

        Scene scene = new Scene(borderPane, 500, 500);
        return scene;

    }


    public static Scene winScreen(String winner) throws Exception {

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

        btnnewgame.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                tomenu = true;
                goToMenu();
            }
        });

        btnexitgame.setOnAction((event) -> {

            // Closes Window on Click

            Platform.exit();
        });

        Scene scene = new Scene(borderPane, 500, 500);
        return scene;
    }

}
