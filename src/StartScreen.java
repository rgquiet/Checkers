
import java.util.ArrayList;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

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

        Scene scene = new Scene(gridPane, sizeWindow, sizeWindow);
        stage.setTitle("Checkers");
        stage.setScene(scene);
        stage.show();

    }

    public static void buildWindow() {
        launch();
    }
}


